package com.pollsystem.simpleproject.domain;


import com.pollsystem.simpleproject.services.OptionService;
import com.pollsystem.simpleproject.services.PollService;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinnerOption {

    private Long pollId;
    private Long optionId;
    private int percentOfWinner;

}
