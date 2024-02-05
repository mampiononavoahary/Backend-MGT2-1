package com.mgt2.backendproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "app_user")
public class User implements Serializable {
    @Id
    @Column(name = "id_user", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_user;

    @Column(name = "first_name", nullable = false, length = 200)
    private String first_name;

    @Column(name = "last_name", nullable = false, length = 200)
    private String last_name;

    @Column(name = "email", nullable = false, length = 200,unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "profil",length = 250)
    private String profil;

    @ManyToMany
    @JoinTable(
            name = "collabo",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_document")
    )
    private Set<Document> documents = new HashSet<>();
}
