//package com.garvit.provider.mapper;
//
//import com.garvit.provider.dto.ProviderDTO;
//import com.garvit.provider.model.Provider;
//import com.garvit.provider.model.SLA;
//
//public class ProviderMapper {
//    public static Provider toEntity(ProviderDTO dto) {
//        Provider p = new Provider();
//        p.setPartnerId(dto.getPartnerId());
//        p.setName(dto.getName());
//        p.setContactInfo(dto.getContactInfo());
//        p.setSupportedChannels(dto.getSupportedChannels());
//
//        SLA sla = new SLA();
//        sla.setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
//        sla.setUptimePercent(dto.getSla().getUptimePercent());
//
//        p.setSla(sla);
//        return p;
//    }
//}


package com.garvit.provider.mapper;

import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.model.Provider;
import com.garvit.provider.model.SLA;

public class ProviderMapper {
    public static Provider toEntity(ProviderDTO dto) {
        // Convert SLADTO to SLA entity
        SLA sla = new SLA();
        sla.setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
        sla.setUptimePercent(dto.getSla().getUptimePercent());

        // Convert ProviderDTO to Provider entity
        Provider p = new Provider();
        p.setPartnerId(dto.getPartnerId());
        p.setName(dto.getName());
        p.setContactInfo(dto.getContactInfo());
        p.setSupportedChannels(dto.getSupportedChannels()); //

        p.setSla(sla);

        return p;
    }
}


