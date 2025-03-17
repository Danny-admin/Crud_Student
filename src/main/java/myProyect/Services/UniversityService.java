package myProyect.Services;

import myProyect.Model.University;
import myProyect.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityService extends GenericService<University,Long>{

    private final UniversityRepository repository;

    @Autowired
    public UniversityService(UniversityRepository repository) { //inyectamos el repositorio
        super(repository);
        this.repository = repository;
    }
    @Override
    public Optional<University> postObject(University university) {
        return Optional.of(repository.save(university));
    }
}
