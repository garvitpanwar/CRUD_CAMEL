//package com.garvit.provider.dto;
//
//import java.util.List;
//
//public class ProviderDTO {
//    private String partnerId;
//    private String name;
//    private String contactInfo;
//    private List<String> supportedChannels;
//    private SLADTO sla;
//
//    public String getPartnerId() {
//        return partnerId;
//    }
//
//    public void setPartnerId(String partnerId) {
//        this.partnerId = partnerId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getContactInfo() {
//        return contactInfo;
//    }
//
//    public void setContactInfo(String contactInfo) {
//        this.contactInfo = contactInfo;
//    }
//
//    public List<String> getSupportedChannels() {
//        return supportedChannels;
//    }
//
//    public void setSupportedChannels(List<String> supportedChannels) {
//        this.supportedChannels = supportedChannels;
//    }
//
//    public SLADTO getSla() {
//        return sla;
//    }
//
//    public void setSla(SLADTO sla) {
//        this.sla = sla;
//    }
//}


package com.garvit.provider.dto;

public class ProviderDTO {

    private String partnerId;
    private String name;
    private String contactInfo;
    private String[] supportedChannels;
    private SLADTO sla;

    // Getters and Setters
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String[] getSupportedChannels() {
        return supportedChannels;
    }

    public void setSupportedChannels(String[] supportedChannels) {
        this.supportedChannels = supportedChannels;
    }

    public SLADTO getSla() {
        return sla;
    }

    public void setSla(SLADTO sla) {
        this.sla = sla;
    }
}
