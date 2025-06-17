package com.garvit.provider.service;

import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.model.Provider;
import com.garvit.provider.model.SLA;
import com.garvit.provider.repository.ProviderRepository;
import com.garvit.provider.mapper.ProviderMapper; // Add this import
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.garvit.provider.mapper.ProviderMapper;

@ApplicationScoped
public class ProviderService {

    @Inject
    ProviderRepository providerRepository;

    @Transactional
    public ProviderDTO updateProvider(Long id, ProviderDTO dto) {
        Provider existing = providerRepository.findById(id);
        if(existing == null) {
            throw new RuntimeException("Provider not found");
        }

        // Update fields
        existing.setName(dto.getName());
        existing.setContactInfo(dto.getContactInfo());
        existing.setSupportedChannels(dto.getSupportedChannels());

        // Handle SLA update
        if(dto.getSla() != null) {
            if(existing.getSla() == null) {
                existing.setSla(new SLA());
            }
            existing.getSla().setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
            existing.getSla().setUptimePercent(dto.getSla().getUptimePercent());
        }

        return ProviderMapper.toDto(existing); // Now this will work
    }
}