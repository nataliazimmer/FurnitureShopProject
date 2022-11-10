package pl.coderslab.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    @Size(min = 3, message = "Imię musi zawierać conajmniej 3 znaki")
    private String firstName;

    @Size(min = 3, message = "Nazwisko musi zawierać conajmniej 3 znaki")
    private String lastName;

    private String username;

    @Size(min = 5, max = 10, message = "Hasło musi zawierać od 5 do 10 znaków")
    private String password;

    private String confirmPassword;
}

