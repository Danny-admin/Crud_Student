package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Dto.CredentialDto;
import myProyect.Dto.StudentDto;
import myProyect.Model.Student;
import myProyect.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController  // Indica que es un controlador
@RequestMapping("/Student")   // indica la ruta base del controlador
@Validated  //Responsable de validar objetos inconsistentes
public class StudentController extends GenericController<Student,Long>{

    private final StudentService service;

    @Autowired  //inyeccion de dependencia auto
    public StudentController(StudentService service) {
        super(service);
        this.service = service;
    }

//======================================================================
    @GetMapping("/{id}")
    @Override  //sobreescribimos el metodo
    public ResponseEntity<?> getObjetById(@PathVariable Long id){
        Optional<StudentDto> student = service.getStudent(id);  //Guardamos el resultado
        // verificamos si el estudiante esta presente
        if(student.isPresent()){return ResponseEntity.ok(student.get());}  //codigo (200)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }
    @GetMapping("byCredendials")
    public ResponseEntity<?> getStudentByUserAndPassword(@Valid @RequestBody CredentialDto credentialDto){
        Optional<StudentDto> student = service.getStudentByUserAndPassword(credentialDto.getUser(), credentialDto.getPassword());

        if(student.isPresent()){return ResponseEntity.ok(student.get());}
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }

    @Override
    @PostMapping
    public ResponseEntity<?> postObjet(@Valid @RequestBody Student student) {
        Optional<Student> studentStatus = service.postObject(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentStatus); //codigo (201)
    }
}
