package myProyect.Repository;

import myProyect.Model.Courses_base;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends GenericRepository<Courses_base, Long> {
    List<Courses_base> findByFaculty_Id(Long facultyId);
}
