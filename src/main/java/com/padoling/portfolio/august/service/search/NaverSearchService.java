package com.padoling.portfolio.august.service.search;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import com.padoling.portfolio.august.web.dto.SearchedBookListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NaverSearchService {

    @Value("${search.api.naver.client-id}")
    private String CLIENT_ID;

    @Value("${search.api.naver.client-secret}")
    private String CLIENT_SECRET;

    @Value("${search.api.naver.url}")
    private String REQUEST_URL;

    private final RestTemplate restTemplate;

    public NaverSearchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private HttpHeaders setHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Naver-Client-Id", CLIENT_ID);
        httpHeaders.set("X-Naver-Client-Secret", CLIENT_SECRET);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return httpHeaders;
    }

    public List<SearchedBookListDto> searchByQuery(NaverSearchRequestDto requestDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REQUEST_URL)
                .queryParam("query", requestDto.getQuery())
                .queryParam("display", requestDto.getDisplay())
                .queryParam("start", requestDto.getStart());

        HttpEntity<?> httpEntity = new HttpEntity<>(setHeaders());
        NaverSearchResponseDto responseDto = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, NaverSearchResponseDto.class).getBody();
        return responseDto.getItems().stream()
                .map(SearchedBookListDto::new)
                .collect(Collectors.toList());
    }
}
