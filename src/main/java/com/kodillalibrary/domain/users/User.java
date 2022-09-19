package com.kodillalibrary.domain.users;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kodillalibrary.domain.book_rental.BookRental;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="account_creation_date")
    private LocalDate accountCreationDate;

    @OneToMany(mappedBy = "userID")
    @JsonManagedReference
    private List<BookRental> bookRentals;
}
