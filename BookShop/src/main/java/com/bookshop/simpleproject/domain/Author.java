package com.bookshop.simpleproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String FirstName;
    private String LastName;
    private Date Birthday= null;

    @OneToMany(mappedBy = "author")
    //@JoinColumn(name = "author_id") o joincolumn o mappedby...
    private Set<Book> books = new HashSet<>();

    public Author(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Birthday=" + Birthday +
                '}';
    }
}
