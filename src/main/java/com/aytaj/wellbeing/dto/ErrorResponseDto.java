package com.aytaj.wellbeing.dto;

import com.aytaj.wellbeing.util.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private CustomResponseStatus status;
    private String message;
}
