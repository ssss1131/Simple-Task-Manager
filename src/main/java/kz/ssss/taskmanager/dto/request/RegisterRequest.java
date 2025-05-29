package kz.ssss.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Size(min = 5, max = 50, message = "Username should be between 5 and 50 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 6, max = 255, message = "Passwords length should be between 6 and 255 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
