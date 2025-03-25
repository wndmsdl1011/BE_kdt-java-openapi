package com.example.newsapi.disaster;

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
@RequestMapping("/api/disaster")
@Tag(name = "Disaster Message API", description = "재난 문자 API 엔드포인트")
public class DisasterMessageController {
    private final DisasterMessageService disasterMessageService;

    @Autowired
    public DisasterMessageController(DisasterMessageService disasterMessageService) {
        this.disasterMessageService = disasterMessageService;
    }

    @GetMapping("/all")
    @Operation(summary = "모든 재난 문자 데이터 조회", description = "데이터베이스에 저장된 모든 재난 문자 데이터 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "데이터 조회 성공",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public Page<DisasterMessage> getAllDisasterMessages(
            @Parameter(description = "페이지 번호(1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        return disasterMessageService.findAllByOrderByCrtDtDesc(pageable);
    }



    @GetMapping
    @Operation(summary = "키워드로 재난 문자 내용 검색", description = "재난 문자 내용에 키워드가 포함된 데이터 출력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 검색된 재난 문자 반환",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "검색어가 입력되지 않음",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "검색어에 맞는 재난 문자가 없음",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))
    })
    public Page<DisasterMessage> searchDisasterMessages(
            @Parameter(description = "검색할 키워드", example = "강풍", required = false)
            @RequestParam(value = "keyword", required = false) String keyword,
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지당 항목 수", example = "10")
            @RequestParam(defaultValue = "10") int size)
    {
        if (keyword == null || keyword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요.");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DisasterMessage> result = disasterMessageService.searchDisasterMessages(keyword, pageable);
        if(result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "검색어가 포함된 재난 문자가 없습니다.");
        }
        return result;
    }
}
