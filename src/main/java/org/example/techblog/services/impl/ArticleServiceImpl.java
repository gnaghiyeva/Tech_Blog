package org.example.techblog.services.impl;

import org.example.techblog.dtos.articledtos.*;
import org.example.techblog.dtos.commentdtos.CommentDto;
import org.example.techblog.helpers.SeoHelper;
import org.example.techblog.models.Article;
import org.example.techblog.models.Category;
import org.example.techblog.models.Comment;
import org.example.techblog.models.UserEntity;
import org.example.techblog.repositories.ArticleRepository;
import org.example.techblog.repositories.CategoryRepository;
import org.example.techblog.repositories.CommentRepository;
import org.example.techblog.repositories.UserRepository;
import org.example.techblog.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Override
//    public void addArticle(ArticleCreateDto articleDto) {
//        Article article = modelMapper.map(articleDto, Article.class);
//        Category category = categoryRepository.findById(articleDto.getCategoryId()).get();
//        UserEntity user = userRepository.findById(9);
//        article.setUpdatedDate(new Date());
//        article.setCreatedDate(new Date());
//        article.setCategory(category);
//        article.setUser(user);
//        articleRepository.save(article);
//    }

    public void addArticle(ArticleCreateDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email);


        article.setCreatedDate(new Date());
        article.setUpdatedDate(new Date());

        article.setCategory(category);


        article.setUser(user);
        SeoHelper seoHelper = new SeoHelper();
        article.setSeoUrl(seoHelper.seoUrlHelper(articleDto.getTitle()));

        article.setIsDeleted(false);

        articleRepository.save(article);
    }

    @Override
    public List<ArticleDto> getArticles() {
        List<ArticleDto> articleDtoList = articleRepository.findAll().stream()
                .filter(article -> !article.getIsDeleted())
                .map(article -> modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());

        long countArticles = articleDtoList.size();
        return articleDtoList;
    }

    @Override
    public void updateArticle(ArticleUpdateDto articleDto) {
        Article findArticle = articleRepository.findById(articleDto.getId()).orElseThrow();
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email);

        String photoUrl = findArticle.getPhotoUrl();
        if (photoUrl != null && !photoUrl.isEmpty()) {
            File imageFile = new File("src/main/resources/static/uploads/" + photoUrl);
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Fotoğraf dosyası başarıyla silindi: " + photoUrl);
                } else {
                    System.out.println("Fotoğraf dosyası silinemedi: " + photoUrl);
                }
            } else {
                System.out.println("Fotoğraf dosyası bulunamadı: " + photoUrl);
            }
        } else {
            System.out.println("Fotoğraf URL'si geçersiz: " + photoUrl);
        }
        findArticle.setId(articleDto.getId());
        findArticle.setTitle(articleDto.getTitle());
        findArticle.setSubTitle(articleDto.getSubTitle());
        findArticle.setDescription(articleDto.getDescription());
        findArticle.setUpdatedDate(new Date());
        findArticle.setPhotoUrl(articleDto.getPhotoUrl());
        findArticle.setVideoUrl(articleDto.getVideoUrl());
        findArticle.setCategory(category);
        findArticle.setUser(user);
        SeoHelper seoHelper = new SeoHelper();
        findArticle.setSeoUrl(seoHelper.seoUrlHelper(articleDto.getTitle()));
        articleRepository.saveAndFlush(findArticle);
    }

    @Override
    public ArticleUpdateDto findUpdatedArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        ArticleUpdateDto articleUpdateDto = modelMapper.map(article, ArticleUpdateDto.class);
        return articleUpdateDto;
    }


    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + id));

        ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);
        return articleDto;
    }

    public void removeArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        String photoUrl = article.getPhotoUrl();
        if (photoUrl != null && !photoUrl.isEmpty()) {
            // photoUrl değeri /uploads/filename.ext ise, bu kısmı kesip sadece filename.ext olarak alıyoruz
            String fileName = photoUrl.substring(photoUrl.lastIndexOf("/") + 1);
            File imageFile = new File("src/main/resources/static/uploads/" + fileName);

            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Fotoğraf dosyası başarıyla silindi: " + photoUrl);
                } else {
                    System.out.println("Fotoğraf dosyası silinemedi: " + photoUrl);
                }
            } else {
                System.out.println("Fotoğraf dosyası bulunamadı: " + photoUrl);
            }
        } else {
            System.out.println("Fotoğraf URL'si geçersiz: " + photoUrl);
        }

        article.setIsDeleted(true);
        articleRepository.save(article);
    }

    @Override
    public List<ArticleHomeDto> getHomeArticles() {
        List<ArticleHomeDto> articleDtoList = articleRepository.findAll().stream()
                .filter(x -> !x.getIsDeleted())
                .sorted(Comparator.comparing(Article::getCreatedDate).reversed())
                .map(article -> modelMapper.map(article, ArticleHomeDto.class))
                .collect(Collectors.toList());
        return articleDtoList;
    }

    @Override
    public List<ArticleHomeDto> getMostView() {
        List<ArticleHomeDto> articleDtoList = articleRepository.findAll().stream()
                .filter(x -> !x.getIsDeleted())
                .sorted((a1, a2) -> Integer.compare(a2.getViewCount(), a1.getViewCount())) // Görüntülenme sayısına göre sırala
                .limit(5) // En çok izlenen 5 makaleyi al
                .map(article -> modelMapper.map(article, ArticleHomeDto.class))
                .collect(Collectors.toList());
        return articleDtoList;
    }

//    @Override
//    public ArticleDetailDto articleDetail(Long id) {
//        Article article = articleRepository.findById(id).orElseThrow();
//        ArticleDetailDto articleUpdateDto = modelMapper.map(article, ArticleDetailDto.class);
//        return articleUpdateDto;
//    }



    @Override
    public ArticleDetailDto articleDetail(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + id));

        // View count'u artır
        article.setViewCount(article.getViewCount() + 1);

        // Değişikliği veritabanına kaydet
        articleRepository.save(article);

        ArticleDetailDto articleDetailDto = modelMapper.map(article, ArticleDetailDto.class);

        List<Comment> comments = commentRepository.findByArticleId(id);
        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
        articleDetailDto.setComments(commentDtos);

        return articleDetailDto;
    }
//@Override
//public List<ArticleHomeDto> getHomeArticles() {
//    List<ArticleHomeDto> articleDtoList = articleRepository.findAll().stream()
//            .map(article -> modelMapper.map(article, ArticleHomeDto.class))
//            .collect(Collectors.toList());
//    return articleDtoList;
//}


}
