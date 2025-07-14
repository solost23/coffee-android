package com.example.coffee.model.request;

public class LoginRequest
{
    private Data data;

    public static class Data
    {
        private String account;
        private String password;
        private String platform;
        private String serialNumber;

        public Data(String account, String password, String platform, String serialNumber)
        {
            this.account = account;
            this.password = password;
            this.platform =  platform;
            this.serialNumber = serialNumber;
        }

        public String getAccount()
        {
            return this.account;
        }

        public String password()
        {
            return this.password;
        }

        public String getPassword()
        {
            return this.platform;
        }

        public String getSerialNumber()
        {
            return this.serialNumber;
        }
    }

    public LoginRequest(Data data)
    {
        this.data = data;
    }

    public Data getData()
    {
        return this.data;
    }
}
