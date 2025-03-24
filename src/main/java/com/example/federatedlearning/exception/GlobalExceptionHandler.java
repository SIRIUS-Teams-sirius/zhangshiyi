/*
package com.example.federatedlearning.exception;

import com.example.federatedlearning.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
*/
package com.example.federatedlearning.exception;

import com.example.federatedlearning.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "操作失败");
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Result handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, Object> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage()));
//        return Result.error(400,"参数校验失败", errors);
//    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Result<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, List<String>> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
            });
            return Result.error(400, "参数校验失败", errors);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public Result<String> handleIllegalArgumentException(IllegalArgumentException ex) {
            return Result.error(400, ex.getMessage());
        }

}