package com.org.repository;

import com.org.model.WalkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WalkHistoryRepository extends JpaRepository<WalkHistory, Long> {
    List<WalkHistory> findByUserIdOrderByStartTimeDesc(Long userId);
    int countByUserId(Long userId);
    int countByUserIdAndStatus(Long userId, String status);
}