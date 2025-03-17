package myProyect.Repository;

import myProyect.Model.Faculty_base;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends GenericRepository<Faculty_base, Long> {
}
