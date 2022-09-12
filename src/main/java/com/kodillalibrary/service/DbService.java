package com.kodillalibrary.service;

import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.repository.LibraryBookCopyRepository;
import com.kodillalibrary.repository.LibraryBookRentalRepository;
import com.kodillalibrary.repository.LibraryBookTitleRepository;
import com.kodillalibrary.repository.LibraryUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {

    private final LibraryBookRentalRepository libraryBookRentalRepository;
    private final LibraryBookCopyRepository libraryBookCopyRepository;
    private final LibraryBookTitleRepository libraryBookTitleRepository;
    private final LibraryUsersRepository libraryUsersRepository;

    public List<User> getAllUsers(){
        return libraryUsersRepository.findAll();
    }

    public Optional<User> getUser(Long id){
        return libraryUsersRepository.findById(id);
    }

}
