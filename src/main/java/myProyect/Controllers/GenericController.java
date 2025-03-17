package myProyect.Controllers;

/*T: Representa el tipo de la entidad (por ejemplo, User, Product, etc.).
  ID: Representa el tipo de la clave primaria de la entidad (por ejemplo, Long, Integer, etc.).*/

import jakarta.validation.Valid;
import myProyect.Services.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//======== clase molde ========
@Validated
public abstract class GenericController <T,ID>{

    private final GenericService<T,ID> service;

    //Inyeccion base para que sus hijos pa puedan implementar su propio servicion personalizado
    public GenericController(GenericService<T,ID> service) {
        this.service = service;
    }
    // ======== Enpoint (Mostrar recursos de una Entidad completa) ===============
    @GetMapping
    public ResponseEntity<List<T>> getObjets(){
        List<T> result = service.getObjects();
        if(result.isEmpty()){return ResponseEntity.noContent().build();}   //codigo (204) (no Content)
        return ResponseEntity.ok(result);  //codigo (200)
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getObjetById(@PathVariable ID id){
        Optional<T> foundObject = service.getObject(id);
        if(foundObject.isPresent()){return ResponseEntity.ok(foundObject) ;} //codigo (200)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }
    //==================
    @PostMapping //inidcamos que este es un enpoint reutilizable
    public abstract ResponseEntity<?> postObjet(@Valid @RequestBody T object);

    @PutMapping("/{id}")
    public ResponseEntity<?> putStudent(@PathVariable ID id,@Valid @RequestBody T modifiedObject){
        Optional<T> foundObject = service.getObject(id);  //Guardamos el resultado del recurso
        if(foundObject.isPresent()){
            Optional<T> updatedObject = service.postObject(modifiedObject); // guardamos el recurso
            return ResponseEntity.ok(updatedObject);  //codigo (200)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteObjetById(@PathVariable ID id){
        Optional<T> foundObject = service.getObject(id);
        if(foundObject.isPresent()){
            service.deleteObject(id);
            return ResponseEntity.noContent().build(); //codigo (204)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: Recurso no Encontrado"); //codigo (404)
    }

}
