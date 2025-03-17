package myProyect.Dto;

/*Esta carpeta me permite interceptar los datos que se envian de un estudiante ocultando datos clasificados
     ( enviando )Student = id,name,age,email,course,user,passsword
                       Dto(interceptar)   (enviado)StudentDto = id,name,age,email,course */

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudentDto {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    public StudentDto(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
