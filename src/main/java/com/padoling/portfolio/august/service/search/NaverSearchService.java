package com.padoling.portfolio.august.service.search;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        httpHeaders.add("X-Naver-Client-Id", CLIENT_ID);
        httpHeaders.add("X-Naver-Client-Secret", CLIENT_SECRET);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return httpHeaders;
    }

    public NaverSearchResponseDto searchByQuery(NaverSearchRequestDto requestDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REQUEST_URL)
                .queryParam("query", requestDto.getQuery())
                .queryParam("display", requestDto.getDisplay())
                .queryParam("start", requestDto.getStart());

        HttpEntity<?> httpEntity = new HttpEntity<>(setHeaders());
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, NaverSearchResponseDto.class).getBody();
    }
}
