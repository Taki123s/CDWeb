package com.animeweb.dto;

import com.animeweb.entities.Movie;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieDTO {
    private Long id;
    private String descriptions;
    private List<Movie> movieSet;
}
