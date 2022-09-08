package com.kodillalibrary.domain.book_copy;

import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "book-copies")
public class BookCopy {
    @Id
    @GeneratedValue
    private Long id;
    //private Title titleInfo;
    private BookStatusEnum status;
}
