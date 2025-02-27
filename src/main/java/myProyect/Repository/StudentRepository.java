package myProyect.Repository;

import myProyect.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// JpaRepository<Student, Integer> -> indica la Entidad y el tipo de dato de la clave primaria
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUserAndPassword(String user, String password);
}
