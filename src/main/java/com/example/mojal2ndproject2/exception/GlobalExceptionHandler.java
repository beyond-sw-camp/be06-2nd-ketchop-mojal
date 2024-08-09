package com.example.mojal2ndproject2.exception;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<BaseResponseStatus>> BaseResponseHandler(BaseException e){
        BaseResponse<BaseResponseStatus> response = new BaseResponse<>(e.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //@Valid의 예외처리부분
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .ok()
                .body(new BaseResponse<>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

//    @Getter
//    @AllArgsConstructor
//    public static class Response {
//        private String code;
//        private String message;
//    }

}
