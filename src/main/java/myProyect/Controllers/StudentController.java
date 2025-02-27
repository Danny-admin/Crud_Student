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

import java.util.List;
import java.util.Optional;

@RestController  // Indica que es un controlador
@RequestMapping("/Student")   // indica la ruta base del controlador
@Validated  //Responsable de validar objetos inconsistentes
public class StudentController {

    private final StudentService studentService;

    @Autowired  //inyeccion de dependencia auto
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //  @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
        return ResponseEntity.ok(studentService.getStudents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        Optional<StudentDto> student = studentService.getStudent(id);  //Guardamos el resultado
        // verificamos si el estudiante esta presente
        if(student.isPresent()){return ResponseEntity.ok(student.get());}  //codigo (200)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }

    @GetMapping("byCredendials")
    public ResponseEntity<?> getStudentByUserAndPassword(@Valid @RequestBody CredentialDto credentialDto){
        Optional<StudentDto> student = studentService.getStudentByUserAndPassword(credentialDto.getUser(), credentialDto.getPassword());

        if(student.isPresent()){return ResponseEntity.ok(student.get());}
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }

    @PostMapping
    public ResponseEntity<Student> postStudent(@Valid @RequestBody Student modifiedStudent){
        Student student = studentService.saveStudent(modifiedStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);  //codigo (201)
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> putStudent(@PathVariable Long id,@Valid @RequestBody Student modifiedStudent){
        Optional<StudentDto> student = studentService.getStudent(id);  //Guardamos el resultado del estudiante
        if(student.isPresent()){
            modifiedStudent.setId(id);  //Nos aseguramos de que su id sean iguales
            Student updatedStudent = studentService.saveStudent(modifiedStudent); // guardamos al estudiante
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        Optional<StudentDto> student = studentService.getStudent(id);
        if(student.isPresent()){
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();  // codigo (204)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }
}
