package myProyect.Services;

import myProyect.Model.Faculty_base;
import myProyect.Model.University;
import myProyect.Repository.FacultyRepository;
import myProyect.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacultyService extends GenericService<Faculty_base,Long>{

    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, UniversityRepository universityRepository) {
        super(facultyRepository);
        this.facultyRepository = facultyRepository;
        this.universityRepository = universityRepository;
    }
    //====== Crear una facultad con (verificacion de existencia de universidad) ========
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
    //==============================================
}
