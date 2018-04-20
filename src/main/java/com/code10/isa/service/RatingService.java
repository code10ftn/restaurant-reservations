package com.code10.isa.service;

import com.code10.isa.model.Rating;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.user.Guest;
import com.code10.isa.repository.RatingRepository;
import com.code10.isa.repository.ReservationGuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ReservationGuestRepository reservationGuestRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, ReservationGuestRepository reservationGuestRepository) {
        this.ratingRepository = ratingRepository;
        this.reservationGuestRepository = reservationGuestRepository;
    }

    public List<Rating> findByRestaurant(long id) {
        return ratingRepository.findByReservationGuestReservationRestaurantId(id);
    }

    public List<Rating> findByWaiter(long id) {
        return ratingRepository.findByReservationGuestReservationOrderWaiterId(id);
    }

    public Rating rateRestaurant(ReservationGuest reservationGuest, int rate) {
        Rating rating = ratingRepository.findByReservationGuestId(reservationGuest.getId());
        if (rating == null) {
            rating = new Rating();
            rating.setReservationGuest(reservationGuest);
        }
        rating.setRestaurantRating(rate);
        final Rating savedRating = ratingRepository.save(rating);

        if (reservationGuest.getRating() == null) {
            reservationGuest.setRating(savedRating);
            reservationGuestRepository.save(reservationGuest);
        }

        return savedRating;
    }

    public Rating rateWaiter(ReservationGuest reservationGuest, int rate) {
        Rating rating = ratingRepository.findByReservationGuestId(reservationGuest.getId());
        if (rating == null) {
            rating = new Rating();
            rating.setReservationGuest(reservationGuest);
        }
        rating.setWaiterRating(rate);
        final Rating savedRating = ratingRepository.save(rating);

        if (reservationGuest.getRating() == null) {
            reservationGuest.setRating(savedRating);
            reservationGuestRepository.save(reservationGuest);
        }

        return savedRating;
    }

    public Rating rateOrder(ReservationGuest reservationGuest, int rate) {
        Rating rating = ratingRepository.findByReservationGuestId(reservationGuest.getId());
        if (rating == null) {
            rating = new Rating();
            rating.setReservationGuest(reservationGuest);
        }
        rating.setOrderRating(rate);
        final Rating savedRating = ratingRepository.save(rating);

        if (reservationGuest.getRating() == null) {
            reservationGuest.setRating(savedRating);
            reservationGuestRepository.save(reservationGuest);
        }

        return savedRating;
    }

    public double getOverallRating(long restaurantId) {
        double rating = 0;
        final List<Rating> ratings = ratingRepository.findByReservationGuestReservationRestaurantId(restaurantId);

        if (ratings.size() != 0) {
            for (Rating r : ratings) {
                rating += r.getRestaurantRating();
            }
            rating /= ratings.size();
        }

        return rating;
    }

    public double getFriendsRating(long restaurantId, List<Guest> guests) {
        double rating = 0;
        int count = 0;

        for (Guest guest : guests) {
            final List<Rating> ratings = ratingRepository.findByReservationGuestGuestId(guest.getId());

            for (Rating r : ratings) {
                if (r.getReservationGuest().getReservation().getRestaurant().getId() == restaurantId) {
                    count++;
                    rating += r.getRestaurantRating();
                }
            }
        }
        if (count > 0) {

            rating /= count;
        }

        return rating;
    }

    public List<Rating> findByMenuItem(long id) {
        return ratingRepository.findByMenuItem(id);
    }
}
