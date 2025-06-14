package com.garvit.provider.repository;

import com.garvit.provider.model.SLA;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SLARepository implements PanacheRepository<SLA> {
}
