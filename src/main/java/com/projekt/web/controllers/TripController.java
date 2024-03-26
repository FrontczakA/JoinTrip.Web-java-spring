package com.projekt.web.controllers;

import com.projekt.web.dto.EventDto;
import com.projekt.web.dto.TripDto;
import com.projekt.web.models.Comment;
import com.projekt.web.models.Event;
import com.projekt.web.models.Trip;
import com.projekt.web.models.User;
import com.projekt.web.service.TripService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TripController {
    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips")
    public String listTrips(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        List<TripDto> trips = tripService.findPaginatedTrips(page, size);
        model.addAttribute("trips", trips);
        return "trips-list";
    }


    @GetMapping("/trips/{tripId}")
    public String tripDetail(@PathVariable("tripId") long tripId, Model model ){
        TripDto tripDto = tripService.findTripById(tripId);
        if (tripDto == null) {
            return "redirect:/trips";
        }
        List<Comment> comments = tripService.getComments(tripId);
        model.addAttribute("trip", tripDto);
        model.addAttribute("comments", comments);
        return "trip-detail";
    }




    @GetMapping("/trips/new")
    public String createTripForm(Model model){
        Trip trip = new Trip();
        model.addAttribute("trip", trip);
        return "trips-create";
    }

    @PostMapping("/trips/new")
    public String saveTrip(@Valid @ModelAttribute("trip") TripDto tripDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("trip", tripDto);
            return "trips-create";
        }
        tripService.saveTrip(tripDto);
        return "redirect:/trips";
    }

    @GetMapping("/trips/search")
    public String searchTrip(@RequestParam(value = "query") String query, Model model){
        List<TripDto> trips = tripService.searchTrips(query);
        model.addAttribute("trips", trips);
        return "trips-list";
    }

    @GetMapping("/trips/{tripId}/edit")
    public String editTrip(@PathVariable("tripId") Long tripId, Model model){
        TripDto trip = tripService.findTripById(tripId);
        model.addAttribute("trip", trip);
        return "trips-edit";
    }

    @PostMapping("/trips/{tripId}/edit")
    public String updateTrip(@PathVariable("tripId") Long tripId, @Valid @ModelAttribute("trip") TripDto trip, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("trip", trip);
            return "trips-edit";
        }
        trip.setId(tripId);
        tripService.updateTrip(trip);
        return "redirect:/trips";
    }

    @GetMapping("/trips/{tripId}/delete")
    public String deleteTrip(@PathVariable("tripId") Long tripId){
        tripService.delete(tripId);
        return "redirect:/trips";
    }

    @GetMapping("/trips/{tripId}/comments")
    public String getCommentsForTrip(@PathVariable("tripId") Long tripId, Model model) {
        TripDto tripDto = tripService.findTripById(tripId);
        if (tripDto == null) {
            return "redirect:/trips";
        }
        List<Comment> comments = tripDto.getComments();
        model.addAttribute("comments", comments);
        return "trip-detail";
    }

    @PostMapping("/trips/{tripId}/comments")
    public String addComment(@PathVariable("tripId") Long tripId,
                             @RequestParam("content") String content,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        TripDto tripDto = tripService.findTripById(tripId);
        if (tripDto == null) {
            return "redirect:/trips";
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        if (content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "You can't add empty comment");
            return "redirect:/trips/" + tripId;
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreationTime(LocalDateTime.now());
        comment.setUser(loggedInUser);

        tripService.addCommentToTrip(tripId, comment);

        return "redirect:/trips/" + tripId;
    }


    @GetMapping("/trips/{tripId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("tripId") Long tripId, @PathVariable("commentId") Long commentId){
        tripService.deleteComment(commentId);
        return "redirect:/trips/" + tripId;
    }
    @PostMapping("/trips/{tripId}/comments/{commentId}/like")
    public String likeComment(@PathVariable("tripId") Long tripId, @PathVariable("commentId") Long commentId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            tripService.likeComment(tripId, commentId, loggedInUser);
        }
        return "redirect:/trips/" + tripId;
    }


    private Trip mapToTrip(TripDto trip) {
        Trip tripDto = Trip.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .photoUrl(trip.getPhotoUrl())
                .description(trip.getDescription())
                .creationTime(trip.getCreationTime())
                .updateTime(trip.getUpdateTime())
                .build();
        return tripDto;

    }

    private TripDto mapToTripDto(Trip trip){
        TripDto tripDto= TripDto.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .photoUrl(trip.getPhotoUrl())
                .description(trip.getDescription())
                .creationTime(trip.getCreationTime())
                .updateTime(trip.getUpdateTime())
                .events(trip.getEvents().stream().map((event)->mapToEventDto(event)).collect(Collectors.toList()))
                .build();
        return tripDto;
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
                .build();
    }
}
