package com.example.coffee.model.response;

public class ResultDTO<T>
{
    private Boolean success;
    private String code;
    private String message;
    private T data;

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

    public T getData()
    {
        return this.data;
    }
}
