package com.example.LibrarySystem.Controller.PatronController;

import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Helpers.PatronHelper.PatronRequest;
import com.example.LibrarySystem.Helpers.PatronHelper.PatronResponse;
import com.example.LibrarySystem.Service.PatronService.IPatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private IPatronService patronService;

    @GetMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<PatronResponse> fetchPatrons(){
        List<Patron> patrons =patronService.getAllPatrons();
        if(patrons==null){
            return ResponseEntity.notFound().build();
        }
        PatronResponse patronResponse = new PatronResponse(patrons);
        return ResponseEntity.ok(patronResponse);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Patron> fetchPatron(@PathVariable("id") Integer id){
        if(id==null){
            return ResponseEntity.badRequest().build();
        }
        Patron patron = patronService.getPatron(id);
        if(patron ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Patron> createPatron(@RequestBody PatronRequest patronRequest){
        if(patronRequest==null){
            return ResponseEntity.badRequest().build();
        }
        Patron patron = patronService.createPatron(patronRequest);
        if(patron!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(patron);
        }
        return  ResponseEntity.internalServerError().build();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Patron> updatePatron(@PathVariable("id") Integer id,@RequestBody PatronRequest patronRequest){
        if(patronRequest==null||id ==null){
            return ResponseEntity.badRequest().build();
        }
        Patron patron = patronService.updatePatron(id,patronRequest);
        if(patron!=null){
            return ResponseEntity.status(HttpStatus.OK).body(patron);
        }
        return  ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<String> deletePatron(@PathVariable("id") Integer id){
        if(id ==null){
            return ResponseEntity.badRequest().build();
        }
        boolean isDeleted= patronService.deletePatron(id);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Patron Deleted Successfully");
        }
        return ResponseEntity.internalServerError().build();
    }



}
