package myProyect.Services;

import myProyect.Dto.SearchStudentDto;
import myProyect.Model.Courses_base;
import myProyect.Model.Faculty_base;
import myProyect.Model.Student;
import myProyect.Model.Subjet_base;
import myProyect.Repository.CourseRepository;
import myProyect.Repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseService extends GenericService<Courses_base,Long>{

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final SubjetService subjetService;  // Se creará solo cuando alguien lo necesite

    @Autowired
    public CourseService(CourseRepository courseRepository, FacultyRepository facultyRepository,@Lazy SubjetService subjetService) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        this.facultyRepository = facultyRepository;
        this.subjetService = subjetService;
    }

    //===========================================================
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
    //=========================== METODO DE RECOLECCION DE ESTUDIANTES =================================
    public ResponseEntity<?> getStudents(SearchStudentDto information) {
        Optional<Courses_base> foundCourse = this.getObject(information.getIdCourse());
        if(foundCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: RECURSOS NO ENCONTRADOS");
        }
        //buscamos todas las materias de esta carrera y filtramos solo las que tienen alumnos
        List<Subjet_base> selectedSubjets = ( subjetService.getSubjetsByCourse(information.getIdCourse()) ).stream()
                .filter(subjet -> !subjet.getListStudent().isEmpty() ).toList();

        //Metodo que me permite extraer los alumnos de los Bodys
        List<Student> listStudent = getStudentsByCodes(
                selectedSubjets,subjet -> subjetService.getStudents(
                        //le indicamos como parametro la "id" y el "atributo seleccionado del Enum"
                        new SearchStudentDto(subjet.getId(), SearchStudentDto.AttributeType.SUBJECT
                        ))
        );

        return  ( listStudent.isEmpty() ?  ResponseEntity.noContent().build() :  ResponseEntity.ok(listStudent) );
    }
    //==========================================================
    public List<Courses_base> getCoursesByFaculty(Long facultyId) {
        return courseRepository.findByFaculty_Id(facultyId);
    }
    //================= EXTRACCION DE BODY DE LOS CODIGOS EXTRAIDOS ============================
    public static <T> List<Student> getStudentsByCodes(List<T> objectsSelected, Function<T,ResponseEntity<?>> fetchfunction) {
        return extractCodes(objectsSelected,fetchfunction).stream()
                .map(ResponseEntity::getBody)  // estraemos el "Body" de todos los codigos
                .filter(body -> body instanceof List<?>)   //verificacos si el "Body" es una lista si no lo descartamos
                .flatMap(body -> ((List<?>) body).stream())  //aplanamos la list<Student> a Student
                .filter(object -> object instanceof Student)   //Verificamos di el objeto del "Body" es un "Student"
                .map(object -> (Student) object)   //Transformamos ese objeto <?> a Studiant
                .collect(Collectors.toMap(      //ELIMINACION DE DUPLICADOS SEGUN SU ID
                        Student::getId,        // 🔥 Clave única: el ID del estudiante
                        Student -> Student,       // Valor de entrada -> valor de salida: el mismo objeto Student
                        (existente,duplicado) -> existente       // Si hay duplicados, mantiene el primero
                ))
                .values()   //recolectamos los valores
                .stream()   // lo convertimos en un flujo
                .toList();  //y lo almacenamos en una lista
    }
    //================ EXTRACCION  DE CODIGOS (200 OK) DE LOS OBJETOS SELECCIONADOS ===========================
    private  static <T> List<ResponseEntity<?>> extractCodes(List<T> objectsSelected, Function<T,ResponseEntity<?>> fetchfunction) {
        return objectsSelected.stream()
                .map(fetchfunction)
                .filter(code -> code.getStatusCode().is2xxSuccessful())
                .collect(Collectors.toList());  //a diferencia de .toList(), este es mas flexible con <capture of ?>
    }
    //===============================================
}
