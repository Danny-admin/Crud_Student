package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Model.Faculty_base;
import myProyect.Services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/Faculty")
@Validated
public class FacultyController extends GenericController<Faculty_base, Long>{

    private final FacultyService service;

    @Autowired
    public FacultyController(FacultyService service) {
        super(service);
        this.service = service;
    }
    @Override
    @PostMapping
    public ResponseEntity<?> postObjet(@Valid @RequestBody Faculty_base faculty){
        Optional<Faculty_base> verifiedFaculty = service.postObject(faculty);
        if(verifiedFaculty.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(verifiedFaculty); //codigo (201)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: UNIVERSIDAD NO ENCONTRADA...");
    }
}
