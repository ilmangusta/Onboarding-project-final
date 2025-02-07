package com.pollsystem.simpleproject.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RegisterModelDTO extends LoginModelDTO{

    private String email;
}
