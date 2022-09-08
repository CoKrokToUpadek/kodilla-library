package com.kodillalibrary.domain.book_rental;

import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.users.User;
import lombok.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "book-rentals")
public class BookRental {
    @Id
    @GeneratedValue
    private Long id;
//    private BookCopy copyID;
//    private User userID;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}