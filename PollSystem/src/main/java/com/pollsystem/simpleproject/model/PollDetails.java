package com.pollsystem.simpleproject.model;

import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.WinnerOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollDetails {

    private Long id;
    private String owner;
    private Date expiresAt;
    private String status;
    private String question;
    private WinnerOption winner;
    private Set<Option> options;

}
