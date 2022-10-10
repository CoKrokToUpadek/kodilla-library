package com.kodillalibrary;

import com.kodillalibrary._resources.CorruptedDataException;
import com.kodillalibrary.controller.LibraryUserController;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import com.kodillalibrary.repository.LibraryBookCopyRepository;
import com.kodillalibrary.repository.LibraryBookRentalRepository;
import com.kodillalibrary.repository.LibraryBookTitleRepository;
import com.kodillalibrary.repository.LibraryUsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class LibraryUserControllerTests {
    @Autowired
    LibraryUsersRepository libraryUsersRepository;
    @Autowired
    LibraryBookRentalRepository libraryBookRentalRepository;
    @Autowired
    LibraryBookTitleRepository libraryBookTitleRepository;
    @Autowired
    LibraryBookCopyRepository libraryBookCopyRepository;
    @Autowired
    LibraryUserController libraryUserController;

    @AfterEach
    void deleteAll(){
        libraryBookRentalRepository.deleteAll();
        libraryBookCopyRepository.deleteAll();
        libraryBookTitleRepository.deleteAll();
        libraryUsersRepository.deleteAll();
    }

    @Test
    void testAddUser() throws CorruptedDataException {
        //given & when
        libraryUserController.addUser(new UserDto(null, "testFirstName", "testLastName", null, new ArrayList<>()));
        User user = libraryUsersRepository.findAll().get(0);
        //then
        Assertions.assertEquals(user.getFirstName(), "testFirstName");
        //cleanup
        libraryUsersRepository.deleteAll();
    }

    @Test
    void testGetUser() throws CorruptedDataException {
        //given & when
        libraryUserController.addUser(new UserDto(null, "testFirstName", "testLastName", null, null));
        User user = libraryUsersRepository.findAll().get(0);
        //then
        Assertions.assertEquals(user.getFirstName(), "testFirstName");

    }


}
