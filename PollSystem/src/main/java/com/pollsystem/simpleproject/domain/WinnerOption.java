package com.pollsystem.simpleproject.domain;


import com.pollsystem.simpleproject.services.OptionService;
import com.pollsystem.simpleproject.services.PollService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WinnerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private Long pollId;
    private Long optionId;
    private int percentOfWinner;

    public WinnerOption(Long pollId, Long optionId, int percentOfWinner) {
        this.pollId = pollId;
        this.optionId = optionId;
        this.percentOfWinner = percentOfWinner;
    }
}
