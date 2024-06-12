package org.example.techblog.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "isDeleted", columnDefinition = "false")
    private Boolean isDeleted;

    @OneToMany
    @JoinColumn(name = "articles", nullable = true)
    private List<Article> articles;
}
