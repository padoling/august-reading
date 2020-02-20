package com.padoling.portfolio.august.search;

import com.padoling.portfolio.august.search.NaverSearchService;
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
        String query = "주식";
        Integer display = 10;
        Integer start = 999;
        NaverSearchRequestDto requestDto = NaverSearchRequestDto.builder()
                .query(query)
                .start(start)
                .display(display)
                .build();

        //when
        NaverSearchResponseDto responseDto = naverSearchService.searchByQuery(requestDto);

        //then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getQuery()).isEqualTo(query);
        assertThat(responseDto.getStart()).isEqualTo(start);
        assertThat(responseDto.getDisplay()).isEqualTo(display);
        assertThat(responseDto.isFirst()).isEqualTo(false);
        assertThat(responseDto.isLast()).isEqualTo(true);
        assertThat(responseDto.getItems().get(0)).isNotNull();
        System.out.println(responseDto.getItems().get(0).toString());
    }
}