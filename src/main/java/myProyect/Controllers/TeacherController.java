package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Model.Teacher;
import myProyect.Services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Teacher")
@Validated
public class TeacherController extends GenericController<Teacher,Long>{

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> postObjet(@Valid @RequestBody Teacher object) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postObject(object)); //codigo (201)
    }
}
