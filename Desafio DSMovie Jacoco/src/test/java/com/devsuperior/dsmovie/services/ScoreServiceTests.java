package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {

    @InjectMocks
    private ScoreService service;

    @Mock
    private ScoreRepository repository;
	@Mock
	private MovieRepository movieRepository;
	@Mock
	private UserService userService;

	private ScoreDTO scoreDTO, nonExistingMovieIdScoreDTO;

	@BeforeEach
    void setUp() throws Exception {
		long nonExistingMovieId = 99L;

		UserEntity user = UserFactory.createUserEntity();
		ScoreEntity score = ScoreFactory.createScoreEntity();
		MovieEntity movie = MovieFactory.createMovieEntity();

		scoreDTO = new ScoreDTO(score);
		nonExistingMovieIdScoreDTO = new ScoreDTO(nonExistingMovieId, score.getValue());

		Mockito.when(userService.authenticated()).thenReturn(user);
		Mockito.when(movieRepository.findById(scoreDTO.getMovieId())).thenReturn(Optional.of(movie));
		Mockito.when(movieRepository.findById(nonExistingMovieId)).thenThrow(ResourceNotFoundException.class);

		Mockito.when(repository.saveAndFlush(score)).thenReturn(score);
		Mockito.when(movieRepository.save(movie)).thenReturn(movie);
    }

    @Test
    public void saveScoreShouldReturnMovieDTO() {
		MovieDTO result = service.saveScore(scoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), scoreDTO.getMovieId());
	}

    @Test
    public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.saveScore(nonExistingMovieIdScoreDTO);
		});
    }
}
