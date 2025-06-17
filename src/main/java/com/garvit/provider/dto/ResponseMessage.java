//package com.garvit.provider.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.util.List;
//
//public class ResponseMessage {
//    @JsonProperty("message")
//    private String message;
//
//    public ResponseMessage(String validationFailed, List<String> list) {
//    }
//
//    public ResponseMessage(String message) {
//        this.message = message;
//    }
//
//    // Getters and setters...
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
package com.garvit.provider.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {
    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private List<String> details;

    // Constructors
    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}