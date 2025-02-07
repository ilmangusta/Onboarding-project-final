package com.pollsystem.simpleproject.domain;

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

    @ManyToOne(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "option_id")
    private Option option;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                '}';
    }
}
