package com.garvit.provider.service;

import com.garvit.provider.model.Provider;
import com.garvit.provider.repository.ProviderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProviderService {

    @Inject
    ProviderRepository providerRepository;

    @Transactional
    public void saveProvider(Provider provider) {
        providerRepository.persist(provider);
    }
}
