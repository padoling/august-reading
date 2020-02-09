package com.padoling.portfolio.august.service.search;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import com.padoling.portfolio.august.web.dto.SearchedBookListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        Integer start = 1;
        NaverSearchRequestDto requestDto = NaverSearchRequestDto.builder()
                .query(query)
                .start(start)
                .display(display)
                .build();

        //when
        List<SearchedBookListDto> bookListDto = naverSearchService.searchByQuery(requestDto);

        //then
        assertThat(bookListDto).isNotNull();
        assertThat(bookListDto.size()).isEqualTo(display);
        assertThat(bookListDto.get(0)).isNotNull();
        System.out.println(bookListDto.get(0).toString());
    }
}