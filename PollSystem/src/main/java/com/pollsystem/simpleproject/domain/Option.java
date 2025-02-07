package com.pollsystem.simpleproject.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;
    private Date createdAt;

    @JsonIgnore
    @OneToMany(
            mappedBy = "option",
            cascade = CascadeType.REMOVE
    )
    private Set<Vote> votes = new HashSet<>();

    @JsonIgnore
    @ManyToOne(
            cascade =  CascadeType.PERSIST
    )
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public Option(String message, Date createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
