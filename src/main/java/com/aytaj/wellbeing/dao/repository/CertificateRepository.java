package com.aytaj.wellbeing.dao.repository;

import com.aytaj.wellbeing.dao.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {
}
