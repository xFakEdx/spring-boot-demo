package de.itzbund.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class PersonEntity {
    
    @Id
    private Long id;
    private String vorname;
    private String nachname;
    
    public PersonEntity() {
    }
    
    public PersonEntity(Long id, String vorname, String nachname) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getVorname() {
        return vorname;
    }
    
    public String getNachname() {
        return nachname;
    }
    
}
