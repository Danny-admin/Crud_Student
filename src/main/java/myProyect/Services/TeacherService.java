package myProyect.Services;

import myProyect.Model.Teacher;
import myProyect.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService extends GenericService<Teacher,Long>{

    private final TeacherRepository repository;

    @Autowired
    public TeacherService(TeacherRepository repository) {
        super(repository);
        this.repository = repository;

    }

    @Override
    public Optional<Teacher> postObject(Teacher object) {
        return Optional.of(repository.save(object));
    }
}
