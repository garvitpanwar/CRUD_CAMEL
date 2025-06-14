//package com.garvit.provider.model;
//
//import jakarta.persistence.Embeddable;
//
//@Embeddable
//public class SLA {
//    private long deliveryTimeMs;
//    private double uptimePercent;
//
//    public long getDeliveryTimeMs() {
//        return deliveryTimeMs;
//    }
//
//    public void setDeliveryTimeMs(long deliveryTimeMs) {
//        this.deliveryTimeMs = deliveryTimeMs;
//    }
//
//    public double getUptimePercent() {
//        return uptimePercent;
//    }
//
//    public void setUptimePercent(double uptimePercent) {
//        this.uptimePercent = uptimePercent;
//    }
//}
package com.garvit.provider.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sla")
public class SLA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_time_ms", nullable = false)
    private long deliveryTimeMs;

    @Column(name = "uptime_percent", nullable = false)
    private double uptimePercent;

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDeliveryTimeMs() {
        return deliveryTimeMs;
    }

    public void setDeliveryTimeMs(long deliveryTimeMs) {
        this.deliveryTimeMs = deliveryTimeMs;
    }

    public double getUptimePercent() {
        return uptimePercent;
    }

    public void setUptimePercent(double uptimePercent) {
        this.uptimePercent = uptimePercent;
    }
}
