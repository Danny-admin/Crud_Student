package myProyect.Repository;

import myProyect.Model.Subjet_base;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjetRepository extends GenericRepository<Subjet_base,Long>{
    List<Subjet_base> findByCourse_Id(Long idCourse);
}
