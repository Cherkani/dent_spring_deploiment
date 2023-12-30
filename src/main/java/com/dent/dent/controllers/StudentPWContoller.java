package com.dent.dent.controllers;

import com.dent.dent.entities.StudentPW;
import com.dent.dent.entities.StudentPWPK;
import com.dent.dent.services.StudentPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studentpws")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentPWContoller {
    @Autowired
    StudentPWService studentPWService;

    @PostMapping("/add/{studentId}/{pwId}")
    public ResponseEntity<Object> create(@RequestParam long studentId,@RequestParam long pwId,@RequestBody StudentPW  studentPW){
        StudentPWPK studentPWPK = new StudentPWPK(studentId,pwId);
        studentPW.setId(studentPWPK);
        studentPWService.create(studentPW);
        return ResponseEntity.ok(studentPW);
    }


    @GetMapping("/all")
    public List<StudentPW> getAll(){
        return studentPWService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id){
        StudentPW studentPW = studentPWService.findById(id);
        if(studentPW == null)
            return new ResponseEntity<>(Map.of("message","studentPW does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(studentPW);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id){
        StudentPW studentPW = studentPWService.findById(id);
        if (studentPW == null)
            return new ResponseEntity<>(Map.of("message","studentPW does not exist"), HttpStatus.NOT_FOUND);
        else{
            studentPWService.delete(studentPW);
            return ResponseEntity.ok(Map.of("message","studentPW deleted successfully"));
        }
    }






}
