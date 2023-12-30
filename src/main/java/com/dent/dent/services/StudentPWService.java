package com.dent.dent.services;

import com.dent.dent.IDao.IDao;
import com.dent.dent.entities.StudentPW;
import com.dent.dent.entities.StudentPWPK;
import com.dent.dent.entities.Tooth;
import com.dent.dent.repositories.StudentPWRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPWService implements IDao<StudentPW> {
    @Autowired
    StudentPWRepository studentPWRepository;
    @Override
    public StudentPW create(StudentPW o) {
        return studentPWRepository.save(o);
    }

    @Override
    public StudentPW update(StudentPW o) {
        return studentPWRepository.save(o);
    }

    @Override
    public StudentPW findById(int id) {
        return studentPWRepository.findById((long) id).orElse(null);
    }
    public StudentPW findById(long id) {
        return studentPWRepository.findById((long) id).orElse(null);
    }


   

    @Override
    public List<StudentPW> findAll() {
        return studentPWRepository.findAll();
    }

    @Override
    public Boolean delete(StudentPW o) {
        try{
            studentPWRepository.delete(o);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
