package myProyect.Services;

import myProyect.Dto.StudentDto;
import myProyect.Model.Student;
import myProyect.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    //al ser unico repositorio se inyecta solo
    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    //==== mostrar todos los estudiantes ====
    public List<Student> getStudents(){
        return repository.findAll();
    }
    //==== mostrar un estudiante en especifico ====
    public Optional<StudentDto> getStudent(Long id){
        // findById(id): Devuelve un Optional<T>, y si no encuentra el registro, devuelve un Optional.empty().
        Optional<Student> student = repository.findById(id);
        //lambda (programacion funcional)
        return student.map(value -> new StudentDto(value.getId(),
                value.getName(),
                value.getAge(),
                value.getEmail(),
                value.getCourse()));
    }
    //==== mostrar un estudiante en especifico por sus credenciales ====
    public  Optional<StudentDto> getStudentByUserAndPassword(String user, String password){
        Optional<Student> student = repository.findByUserAndPassword(user, password);
        //lambda (programacion funcional)
        return student.map(value -> new StudentDto(value.getId(),
                value.getName(),
                value.getAge(),
                value.getEmail(),
                value.getCourse()));
    }

    //==== crear o guardar (si la id existe en BD) un estudiante  ====
    public Student saveStudent(Student student){
        repository.save(student);   //si la entidad es nueva se "crea" si no lo es, se " actualiza"
        return student;  //retornamos para generar una respuesta http
    }
    //==== eliminar un estudiante ====
    public void deleteStudent(Long id){
        repository.deleteById(id);
    }
}
