package myProyect.Repository;

import myProyect.Model.Courses_base;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends GenericRepository<Courses_base, Long> {
}
