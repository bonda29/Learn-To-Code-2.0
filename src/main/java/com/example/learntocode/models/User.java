package com.example.learntocode.models;

import com.example.learntocode.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("user_id")
    @Column(name = "auth0_id", unique = true)
    private String auth0Id;

    private String username;

    private String nickname;

    @Email
    private String email;

    private String name;

    @JsonProperty("picture")
    @Column(name = "picture_url")
    private String pictureUrl;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions = new LinkedHashSet<>();

    @Column(name = "blocked", nullable = false, columnDefinition = "boolean default false")
    private boolean blocked;

    @JsonProperty("created_at")
    @Column(name = "date_of_creation")
    private LocalDateTime createdAt;

    private Status status;

//    @ManyToMany(mappedBy = "participants")
//    private Set<ChatRoom> chatRooms = new LinkedHashSet<>();
}
