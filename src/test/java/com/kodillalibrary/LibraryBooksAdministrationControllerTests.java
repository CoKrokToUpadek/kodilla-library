package com.kodillalibrary;

import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary._resources.CorruptedDataException;
import com.kodillalibrary._resources.EmptyListException;
import com.kodillalibrary._resources.FailedToFetchDataException;
import com.kodillalibrary.controller.LibraryBooksAdministrationController;
import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_copy.BookCopyDto;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.book_title.BookTitle;
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
import java.util.List;


@SpringBootTest
class LibraryBooksAdministrationControllerTests {

    @Autowired
    LibraryBooksAdministrationController controller;
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
    void testAddBookTitle() throws CorruptedDataException {
        //given & when
        controller.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        BookTitle title = libraryBookTitleRepository.findAll().get(0);
        //then
        Assertions.assertEquals(title.getTitle(), "test title");

    }

    @Test
    void testAddBookCopy() throws CorruptedDataException {
        //given & when
        controller.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        controller.addBookCopy("test title");
        BookCopy copy = libraryBookCopyRepository.findAll().get(0);
        //then
        Assertions.assertEquals(copy.getTitle().getTitle(), "test title");

    }



    @Test
    void testGetBookTitle() throws CorruptedDataException, FailedToFetchDataException {
        //given & when
        controller.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        ResponseEntity<BookTitleDto> bookTitle=controller.getBookTitle(libraryBookTitleRepository.findAll().get(0).getId());
        //then
        Assertions.assertEquals(bookTitle.getBody().getTitle(), "test title");

        }

        //some null pointer exception problems
    @Test
    void testGetBookCopy() throws CorruptedDataException, FailedToFetchDataException {
        //given & when
        controller.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        controller.addBookCopy("test title");
        ResponseEntity<BookCopyDto> copy = controller.getBookCopy(libraryBookCopyRepository.findAll().get(0).getId());
        //then
        Assertions.assertEquals( copy.getBody().getTitleId(), libraryBookTitleRepository.findAll().get(0).getId());

    }

    @Test
    void testCheckBookCopiesAvailable() throws CorruptedDataException, FailedToFetchDataException, EmptyListException {
        //given & when
        controller.addBookTitle(new BookTitleDto(null, "test title", "test author", LocalDate.now(), null));
        controller.addBookCopy("test title");
        controller.addBookCopy("test title");
        controller.addBookCopy("test title");
        controller.addBookCopy("test title");
        ResponseEntity<List<BookCopyDto>> copylist= controller.checkBookCopiesAvailable("test title");
        //then
        Assertions.assertEquals(copylist.getBody().size(), 4);

    }



}
