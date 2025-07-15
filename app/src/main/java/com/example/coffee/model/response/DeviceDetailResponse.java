package com.example.coffee.model.response;

public class DeviceDetailResponse
{
    private Boolean success;
    private String code;
    private String message;
    private Data data;

    public static class Data
    {
        private Integer operationStatus;
        private String serialNumber;
        private String name;
        private Long id;
        private Long orgId;
        private Integer status;

        public Data(Integer operationStatus, String serialNumber, String name, Long id, Long orgId, Integer status)
        {
            this.operationStatus = operationStatus;
            this.serialNumber = serialNumber;
            this.name = name;
            this.id = id;
            this.orgId = orgId;
            this.status = status;
        }

        public Integer getOperationStatus()
        {
            return this.operationStatus;
        }

        public String getSerialNumber()
        {
            return this.serialNumber;
        }

        public String getName()
        {
            return this.name;
        }

        public Long getId()
        {
            return this.id;
        }

        public Long getOrgId()
        {
            return this.orgId;
        }

        public Integer getStatus()
        {
            return this.status;
        }
    }

    public DeviceDetailResponse(Boolean success, String code, String message, Data data)
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

    public Data getData()
    {
        return this.data;
    }
}
