package myProyect.Repository;

import myProyect.Model.University;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends GenericRepository<University, Long> {
}
