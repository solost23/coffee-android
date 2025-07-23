package com.example.coffee.model.request;

public class DeviceOperationStatusUpdateRequest
{
    private Data data;

    public DeviceOperationStatusUpdateRequest(Data data)
    {
        this.data = data;
    }

    public Data getData()
    {
        return this.data;
    }

    public static class Data
    {
        private Long id;
        private Integer operationStatus;

        public Data(Long id, Integer operationStatus)
        {
            this.id = id;
            this.operationStatus = operationStatus;
        }

        public Long getId()
        {
            return this.id;
        }

        public Integer getOperationStatus()
        {
            return this.operationStatus;
        }
    }
}
