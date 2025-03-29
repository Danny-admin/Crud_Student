package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Dto.SearchStudentDto;
import myProyect.Model.Courses_base;
import myProyect.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Course")
@Validated
public class CourseController extends GenericController<Courses_base,Long>{

    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping //Inidamos que recibe solicitudes de tipo "post"
    public ResponseEntity<?> postObjet(@Valid @RequestBody Courses_base course) {
        Optional<Courses_base> courseStatus = service.postObject(course);
        if(courseStatus.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(courseStatus); //codigo (201)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: FACULTY NOT FOUND...");  //codigo (404)
    }
    @GetMapping("/Students")
    public ResponseEntity<?> getStudents(
            @RequestBody @Valid @Validated(SearchStudentDto.course.class) SearchStudentDto information){
        return service.getStudents(information);
    }

}
