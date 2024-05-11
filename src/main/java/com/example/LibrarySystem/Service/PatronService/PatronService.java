package com.example.LibrarySystem.Service.PatronService;

import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Helpers.PatronHelper.PatronRequest;
import com.example.LibrarySystem.Reposirtoy.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService implements IPatronService{
    @Autowired
    private PatronRepository patronRepository;


    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron getPatron(Integer patronId) {
        return patronRepository.findById(patronId).orElseThrow(()->
                new RuntimeException("Patron not found with id:"  + patronId));
    }

    @Override
    public boolean createPatron(PatronRequest patronRequest) {
        Patron patron = new Patron();
        patron.setName(patronRequest.getName());
        patron.setContactInformation(patronRequest.getContactInformation());
        Patron savedPatron =patronRepository.save(patron);
        if(savedPatron!=null){
            return true;
        }
        return false;
    }

    @Override
    public Patron updatePatron(Integer patronId, PatronRequest patronRequest) {
        Patron patron = patronRepository.findById(patronId).orElseThrow(()->
                new RuntimeException("Patron not found with id:"  + patronId));
        patron.setName(patronRequest.getName());
        patron.setContactInformation(patronRequest.getContactInformation());
        patronRepository.save(patron);
        return patron;
    }

    @Override
    public boolean deletePatron(Integer patronId) {
        Patron patron = patronRepository.findById(patronId).orElseThrow(()->
                new RuntimeException("Patron not found with id:"  + patronId));
        if(patron!=null){
            patronRepository.delete(patron);
            return true;
        }
        return false;
    }
}
