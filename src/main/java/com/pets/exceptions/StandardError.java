package com.pets.exceptions;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
