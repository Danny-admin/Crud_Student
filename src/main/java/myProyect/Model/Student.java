package myProyect.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// Esta anotación agrega un ID único para referenciar objetos ya serializados.
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity  //Indica que va a ser una tabla entidad en la base de datos
@Getter @Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotNull
    private Integer age;
    @Email
    private String email;
    @NotBlank
    private String user;
    @NotBlank
    private String password;

    @ManyToMany //relacion con materia
    @JoinTable(
            name = "student_subjet",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subjet_id")
    )
    private List<Subjet_base> listSubjet = new ArrayList<>();

    public Student() {}  //contructor vacio (importante)

    public void transition(List<Subjet_base> listSubjet){
        //Me permite incluir este alumno a la lista de alumnos de las materias almacenadas
        listSubjet.forEach(subjet -> subjet.getListStudent().add(this));
    }
}
