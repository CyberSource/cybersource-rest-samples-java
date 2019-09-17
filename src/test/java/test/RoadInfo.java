/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.List;
import java.util.Map;

/**
 *
 * @author simajumd
 */
//Class for the Road Variable
public class RoadInfo {
    private String name;
    private String requestClassName;
    private List<String> requiredFields;
    private Map<String,String> expectedValues;
    private String httpStatus;
    private String dependentClass;
    private List<String> responseFields;
    private List<String> dependentFields;
    private boolean isAssert;
    
    public RoadInfo() {
    }

    public String getRequestClassName() {
        return requestClassName;
    }

    public Map<String, String> getExpectedValues() {
        return expectedValues;
    }

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getName() {
        return name;
    }    
    
    public List<String> getResponseFields(){
        return responseFields;
    }
    
    public List<String> getDependentFields(){
        return dependentFields;
    }
    
    public boolean getIsAssert(){
        return isAssert;
    }
    
    public String getDependentClass(){
        return dependentClass;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
    }

    public void setExpectedValues(Map<String, String> expectedValues) {
        this.expectedValues = expectedValues;
    }

    public void setRequiredFields(List<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public void setResponseFields(List<String> responseFields){
        this.responseFields = responseFields;
    }
    
    public void setDependentFields(List<String> dependentFields){
        this.dependentFields = dependentFields;
    }
    
    public void setIsAssert(boolean isAssert){
        this.isAssert = isAssert;
    }
    
    public void setDependentClass(String dependentClass){
        this.dependentClass = dependentClass;
    }
}
