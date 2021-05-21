package com.gtf.library.model.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int httpCode;
    private String httpMessage;
    private String moreInformation;
}
