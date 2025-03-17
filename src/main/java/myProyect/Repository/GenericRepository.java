package myProyect.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean  //(IMPORTANTE) Indica a Spring que no debe instanciar el GenericRepository directamente.

//GenericRepository <T,ID> -> (T) generalizacion de objeto (ID) generalizacion de dato de PK
public interface GenericRepository <T,ID> extends JpaRepository<T, ID> {
}
