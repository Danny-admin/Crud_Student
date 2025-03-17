package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Model.University;
import myProyect.Services.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/University")
@Validated
public class UniversityController extends GenericController<University,Long>{

    private final UniversityService universityService;

    public UniversityController(UniversityService service) {
        super(service);
        this.universityService = service;
    }

    //====== crear una nueva universidad ==========
    @Override
    @PostMapping
    public ResponseEntity<?> postObjet(@Valid @RequestBody University university) {  // codigo (400)
        return ResponseEntity.status(HttpStatus.CREATED).body(universityService.postObject(university)); //codigo (201)
    }
}
