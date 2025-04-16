package de.itzbund.demo.service;

import de.itzbund.demo.domain.Person;
import de.itzbund.demo.entity.PersonEntity;
import de.itzbund.demo.repository.PersonRepository;
import de.itzbund.demo.service.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public Person createPerson(Long id, String vorname, String nachname) {
        LOGGER.info("Create Person with ID: {}", id);
        return PersonMapper.toDomain(personRepository.save(new PersonEntity(id, vorname, nachname)));
    }
    
    public Person getPerson(Long id) {
        LOGGER.info("Get Person with ID: {}", id);
        return personRepository.findById(id)
                .map(PersonMapper::toDomain)
                .orElse(null);
    }
}
