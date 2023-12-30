package com.dent.dent.controllers;

import com.dent.dent.entities.Admin;
import com.dent.dent.services.AdminService;
import com.dent.dent.services.AdminService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;


@RestController
    @RequestMapping("/api/v1/admins")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001","http://localhost:5173"})

    public class AdminController {
        @Autowired
        private AdminService service;

        @GetMapping
        public List<Admin> findAllAdmins(){
            return service.findAll();
        }
        @PostMapping
        public Admin createAdmin(@RequestBody Admin Admin){
            // Admin.setId((long) 0);
            return service.create(Admin);



//            if (utilS.findByEmail(user.getEmail()) != null) {
//                return new ResponseEntity<>(Map.of("message", "User already exist"), HttpStatus.BAD_REQUEST);
//            } else {
//                String hashPWD = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
//                user.setPassword(hashPWD);
//                user.setActivate(false);
//                utilS.create(user);
//                return new ResponseEntity<>(Map.of("message", "Your account has been created successfully. You will receive an email when the admin activates your account"), HttpStatus.OK);
//            }
        }
        @GetMapping("/{id}")
        public ResponseEntity<Object> findById(@PathVariable int id) {
            Admin Admin = service.findById(id);
            if (Admin==null) {
                return new ResponseEntity<Object>("la Admin avec id : "+id+"est introuvable", HttpStatus.BAD_REQUEST);
            }else {
                return ResponseEntity.ok(Admin);
            }
        }
        @PutMapping("/{id}")
        public ResponseEntity<Object> updateAdmin(@PathVariable int id, @RequestBody Admin newAdmin){
            Admin Admin = service.findById(id);
            if (Admin==null) {
                return new ResponseEntity<Object>("le Admin avec id : "+id+"est introuvable", HttpStatus.BAD_REQUEST);
            }else {
                newAdmin.setId((long) id);
                return ResponseEntity.ok(service.update(newAdmin));
            }
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Object> deleteAdmin(@PathVariable int id){
            Admin Admin = service.findById(id);
            if (Admin==null) {
                return new ResponseEntity<Object>("le Admin avec id : "+id+"est introuvable", HttpStatus.BAD_REQUEST);
            }else {
                return ResponseEntity.ok(service.delete(Admin));
            }
        }



    }