package com.example.newsapi.news;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsArticleService {
    private NewsArticleRepository newsArticleRepository;

    @Autowired
    public NewsArticleService(NewsArticleRepository newsArticleRepository) {
        this.newsArticleRepository = newsArticleRepository;
    }

    public Page<NewsArticle> getAllNewsArticles(Pageable pageable) {
        return newsArticleRepository.findAll(pageable);
    }

    public Page<NewsArticleDescDto> getNewsArticleByDescription(Pageable pageable) {
        Page<NewsArticle> newsArticles = newsArticleRepository.findAll(pageable);
        List<NewsArticleDescDto> newsDescDto = newsArticles.getContent().stream()
                .map(article -> new NewsArticleDescDto(
                        article.getYnaTtl(),
                        article.getNewsLink(),
                        article.getYnaYmd()))
                .collect(Collectors.toList());
        return new PageImpl<>(newsDescDto, pageable, newsArticles.getTotalElements());
    }

    public Page<NewsArticleResponseDto> searchNewsArticles(String ynaTtl,Pageable pageable) {
        Page<NewsArticle> newsArticles = newsArticleRepository.findByYnaTtlContainingIgnoreCase(ynaTtl, pageable);
        if(newsArticles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"검색어에 맞는 기사가 없습니다!");
        }
        List<NewsArticleResponseDto> newsResDto = newsArticles.getContent().stream()
                .map(article -> new NewsArticleResponseDto(
                        article.getYnaTtl(),
                        article.getNewsLink(),
                        article.getYnaCn(),
                        article.getYnaYmd()))
                .collect(Collectors.toList());
        return new PageImpl<>(newsResDto, pageable, newsArticles.getTotalElements());

    }
}
