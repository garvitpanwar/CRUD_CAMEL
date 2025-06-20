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

//
//package com.garvit.provider.dto;
//
//
//public class ProviderDTO {
//
//    private String partnerId;
//    private String name;
//    private String contactInfo;
//    private String[] supportedChannels;
//    private SLADTO sla;
//
//    // Getters and Setters
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
//    public String[] getSupportedChannels() {
//        return supportedChannels;
//    }
//
//    public void setSupportedChannels(String[] supportedChannels) {
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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import java.util.List;

@ApplicationScoped
public class ProviderDTO {

    @NotBlank(message = "Partner ID cannot be blank")
    @Size(min = 3, max = 20, message = "Partner ID must be between 3-20 characters")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Partner ID must be alphanumeric")
    private String partnerId;


    @NotBlank(message = "Provider name cannot be blank")
    @Size(min = 2, max = 100, message = "Provider name must be between 2-100 characters")
    private String name;

    @NotBlank(message = "Contact info cannot be blank")
    @Email(message = "Contact info must be a valid email address")
    private String contactInfo;

    @NotNull(message = "Supported channels cannot be null")
    @Size(min = 1, message = "At least one channel must be specified")
    private String[] supportedChannels;

    @NotNull(message = "SLA details are required")
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

//package com.garvit.provider.dto;
//
//public class ProviderDTO {
//
//    private String partnerId;
//    private String name;
//    private String contactInfo;
//    private String[] supportedChannels;
//    private SLADTO sla;
//
//    // Getters and Setters
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
//    public String[] getSupportedChannels() {
//        return supportedChannels;
//    }
//
//    public void setSupportedChannels(String[] supportedChannels) {
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