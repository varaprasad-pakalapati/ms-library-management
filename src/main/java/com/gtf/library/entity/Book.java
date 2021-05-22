package com.gtf.library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String isbn;

    @Column(length = 50, nullable = false, unique = true)
    private String title;

    private long noOfPages;

    private LocalDateTime publicationDate = LocalDateTime.now();

    @Column(nullable = false)
    private int noOfCopies;

    @OneToMany(mappedBy = "bookId")
    private List<BookRent> bookRents;
}
