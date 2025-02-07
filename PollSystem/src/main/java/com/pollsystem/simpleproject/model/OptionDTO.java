package com.pollsystem.simpleproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionDTO {

    private Long id;
    private String message;
    private Date createdAt;

}
