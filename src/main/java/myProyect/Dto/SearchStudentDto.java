package myProyect.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchStudentDto {
    @NotNull(groups = university.class)  //esta etiqueta no debe estar vacia las otras deben estar vacias
    @Null(groups = faculty.class)
    @Null(groups = course.class)
    @Null(groups = subject.class)
    private Long idUniversity;

    @NotNull(groups = faculty.class)
    @Null(groups = university.class)
    @Null(groups = course.class)
    @Null(groups = subject.class)
    private Long idFaculty;

    @NotNull(groups = course.class)
    @Null(groups = university.class)
    @Null(groups = faculty.class)
    @Null(groups = subject.class)
    private Long idCourse;

    @NotNull(groups = subject.class)
    @Null(groups = university.class)
    @Null(groups = faculty.class)
    @Null(groups = course.class)
    private Long idSubject;

    // Enum permite definir valores constantes como SUBJECT y FACULTY.
    public enum AttributeType{ UNIVERSITY,FACULTY,COURSE,SUBJECT }

    //El constructor usa Enum para decidir dónde almacenar el ID.
    public SearchStudentDto(Long id,AttributeType type){
        if(type.equals(AttributeType.UNIVERSITY)){ //si "type" es igual a "UNIVERSITY" la id sera para "idUniversity"
            this.idUniversity = id;
        }
        if(type.equals(AttributeType.FACULTY)){
            this.idFaculty = id;
        }
        if(type.equals(AttributeType.COURSE)){
            this.idCourse = id;
        }
        if(type.equals(AttributeType.SUBJECT)){
            this.idSubject = id;
        }
    }

    // estas interfaces son etiquetas para cada solocitud http
    public interface university {}
    public interface faculty {}
    public interface course {}
    public interface subject {}
}
