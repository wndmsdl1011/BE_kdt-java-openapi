package com.example.newsapi.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsArticleDescDto {
    private String ynaTtl;
    private String newsLink;
    private String ynaYmd;

    public NewsArticleDescDto(String ynaTtl, String newsLink, String ynaYmd) {
        this.newsLink = newsLink;
        this.ynaTtl = ynaTtl;
        this.ynaYmd = ynaYmd;
    }
}
