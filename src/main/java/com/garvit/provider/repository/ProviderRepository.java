//package com.garvit.provider.repository;
//
//import com.garvit.provider.model.Provider;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.List;
//
//@ApplicationScoped
//public class ProviderRepository implements PanacheRepository<Provider> {
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Transactional
//    public List<Provider> getAllProviders() {
//        return listAll();
//    }
//
//    @Transactional
//    public void persistProvider(Provider provider) {
//        persist(provider);
//    }
//
//    @Transactional
//    public void deleteProvider(Provider provider) {
//        delete(provider);
//    }
//
//    @Transactional
//    public Provider findProviderById(Long id) {
//        return findById(id);
//    }
//
//    @Transactional
//    public void updateProvider(Provider provider) {
//        em.merge(provider);  // ✅ FIXED: use merge for updates
//    }
//}

//
//package com.garvit.provider.repository;
//
//import com.garvit.provider.model.Provider;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import java.util.List;
//
//@ApplicationScoped
//public class ProviderRepository implements PanacheRepository<Provider> {
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Transactional
//    public List<Provider> getAllProviders() {
//        return listAll();
//    }
//
//    @Transactional
//    public void persistProvider(Provider provider) {
//        persist(provider);
//    }
//
//    @Transactional
//    public boolean deleteProviderById(Long id) {
//        return deleteById(id);
//    }
//
//    @Transactional
//    public void deleteProvider(Provider provider) {
//        delete(provider);
//    }
//
//    @Transactional
//    public Provider findProviderById(Long id) {
//        return findById(id);
//    }
//
//    @Transactional
//    public Provider updateProvider(Provider provider) {
//        return em.merge(provider);
//    }
//}


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
    public Provider findByPartnerIdWithTransaction(String partnerId) {
        return find("partnerId", partnerId).firstResult();
    }

    @Transactional
    public boolean deleteProviderById(Long id) {
        Provider provider = findById(id);
        if (provider != null) {
            // FIXED: First detach SLA relationship, then delete
            if (provider.getSla() != null) {
                provider.setSla(null); // Remove SLA reference
                em.merge(provider);    // Update provider to remove SLA reference
                em.flush();            // Ensure changes are flushed
            }

            // Now delete the provider (SLA will be deleted by orphanRemoval)
            delete(provider);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteProvider(Provider provider) {
        // Same logic as deleteProviderById
        if (provider.getSla() != null) {
            provider.setSla(null);
            em.merge(provider);
            em.flush();
        }
        delete(provider);
    }

    @Transactional
    public Provider findProviderById(Long id) {
        return findById(id);
    }

    @Transactional
    public Provider updateProvider(Provider provider) {
        return em.merge(provider);
    }
}