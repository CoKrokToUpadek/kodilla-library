package com.kodillalibrary;

import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary._resources.CorruptedDataException;
import com.kodillalibrary._resources.EmptyListException;
import com.kodillalibrary._resources.FailedToFetchDataException;
import com.kodillalibrary.controller.LibraryBooksAdministrationController;
import com.kodillalibrary.controller.LibraryRentalsController;
import com.kodillalibrary.controller.LibraryUserController;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import com.kodillalibrary.mapper.BookCopyMapper;
import com.kodillalibrary.mapper.BookTitleMapper;
import com.kodillalibrary.repository.LibraryBookCopyRepository;
import com.kodillalibrary.repository.LibraryBookRentalRepository;
import com.kodillalibrary.repository.LibraryBookTitleRepository;
import com.kodillalibrary.repository.LibraryUsersRepository;
import com.kodillalibrary.service.DbService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@SpringBootTest
public class LibraryRentalsControllerTests {

    @Autowired
    LibraryBooksAdministrationController libraryBooksAdministrationController;
    @Autowired
    LibraryRentalsController libraryRentalsController;
    @Autowired
    LibraryUserController libraryUserController;


    @Autowired
    DbService dbService;

    @Autowired
    LibraryUsersRepository libraryUsersRepository;
    @Autowired
    LibraryBookRentalRepository libraryBookRentalRepository;
    @Autowired
    LibraryBookTitleRepository libraryBookTitleRepository;
    @Autowired
    LibraryBookCopyRepository libraryBookCopyRepository;
    @Autowired
    BookCopyMapper bookCopyMapper;

    @Autowired
    BookTitleMapper bookTitleMapper;

    @AfterEach
    void deleteAll(){
        libraryBookRentalRepository.deleteAll();
        libraryBookCopyRepository.deleteAll();
        libraryBookTitleRepository.deleteAll();
        libraryUsersRepository.deleteAll();
    }

    @Test
    void testBookCopyRental() throws CorruptedDataException, FailedToFetchDataException {
        //given & when
        libraryUserController.addUser(new UserDto(null, "testFirstName", "testLastName", null, null));
        libraryBooksAdministrationController.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        libraryBooksAdministrationController.addBookCopy("test title");
        libraryRentalsController.bookCopyRental("test title",libraryUsersRepository.findAll().get(0).getId());
        User user=libraryUsersRepository.findAll().get(0);
        //then
        Assertions.assertEquals(libraryBookCopyRepository.findAll().get(0).getStatus(), BookStatusEnum.RENTED.toString());
        Assertions.assertEquals(user.getId(), libraryBookRentalRepository.findAll().get(0).getUserID().getId());

    }


    @Test
    void testBookCopyReturn() throws CorruptedDataException, FailedToFetchDataException, EmptyListException {
        //given & when
        libraryUserController.addUser(new UserDto(null, "testFirstName", "testLastName", null, null));
        libraryBooksAdministrationController.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        libraryBooksAdministrationController.addBookCopy("test title");
        libraryRentalsController.bookCopyRental("test title",libraryUsersRepository.findAll().get(0).getId());
        libraryRentalsController.bookCopyReturn(libraryBookCopyRepository.findAll().get(0).getId());
        //then
        Assertions.assertEquals(libraryBookCopyRepository.findAll().get(0).getStatus(), BookStatusEnum.AVAILABLE.toString());


    }
    @Test
    void testGetBookRental() throws CorruptedDataException, FailedToFetchDataException {
        //given & when
        libraryUserController.addUser(new UserDto(null, "testFirstName", "testLastName", null, null));
        libraryBooksAdministrationController.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        libraryBooksAdministrationController.addBookCopy("test title");
        libraryRentalsController.bookCopyRental("test title",libraryUsersRepository.findAll().get(0).getId());
        ResponseEntity<BookRentalDto> rental= libraryRentalsController.getBookRental(libraryBookRentalRepository.findAll().get(0).getId());
        //then
        Assertions.assertEquals(rental.getBody().getRentalDate(), LocalDate.now());

    }

    @Test
    void testEditBookCopyStatus() throws CorruptedDataException {
        //given & when
        libraryBooksAdministrationController.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        libraryBooksAdministrationController.addBookCopy("test title");
        libraryRentalsController.editBookCopyStatus(libraryBookCopyRepository.findAll().get(0).getId(),BookStatusEnum.LOST);
        //then
        Assertions.assertEquals(libraryBookCopyRepository.findAll().get(0).getStatus(), BookStatusEnum.LOST.toString());


    }

}
