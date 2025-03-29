package myProyect.Services;

import myProyect.Dto.SearchStudentDto;
import myProyect.Model.Courses_base;
import myProyect.Model.Faculty_base;
import myProyect.Model.Student;
import myProyect.Model.University;
import myProyect.Repository.FacultyRepository;
import myProyect.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static myProyect.Services.CourseService.getStudentsByCodes;

@Service
public class FacultyService extends GenericService<Faculty_base,Long>{

    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;
    private final CourseService courseService;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, UniversityRepository universityRepository,@Lazy CourseService courseService) {
        super(facultyRepository);
        this.facultyRepository = facultyRepository;
        this.universityRepository = universityRepository;
        this.courseService = courseService;
    }
    //====== SERVICIOS BASICOS  ========
    @Override
    public Optional<Faculty_base> postObject(Faculty_base faculty){
        Optional<University> foundUniversity = universityRepository.findById(faculty.getUniversity().getId()); //buscamos la uni

        //retorna un Optional<faculty_base>,si no se encuentra retorna Optional.empty();
        return foundUniversity.map(university -> {faculty.setUniversity(university);
                                                            transition(university,faculty);
                                                            return  facultyRepository.save(faculty);} );
    }
    private static void transition(University university,Faculty_base faculty){
        university.transition(faculty);  //Nos dirigimos a la entidad de Uni. Para hacer la relacion entre ellas
    }
    //=================== METODO DE RECOLECCION DE ESTUDIANTES ===========================
    public ResponseEntity<?> getStudents(SearchStudentDto information) {
        Optional<Faculty_base> foundFaculty = this.getObject(information.getIdFaculty());
        if(foundFaculty.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: RECURSOS NO ENCONTRADOS");
        }
        //buscamos las carreras que pertenecen a esta facultad
        List<Courses_base> listCourses = (courseService.getCoursesByFaculty(information.getIdFaculty()));
        //Extraemos los estudiantes de los codigos del "Body"
        List<Student> listStudent = getStudentsByCodes(
                listCourses,course -> courseService.getStudents(
                        //Le pasamos los parametros de id y el atributo Course donde se insertara la id
                        new SearchStudentDto(course.getId(), SearchStudentDto.AttributeType.COURSE))
        );

        return listStudent.isEmpty() ? ResponseEntity.noContent().build() :  ResponseEntity.ok(listStudent);
    }
    public List<Faculty_base> getFacultyByUniversity(Long universityId){
        return facultyRepository.findByUniversity_Id(universityId);
    }
}
