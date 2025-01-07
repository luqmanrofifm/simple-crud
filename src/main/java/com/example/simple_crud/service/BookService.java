package com.example.simple_crud.service;

import com.example.simple_crud.constant.CurrentUser;
import com.example.simple_crud.entity.Book;
import com.example.simple_crud.exceptions.BaseException;
import com.example.simple_crud.repository.BookRepository;
import com.example.simple_crud.requestdto.RequestBookDto;
import com.example.simple_crud.specification.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CurrentUser currentUser;

    protected BookService(BookRepository repository, CurrentUser currentUser) {
        this.bookRepository = repository;
        this.currentUser = currentUser;
    }

    public Specification<Book> instantiateSpecification(RequestBookDto dto) {
        return new BookSpecification(dto);
    }

    public PagedModel<Book> findAll(RequestBookDto dto, Pageable pageable) throws BaseException {
        Specification<Book> spec = this.instantiateSpecification(dto);
        Page<Book> books = this.bookRepository.findAll(spec, pageable);
        return new PagedModel<>(books);
    }

    public Optional<Book> get(UUID id) throws BaseException {
        return this.bookRepository.findById(id);
    }


    public Book createFromDto(RequestBookDto dto) throws BaseException {
        Book entity = dto.fromDto();
        entity.setCreatedDate(new Date());
        entity.setCreatedBy(this.getCurrentUser());
        this.bookRepository.save(entity);
        return entity;
    }

    public Book update(Book entity, RequestBookDto dto) throws BaseException {
        dto.copyFromDto(entity);
        entity.setUpdatedDate(new Date());
        entity.setUpdatedBy(this.getCurrentUser());
        return this.bookRepository.save(entity);
    }

    public void delete(Book entity) throws BaseException {
        this.bookRepository.delete(entity);
    }

    public String getCurrentUser(){
        return this.currentUser.getEmail();
    }
}
