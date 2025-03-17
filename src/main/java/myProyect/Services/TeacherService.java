package myProyect.Services;

import myProyect.Model.Teacher;
import myProyect.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService extends GenericService<Teacher,Long>{

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        super(repository);
        this.repository = repository;

    }

    @Override
    public Optional<Teacher> postObject(Teacher object) {
        return Optional.of(repository.save(object));
    }
}
