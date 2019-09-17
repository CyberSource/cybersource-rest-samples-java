/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author simajumd
 */
//Loads JSON Object from file into instances of RoadInfo class
public class Utility {
    public static List<RoadInfo> getRoads(String filepath){
        List<RoadInfo> roads = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        try {
            FileReader reader = new FileReader(filepath);
            JsonObject spec = jsonParser.parse(reader).getAsJsonObject();
            JsonArray jsonRoads = spec.get("Execution Order").getAsJsonArray();
            
            for(JsonElement jsonRoad : jsonRoads){
                JsonObject jsonRoadObj = jsonRoad.getAsJsonObject();
                RoadInfo road = new RoadInfo();
                road.setName(jsonRoadObj.get("uniqueName").getAsString());
                road.setRequestClassName(jsonRoadObj.get("sampleClassNames").getAsJsonObject().get("java").getAsString());
                JsonElement assertions = jsonRoadObj.get("Assertions");
                if(!(assertions.toString().equals("{}"))){
                    road.setIsAssert(true);
                    
                    road.setHttpStatus(jsonRoadObj.get("Assertions").getAsJsonObject().get("httpStatus").getAsString());
                    JsonArray jsonExpectedValues = jsonRoadObj.get("Assertions").getAsJsonObject().getAsJsonArray("expectedValues");
                    Map<String,String> expectedValues = new HashMap<>();
                    for (JsonElement jsonExpectedValue : jsonExpectedValues) {
                        expectedValues.put(jsonExpectedValue.getAsJsonObject().get("field").getAsString(), 
                            jsonExpectedValue.getAsJsonObject().get("value").getAsString());
                    }
                    road.setExpectedValues(expectedValues);
                
                    JsonArray jsonRequiredFields = jsonRoadObj.get("Assertions").getAsJsonObject().getAsJsonArray("requiredFields");
                    List<String> requiredFields = new ArrayList<>();
                    for (JsonElement jsonRequiredField : jsonRequiredFields) {
                        requiredFields.add(jsonRequiredField.getAsString());
                    }
                    road.setRequiredFields(requiredFields);
                }
                else{
                    road.setIsAssert(false);
                }
                road.setDependentClass(jsonRoadObj.get("prerequisiteRoad").getAsString());
                JsonArray jsonResponseFields = jsonRoadObj.get("storedResponseFields").getAsJsonArray();
                List<String> responseFields = new ArrayList<>();
                for(JsonElement jsonResponseField : jsonResponseFields){
                    responseFields.add(jsonResponseField.getAsString());
                }
                road.setResponseFields(responseFields);
                JsonArray jsonDependentFields = jsonRoadObj.get("dependentFieldMapping").getAsJsonArray();
                List<String> dependentFields = new ArrayList<>();
                for(JsonElement jsonDependentField : jsonDependentFields){
                    dependentFields.add(jsonDependentField.getAsString());
                }
                road.setDependentFields(dependentFields);
                roads.add(road);
            }
            return roads;
        } catch (JsonIOException | JsonSyntaxException ex) {
//            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("JSON Exception");
            return null;
        } catch (FileNotFoundException ex){
            System.out.println("File Not Found");
            return null;
        }

    }
}
