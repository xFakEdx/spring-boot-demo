package de.itzbund.demo.service.mapper;

import de.itzbund.demo.domain.Person;
import de.itzbund.demo.entity.PersonEntity;

public class PersonMapper {
    
    private PersonMapper() {
    }
    
    public static Person toDomain(PersonEntity personEntity) {
        return new Person(personEntity.getId(), personEntity.getVorname(), personEntity.getNachname());
    }
}
