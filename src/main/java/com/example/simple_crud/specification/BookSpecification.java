package com.example.simple_crud.specification;

import com.example.simple_crud.entity.Book;
import com.example.simple_crud.requestdto.RequestBookDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification implements Specification<Book> {
    private final RequestBookDto dto;
    public BookSpecification(RequestBookDto dto){
        this.dto = dto;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(dto.getSearch())){
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + dto.getSearch() + "%"));
        }

        if (!predicates.isEmpty()){
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }

        return null;
    }
}
