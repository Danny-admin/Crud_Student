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
@Table(name = "Faculties") //peronalizar nombre de tabla
@Getter @Setter
public class Faculty_base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @NotBlank
    private String name;

    //cascade = CascadeType.ALL = ¿Quieres actualizar/guardar automáticamente?
    //orphanRemoval = true = ¿Quieres eliminar objetos huérfanos?
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)  //relacion con carreras
    @JsonManagedReference
    private List<Courses_base> listCourses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "University_id")  //relacion con universidad
    @JsonBackReference  // 🔙 Se omite al serializar desde aquí
    private University university;

    public Faculty_base() {}

    //============  METODO DE RELACION BIDIRECCIONAL =========
    public void trasition(Courses_base course){
        listCourses.add(course);
        course.setFaculty(this);
    }
}
