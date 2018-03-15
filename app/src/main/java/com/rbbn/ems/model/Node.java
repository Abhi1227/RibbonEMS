package com.rbbn.ems.model;

/**
 * Created by nsoni on 3/12/2018.
 */

public class Node {
    private String name;
    private String type;
    private String ipAddress;
    private String version;
    private String status;
public Node(){}
    public Node(String name, String type, String ipAddress, String version, String status) {
        this.name = name;
        this.type = type;
        this.ipAddress = ipAddress;
        this.version = version;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
