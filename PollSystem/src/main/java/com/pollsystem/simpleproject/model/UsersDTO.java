package com.pollsystem.simpleproject.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class UsersDTO extends LoginModelDTO{
    private Long id;
    private String token;
}
