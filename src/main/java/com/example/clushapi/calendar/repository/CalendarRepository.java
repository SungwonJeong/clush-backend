package com.example.clushapi.calendar.repository;

import com.example.clushapi.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    boolean existsByUserIdAndStartTimeBeforeAndEndTimeAfter(Long userId, LocalDateTime endTime, LocalDateTime startTime);
}
