package com.kodillalibrary.domain.book_title;

import com.kodillalibrary.domain.book_copy.BookCopy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "book_titles")
public class BookTitle {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="release_date")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "title")
    private List<BookCopy>bookCopies;
}
