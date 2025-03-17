package myProyect.Services;

import myProyect.Model.Courses_base;
import myProyect.Model.Subjet_base;
import myProyect.Model.Teacher;
import myProyect.Repository.CourseRepository;
import myProyect.Repository.SubjetRepository;
import myProyect.Repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjetService extends GenericService<Subjet_base,Long>{

    private final SubjetRepository subjetRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public SubjetService(SubjetRepository subjetRepository, CourseRepository courseRepository, TeacherRepository teacherRepository) {
        super(subjetRepository);
        this.subjetRepository = subjetRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;

    }
    //==============
    @Override
    public Optional<Subjet_base> postObject(Subjet_base subjet) {
        Optional<Courses_base> foundCourse = courseRepository.findById(subjet.getCourse().getId());
        Optional<Teacher> foundTeacher = teacherRepository.findById(subjet.getTeacher().getId());

        //verificamos si "foundCourse" se encontro,
        // .flatMap() por que lo que sigue me devuelve un "Optional" y nesesito desembolverlo evitando 'Optional<optional<Subjet_base>>'
        return foundCourse.flatMap(course ->
                //verificamos si "foundTeacher" se encontro, me devuelve un optional<Subjet_base>
                   foundTeacher.map(teacher -> {
                       //hacemos las conecciones bidireccionales
                       subjet.setCourse(course);
                       subjet.setTeacher(teacher);
                       trasition(course,subjet,teacher);
                       //guardamos "subjet" y lo retornamos como "Subjet_base"
                       return subjetRepository.save(subjet);
                   }));
        //Si "foundCourse" o "foundTeacher" no se encontro retornara un 'optional.empty();'
    }
    private void trasition(Courses_base course, Subjet_base subjet, Teacher teacher){
        subjet.transition(course,teacher);
    }
    //===============
}
