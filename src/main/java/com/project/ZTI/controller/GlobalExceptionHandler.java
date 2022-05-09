package com.project.ZTI.controller;

import com.project.ZTI.exception.RateNotFoundException;
import com.project.ZTI.exception.RestaurantNotFoundException;
import com.project.ZTI.models.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            RestaurantNotFoundException.class,
            RateNotFoundException.class
    })
    @Nullable
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        log.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof RestaurantNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            RestaurantNotFoundException restaurantNotFoundException = (RestaurantNotFoundException) ex;
            return handleRestaurantNotFoundException(restaurantNotFoundException, headers, status, request);
        } else if (ex instanceof RateNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            RateNotFoundException rateNotFoundException = (RateNotFoundException) ex;
            return handleRateNotFoundException(rateNotFoundException, headers, status, request);
        } else {
            log.warn("Unknown exception type: " + ex.getClass().getName());

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    protected ResponseEntity<ApiError> handleRestaurantNotFoundException(RestaurantNotFoundException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleRateNotFoundException(RateNotFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }


    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, @Nullable ApiError body,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}