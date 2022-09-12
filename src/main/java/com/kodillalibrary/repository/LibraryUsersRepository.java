package com.kodillalibrary.repository;

import com.kodillalibrary.domain.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryUsersRepository extends CrudRepository <User,Long>{
    List<User> findAll();

    @Override
    Optional<User> findById(Long id);
}
