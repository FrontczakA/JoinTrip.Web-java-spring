package com.projekt.web.controllers;

import com.projekt.web.dto.EventDto;
import com.projekt.web.models.Event;
import com.projekt.web.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {
    private EventService eventService;
    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping("/events/{tripId}/new")
    public String createEventFrom(@PathVariable("tripId") Long tripId, Model model){
        Event event = new Event();
        model.addAttribute("tripId", tripId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @GetMapping("/events/{eventId}")
    public String eventDetail(@PathVariable("eventId") long eventId, Model model ){
        EventDto eventDto = eventService.findEventById(eventId);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }
    @PostMapping("/events/{tripId}")
    public String createEvent(@PathVariable("tripId") Long tripId, @ModelAttribute("event") EventDto eventDto, Model model){
        eventService.createEvent(tripId,eventDto);
        return "redirect:/trips/" + tripId;
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEvent(@PathVariable("eventId") Long eventId, Model model){
        EventDto event = eventService.findEventById(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId, @Valid @ModelAttribute("event") EventDto event, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDto eventDto = eventService.findEventById(eventId);
        event.setId(eventId);
        event.setTrip(eventDto.getTrip());
        eventService.updateEvent(event);
        return "redirect:/trips";
    }

    @GetMapping("/events/{eventId}/delete")
    public String delete(@PathVariable("eventId") Long eventId){
        eventService.delete(eventId);
        return "redirect:/trips";
    }


}
