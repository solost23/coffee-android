package com.example.coffee.model.response;

public class LoginResponse
{
    private Boolean success;
    private String code;
    private String message;
    private String data;

    public LoginResponse(Boolean success, String code, String message, String data)
    {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess()
    {
        return this.success;
    }

    public String getCode()
    {
        return this.code;
    }

    public String getMessage()
    {
        return this.message;
    }

    public String getData()
    {
        return this.data;
    }
}
