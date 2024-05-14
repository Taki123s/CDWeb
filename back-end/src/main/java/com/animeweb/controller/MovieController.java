package com.animeweb.controller;


import com.animeweb.dto.MovieDTO;
import com.animeweb.entities.Movie;
import com.animeweb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO){
        MovieDTO savedMovie = movieService.createMovie(movieDTO);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovie(){
        return ResponseEntity.ok(movieService.getAllMovie());
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable Long movieId){
        return ResponseEntity.ok(movieService.findMovieById(movieId));
    }
    @GetMapping("/watching/{movieId}")
    public ResponseEntity<MovieDTO> findMovieWatching(@PathVariable Long movieId){
        return ResponseEntity.ok(movieService.findMovieWatching(movieId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> search(@RequestParam("term") String keyword) {
        List<MovieDTO> movieList = null;
        if (keyword != null && !keyword.isEmpty()) {
            movieList = movieService.searchMovie(keyword);
        } else {
            movieList = movieService.getAllMovie();
        }

        if (movieList != null && !movieList.isEmpty()) {
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
