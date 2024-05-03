package com.animeweb.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ordinal")
    private Integer ordinal;

    @Column(name = "link")
    private String link;

    @Column(name = "status",columnDefinition = "tinyint default 1")
    private Boolean status;

    @Column(name = "type")
    private Integer type;

    @ManyToOne
    @JoinColumn(name ="movie_id",nullable = false,referencedColumnName = "id")
    @JsonBackReference
    private Movie movie;
    @OneToMany(mappedBy = "chapter",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Comment> comments;
}