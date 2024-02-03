package com.mgt2.backendproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "first_name", nullable = false, length = 200)
    private String first_name;

    @Column(name = "last_name", nullable = false, length = 200)
    private String last_name;

    @Column(name = "email", nullable = false, length = 200,unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "collabo",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Document> documents = new HashSet<>();
}
