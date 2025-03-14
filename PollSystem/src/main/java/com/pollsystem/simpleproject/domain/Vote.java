package com.pollsystem.simpleproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date votedAt;

    @JsonIgnore
    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "user_id")
    private Users user;

    @JsonIgnore
    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "option_id")
    private Option option;

    public Vote(Date votedAt) {
        this.votedAt = votedAt;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", votedAt=" + votedAt +
                ", option=" + option +
                '}';
    }
}
