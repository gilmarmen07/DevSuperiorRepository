package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT movie FROM Movie movie JOIN FETCH movie.genre WHERE movie.id = :id")
    public Movie searchById(Long id);

    @Query(value = "SELECT movie FROM Movie movie JOIN FETCH movie.genre WHERE movie.genre.id IN :genreIds")
    public Page<Movie> findByGenre(@Param("genreIds") List<Long> genreIds, Pageable pageable);
}
