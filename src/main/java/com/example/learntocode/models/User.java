package com.example.learntocode.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
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

    @Column(name = "blocked", nullable = false, columnDefinition = "boolean default false")
    private boolean blocked;

    @JsonProperty("created_at")
    @Column(name = "date_of_creation")
    private LocalDateTime createdAt;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
