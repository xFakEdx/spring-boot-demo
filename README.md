# itzbunddemo
Demo repo für ITZ Bund

## Projektaufbau:
### Schritt 1: Erstellen eines Java-Spring-Boot-Projekts

1. **Spring Initializr verwenden**

2. **Projekt in einer IDE öffnen**:
   -  ZIP runterladen und in IDE öffnen

3. **Herstellen der REST-Fähigkeit**:
    - MVC Struktur, entity Model, DTO oder flat object, controller, repo + service, 
    - Tests dazu

4. **OpenAPI-Dokumentation**:
   - Maven dependency für OpenAPI-Dokumentation ins pom.xml file
   - OpenApi Annotationen in die Controllermethoden 
   - dann per Laufzeit auf http://localhost:8080/swagger-ui/index.html

### Schritt 2: Datenpersistierung mit Hibernate

1. **Datenbankkonfiguration**:
   - in application.properties H2-Datenbankkonfiguration 
     ```
    # DB und JPA
    spring.datasource.url=jdbc:h2:file:./data;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE;
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=password
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    spring.jpa.hibernate.ddl-auto=update
    spring.h2.console.enabled=true
     ```
### Schritt 3: Logging Konfiguration
1. **logback.xml anlegen**
2. **application.properties**:
    ```
    logging.file.name=logs/app.log
    ```

### Schritt 4: Jenkins-Pipeline einrichten

1. **Jenkins installieren**:
   - Lokal installieren oder per Docker
2. **Jenkins-Pipeline-Projekt erstellen**
3. **Jenkinsfile erstellen**
4. ** Prüfen ob alle benötigten Jenkins plugins installiert sind**

### Schritt 5: Code Quality plugin
1. **Sonarqube oder Spotbugs**:
    - Sonarqube Server + credential einrichten oder Spotbugs plugin in pom.xml

