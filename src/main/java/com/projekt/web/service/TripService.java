package com.projekt.web.service;
import com.projekt.web.models.Comment;
import com.projekt.web.models.Trip;
import com.projekt.web.dto.TripDto;
import com.projekt.web.models.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface TripService{
    List<TripDto> findAllTrips();
    Trip saveTrip(TripDto tripDto);

    TripDto findTripById(long tripId);

    void updateTrip(TripDto trip);

    void delete(Long tripId);
    List<TripDto> searchTrips(String query);
    List<TripDto> findPaginatedTrips(int page, int size);
    void likeComment(Long tripId, Long commentId, User user);

    List<Comment> getComments(Long tripId);
    Trip addCommentToTrip(Long tripId, Comment comment);
     void deleteComment(Long id);

}
