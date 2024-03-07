package com.example.learntocode.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(optional = false)
    private User author;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Tag.class)
    @JoinTable(name = "questions_tags",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new LinkedHashSet<>();


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "question_image_urls", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(name = "date_published")
    private LocalDateTime datePublished;

    @PrePersist
    protected void onCreate() {
        this.datePublished = LocalDateTime.now();
    }

}
