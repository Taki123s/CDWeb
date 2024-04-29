package com.animeweb.repository;


import com.animeweb.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    @Query("select m from Movie m where m.name like %:term%" )
    List<Movie> findByNameContainingIgnoreCase(@Param("term")String term);
}
