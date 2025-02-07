package com.pollsystem.simpleproject.model;

import com.pollsystem.simpleproject.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollDTO {

    private Long id;
    private Date expiresAt;
    private String status;
    private String owner;
    private String question;

}
