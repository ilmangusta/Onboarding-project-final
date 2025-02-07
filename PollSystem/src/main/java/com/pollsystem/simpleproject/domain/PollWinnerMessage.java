package com.pollsystem.simpleproject.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PollWinnerMessage {

    private String pollQuestion;
    private String winnerOption;
    private int winnerPercent;
    private Date expiredAt;
    private String ownerEmail;
}
