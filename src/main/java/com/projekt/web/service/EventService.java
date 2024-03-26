package com.projekt.web.service;

import com.projekt.web.dto.EventDto;
import com.projekt.web.dto.TripDto;
import com.projekt.web.models.Event;

import java.util.List;

public interface EventService {
    void createEvent(Long tripId, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findEventById(long eventId);
    void updateEvent(EventDto eventDto);

    void delete(Long eventId);
}
