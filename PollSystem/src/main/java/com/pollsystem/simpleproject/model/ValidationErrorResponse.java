package com.pollsystem.simpleproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    private Date timestamp;
    private int status;
    private String message;
    private String path;
}
