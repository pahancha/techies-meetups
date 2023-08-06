package com.techiesmeetups.web.repository;

import com.techiesmeetups.web.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
