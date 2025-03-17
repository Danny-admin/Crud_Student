package myProyect.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)  //relacion con facultad
    @JsonManagedReference // 🔗 Punto de inicio al serializar
    private List<Faculty_base> listFaculties = new ArrayList<>();

    public University() {}

    //======== METODOS DE RELACION BIDIRECCIONAL =========
    public void transition(Faculty_base faculty) {
        listFaculties.add(faculty);  //seteamos a la lista de facultades
        faculty.setUniversity(this); //setamos esta clase como refernecia a la clase de facultad
    }
}
