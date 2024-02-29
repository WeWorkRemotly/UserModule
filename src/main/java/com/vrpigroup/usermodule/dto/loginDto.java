package com.vrpigroup.usermodule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Schema(name = "loginDto", description = "Data Transfer Object for login")
@NoArgsConstructor
@AllArgsConstructor
@Component
public class loginDto {
@Schema(name = "userName", description = "User Name", example = "Kanhaiya", required = true)
    private String userName;
    @Schema(name = "password", description = "Password", example = "Kanhaiya@123", required = true)
    private String password;
    @Schema(name="email",description = "Email Id",example = "officialkanhaiya121@gmail.com")
    private String email;

}
