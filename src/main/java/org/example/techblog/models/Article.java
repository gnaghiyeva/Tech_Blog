package org.example.techblog.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;//*
    private String subTitle;//*
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;//*

    private String photoUrl;//*
    private Date createdDate;
    private Date updatedDate;
    private int viewCount;
    @Column(name = "isDeleted", columnDefinition = "false")
    private Boolean isDeleted;

    @ManyToOne
    private Category category;

    @ManyToOne
    private UserEntity user;


}
