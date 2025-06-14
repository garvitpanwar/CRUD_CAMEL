package com.garvit.provider.repository;

import com.garvit.provider.model.Provider;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProviderRepository implements PanacheRepository<Provider> {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Provider> getAllProviders() {
        return listAll();
    }

    @Transactional
    public void persistProvider(Provider provider) {
        persist(provider);
    }

    @Transactional
    public void deleteProvider(Provider provider) {
        delete(provider);
    }

    @Transactional
    public Provider findProviderById(Long id) {
        return findById(id);
    }

    @Transactional
    public void updateProvider(Provider provider) {
        em.merge(provider);  // ✅ FIXED: use merge for updates
    }
}
