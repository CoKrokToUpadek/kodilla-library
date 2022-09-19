package com.kodillalibrary.service;

import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_rental.BookRental;
import com.kodillalibrary.domain.book_title.BookTitle;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.repository.LibraryBookCopyRepository;
import com.kodillalibrary.repository.LibraryBookRentalRepository;
import com.kodillalibrary.repository.LibraryBookTitleRepository;
import com.kodillalibrary.repository.LibraryUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbService {

    private final LibraryBookCopyRepository libraryBookCopyRepository;
    private final LibraryBookTitleRepository libraryBookTitleRepository;
    private final LibraryUsersRepository libraryUsersRepository;

    private final LibraryBookRentalRepository libraryBookRentalRepository;


    public List<User> getAllUsers() {
        return libraryUsersRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return libraryUsersRepository.findById(id);
    }

    public Optional<BookTitle> getBookTitle(Long id){
        return libraryBookTitleRepository.findById(id);
    }

    public Optional<BookRental> getBookRental(Long id){
        return libraryBookRentalRepository.findById(id);
    }

    public Optional<BookCopy> getBookCopy(Long id) {
        return libraryBookCopyRepository.findById(id);
    }


    public BookCopy saveBookCopy(final BookCopy bookCopy) {
        return libraryBookCopyRepository.save(bookCopy);
    }

    public void changeBookCopyStatus(Long bookCopyId, BookStatusEnum statusEnum) throws Exception {
        BookCopy copy=libraryBookCopyRepository.findById(bookCopyId).orElse(null);
        if (copy==null){
            throw new Exception();
        }else {
            BookCopy updatedCopy = new BookCopy(copy.getId(), copy.getTitle(),statusEnum.toString(), copy.getBookRentals());
            saveBookCopy(updatedCopy);
        }
    }

    public boolean checkStatusForReturn(Long bookCopyId){
        BookCopy copy=libraryBookCopyRepository.findById(bookCopyId).orElse(null);
        return copy.getStatus().equals(BookStatusEnum.RENTED.toString());
    }


    public List<BookCopy> getAvailableCopies(String title) throws Exception {
        List<BookTitle> allTitles=libraryBookTitleRepository.findAll();
        Long specifiedTitleId=allTitles.stream().filter(e->title.equals(e.getTitle())).map(BookTitle::getId).findAny().orElse(0L);
        if (specifiedTitleId==0){
            throw new Exception();
        }else {
           List<BookCopy> availableCopies=libraryBookCopyRepository.findAll().stream().filter(e->e.getTitle().getId()==specifiedTitleId).collect(Collectors.toList());
           return availableCopies.stream().filter(s->s.getStatus().equals(BookStatusEnum.AVAILABLE.toString())).collect(Collectors.toList());
        }
    }


    public void createUser(User user) throws Exception{
        User newUser=new User(user.getId(), user.getFirstName(), user.getLastName(), LocalDate.now(),null);
        try {
            libraryUsersRepository.save(newUser);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void createTitle(BookTitle title) throws Exception{
        BookTitle newTitle=new BookTitle(title.getId(),title.getTitle(),title.getAuthor(),title.getReleaseDate(),new ArrayList<BookCopy>());
        try {
            libraryBookTitleRepository.save(newTitle);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public BookCopy initiateNewCopy(final BookTitle bookTitle){
        return new BookCopy(null,bookTitle, BookStatusEnum.AVAILABLE.toString(),new ArrayList<>());
    }

    public void createCopy(String title) throws Exception{
            List<BookTitle> allTitles=libraryBookTitleRepository.findAll();
            BookTitle specifiedTitle=allTitles.stream().filter(e->title.equals(e.getTitle())).findAny().orElse(null);
            if (specifiedTitle==null){
                throw new Exception();
            }
            BookCopy copy= initiateNewCopy(specifiedTitle);
            libraryBookCopyRepository.save(copy);
    }

    public void createRental(User user, BookCopy copy){
        BookRental rental=new BookRental(null,copy,user,LocalDate.now(),null);
        libraryBookRentalRepository.save(rental);
    }

    public void rentBook(Long userId, String title) throws Exception {
        User user=getUser(userId).orElse(null);
        if (user==null){
            throw new Exception();
        }
        List<BookCopy> copies=getAvailableCopies(title);
        createRental(user,copies.get(0));
        changeBookCopyStatus(copies.get(0).getId(),BookStatusEnum.RENTED);

    }

    public void returnBook(Long copyId) throws Exception {
     List<BookRental> rental=libraryBookRentalRepository.findAll().stream().filter(e->e.getCopyID().getId().equals(copyId))
                .collect(Collectors.toList()).stream().filter(r->r.getReturnDate()==(null)).collect(Collectors.toList());
     if (rental.isEmpty()){
         throw new Exception();
     }
     BookRental returnedRental=new BookRental(rental.get(0).getId(),rental.get(0).getCopyID(),rental.get(0).getUserID(),rental.get(0).getRentalDate(),LocalDate.now());
     libraryBookRentalRepository.save(returnedRental);
    }

}



