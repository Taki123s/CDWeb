package com.animeweb.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_packed")
public class UserPacked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User userId;

    @ManyToOne
    @JoinColumn(name = "service_pack_id", referencedColumnName = "id")
    @JsonBackReference
    private ServicePack servicePackId;

    @Column(name = "expired_time")
    private Date expiredTime;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(name = "status",columnDefinition = "tinyint default 1")
    private Boolean status;

    @Column(name = "delete_at")
    private Date deletedAt;

}