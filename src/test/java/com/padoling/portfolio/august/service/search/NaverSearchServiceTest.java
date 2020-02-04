package com.padoling.portfolio.august.service.search;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverSearchServiceTest {

    @Autowired
    private NaverSearchService naverSearchService;

    @Test
    public void testSearchByQuery() {
        //given
        String query = "test";
        Integer display = 10;
        Integer start = 1;
        NaverSearchRequestDto requestDto = NaverSearchRequestDto.builder()
                .query(query)
                .start(start)
                .build();

        //when
        NaverSearchResponseDto responseDto = naverSearchService.searchByQuery(requestDto);

        //then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getDisplay()).isEqualTo(display);
        assertThat(responseDto.getLastBuildDate()).isNotNull();
        assertThat(responseDto.getStart()).isEqualTo(start);
        assertThat(responseDto.getTotal()).isNotNull();
        assertThat(responseDto.getItems()).isNotNull();
        assertThat(responseDto.getItems().size()).isEqualTo(display);
    }
}