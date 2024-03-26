package com.projekt.web.service.implementation;

import com.projekt.web.dto.EventDto;
import com.projekt.web.models.Event;
import com.projekt.web.models.Trip;
import com.projekt.web.repository.EventRepository;
import com.projekt.web.repository.TripRepository;
import com.projekt.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImplementation implements EventService {

    private EventRepository eventRepository;
    private TripRepository tripRepository;

    @Autowired
    public EventServiceImplementation(EventRepository eventRepository, TripRepository tripRepository) {
        this.eventRepository = eventRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public void createEvent(Long tripId, EventDto eventDto) {
        Trip trip = tripRepository.findById(tripId).get();
        Event event = mapToEvent(eventDto);
        event.setTrip(trip);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findEventById(long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void delete(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    private Event mapToEvent(EventDto eventDto){
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .photoUrl(eventDto.getPhotoUrl())
                .createDate(eventDto.getCreateDate())
                .updateDate(eventDto.getUpdateDate())
                .trip(eventDto.getTrip())
                .description(eventDto.getDescription())
                .build();
    }

    private EventDto mapToEventDto(Event event){
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .photoUrl(event.getPhotoUrl())
                .createDate(event.getCreateDate())
                .updateDate(event.getUpdateDate())
                .trip(event.getTrip())
                .description(event.getDescription())
                .build();
    }


}
