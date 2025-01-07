package com.example.simple_crud.common.controller;

import com.example.simple_crud.common.Response;
import com.example.simple_crud.exceptions.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

public class BaseController {
    public BaseController() {
    }

    public ResponseEntity<Object> success(Object data) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "success", data), HttpStatus.OK);
    }

    public ResponseEntity<Object> error(Object data) {
        return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", data), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> badRequest(Object data) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), "bad_request", data), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> notFound(Object data) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), "not_found", data), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> forbidden(Object data) {
        return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), "forbidden", data), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> success(Object data, String message) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), message, data), HttpStatus.OK);
    }

    public ResponseEntity<Object> badRequest(Object data, String message) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), message, data), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> forbidden(Object data, String message) {
        return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), message, data), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> notFound(Object data, String message) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), message, data), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> error(HttpStatus httpStatus, Object message) {
        return new ResponseEntity<>(new Response(httpStatus.value(), "error", message), httpStatus);
    }

    public ResponseEntity<Resource> okDownload(String filename, String mediaType, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().headers(headers)).contentLength((long)data.length).contentType(MediaType.parseMediaType(mediaType)).body(new ByteArrayResource(data));
    }

    public Pageable pageFromRequest(int page, int size, String sort, Boolean asc) {
        if (ObjectUtils.isEmpty(sort)) {
            sort = "id";
        }

        return PageRequest.of(page, size, Sort.by(new Sort.Order[]{this.getSortBy(sort, asc, true)}));
    }

    public Sort.Order getSortBy(String sort, Boolean asc, Boolean ignoreCase) {
        if (Boolean.FALSE.equals(ignoreCase)) {
            return Boolean.TRUE.equals(asc) ? Sort.Order.asc(sort) : Sort.Order.desc(sort);
        } else {
            return Boolean.TRUE.equals(asc) ? Sort.Order.asc(sort).ignoreCase() : Sort.Order.desc(sort).ignoreCase();
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        Iterator<FieldError> var4 = fieldErrorList.iterator();
        List<Map<String, String>> messages = new ArrayList();

        while(var4.hasNext()) {
            FieldError field = (FieldError)var4.next();
            Map<String, String> validatedItem = new HashMap();
            validatedItem.put(field.getField(), StringUtils.capitalize(field.getField()) + " " + field.getDefaultMessage());
            messages.add(validatedItem);
        }

        return this.badRequest(messages, HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return this.badRequest(ex.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        return this.badRequest(ex.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return this.badRequest(ex.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex) {
        return this.badRequest(ex.getMessage());
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Object> baseExceptionHandler(HttpServletRequest request, BaseException ex) {
        String text = "";

        try {
            String lang = request.getAttribute("lang").toString();
            ResourceBundle message = ResourceBundle.getBundle("messages", lang.equals("id") ? Locale.ROOT : Locale.ENGLISH);
            text = message.getString(ex.getMessage());
            if (ex.getValues() != null) {
                int i = 0;
                String[] var7 = ex.getValues();
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String value = var7[var9];
                    text = text.replace("{" + i + "}", value);
                    ++i;
                }
            }
        } catch (Exception var11) {
            text = ex.getMessage();
        }

        if (ex.getErrorCode() == 400) {
            return this.badRequest((Object)null, text);
        } else {
            return ex.getErrorCode() == 404 ? this.notFound((Object)null, text) : this.error(HttpStatus.EXPECTATION_FAILED, text);
        }
    }
}

