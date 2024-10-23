package com.example.demo.exception;

public class RemoteAPICallException extends RuntimeException {

    private final int statusCode;
    private final String body;
    private final String url;

    public RemoteAPICallException(int statusCode, String body, String url) {
        this.statusCode = statusCode;
        this.body = body;
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage(){
        String str = "Remote Error. status:" + this.statusCode;
        if (null != url){
            str += ", url: " + url;
        }
        if (null != body){
            str += ", body: " + body;
        }
        return str;
    }
}
