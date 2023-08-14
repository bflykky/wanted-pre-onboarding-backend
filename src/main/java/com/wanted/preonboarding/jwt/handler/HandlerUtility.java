package com.wanted.preonboarding.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.error.ErrorCode;
import com.wanted.preonboarding.error.ErrorResponse;
import com.wanted.preonboarding.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class HandlerUtility {
    public static void writeResponse(HttpServletRequest request, HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (OutputStream os = response.getOutputStream()){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.of(errorCode));
            os.flush();
        }
    }

    public static void writeResponse(HttpServletRequest request, HttpServletResponse response, ErrorCode errorCode, String message) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (OutputStream os = response.getOutputStream()){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.of(errorCode, message));
            os.flush();
        }
    }

    public static void writeResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String code, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (OutputStream os = response.getOutputStream()){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.of(status.value(), code, message));
            os.flush();
        }
    }

    public static void writeResponse(HttpServletRequest request, HttpServletResponse response, ResultResponse resultResponse) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, resultResponse);
            os.flush();
        }
    }
}
