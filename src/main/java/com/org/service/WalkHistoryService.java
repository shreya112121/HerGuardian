package com.org.service;

import com.org.model.WalkHistory;
import com.org.repository.WalkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalkHistoryService {

    @Autowired
    private WalkHistoryRepository walkRepository;

  
    public WalkHistory startWalk(WalkHistory walk) {
        return walkRepository.save(walk);
    }

    
    public WalkHistory endWalk(Long walkId, String status) {
        WalkHistory walk = walkRepository.findById(walkId)
            .orElseThrow(() -> new RuntimeException("Walk not found!"));
        walk.setEndTime(LocalDateTime.now());
        walk.setStatus(status);
        return walkRepository.save(walk);
    }

   
    public List<WalkHistory> getWalksByUserId(Long userId) {
        return walkRepository.findByUserIdOrderByStartTimeDesc(userId);
    }

    public int countWalks(Long userId) {
        return walkRepository.countByUserId(userId);
    }

    public int countSafeWalks(Long userId) {
        return walkRepository.countByUserIdAndStatus(userId, "completed");
    }
}