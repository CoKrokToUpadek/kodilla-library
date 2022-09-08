package com.kodillalibrary.domain.book_title;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "titles")
public class Title {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private LocalDate releaseDate;
}
