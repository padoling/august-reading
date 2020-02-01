package com.padoling.portfolio.august.search;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import com.padoling.portfolio.august.service.search.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NaverSearchApiController {

    private final NaverSearchService naverSearchService;

    @PostMapping("/api/v1/search")
    public NaverSearchResponseDto search(@RequestBody NaverSearchRequestDto requestDto) {
        return naverSearchService.searchByQuery(requestDto);
    }
}
