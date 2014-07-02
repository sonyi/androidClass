package com.example.empmgrdemo;

import java.util.Map;

public class RequestEntity {
     private String url;
     private Map<String,Object> params;
     
     public RequestEntity(String url) {
    	 this.url = url;
     }
     
     public RequestEntity(String url,Map<String,Object> params) {
    	 this.url = url;
    	 this.params = params;
     }
     
     public String getUrl() {
    	 return url;
     }
     
     public Map<String,Object> getParams() {
    	 return params;
     }
}
