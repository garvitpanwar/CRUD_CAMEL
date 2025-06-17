//package com.garvit.provider.model;
//
//import jakarta.persistence.*;
//import java.util.List;
//
//@Entity
//public class Provider {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String partnerId;
//    private String name;
//    private String contactInfo;
//
//    @ElementCollection
//    private List<String> supportedChannels;
//
//    @Embedded
//    private SLA sla;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
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
//    public SLA getSla() {
//        return sla;
//    }
//
//    public void setSla(SLA sla) {
//        this.sla = sla;
//    }
//}
package com.garvit.provider.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partner_id", nullable = false)
    private String partnerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_info")
    private String contactInfo;

    // PostgreSQL-specific: store array of strings as text[]
    @Column(name = "supported_channels", columnDefinition = "text[]")
    private String[] supportedChannels;

    // FIXED: Changed cascade and fetch strategy
    // Provider.java entity mein ye changes karo
    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "sla_id")
    private SLA sla;

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public SLA getSla() {
        return sla;
    }

    public void setSla(SLA sla) {
        this.sla = sla;
    }
}