/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static test.Utility.getRoads;

/**
 *
 * @author simajumd
 */
@RunWith(Parameterized.class)
public class RoadRunner {
    private RoadInfo road;
    private static HashMap<String,String> fieldmap;
        public RoadRunner(RoadInfo road) {
        this.road = road;
    }

    @BeforeClass
    public static void setUpClass() {
        //Create a global map for fieldname and value
        fieldmap = new HashMap<>();
    }

    @Parameterized.Parameters
    public static Collection roads() {
        return getRoads("executor.json");
    }
    @Test
    public void RoadTest(){
        String reqClassName = road.getRequestClassName();
        String name = road.getName();
        boolean brokenDependent = true;
        Class requestClassType = null;
        try {
            requestClassType = Class.forName(reqClassName);
        } catch (ClassNotFoundException e1) {
            Assert.fail("Sample Code "+name+" Missing");
            return;
        }
        try {
            //Remove all field values of the current sample
            List<String> dependentFields = road.getDependentFields();
            String dependentClass = road.getDependentClass();
            road.getResponseFields().stream().filter((responseField) -> (fieldmap.containsKey(name+responseField)))
                    .forEachOrdered((responseField) -> {
                fieldmap.remove(name+responseField);
            });
            Object response = new Object();
            //If there are no dependent field, run sample code without variables
            if(dependentFields.isEmpty()){
                Method runMethod = requestClassType.getMethod("run");
                response = runMethod.invoke(null);
                //Broken Sample Code returns null object and the next line throws a Null Pointer Exception
                String responseString = response.toString();
            }
            //Otherwise pass the arguments in an array
            else{                
                Class[] classlist =  new Class[dependentFields.size()];
                String[] valuelist = new String[dependentFields.size()];
                for(int i = 0; i < dependentFields.size(); i++){
                    classlist[i] = String.class;
                }
                for(int i = 0; i < dependentFields.size(); i++){
                    if(fieldmap.containsKey(""+dependentClass+dependentFields.get(i))){
                        valuelist[i] = fieldmap.get(""+dependentClass+dependentFields.get(i));
                    }
                    else{
                        //Set the flag for anomaly in the previous dependent
                        brokenDependent = false;
                    }
                }
                if(brokenDependent){
                    Method runMethod = requestClassType.getMethod("run", classlist);
                    response = runMethod.invoke(null, (Object[]) valuelist);
                //Broken Sample Code returns null object and the next line throws a Null Pointer Exception 
                    String responseString = response.toString();
                }
            }
            if(brokenDependent){
                for(String responseField : road.getResponseFields()){
                    try{
                        fieldmap.put(name+responseField, getValueFromField(response, responseField).toString());
                    } catch(NullPointerException ne){
                    
                    }
                }
                //Find & Execute SampleCode
                //Get expecutedValues & Assert
                if(road.getIsAssert() == true){
                    for (String expectedValueField : road.getExpectedValues().keySet()) {
                        Assert.assertEquals(road.getExpectedValues().get(expectedValueField), getValueFromField(response, expectedValueField).toString());
                    }

            //Get requiredValues & Assert
                    for (String requiredField : road.getRequiredFields()) {
                        Assert.assertNotNull(getValueFromField(response, requiredField));
                    }
                }
            }
            else{
                Assert.fail("Dependent fields are missing");
            }
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IllegalStateException | NullPointerException e1) {
            // TODO Auto-generated catch block
            if(e1 instanceof NoSuchMethodException){
                Assert.fail("Sample Code "+name+" has missing dependent fields");
            }
            else{
                Assert.fail("Sample Code "+name+" Broken");
            }
            
        }

    }

    private static Map<Object, Class> callGetter(String getterFieldName, Class classType, Object response) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String getterName = "get" + getterFieldName.substring(0, 1).toUpperCase() + getterFieldName.substring(1);
        Method getter = classType.getMethod(getterName);
        Map output = new HashMap<>();
        output.put(getter.invoke(response), getter.invoke(response).getClass());
        return output;
    }
    //Recursively fetches value of a particular field from the response object
    private static Object getValueFromField(Object response, String xField) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String[] fields = xField.split("\\.");
        Object respField = response;
        Class respFieldClass = respField.getClass();
        for(String field : fields){
            Map<Object,Class> output = callGetter(field, respFieldClass, respField);
            respField = output.keySet().toArray()[0];
            respFieldClass = output.get(respField);
        }
        return respField;
    }
}
