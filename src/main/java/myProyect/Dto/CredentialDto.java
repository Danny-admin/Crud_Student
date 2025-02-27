package myProyect.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CredentialDto {
    @NotBlank
    private String user;
    @NotBlank
    private String password;

    public CredentialDto(){}
}
