package myProyect.Services;

/*GenericService<T, ID> está diseñado para trabajar con cualquier entidad (T)
 y cualquier tipo de clave primaria (ID). */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//======== clase molde ========
public abstract class GenericService <T,ID>{

    private final JpaRepository<T,ID> repository;

    //Inyeccion base para que sus hijos pa puedan implementar su propio repositorio personalizado
    public GenericService(JpaRepository<T,ID> repository) { // Spring inyecta el repositorio correcto
        this.repository = repository;
    }

    //==== mostrar todos los recursos de una entidad ====
    public List<T> getObjects(){
        return repository.findAll();
    }
    //==== mostrar un recurso especifico ====
    public Optional<T> getObject(ID id){
        // findById(id): Devuelve un Optional<T>, y si no encuentra el registro, devuelve un Optional.empty().
       return  repository.findById((id));
    }
    //==== crear o guardar (si la id existe en BD) un recurso ====
    public abstract Optional<T> postObject(T object);
    //==== eliminar un recurso ====
    public void deleteObject(ID id){
        repository.deleteById(id);
    }

}
