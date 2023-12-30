package com.dent.dent.controllers;


import com.dent.dent.entities.Groupe;
import com.dent.dent.entities.Student;
import com.dent.dent.entities.User;
import com.dent.dent.services.StudentService;
import com.dent.dent.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*")

public class StudentsController {
    @Autowired
    private StudentService service;

    @Autowired
    UserService userservie;

    @GetMapping
    public List<Student> findAllstudents(){
        return service.findAll();
    }
    @PostMapping()
    public ResponseEntity<Object> registerStudent(@RequestBody Student user) {
        return registerUser(user, true);
    }

    private ResponseEntity<Object> registerUser(User user, boolean activate) {
        if (userservie.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(Map.of("message", "Student already exist"), HttpStatus.BAD_REQUEST);
        } else {
            String hashPWD = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashPWD);

            return ResponseEntity.ok(userservie.create(user));
        }
    }
    ///ilyas_mobile
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody Student student) {

        Student createdStudent = service.create(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);}

    /////
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        Student student = service.findById(id);
        if (student==null) {
            return new ResponseEntity<Object>("la student avec id : "+id+"est introuvable", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(student);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatestudent(@PathVariable int id, @RequestBody Student newstudent){
        Student student = service.findById(id);
        if (student==null) {
            return new ResponseEntity<Object>("le student avec id : "+id+"est introuvable", HttpStatus.BAD_REQUEST);
        }else {
            newstudent.setId((long) id);
            return ResponseEntity.ok(service.update(newstudent));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletestudent(@PathVariable int id){
        Student student = service.findById(id);
        if (student==null) {
            return new ResponseEntity<Object>("le student avec id : "+id+"est introuvable", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(service.delete(student));
        }
    }

    @GetMapping("/groupe/{id}")
    public List<Student> getStudentsByGroupe(@PathVariable Long id) {
        Groupe groupe = new Groupe();
        groupe.setId(id);
        return service.findStudentsByGroupe(groupe);
    }







}
