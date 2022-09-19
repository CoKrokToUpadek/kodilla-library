package com.kodillalibrary.domain.book_copy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_title_id",referencedColumnName = "id")
    @JsonBackReference
    private BookTitle title;

    @Column(name="book_status")
    private String status;

    @OneToMany(mappedBy = "copyID")
    @JsonManagedReference
    private List<BookRental> bookRentals;

}
