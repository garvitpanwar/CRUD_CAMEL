////package com.garvit.provider.service;
////
////import com.garvit.provider.dto.ProviderDTO;
////import com.garvit.provider.model.Provider;
////import com.garvit.provider.model.SLA;
////import com.garvit.provider.repository.ProviderRepository;
////import com.garvit.provider.mapper.ProviderMapper; // Add this import
////import jakarta.enterprise.context.ApplicationScoped;
////import jakarta.inject.Inject;
////import jakarta.transaction.Transactional;
////import com.garvit.provider.mapper.ProviderMapper;
////
////@ApplicationScoped
////public class ProviderService {
////
////    @Inject
////    ProviderRepository providerRepository;
////
////    @Transactional
////    public ProviderDTO updateProvider(Long id, ProviderDTO dto) {
////        Provider existing = providerRepository.findById(id);
////        if(existing == null) {
////            throw new RuntimeException("Provider not found");
////        }
////
////        // Update fields
////        existing.setName(dto.getName());
////        existing.setContactInfo(dto.getContactInfo());
////        existing.setSupportedChannels(dto.getSupportedChannels());
////
////        // Handle SLA update
////        if(dto.getSla() != null) {
////            if(existing.getSla() == null) {
////                existing.setSla(new SLA());
////            }
////            existing.getSla().setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
////            existing.getSla().setUptimePercent(dto.getSla().getUptimePercent());
////        }
////
////        return ProviderMapper.toDto(existing); // Now this will work
////    }
////}
//
//
////
////package com.garvit.provider.service;
////
////import com.garvit.provider.dto.ProviderDTO;
////import com.garvit.provider.model.Provider;
////import com.garvit.provider.model.SLA;
////import com.garvit.provider.repository.ProviderRepository;
////import com.garvit.provider.mapper.ProviderMapper;
////import jakarta.enterprise.context.ApplicationScoped;
////import jakarta.inject.Inject;
////import jakarta.transaction.Transactional;
////
////import java.util.List;
////
////@ApplicationScoped
////public class ProviderService {
////
////    @Inject
////    ProviderRepository providerRepository;
////
////    @Inject
////    ProviderMapper providerMapper;
////
////    @Transactional
////    public ProviderDTO updateProvider(Long id, ProviderDTO dto) {
////        Provider existing = providerRepository.findById(id);
////        if (existing == null) {
////            throw new RuntimeException("Provider not found with id: " + id);
////        }
////
////        // Update basic fields
////        existing.setName(dto.getName());
////        existing.setContactInfo(dto.getContactInfo());
////        existing.setSupportedChannels(dto.getSupportedChannels());
////
////        // Handle SLA update
////        if (dto.getSla() != null) {
////            if (existing.getSla() == null) {
////                existing.setSla(new SLA());
////            }
////            existing.getSla().setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
////            existing.getSla().setUptimePercent(dto.getSla().getUptimePercent());
////        }
////
////        return providerMapper.toDto(existing);
////    }
////
////    @Transactional
////    public Provider saveProvider(ProviderDTO dto) {
////        Provider provider = providerMapper.toEntity(dto);
////        providerRepository.persist(provider);
////        return provider;
////    }
////
////    public Provider getProviderById(Long id) {
////        return providerRepository.findById(id);
////    }
////
////    public List<Provider> getAllProviders() {
////        return providerRepository.listAll();
////    }
////
////    @Transactional
////    public boolean deleteProvider(Long id) {
////        return providerRepository.deleteById(id);
////    }
////}
//
//
package com.garvit.provider.service;

import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.model.Provider;
import com.garvit.provider.model.SLA;
import com.garvit.provider.repository.ProviderRepository;
import com.garvit.provider.mapper.ProviderMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProviderService {

    @Inject
    ProviderRepository providerRepository;

    @Inject
    ProviderMapper providerMapper;

    @Transactional
    public ProviderDTO updateProvider(Long id, ProviderDTO dto) {
        Provider existing = providerRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Provider not found with id: " + id);
        }

        // Update basic fields
        existing.setName(dto.getName());
        existing.setContactInfo(dto.getContactInfo());
        existing.setSupportedChannels(dto.getSupportedChannels());

        // Handle SLA update
        if (dto.getSla() != null) {
            if (existing.getSla() == null) {
                existing.setSla(new SLA());
            }
            existing.getSla().setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
            existing.getSla().setUptimePercent(dto.getSla().getUptimePercent());
        }

        return providerMapper.toDto(existing);
    }

    @Transactional
    public Provider saveProvider(ProviderDTO dto) {
        Provider provider = providerMapper.toEntity(dto);
        providerRepository.persist(provider);
        return provider;
    }

    @Transactional  // Added this to fix ContextNotActiveException
    public Provider getProviderById(Long id) {
        return providerRepository.findById(id);
    }

    @Transactional
    public List<Provider> getAllProviders() {
        return providerRepository.listAll();
    }

    @Transactional
    public boolean deleteProvider(Long id) {
        return providerRepository.deleteById(id);
    }
}

//
//package com.garvit.provider.service;
//
//import com.garvit.provider.dto.ProviderDTO;
//import com.garvit.provider.model.Provider;
//import com.garvit.provider.model.SLA;
//import com.garvit.provider.repository.ProviderRepository;
//import com.garvit.provider.mapper.ProviderMapper; // Add this import
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import com.garvit.provider.mapper.ProviderMapper;
//
//import java.util.List;
//
//@ApplicationScoped
//public class ProviderService {
//
//    @Inject
//    ProviderRepository providerRepository;
//
//    @Inject
//    ProviderMapper providerMapper;
//
//    @Transactional
//    public ProviderDTO updateProvider(Long id, ProviderDTO dto) {
//        Provider existing = providerRepository.findById(id);
//        if(existing == null) {
//            throw new RuntimeException("Provider not found");
//        }
//
//        // Update fields
//        existing.setName(dto.getName());
//        existing.setContactInfo(dto.getContactInfo());
//        existing.setSupportedChannels(dto.getSupportedChannels());
//
//        // Handle SLA update
//        if(dto.getSla() != null) {
//            if(existing.getSla() == null) {
//                existing.setSla(new SLA());
//            }
//            existing.getSla().setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
//            existing.getSla().setUptimePercent(dto.getSla().getUptimePercent());
//        }
//
//        return ProviderMapper.toDto(existing); // Now this will work
//    }
//
//    @Transactional
//    public Provider saveProvider(ProviderDTO dto) {
//        Provider provider = providerMapper.toEntity(dto);
//        providerRepository.persist(provider);
//        return provider;
//    }
//
//    @Transactional  // Added this to fix ContextNotActiveException
//    public Provider getProviderById(Long id) {
//        return providerRepository.findById(id);
//    }
//
//    @Transactional
//    public List<Provider> getAllProviders() {
//        return providerRepository.listAll();
//    }
//
//    @Transactional
//    public boolean deleteProvider(Long id) {
//        return providerRepository.deleteById(id);
//    }
//}