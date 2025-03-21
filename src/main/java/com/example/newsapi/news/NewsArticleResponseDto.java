package com.example.newsapi.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsArticleResponseDto {
    private String ynaTtl;
    private String newsLink;
    private String ynaCn;
    private String ynaYmd;

    public NewsArticleResponseDto(String ynaTtl, String newsLink, String ynaCn, String ynaYmd) {
        this.ynaTtl = ynaTtl;
        this.newsLink = newsLink;
        this.ynaCn = ynaCn;
        this.ynaYmd = ynaYmd;
    }

}
