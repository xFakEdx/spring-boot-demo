package com.itzbund.demo.service;

import com.itzbund.demo.entity.PersonEntity;
import com.itzbund.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    
    private final PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public PersonEntity createPerson(Long id, String vorname, String nachname) {
        return personRepository.save(new PersonEntity(id, vorname, nachname));
    }
    
    public PersonEntity getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }
}
