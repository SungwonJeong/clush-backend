package com.example.clushapi.calendar.repository;

import com.example.clushapi.calendar.entity.CalendarSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CalendarSharingRepository extends JpaRepository<CalendarSharing, Long> {

    List<CalendarSharing> findByCalendarId(Long calendarId);
    List<CalendarSharing> findBySharedUserId(Long userId);
    Optional<CalendarSharing> findByCalendarIdAndSharedUserId(Long calendarId, Long userId);

    @Modifying
    @Query("delete from CalendarSharing cs where cs.calendar.id = :calendarId")
    void deleteByCalendarId(@Param("calendarId") Long calendarId);
}
