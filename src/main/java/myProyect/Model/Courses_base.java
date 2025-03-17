package myProyect.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Courses") //peronalizar nombre de tabla
@Getter @Setter
public class Courses_base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "course")   //relacion con materias
    @JsonManagedReference
    private List<Subjet_base> listSubjet = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "Faculty_id")  //relacion con facultad
    @JsonBackReference
    private Faculty_base faculty;

    public Courses_base() {}
}
