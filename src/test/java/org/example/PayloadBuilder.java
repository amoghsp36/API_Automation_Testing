package org.example;

import lombok.Builder;
import org.json.simple.JSONObject;

//@Builder(toBuilder = true)    //reducing hardcoded values for json object properties/attributes
public class PayloadBuilder {     //manual builder pattern
    private JSONObject payload;
    private JSONObject anotherPayload;

    public PayloadBuilder(){
        payload=new JSONObject();
    }
    public PayloadBuilder withFirstName(String firstname){
        payload.put("firstname",firstname);
        return this;
    }
    public PayloadBuilder withLastName(String lastName){
        payload.put("lastname",lastName);
        return this;
    }
    public PayloadBuilder withId(int id){
        payload.put("id",id);
        return this;
    }
    public PayloadBuilder withSubjectId(int subjectId){
        payload.put("subjectid",subjectId);
        return this;
    }
    public PayloadBuilder withSubData(JSONObject req){
        payload.put("anotherPayload",req);
        return this;
    }
    public JSONObject build(){
        return payload;
    }
}
