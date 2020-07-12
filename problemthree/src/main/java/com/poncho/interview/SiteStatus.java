package com.poncho.interview;

import java.io.Serializable;
import java.util.HashMap;

public class SiteStatus implements Serializable {

    private String webAddress;
    private String status;
    private HashMap<String, String> websitesStatus = new HashMap<String, String>();

    public HashMap<String, String> getWebsitesStatus() {
        return websitesStatus;
    }

    public void setWebsitesStatus(HashMap<String, String> websitesStatus) {
        this.websitesStatus = websitesStatus;
    }



    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
