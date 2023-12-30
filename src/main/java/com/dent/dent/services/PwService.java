package com.dent.dent.services;


import com.dent.dent.IDao.IDao;
import com.dent.dent.entities.PW;
import com.dent.dent.repositories.PwRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PwService implements IDao<PW> {
    @Autowired
    PwRepository pwRepository;
    @Override
    public PW create(PW o) {
        return pwRepository.save(o);
    }

    @Override
    public PW update(PW o) {
        return pwRepository.save(o);
    }

    @Override
    public PW findById(int id) {
        return pwRepository.findById((long) id).orElse(null);
    }
    public PW findById(long id) {
        return pwRepository.findById(id).orElse(null);
    }

    @Override
    public List<PW> findAll() {
        return pwRepository.findAll();
    }

    @Override
    public Boolean delete(PW o) {
        try{
            pwRepository.delete(o);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
