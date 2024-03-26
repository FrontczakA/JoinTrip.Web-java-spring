package com.projekt.web.repository;

import com.projekt.web.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByTitle(String url);
    @Query("SELECT t from Trip t WHERE t.title LIKE CONCAT('%', :query ,'%')")
    List<Trip> searchTrips(String query);

}
