package com.example.LibrarySystem.Controllers.test;

import com.example.LibrarySystem.Controller.PatronController.PatronController;
import com.example.LibrarySystem.Entity.Patron;
import com.example.LibrarySystem.Helpers.PatronHelper.PatronRequest;
import com.example.LibrarySystem.Helpers.PatronHelper.PatronResponse;
import com.example.LibrarySystem.Service.PatronService.IPatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PatronControllerTest {
    @Autowired
    private PatronController patronController;
    @MockBean
    private IPatronService patronService;

    @BeforeEach
    public void setup() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testGetAllPatrons_shouldReturnListOfPatrons() throws Exception {
        Patron patron1 = new Patron("John Doe", "1234567890");
        patron1.setPatronId(1);
        Patron patron2 = new Patron("Jane Doe", "9876543210");
        patron2.setPatronId(2);

        List<Patron> patrons = new ArrayList<>();
        patrons.add(patron1);
        patrons.add(patron2);

        Mockito.when(patronService.getAllPatrons()).thenReturn(patrons);

        ResponseEntity<PatronResponse> response = patronController.fetchPatrons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getPatrons().size());
        assertEquals("John Doe", response.getBody().getPatrons().get(0).getName());
    }
    @Test
    public void testFetchPatron_shouldReturnPatron_whenFound() throws Exception {
        Integer id = 1;
        Patron existingPatron = new Patron("John Doe", "1234567890");
        existingPatron.setPatronId(id);

        Mockito.when(patronService.getPatron(id)).thenReturn(existingPatron);

        ResponseEntity<Patron> response = patronController.fetchPatron(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getPatronId());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testFetchPatron_shouldReturnNotFound_whenPatronNotFound() throws Exception {
        Integer id = 1;

        Mockito.when(patronService.getPatron(id)).thenReturn(null);

        ResponseEntity<Patron> response = patronController.fetchPatron(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFetchPatron_shouldReturnBadRequest_whenIdIsNull() throws Exception {
        ResponseEntity<Patron> response = patronController.fetchPatron(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreatePatron_shouldCreatePatronAndReturnCreatedStatus() throws Exception {
        PatronRequest patronRequest = new PatronRequest("New Patron", "0987654321");

        Patron createdPatron = new Patron("New Patron", "0987654321");
        createdPatron.setPatronId(10);
        Mockito.when(patronService.createPatron(patronRequest)).thenReturn(createdPatron);

        ResponseEntity<Patron> response = patronController.createPatron(patronRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Patron", response.getBody().getName());
        assertEquals("0987654321", response.getBody().getContactInformation());
    }
    @Test
    public void testCreatePatron_shouldReturnBadRequest_whenEmptyRequest() throws Exception {
        ResponseEntity<Patron> response = patronController.createPatron(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testUpdatePatron_shouldUpdatePatronAndReturnOkStatus() throws Exception {
        Integer id = 1;
        Patron existingPatron = new Patron("John Doe", "1234567890");
        existingPatron.setPatronId(id);
        PatronRequest updateRequest = new PatronRequest("Updated Name", "9876543210");
        Patron updatedPatron = new Patron("Updated Name", "9876543210");
        updatedPatron.setPatronId(id);

        Mockito.when(patronService.getPatron(id)).thenReturn(existingPatron);
        Mockito.when(patronService.updatePatron(id, updateRequest)).thenReturn(updatedPatron);

        ResponseEntity<Patron> response = patronController.updatePatron(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateRequest.getName(), response.getBody().getName());
        assertEquals(updateRequest.getContactInformation(), response.getBody().getContactInformation());
    }

    @Test
    public void testDeletePatron_shouldReturnOk_whenPatronDeletedSuccessfully() throws Exception {
        Integer id = 1;

        Mockito.when(patronService.deletePatron(id)).thenReturn(true);

        ResponseEntity<String> response = patronController.deletePatron(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patron Deleted Successfully", response.getBody());
    }
}
