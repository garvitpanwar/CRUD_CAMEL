//package com.garvit.provider.dto;
//
//public class SLADTO {
//    private long deliveryTimeMs;
//    private double uptimePercent;
//
//    // Getters and Setters
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



package com.garvit.provider.dto;
public class SLADTO {
    private Long id;  // Optional, useful if updating SLA later
    private long deliveryTimeMs;
    private double uptimePercent;

    // Getters and Setters
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
