package com.dent.dent.controllers;



import com.dent.dent.entities.*;
import com.dent.dent.repositories.UserRepository;
import com.dent.dent.services.StudentPWService;
import com.dent.dent.services.UserService;
import com.dent.dent.util.JwtToken;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService utilS;
//    @Autowired
//    EmailSender emailSender;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    StudentPWService studentPWService;

    @GetMapping("/all")
    public List<User> getAll(){
        return utilS.findAll();
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Object> registerAdmin(@RequestBody Admin user) {
        return registerUser(user, true);
    }

    @PostMapping("/register/professor")
    public ResponseEntity<?> registerProf(@RequestBody Professor user) {

        if (utilS.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(Map.of("message", "User already exist"), HttpStatus.BAD_REQUEST);
        } else {
            String hashPWD = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashPWD);
            user.setActivate(false);
            utilS.create(user);
            return new ResponseEntity<>(Map.of("message", "Your account has been created successfully."), HttpStatus.OK);
        }
    }

    @PostMapping("/register/student")
    public ResponseEntity<Object> registerStudent(@RequestBody Student user) {
        return registerUser(user, true);
    }

    private ResponseEntity<Object> registerUser(User user, boolean activate) {
        if (utilS.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(Map.of("message", "User already exist"), HttpStatus.BAD_REQUEST);
        } else {
            String hashPWD = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashPWD);
            user.setActivate(activate);
            return ResponseEntity.ok(utilS.create(user));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User loginuser){
        User user = utilS.findByEmail(loginuser.getEmail());
        if (user == null)
            return new ResponseEntity<>(Map.of("message","invalid email or password"),HttpStatus.NOT_FOUND);

        else {
            boolean isRegister = BCrypt.checkpw(loginuser.getPassword(), user.getPassword());
            if(! isRegister)
                return new ResponseEntity<>(Map.of("message","invalid email or password"),HttpStatus.NOT_FOUND);
            else if(!user.isActivate())
                return new ResponseEntity<>(Map.of("message","Your account is not activated"),HttpStatus.NOT_FOUND);
            else{
                String role ;
                if(user instanceof Professor)
                    role = "professor";
                else if (user instanceof Student)
                    role = "student";
                else
                    role = "admin";
                String token = JwtToken.generateToken(user.getId()+user.getUserName());
                Map<String, Object> response = Map.of(
                        "message", "Authentication successful",
                        "role",role,
                        "token", token,
                        "email", user.getEmail(),
                        "id",user.getId()
                );
                return ResponseEntity.ok(response);
            }
        }
    }

    @DeleteMapping("/delete/student/{profId}/{studentId}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token, @PathVariable long profId,@PathVariable long studentId) {
        User user = utilS.findById(profId);
        User user1 = utilS.findById(studentId);
        System.out.println(token);
        if(user == null)
            return new ResponseEntity<>(Map.of("message","user does not exist"), HttpStatus.NOT_FOUND);
        else if(token == null)
            return new ResponseEntity<>(Map.of("message","token is empty"),HttpStatus.UNAUTHORIZED);
        else if(!JwtToken.validateToken(token.substring(7),user.getId()+user.getUserName()))
            return new ResponseEntity<>(Map.of("message","invalid token"),HttpStatus.UNAUTHORIZED);
        else{
            utilS.delete(user1);
            return new ResponseEntity<>(Map.of("message", "user deleted successfully"), HttpStatus.OK);
        }
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<Object> activateCompte(@PathVariable long id){
        User user = utilS.findById(id);
        if(user == null)
            return new ResponseEntity<>(Map.of("message","user does not exist"), HttpStatus.NOT_FOUND);
        else {
            if(user.isActivate()) {
                user.setActivate(false);
                utilS.update(user);
                return new ResponseEntity<>(Map.of("message", "User disactivated successfully "), HttpStatus.OK);
            }
            else{
                user.setActivate(true);
                utilS.update(user);
//                emailSender.sendEmail(user.getEmail(),"Account activation","Your account is activated");
                return new ResponseEntity<>(Map.of("message", "user activated successfully "), HttpStatus.OK);
            }
        }
    }


    @GetMapping("/allProfessor")
    public List<Professor> getAllProf(){
        return utilS.findAllProfessors();
    }

    @GetMapping("/allStudent")
    public List<Student> getAllStudent(){
        return utilS.findAllStudents();
    }

    @GetMapping("/students/{id}")
    public List<Student> getStudentByProfessor(@PathVariable long id){
        return userRepository.findStudentsByProfessor(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getbyid(@PathVariable long id){
        User user =  userService.findById(id);
        if(user == null)
            return new ResponseEntity<>(Map.of("message","User does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(user);
    }
    @GetMapping("/pw/{id}")
    public ResponseEntity<?> getStudentPw(@PathVariable long id){
        User user =  userService.findById(id);
        if(user == null)
            return new ResponseEntity<>(Map.of("message","User does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(userRepository.findPwByStudent(id));
    }

    @PutMapping("/update/professor/{id}")
    public ResponseEntity<Object> updateid(@PathVariable long id, @RequestBody Professor user0){
        Professor user = (Professor) userService.findById(id);
        if(user == null)
            return new ResponseEntity<>(Map.of("message","User does not exist"), HttpStatus.NOT_FOUND);
        else{
            user.setFirstName(user0.getFirstName());
            user.setLastName(user0.getLastName());
            user.setUserName(user0.getUserName());
            user.setGrade(user0.getGrade());
            user.setPhoto(user0.getPhoto());
            userService.update(user);
            return ResponseEntity.ok(user);
        }

    }

    @GetMapping("/students/group/{id}")
    public List<Student> getStudentsByGroup(@PathVariable long id){
        return userRepository.findStudentsByGroup(id);
    }

    @GetMapping("/testpw/{id}")
    public ResponseEntity<?> testpw(@PathVariable long id){
        Student student = (Student) userService.findById(id);
        if(student == null)
            return new ResponseEntity<>(Map.of("message","User does not exist"), HttpStatus.NOT_FOUND);
        else{
            List<Boolean> list = new ArrayList<>();
            List<PW> pws = student.getGroup().getPws();
            List<StudentPW> studentPWS = studentPWService.findAll();
            for(PW pw: pws){
                int t = 0;
                for(StudentPW studentPW: studentPWS){
                    if (studentPW.getId().getStudent_id() == id && studentPW.getId().getPw_id() == pw.getId()) {
                        t = 1;
                        break;
                    }
                }
                if(t == 0)
                    list.add(false);
                else
                    list.add(true);
            }
            return ResponseEntity.ok(list);

        }

    }


}
