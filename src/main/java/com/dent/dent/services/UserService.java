package com.dent.dent.services;


import com.dent.dent.IDao.IDao;
import com.dent.dent.entities.Professor;
import com.dent.dent.entities.Student;
import com.dent.dent.entities.User;
import com.dent.dent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IDao<User> {

    @Autowired
    UserRepository userR;

    @Override
    public List<User> findAll() {
        return userR.findAll();
    }


    @Override

    public User findById(int id) {
        return userR.findById((long) id).orElse(null);
    }

    public User findById(long id) {
        return userR.findById(id).orElse(null);
    }

    @Override
    public User create(User o) {
        return userR.save(o);
    }

    @Override
    public User update(User o) {
        return userR.save(o);
    }

    @Override
    public Boolean delete(User o) {
        try {
            userR.delete(o);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public User findByEmail(String email){
        return userR.findUserByEmail(email);
    }

    public List<Professor> findAllProfessors(){
        return userR.findAllProfessors();
    }
    public List<Student> findAllStudents(){
        return userR.findAllStudents();
    }
}
