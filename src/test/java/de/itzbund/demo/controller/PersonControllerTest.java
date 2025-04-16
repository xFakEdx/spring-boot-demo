package de.itzbund.demo.controller;

import de.itzbund.demo.domain.Person;
import de.itzbund.demo.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {
    
    private static final String PERSON_BASE_PATH = "/api/v1/person";
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private PersonService personService;
    
    @Test
    @DisplayName("POST /create sollte eine Person erstellen")
    void testCreatePerson() throws Exception {
        Person testPerson = new Person(1L, "Max", "Mustermann");
        
        when(personService.createPerson(1L, "Max", "Mustermann")).thenReturn(testPerson);
        
        mockMvc.perform(post(PERSON_BASE_PATH + "/create")
                        .param("id", "1")
                        .param("vorname", "Max")
                        .param("nachname", "Mustermann")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.vorname").value("Max"))
                .andExpect(jsonPath("$.nachname").value("Mustermann"));
    }
    
    @Test
    @DisplayName("GET /get/{id} sollte eine Person zurückgeben")
    void testGetPersonFound() throws Exception {
        Person testPerson = new Person(2L, "Anna", "Schulz");
        
        when(personService.getPerson(2L)).thenReturn(testPerson);
        
        mockMvc.perform(get(PERSON_BASE_PATH + "/get/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.vorname").value("Anna"))
                .andExpect(jsonPath("$.nachname").value("Schulz"));
    }
    
    @Test
    @DisplayName("GET /get/{id} sollte 404 liefern, wenn Person nicht gefunden")
    void testGetPersonNotFound() throws Exception {
        when(personService.getPerson(99L)).thenReturn(null);
        
        mockMvc.perform(get(PERSON_BASE_PATH + "/get/99"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("GET /getFlat/{id} sollte eine flache Darstellung zurückgeben")
    void testGetFlatPerson() throws Exception {
        Person testPerson = new Person(3L, "Lena", "Maier");
        
        when(personService.getPerson(3L)).thenReturn(testPerson);
        
        mockMvc.perform(get(PERSON_BASE_PATH + "/getFlat/3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id=3")))
                .andExpect(content().string(containsString("vorname=Lena")))
                .andExpect(content().string(containsString("nachname=Maier")));
    }
    
    @Test
    @DisplayName("GET /getFlat/{id} sollte 404 liefern, wenn Person nicht gefunden")
    void testGetFlatPersonNotFound() throws Exception {
        when(personService.getPerson(42L)).thenReturn(null);
        
        mockMvc.perform(get(PERSON_BASE_PATH + "/getFlat/42"))
                .andExpect(status().isNotFound());
    }
}