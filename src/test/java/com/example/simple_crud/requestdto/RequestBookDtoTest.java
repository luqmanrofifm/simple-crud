package com.example.simple_crud.requestdto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBookDtoTest {

    private RequestBookDto requestBookDtoUnderTest;

    @BeforeEach
    void setUp() {
        requestBookDtoUnderTest = new RequestBookDto();
    }

    @Test
    void testTitleGetterAndSetter() {
        final String title = "title";
        requestBookDtoUnderTest.setTitle(title);
        assertThat(requestBookDtoUnderTest.getTitle()).isEqualTo(title);
    }

    @Test
    void testAuthorGetterAndSetter() {
        final String author = "author";
        requestBookDtoUnderTest.setAuthor(author);
        assertThat(requestBookDtoUnderTest.getAuthor()).isEqualTo(author);
    }

    @Test
    void testYearGetterAndSetter() {
        final Integer year = 2020;
        requestBookDtoUnderTest.setYear(year);
        assertThat(requestBookDtoUnderTest.getYear()).isEqualTo(year);
    }

    @Test
    void testSearchGetterAndSetter() {
        final String search = "search";
        requestBookDtoUnderTest.setSearch(search);
        assertThat(requestBookDtoUnderTest.getSearch()).isEqualTo(search);
    }
}
