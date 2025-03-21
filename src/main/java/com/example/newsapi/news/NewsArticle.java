package com.example.newsapi.news;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "news_articles")
@Getter
@Setter
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "yna_no", unique = true)
    private long ynaNo;

    @Column(name = "crt_dt")
    private String crtDt;

    @Column(name = "yna_wrtr_nm")
    private String ynaWrtrNm;

    @Column(name = "yna_cn", columnDefinition = "CLOB")
    private String ynaCn;

    @Column(name = "yna_ymd")
    private String ynaYmd;

    @Column(name = "yna_ttl")
    private String ynaTtl;

    @Column(name = "news_link")
    private String newsLink;
}
