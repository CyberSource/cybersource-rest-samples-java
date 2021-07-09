/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.Utility;

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

    @SuppressWarnings("rawtypes")
	@Parameterized.Parameters
    public static Collection roads() {
    	return Utility.getRoads("executor.json");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
    public void RoadTest() throws InterruptedException {
        String reqClassName = road.getRequestClassName();
        String name = road.getName();
        boolean brokenDependent = true;
        Class requestClassType = null;
        
        System.out.println("UNIQUE NAME : " + reqClassName);
        
        if (reqClassName.contains("RetrieveTransaction") || reqClassName.contains("DeleteInstrumentIdentifier") || reqClassName.contains("RetrieveAvailableReports")) {
        	Thread.sleep(20000);
        } 
        
        try {
            requestClassType = Class.forName(reqClassName);
        } catch (ClassNotFoundException e1) {
            Assert.fail("Sample Code " + name + " Missing");
            return;
        }
        try {
            //Remove all field values of the current sample
            List<String> dependentFields = road.getDependentFields();
            String dependentClass = road.getDependentClass();
            road.getResponseFields().stream().filter((responseField) -> (fieldmap.containsKey(name + responseField)))
                    		.forEachOrdered((responseField) -> {
						                fieldmap.remove(name + responseField);
						            });
            Object response = new Object();
            //If there are no dependent field, run sample code without variables
            if(dependentFields.isEmpty()) {
            	System.out.println("\n##### Running Sample for " + requestClassType.getName() + "#####\n");                
                
                if (reqClassName.contains("Token_Management")) {
                	Method runMethod = requestClassType.getMethod("run", String.class);
                    String[] valuelist = { "93B32398-AD51-4CC2-A682-EA3E93614EB1" };
                	response = runMethod.invoke(null, (Object[]) valuelist);
                } else if (reqClassName.contains("GetReportDefinition")) {
                	Method runMethod = requestClassType.getMethod("run", String.class);
                    String[] valuelist = { "TransactionRequestClass" };
                	response = runMethod.invoke(null, (Object[]) valuelist);
                } else {                
                	Method runMethod = requestClassType.getMethod("run");
                    response = runMethod.invoke(null);
                }
                
                
                System.out.println("\n##### Ending Sample for " + requestClassType.getName() + "#####\n");   
            }
            //Otherwise pass the arguments in an array
            else {                
                Class[] classlist =  new Class[dependentFields.size()];
                String[] valuelist = new String[dependentFields.size()];
                for(int i = 0; i < dependentFields.size(); i++) {
                    classlist[i] = String.class;
                }
                for(int i = 0; i < dependentFields.size(); i++) {
                    if(fieldmap.containsKey("" + dependentClass + dependentFields.get(i))) {
                        valuelist[i] = fieldmap.get("" + dependentClass + dependentFields.get(i));
                    }
                    else{
                        //Set the flag for anomaly in the previous dependent
                        brokenDependent = false;
                    }
                }
                if(brokenDependent) {
                    try {
                    	System.out.println("\n##### Running Sample for " + requestClassType.getName() + "#####\n");
	                    
	                    if (reqClassName.contains("Token_Management")) {
	                    	classlist = (Class[]) ArrayUtils.add(classlist, 0, String.class);
	                    	Method runMethod = requestClassType.getMethod("run", classlist);
	                    	valuelist = (String[]) ArrayUtils.add(valuelist, 0, "93B32398-AD51-4CC2-A682-EA3E93614EB1");
	                    	response = runMethod.invoke(null, (Object[]) valuelist);
	                    } else {
	                    	Method runMethod = requestClassType.getMethod("run", classlist);
		                    response = runMethod.invoke(null, (Object[]) valuelist);
	                    }	                    
	                    
	                    System.out.println("\n##### Ending Sample for " + requestClassType.getName() + "#####\n");  
                    } catch (Exception e) {
                    	System.out.println("Parameter List : " + Arrays.toString(classlist));
                    }
                }
            }
           // String responseCode = getValueFromField(response, "responseCode").toString();
           // Assert.assertEquals('2', responseCode.charAt(0));
            if(brokenDependent) {
                for(String responseField : road.getResponseFields()) {
                    try{
                        fieldmap.put(name + responseField, getValueFromField(response, responseField).toString());
                    } catch(NullPointerException ne) {
                    	Assert.fail("Required field missing from response payload");
                    }
                }
            }
            else{
                Assert.fail("Dependent fields are missing.");
            }
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IllegalStateException | NullPointerException e1) {
            // TODO Auto-generated catch block
        	System.out.println(e1.getMessage());
        	System.out.println(e1.getStackTrace());
            if(e1 instanceof NoSuchMethodException) {
                Assert.fail("Sample Code " + name + " has missing dependent fields.");
            }
            else{
                Assert.fail("Sample Code " + name + " broken.");
            }
            
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<Object, Class> callGetter(String getterFieldName, Class classType, Object response) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String getterName = "get" + getterFieldName.substring(0, 1).toUpperCase() + getterFieldName.substring(1);
        Method getter = classType.getMethod(getterName);
        Map output = new HashMap<>();
        output.put(getter.invoke(response), getter.invoke(response).getClass());
        return output;
    }
    
    //Recursively fetches value of a particular field from the response object
    @SuppressWarnings("rawtypes")
	private static Object getValueFromField(Object response, String xField) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	String[] splitFields = xField.split("\\.");
    	Object respField = response;
    	Class respFieldClass = respField.getClass();
        
    	for (String field : splitFields) {
    		if (field.contains("[")) {
            	String field1 = field.split("\\[")[0];
            	int field1Index = Integer.valueOf((field.split("\\[")[1]).split("\\]")[0]);
    			
    			Map<Object, Class> output = callGetter(field1, respFieldClass, respField);
    			respField = ((ArrayList)(output.keySet().toArray()[0])).get(field1Index);
                respFieldClass = respField.getClass();
    		} else {
    			Map<Object, Class> output = callGetter(field, respFieldClass, respField);
                respField = output.keySet().toArray()[0];
                respFieldClass = output.get(respField);
    		}
    	}

    	return respField;
    }
}
