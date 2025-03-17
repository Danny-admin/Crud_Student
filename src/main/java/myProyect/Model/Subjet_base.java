package myProyect.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// Esta anotación agrega un ID único para referenciar objetos ya serializados.
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity
@Table(name = "Subjets") //peronalizar nombre de tabla
@Getter @Setter
public class Subjet_base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @NotBlank
    private String name;
    @NotBlank
    private String difficulty;

    @ManyToMany(mappedBy = "listSubjet")  //relacion con estudiante
    private List<Student> listStudent = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")  //relacion con carrera
    @JsonBackReference
    private Courses_base course;

    @OneToOne
    @JoinColumn(name = "teacher_id")  //relacion con profesor
    private Teacher teacher;

    public Subjet_base() {}

    public void transition(Courses_base course,Teacher teacher){
        course.getListSubjet().add(this);
        teacher.setSubjet(this);
    }
}
