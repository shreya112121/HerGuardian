package com.org.repository;

import com.org.model.SosAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SosAlertRepository extends JpaRepository<SosAlert, Long> {
    List<SosAlert> findByUserIdOrderByTriggeredAtDesc(Long userId);

    long countByUserId(Long userId);
}