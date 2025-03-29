package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Dto.SearchStudentDto;
import myProyect.Model.Student;
import myProyect.Model.Subjet_base;
import myProyect.Services.SubjetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Subjet")
@Validated
public class SubjetController extends GenericController<Subjet_base,Long>{

    private final SubjetService service;

    public SubjetController(SubjetService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> postObjet(@Valid @RequestBody Subjet_base subjet) {
        Optional<Subjet_base> subjetStatus = service.postObject(subjet);
        if(subjetStatus.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(subjetStatus);  //codigo (201)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: COURSE OR TEACHER NOT FOUND...");
    }

    @GetMapping("/Students")
    public ResponseEntity<?> getStudents(  //hago la validacion de mi atributo "idSubject" no debe estar vacio
            @RequestBody @Valid @Validated(SearchStudentDto.subject.class) SearchStudentDto information){

        return service.getStudents(information);  //retornamos el codigo proporcionado por el servicio
    }
}
