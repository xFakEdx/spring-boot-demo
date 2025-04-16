package de.itzbund.demo.service;

import de.itzbund.demo.domain.Person;
import de.itzbund.demo.entity.PersonEntity;
import de.itzbund.demo.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonServiceTest {
    
    @Mock
    private PersonRepository personRepository;
    
    @InjectMocks
    private PersonService personService;
    
    private AutoCloseable mocks;
    
    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
    
    
    @Test
    @DisplayName("Testet die Erstellung bzw. Persistierung einer Person in der DB Tabelle person")
    void createPerson_shouldSaveAndReturnMappedPerson() {
        // Given
        Long id = 1L;
        String vorname = "Max";
        String nachname = "Mustermann";
        PersonEntity savedEntity = new PersonEntity(id, vorname, nachname);
        
        when(personRepository.save(any(PersonEntity.class))).thenReturn(savedEntity);
        
        // When
        Person result = personService.createPerson(id, vorname, nachname);
        
        // Then
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(vorname, result.vorname());
        assertEquals(nachname, result.nachname());
        verify(personRepository).save(any(PersonEntity.class));
    }
    
    @Test
    @DisplayName("Testet die Selektierung einer Person über ID aus der DB tabelle person")
    void getPerson_shouldReturnMappedPersonIfExists() {
        // Given
        Long id = 2L;
        PersonEntity foundEntity = new PersonEntity(id, "Erika", "Musterfrau");
        
        when(personRepository.findById(id)).thenReturn(Optional.of(foundEntity));
        
        // When
        Person result = personService.getPerson(id);
        
        // Then
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Erika", result.vorname());
        assertEquals("Musterfrau", result.nachname());
    }
    
    @Test
    @DisplayName("Testet das Verhalten, wenn eine Person über ID nicht gefunden wurde")
    void getPerson_shouldThrowNullPointerExceptionIfNotFound() {
        // Given
        Long id = 3L;
        when(personRepository.findById(id)).thenReturn(Optional.empty());
        
        //wehen
        Person person = personService.getPerson(id);
        
        // Then
        assertNull(person);
    }
}


