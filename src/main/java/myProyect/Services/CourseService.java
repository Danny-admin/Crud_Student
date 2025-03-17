package myProyect.Services;

import myProyect.Model.Courses_base;
import myProyect.Model.Faculty_base;
import myProyect.Repository.CourseRepository;
import myProyect.Repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService extends GenericService<Courses_base,Long>{

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, FacultyRepository facultyRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Optional<Courses_base> postObject(Courses_base course) {
        Optional<Faculty_base> foundFaculty = facultyRepository.findById(course.getFaculty().getId());  //busmos la facultad que ingreso
        //.map() por que en su interior solo devuelve datos de tipo T basicos
        return foundFaculty.map(faculty -> {course.setFaculty(faculty);
                                                        transition(course.getFaculty(),course);
                                                        return courseRepository.save(course);} );
    }
    private void transition(Faculty_base faculty, Courses_base course){
        faculty.trasition(course); //mantenemos la relacion bidireccional
    }
}
