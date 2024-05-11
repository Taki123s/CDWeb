package com.animeweb.service;

import com.animeweb.dto.MovieDTO;
import com.animeweb.entities.Movie;

import java.util.List;

public interface MovieService {
    MovieDTO createMovie(MovieDTO movieDTO);
    List<MovieDTO> getAllMovie();
    MovieDTO findMovieById(Long movieId);
    MovieDTO findMovieWatching(Long movieId);
    List<MovieDTO> searchMovie(String name);

}
