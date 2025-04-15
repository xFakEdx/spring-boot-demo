package com.itzbund.demo.controller;

import com.itzbund.demo.entity.PersonEntity;
import com.itzbund.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/person")
@Tag(name = "Person API", description = "Verwaltet Personeninformationen")
public class PersonController {
    
    private final PersonService personService;
    
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @PostMapping("/create")
    @Operation(
            summary = "Erstellt eine neue Person",
            description = "Erzeugt eine neue Person mit Vorname, Nachname und ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Erfolgreich erstellt",
                            content = @Content(schema = @Schema(implementation = PersonEntity.class))
                    )
            }
    )
    public PersonEntity createPerson(@Parameter(description = "Vorname der Person", required = true) @RequestParam("vorname") String vorname,
                                     @Parameter(description = "Nachname der Person", required = true) @RequestParam("nachname") String nachname,
                                     @Parameter(description = "ID der Person", required = true) @RequestParam("id") Long id) {
        return personService.createPerson(id, vorname, nachname);
    }
    
    @GetMapping("/get/{id}")
    @Operation(
            summary = "Gibt eine Person zurück",
            description = "Liefert die Person mit der angegebenen ID zurück",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gefundene Person",
                            content = @Content(schema = @Schema(implementation = PersonEntity.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Person nicht gefunden")
            }
    )
    public PersonEntity getPerson(@Parameter(description = "ID der gesuchten Person", required = true) @PathVariable("id") Long id) {
        return personService.getPerson(id);
    }
    
    @GetMapping("/getFlat/{id}")
    @Operation(
            summary = "Gibt eine flache Darstellung der Person zurück",
            description = "Liefert eine textuelle Darstellung (ID, Vorname, Nachname) der Person mit der angegebenen ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Textuelle Darstellung der Person"),
                    @ApiResponse(responseCode = "404", description = "Person nicht gefunden")
            }
    )
    public String getFlat(@Parameter(description = "ID der gesuchten Person", required = true) @PathVariable("id") Long id) {
        PersonEntity person = personService.getPerson(id);
        return String.format("Person: %s %s %s", person.getId(), person.getVorname(), person.getNachname());
    }
    
}
