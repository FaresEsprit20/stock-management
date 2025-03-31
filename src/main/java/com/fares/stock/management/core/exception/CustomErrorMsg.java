package com.fares.stock.management.core.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomErrorMsg {

    private Integer httpCode;

    private ErrorCodes code;

    private String message;

    private List<String> errors = new ArrayList<>();

}
