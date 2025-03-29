package myProyect.Repository;

import myProyect.Model.Faculty_base;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends GenericRepository<Faculty_base, Long> {
    List<Faculty_base> findByUniversity_Id(Long universityId);
}
