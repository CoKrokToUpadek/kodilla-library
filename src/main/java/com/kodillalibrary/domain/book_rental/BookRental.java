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
@Entity(name = "book_rentals")
public class BookRental {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "copy_id",referencedColumnName = "id")
    private BookCopy copyID;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userID;

    @Column(name="rental_date")
    private LocalDate rentalDate;
    @Column(name="return_date")
    private LocalDate returnDate;
}
