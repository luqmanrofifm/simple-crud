package com.example.simple_crud.requestdto;

import com.example.simple_crud.common.dto.BaseDto;
import com.example.simple_crud.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class RequestBookDto extends BaseDto<Book> {
    private String title;
    private String author;
    private Integer year;

    @JsonIgnore
    private String search;
}
