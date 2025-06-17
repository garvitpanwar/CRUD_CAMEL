package com.garvit.provider.mapper;

import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.dto.SLADTO;
import com.garvit.provider.model.Provider;
import com.garvit.provider.model.SLA;

public class ProviderMapper {

    public static ProviderDTO toDto(Provider provider) {
        if (provider == null) {
            return null;
        }

        ProviderDTO dto = new ProviderDTO();
        dto.setPartnerId(provider.getPartnerId());
        dto.setName(provider.getName());
        dto.setContactInfo(provider.getContactInfo());
        dto.setSupportedChannels(provider.getSupportedChannels());

        // Handle SLA mapping
        if (provider.getSla() != null) {
            SLADTO slaDto = new SLADTO();
            slaDto.setDeliveryTimeMs(provider.getSla().getDeliveryTimeMs());
            slaDto.setUptimePercent(provider.getSla().getUptimePercent());
            dto.setSla(slaDto);
        }

        return dto;
    }

    public static Provider toEntity(ProviderDTO dto) {
        if (dto == null) {
            return null;
        }

        Provider provider = new Provider();
        provider.setPartnerId(dto.getPartnerId());
        provider.setName(dto.getName());
        provider.setContactInfo(dto.getContactInfo());
        provider.setSupportedChannels(dto.getSupportedChannels());

        // Handle SLA mapping
        if (dto.getSla() != null) {
            SLA sla = new SLA();
            sla.setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
            sla.setUptimePercent(dto.getSla().getUptimePercent());
            provider.setSla(sla);
        }

        return provider;
    }
}