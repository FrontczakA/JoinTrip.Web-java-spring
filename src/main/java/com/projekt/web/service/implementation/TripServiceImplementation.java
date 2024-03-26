package com.projekt.web.service.implementation;

import com.projekt.web.dto.EventDto;
import com.projekt.web.dto.TripDto;
import com.projekt.web.models.Comment;
import com.projekt.web.models.Event;
import com.projekt.web.models.Trip;
import com.projekt.web.models.User;
import com.projekt.web.repository.CommentRepository;
import com.projekt.web.repository.TripRepository;
import com.projekt.web.service.TripService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripServiceImplementation implements TripService {
    private TripRepository tripRepository;
    private CommentRepository commentRepository;
    @Autowired
    public TripServiceImplementation(TripRepository tripRepository , CommentRepository commentRepository) {

        this.tripRepository = tripRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<TripDto> findAllTrips(){
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map( (trip) -> mapToTripDto(trip)).collect(Collectors.toList());
    }

    @Override
    public Trip saveTrip(TripDto tripDto) {
        Trip trip = mapToTrip(tripDto);
        return tripRepository.save(trip);
    }

    @Override
    public TripDto findTripById(long tripId) {
        Trip trip = tripRepository.findById(tripId).get();
        return mapToTripDto(trip);
    }

    @Override
    public void updateTrip(TripDto tripDto) {
        Trip trip = mapToTrip(tripDto);
        tripRepository.save(trip);
    }

    @Override
    public void delete(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    @Override
    public List<TripDto> searchTrips(String query) {
        List<Trip> trips = tripRepository.searchTrips(query);
        return trips.stream().map(trip -> mapToTripDto(trip)).collect(Collectors.toList());
    }

    @Override
    public List<TripDto> findPaginatedTrips(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> tripPage = tripRepository.findAll(pageable);
        return tripPage.getContent().stream()
                .map(this::mapToTripDto)
                .collect(Collectors.toList());
    }

    @Override
    public void likeComment(Long tripId, Long commentId, User user) {
        Trip trip = tripRepository.findById(tripId).orElse(null);
        if (trip != null) {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment != null) {
                boolean isUserLiked = comment.getLikedByUsers().stream()
                        .anyMatch(likedUser -> likedUser.getId().equals(user.getId()));

                if (isUserLiked) {
                    comment.getLikedByUsers().removeIf(likedUser -> likedUser.getId().equals(user.getId()));
                    comment.setLikes(comment.getLikes() - 1);
                } else {
                    comment.getLikedByUsers().add(user);
                    comment.setLikes(comment.getLikes() + 1);
                }
                commentRepository.save(comment);
            }
        }
    }

    @Override
    public List<Comment> getComments(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElse(null);
        if (trip != null) {
            return trip.getComments();
        } else {
            return Collections.emptyList();
        }
    }
    @Override
    public Trip addCommentToTrip(Long tripId, Comment comment) {
        Trip trip = tripRepository.findById(tripId).orElse(null);
        if (trip != null) {
            comment.setTrip(trip);
            List<Comment> comments = trip.getComments();
            comments.add(comment);
            trip.setComments(comments);
            return tripRepository.save(trip);
        }
        return null;
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
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
