package com.bookshop.simpleproject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "editor_id")
    private Editor editor;

    private String title;
    private double price;
    private int pages;
    private int quantity;

    public Book(String title, double price, int pages, int quantity) {
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author=" + author +
                ", editor=" + editor +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", pages=" + pages +
                ", quantity=" + quantity +
                '}';
    }
}
