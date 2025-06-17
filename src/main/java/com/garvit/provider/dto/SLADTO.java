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


//
//package com.garvit.provider.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//public class SLADTO {
//    @JsonIgnore
//    private Long id;  // Optional, useful if updating SLA later
//    private long deliveryTimeMs;
//    private double uptimePercent;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
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


package com.garvit.provider.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SLADTO {
    @JsonIgnore
    private Long id;  // Optional, useful if updating SLA later

    @NotNull(message = "Delivery time is required")
    @Positive(message = "Delivery time must be a positive number (milliseconds)")
    private long deliveryTimeMs;

    @NotNull(message = "Uptime percentage is required")
    @Positive(message = "Uptime percentage must be a positive number")
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