package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public List<ReviewDTO> findByMovie(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        return reviewRepository.findByMovie(movie).stream().map(ReviewDTO::new).toList();
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO) {
        User user = userService.authenticated();
        return new ReviewDTO(reviewRepository.save(copyToReview(reviewDTO, user)));
    }

    public Review copyToReview(ReviewDTO reviewDTO, User user) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setText(reviewDTO.getText());
        Movie movie = new Movie();
        movie.setId(reviewDTO.getMovieId());
        review.setMovie(movie);
        review.setUser(user);
        return review;
    }
}
