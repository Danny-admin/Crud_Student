package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Dto.SearchStudentDto;
import myProyect.Model.University;
import myProyect.Services.FacultyService;
import myProyect.Services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/University")
@Validated
public class UniversityController extends GenericController<University,Long>{

    private final UniversityService universityService;

    @Autowired
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
    @GetMapping("/Students")
    public ResponseEntity<?> getStudents(
            @RequestBody @Valid @Validated(SearchStudentDto.university.class) SearchStudentDto information){
       return  universityService.getStudents(information);
    }
}
