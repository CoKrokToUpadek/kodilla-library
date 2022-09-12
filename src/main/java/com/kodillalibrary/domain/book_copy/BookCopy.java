package com.kodillalibrary.domain.book_copy;

import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_rental.BookRental;
import com.kodillalibrary.domain.book_title.BookTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "book_copies")
public class BookCopy {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_title_id",referencedColumnName = "id")
    private BookTitle title;
    @Column(name="book_status")
    private BookStatusEnum status;


    @OneToMany(mappedBy = "copyID")
    private List<BookRental> bookRentals;
}
