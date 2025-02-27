package myProyect.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity  //Indica que va a ser una tabla entidad en la base de datos
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
    private String course;
    @NotBlank
    private String user;
    @NotBlank
    private String password;

    public Student() {}  //contructor vacio (importante)
}
