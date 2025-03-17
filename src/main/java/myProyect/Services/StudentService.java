package myProyect.Services;

import myProyect.Dto.StudentDto;
import myProyect.Model.Student;
import myProyect.Model.Subjet_base;
import myProyect.Repository.StudentRepository;
import myProyect.Repository.SubjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends  GenericService<Student,Long>{

    private final StudentRepository studentRepository;
    private final SubjetRepository subjetRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, SubjetRepository subjetRepository) {
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.subjetRepository = subjetRepository;
    }
    //==== mostrar un estudiante en especifico ====
    public Optional<StudentDto> getStudent(Long id){
        // findById(id): Devuelve un Optional<T>, y si no encuentra el registro, devuelve un Optional.empty().
        Optional<Student> student = studentRepository.findById(id);
        //lambda (programacion funcional)
        return student.map(value -> new StudentDto(value.getId(),
                value.getName(),
                value.getAge(),
                value.getEmail()));
    }
    //==== mostrar un estudiante en especifico por sus credenciales ====
    public  Optional<StudentDto> getStudentByUserAndPassword(String user, String password){
        Optional<Student> student = studentRepository.findByUserAndPassword(user, password);
        //lambda (programacion funcional)
        return student.map(value -> new StudentDto(value.getId(),
                value.getName(),
                value.getAge(),
                value.getEmail()));
    }

    @Override
    public Optional<Student> postObject(Student student) {
        List<Subjet_base> checkList = searching(student.getListSubjet());
        student.setListSubjet(checkList);
        transition(student);
        return Optional.of(studentRepository.save(student));  //casteamos a tipo "Optional"
    }
    private List<Subjet_base> searching (List<Subjet_base> listSubjets){
        if(listSubjets.isEmpty()){ //si las lista esta vacia retornamos una lista vacia
            return Collections.emptyList();
        }
        List<Subjet_base> newList = new ArrayList<>();

        for(Subjet_base subjet : listSubjets){
            // Si la materia existe en la base de datos, la agregamos a la nueva lista
            subjetRepository.findById(subjet.getId()).ifPresent(newList::add);
        }
        return newList;
    }
    private void transition(Student student){
        student.transition(student.getListSubjet());
    }
}
