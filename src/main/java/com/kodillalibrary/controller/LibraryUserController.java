package com.kodillalibrary.controller;


import com.kodillalibrary._resources.CommunicationClass;
import com.kodillalibrary._resources.CorruptedDataException;
import com.kodillalibrary._resources.FailedToFetchDataException;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import com.kodillalibrary.mapper.UserMapper;
import com.kodillalibrary.service.DbService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@AllArgsConstructor
@Transactional(propagation= Propagation.REQUIRED)
public class LibraryUserController {
    private final UserMapper userMapper;
    private final DbService dbService;

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(userMapper.mapToUserDto(dbService.getUser(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @PostMapping(path = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunicationClass> addUser(@RequestBody UserDto userDto) throws CorruptedDataException {
        User user = userMapper.mapToNewUser(userDto);
        dbService.createUser(user);
        return ResponseEntity.ok(new CommunicationClass("user created successfully"));

    }

}
