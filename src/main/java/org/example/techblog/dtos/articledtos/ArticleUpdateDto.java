package org.example.techblog.dtos.articledtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleUpdateDto {
    private Long id;
    private String title;//*
    private String subTitle;//*
    private String description;//*
    private String photoUrl;//*
    private String videoUrl;
    private Long categoryId;
    private Long userId;
}
