package com.padoling.portfolio.august.search;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NaverSearchService {

    @Value("${search.api.naver.client-id}")
    private String CLIENT_ID;

    @Value("${search.api.naver.client-secret}")
    private String CLIENT_SECRET;

    @Value("${search.api.naver.url}")
    private String REQUEST_URL;

    private RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders setHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Naver-Client-Id", CLIENT_ID);
        httpHeaders.set("X-Naver-Client-Secret", CLIENT_SECRET);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

        return httpHeaders;
    }

    private boolean isLast(Integer start, Integer display, Integer total) {
        return start + display > 1000 || start + display > total;
    }

    public NaverSearchResponseDto searchByQuery(NaverSearchRequestDto requestDto) {
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(REQUEST_URL)
                .queryParam("query", requestDto.getQuery())
                .queryParam("display", requestDto.getDisplay())
                .queryParam("start", requestDto.getStart())
                .build();

        HttpEntity<?> httpEntity = new HttpEntity<>(setHeaders());
        NaverSearchResponseDto responseDto = restTemplate.exchange(uri.toString(), HttpMethod.GET, httpEntity, NaverSearchResponseDto.class).getBody();
        responseDto.setQuery(requestDto.getQuery());
        if(requestDto.getStart() == 1) {
            responseDto.setFirst(true);
        }
        responseDto.setLast(isLast(requestDto.getStart(), requestDto.getDisplay(), responseDto.getTotal()));
        return responseDto;
    }
}
