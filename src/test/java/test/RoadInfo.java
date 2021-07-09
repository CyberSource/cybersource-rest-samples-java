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
    private String httpStatus;
    private String dependentClass;
    private List<String> responseFields;
    private List<String> dependentFields;
    
    public RoadInfo() {
    	
    }

    public String getRequestClassName() {
        return requestClassName;
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
    
    public String getDependentClass(){
        return dependentClass;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
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
    
    public void setDependentClass(String dependentClass){
        this.dependentClass = dependentClass;
    }
}
