package org.example.techblog.dtos.articledtos;

import lombok.Getter;
import lombok.Setter;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.userdtos.UserDto;

import java.util.Date;

@Getter
@Setter
public class ArticleDetailDto {
    private Long id;
    private String title;
    private String subTitle;
    private String description;
    private String photoUrl;
    private String videoUrl;
    private Date createdDate;
    private Date updatedDate;
    private int viewCount;
    private CategoryDto category;
    private UserDto user;
}
