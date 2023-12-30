package com.dent.dent.controllers;


import com.dent.dent.entities.Professor;
import com.dent.dent.entities.Professor;
import com.dent.dent.services.ProfessorService;
import com.dent.dent.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/professors")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"})
public class ProfessorController {

    @Autowired
    private ProfessorService service;
    @Autowired
    UserService userService;
    @GetMapping
    public List<Professor> findAllProfessors() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registerProf(@RequestBody Professor user) {

        if (userService.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(Map.of("message", "User already exist"), HttpStatus.BAD_REQUEST);
        } else {
            String hashPWD = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashPWD);
            user.setActivate(true);
            userService.create(user);
            return new ResponseEntity<>(Map.of("message", "Your account has been created successfully. You will receive an email when the admin activates your account"), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        Professor professor = service.findById(id);
        if (professor == null) {
            return new ResponseEntity<>("Professor with id: " + id + " not found", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(professor);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProfessor(@PathVariable int id, @RequestBody Professor newProfessor) {
        Professor professor = service.findById(id);
        if (professor == null) {
            return new ResponseEntity<>("Professor with id: " + id + " not found", HttpStatus.BAD_REQUEST);
        } else {
            newProfessor.setId((long) id);
            return ResponseEntity.ok(service.update(newProfessor));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable int id) {
        Professor professor = service.findById(id);
        if (professor == null) {
            return new ResponseEntity<>("Professor with id: " + id + " not found", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(service.delete(professor));
        }
    }

}
