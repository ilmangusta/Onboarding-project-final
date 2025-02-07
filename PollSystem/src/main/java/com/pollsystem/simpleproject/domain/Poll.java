package com.pollsystem.simpleproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Poll {

    private String question;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String owner;
    private Date expiresAt;
    private String status;

    @JsonIgnore
    @OneToMany(
            mappedBy = "poll",
            cascade = CascadeType.REMOVE//,cascade = CascadeType.ALL
    )
    private Set<Option> options = new HashSet<Option>();

    public Poll(String question, String owner,Date Expireat, String status) {
        this.question = question;
        this.owner = owner;
        this.expiresAt = Expireat;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", expireDate=" + expiresAt +
                ", status='" + status + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
