package myProyect.Services;

import myProyect.Dto.SearchStudentDto;
import myProyect.Model.Faculty_base;
import myProyect.Model.Student;
import myProyect.Model.University;
import myProyect.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static myProyect.Services.CourseService.getStudentsByCodes;

@Service
public class UniversityService extends GenericService<University,Long>{

    private final UniversityRepository repository;
    private final FacultyService facultyService;

    @Autowired
    public UniversityService(UniversityRepository repository,@Lazy FacultyService facultyService) { //inyectamos el repositorio
        super(repository);
        this.repository = repository;
        this.facultyService = facultyService;
    }
    @Override
    public Optional<University> postObject(University university) {
        return Optional.of(repository.save(university));
    }
    //=================== METODO DE RECOLECCION DE ESTUDIANTES ===========================
    public ResponseEntity<?> getStudents(SearchStudentDto information) {
        Optional<University> foundUniversity = this.getObject(information.getIdUniversity());
        if(foundUniversity.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: RECURSOS NO ENCONTRADOS");
        }
        List<Faculty_base> listFaculties = (facultyService.getFacultyByUniversity(information.getIdUniversity())).stream()
                .toList();
        //Extraemos los estudiantes de los codigos del "Body"
        List<Student> listStudent = getStudentsByCodes(
                listFaculties,faculty -> facultyService.getStudents(
                        //Le pasamos los parametros de id y el atributo faculty donde se insertara la id
                        new SearchStudentDto(faculty.getId(), SearchStudentDto.AttributeType.FACULTY))
        ); //importamos el metodo estatico de "CourseService"

        return listStudent.isEmpty() ? ResponseEntity.noContent().build() :  ResponseEntity.ok(listStudent);
    }
}
