package com.example.coffee.model.response;

public class DeviceDetailResponse
{
    private Integer operationStatus;
    private String serialNumber;
    private String name;
    private Long id;
    private Long orgId;
    private Integer status;

    public DeviceDetailResponse(Integer operationStatus, String serialNumber, String name, Long id, Long orgId, Integer status) {
        this.operationStatus = operationStatus;
        this.serialNumber = serialNumber;
        this.name = name;
        this.id = id;
        this.orgId = orgId;
        this.status = status;
    }

    public Integer getOperationStatus() {
        return this.operationStatus;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Integer getStatus() {
        return this.status;
    }
}
