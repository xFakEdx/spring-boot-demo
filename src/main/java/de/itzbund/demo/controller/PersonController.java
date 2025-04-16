package de.itzbund.demo.controller;

import de.itzbund.demo.domain.Person;
import de.itzbund.demo.entity.PersonEntity;
import de.itzbund.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "Person API", description = "Verwaltet Personeninformationen")
public class PersonController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;
    
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @PostMapping("/create")
    @Operation(summary = "Erstellt eine neue Person", description =
            "Erzeugt eine neue Person mit Vorname, Nachname " + "und ID", responses = {@ApiResponse(responseCode =
            "200", description = "Erfolgreich erstellt", content = @Content(schema = @Schema(implementation =
            PersonEntity.class)))})
    public ResponseEntity<Person> createPerson(
            @Parameter(description = "Vorname der Person", required = true) @RequestParam("vorname") String vorname,
            @Parameter(description = "Nachname der Person", required = true) @RequestParam("nachname") String nachname,
            @Parameter(description = "ID der Person", required = true) @RequestParam("id") Long id) {
        LOGGER.info("Received createPerson-Request with Params: vorname={}, nachname={}, id={}", vorname, nachname, id);
        Person person = personService.createPerson(id, vorname, nachname);
        ResponseEntity<Person> response = ResponseEntity.ok(person);
        LOGGER.info("Response for createPerson-Request with created person (id={}) successfully sent", person.id());
        return response;
    }
    
    @GetMapping("/get/{id}")
    @Operation(summary = "Gibt eine Person zurück", description = "Liefert die Person mit der angegebenen ID zurück",
            responses = {@ApiResponse(responseCode = "200", description = "Gefundene Person", content =
            @Content(schema = @Schema(implementation = PersonEntity.class))), @ApiResponse(responseCode = "404",
                    description = "Person nicht gefunden")})
    public ResponseEntity<Person> getPerson(@Parameter(description = "ID der gesuchten Person",
            required = true) @PathVariable("id") Long id) {
        LOGGER.info("Received getPerson-Request with Params: id={}", id);
        Person person = personService.getPerson(id);
        if (Objects.isNull(person)) {
            return ResponseEntity.notFound().build();
        }
        LOGGER.info("Response for getPerson-Request with found person (id={}) successfully sent", person.id());
        return ResponseEntity.ok(person);
    }
    
    @GetMapping("/getFlat/{id}")
    @Operation(summary = "Gibt eine flache Darstellung der Person zurück", description = "Liefert eine textuelle " +
            "Darstellung (ID, Vorname, Nachname) der Person mit der angegebenen " + "ID", responses =
            {@ApiResponse(responseCode = "200", description = "Textuelle Darstellung der Person"),
                    @ApiResponse(responseCode = "404", description = "Person nicht gefunden")})
    public ResponseEntity<String> getFlat(@Parameter(description = "ID der gesuchten Person", required = true) @PathVariable("id") Long id) {
        Person person = personService.getPerson(id);
        if (Objects.isNull(person)) {
            return ResponseEntity.notFound().build();
        }
        LOGGER.info("Received getFlat-Request with Params: id={}", id);
        ResponseEntity<String> flatPersonResponse = ResponseEntity.ok(
                String.format("Person: id=%s vorname=%s nachname=%s", person.id(), person.vorname(),
                        person.nachname()));
        LOGGER.info("Response for getFlat-Request with found person (id={}) successfully sent", person.id());
        return flatPersonResponse;
    }
    
}
