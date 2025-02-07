package com.bookshop.simpleproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Editor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Name;
    private String PhisicalAddress;

    @OneToMany(mappedBy = "editor")
    //@JoinColumn(name = "editor_id")
    private final Set<Book> books =  new HashSet<>();

    public Editor() {
    }

    public Editor(String name, String phisicalAddress) {
        this.Name = name;
        this.PhisicalAddress = phisicalAddress;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", PhisicalAddress='" + PhisicalAddress + '\'' +
                '}';
    }
}
