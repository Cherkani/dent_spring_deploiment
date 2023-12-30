package com.dent.dent.services;


import com.dent.dent.IDao.IDao;
import com.dent.dent.entities.Tooth;
import com.dent.dent.repositories.ToothRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToothService implements IDao<Tooth> {
    @Autowired
    ToothRepository toothRepository;
    @Override
    public Tooth create(Tooth o) {
        return toothRepository.save(o);
    }

    @Override
    public Tooth update(Tooth o) {
        return toothRepository.save(o);
    }

    @Override
    public Tooth findById(int id) {
        return toothRepository.findById((long) id).orElse(null);
    }

    public Tooth findById(long id) {
        return toothRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tooth> findAll() {
        return toothRepository.findAll();
    }

    @Override
    public Boolean delete(Tooth o) {
        try{
            toothRepository.delete(o);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
