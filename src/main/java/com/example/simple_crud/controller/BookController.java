package com.example.simple_crud.controller;

import com.example.simple_crud.common.controller.BaseController;
import com.example.simple_crud.exceptions.BaseException;
import com.example.simple_crud.requestdto.RequestBookDto;
import com.example.simple_crud.service.BookService;
import com.example.simple_crud.entity.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/crud/book")
@Tag(name="Book Controller")
public class BookController extends BaseController {
    private final BookService bookService;

    protected BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({""})
    @Operation(description = "Get List", security = {@SecurityRequirement(name = "OAuth2PasswordBearer")})
    public ResponseEntity<Object> list(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "100") int limit,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "true") boolean asc,
            @RequestParam(required = false) String search) {
        Pageable pageable = this.pageFromRequest(page, limit, sort, asc);
        RequestBookDto dto = new RequestBookDto();
        dto.setSearch(search);
        try {
            PagedModel<Book> result = this.bookService.findAll(dto, pageable);
            return this.success(result);
        } catch (BaseException e) {
            return this.error(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @GetMapping({"/{id}"})
    @Operation(description = "Get Detail", security = {@SecurityRequirement(name = "OAuth2PasswordBearer")})
    public ResponseEntity<Object> get(@PathVariable UUID id) {
        try {
            Optional<Book> optionalE = this.bookService.get(id);
            return optionalE.isPresent() ? this.success(optionalE.get()) : this.error(HttpStatus.NOT_FOUND, "Object not found");
        } catch (BaseException e) {
            return this.error(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping({""})
    @Operation(description = "Create Data", security = {@SecurityRequirement(name = "OAuth2PasswordBearer")})
    public ResponseEntity<Object> create(@RequestBody @Valid RequestBookDto dto) {
        try {
            return this.success(this.bookService.createFromDto(dto));
        } catch (BaseException e) {
            return this.error(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PutMapping({"/{id}"})
    @Operation(description = "Update Data", security = {@SecurityRequirement(name = "OAuth2PasswordBearer")})
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Valid RequestBookDto dto) {
        try {
            Optional<Book> optionalE = this.bookService.get(id);
            if (optionalE.isPresent()) {
                Book updatedEntity = this.bookService.update(optionalE.get(), dto);
                return this.success(updatedEntity);
            } else {
                return this.error(HttpStatus.NOT_FOUND, "Object not found");
            }
        } catch (BaseException e) {
            return this.error(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @DeleteMapping({"/{id}"})
    @Operation(description = "Delete Data", security = {@SecurityRequirement(name = "OAuth2PasswordBearer")})
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            Optional<Book> optionalE = this.bookService.get(id);
            if (optionalE.isPresent()) {
                this.bookService.delete(optionalE.get());
                return this.success("Object was deleted");
            } else {
                return this.error(HttpStatus.NOT_FOUND, "Object not found");
            }
        } catch (BaseException e) {
            return this.error(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }
}
