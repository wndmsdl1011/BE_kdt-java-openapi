package com.example.newsapi.news;

import com.example.newsapi.GlobalExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/news")
@Tag(name = "News Api", description = "재난 뉴스 API 엔드포인트")
public class NewsArticleController {
    private NewsArticleService newsArticleService;

    @Autowired
    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }



    @GetMapping("/all")
    @Operation(summary = "모든 뉴스 기사 데이터 조회", description = "데이터베이스에 저장된 모든 뉴스 기사 데이터를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 뉴스 기사를 반환",
                        content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                        content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public Page<NewsArticle> getAllNewsArticles(
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return newsArticleService.getAllNewsArticles(pageable);
    }



    @GetMapping("/description")
    @Operation(summary = "뉴스 기사만 조회", description = "뉴스 기사의 제목, 링크, 내용을 포함한 목록을 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 뉴스 목록 반환",
                        content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                        content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public Page<NewsArticleDescDto> getNewsArticleByDescription(
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return newsArticleService.getNewsArticleByDescription(pageable);
    }



    @GetMapping
    @Operation(summary = "키워드로 뉴스 기사 제목 검색", description = "제목(ynaTtl)에 검색어가 포함된 뉴스 기사를 반환. 검색어가 없으면 오류를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 검색된 뉴스 기사 반환",
                        content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "검색어가 입력되지 않음",
                        content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "검색어에 맞는 기사가 없음",
                        content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                        content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public Page<NewsArticleResponseDto> searchNewsArticles(
            @Parameter(description = "검색할 뉴스 제목 키워드", example = "자전거", required = false)
            @RequestParam(value = "ynaTtl", required = false) String ynaTtl,
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size
    )
    {
        if(ynaTtl != null && !ynaTtl.isEmpty()){
            Pageable pageable = PageRequest.of(page - 1, size);
            return newsArticleService.searchNewsArticles(ynaTtl,pageable);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어를 입력하세요");
    }


}
