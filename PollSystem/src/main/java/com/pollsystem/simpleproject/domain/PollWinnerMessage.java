package com.pollsystem.simpleproject.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date expiredAt;
    private String ownerEmail;

    @Override
    public String toString() {
        return "{\"pollQuestion\": \"" + pollQuestion + "\", " +
                "\"winnerOption\": \"" + winnerOption + "\", " +
                "\"winnerPercent\": " + winnerPercent + ", " +
                "\"expiredAt\": \"" + expiredAt + "\", " +
                "\"ownerEmail\": \"" + ownerEmail + "\"}";
    }

}
