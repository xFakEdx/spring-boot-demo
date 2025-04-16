package de.itzbund.demo.repository;

import de.itzbund.demo.entity.PersonEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonRepositoryTest {
    
    @Autowired
    private PersonRepository personRepository;
    
    @Test
    @DisplayName("Testet die FindById Methode des PersonRepository")
    void saveAndFindById() {
        // given
        PersonEntity person = new PersonEntity(1L, "Max", "Mustermann");
        
        // when
        personRepository.save(person);
        Optional<PersonEntity> loaded = personRepository.findById(1L);
        
        // then
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getVorname()).isEqualTo("Max");
        assertThat(loaded.get().getNachname()).isEqualTo("Mustermann");
    }
    
    @Test
    @DisplayName("Testet die FindById Methode des PersonRepository für den Fall das die Person nicht existiert")
    void findById_notFound() {
        // when
        Optional<PersonEntity> result = personRepository.findById(999L);
        
        // then
        assertThat(result).isEmpty();
    }
    
    @Test
    @DisplayName("should delete a PersonEntity")
    void deletePersonEntity() {
        // given
        PersonEntity person = new PersonEntity(2L, "Erika", "Musterfrau");
        personRepository.save(person);
        
        // when
        personRepository.deleteById(2L);
        
        // then
        assertThat(personRepository.findById(2L)).isEmpty();
    }
    
    @Test
    @DisplayName("Testet die Methode save des PersonRepository")
    void save_shouldPersistPersonEntity() {
        // given
        PersonEntity person = new PersonEntity();
        person.setId(42L);
        person.setVorname("Lena");
        person.setNachname("Beispiel");
        
        // when
        PersonEntity saved = personRepository.save(person);
        
        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(42L);
        assertThat(saved.getVorname()).isEqualTo("Lena");
        assertThat(saved.getNachname()).isEqualTo("Beispiel");
        
        // additionally: check retrieval from database
        Optional<PersonEntity> found = personRepository.findById(42L);
        assertThat(found).isPresent();
        assertThat(found.get().getVorname()).isEqualTo("Lena");
    }
}
