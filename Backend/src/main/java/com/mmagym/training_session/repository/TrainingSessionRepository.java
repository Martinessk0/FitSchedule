package com.mmagym.training_session.repository;

import com.mmagym.model.enums.SessionType;
import com.mmagym.training_session.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    //Primerno query
    @Query("""
         select ts from TrainingSession ts
                join fetch ts.room r
                where ts.startTime between :from and :to
                  and (:type is null or ts.type = :type)
                  and (:roomId is null or r.id = :roomId)
                order by ts.startTime asc
    """)
    List<TrainingSession> findSchedule(LocalDateTime from, LocalDateTime to, SessionType type, Long roomId);
}
