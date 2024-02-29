package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import com.vrpigroup.usermodule.annotations.phone.Phone;
import com.vrpigroup.usermodule.entity.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
@Schema(name = "UserDto", description = "Data Transfer Object for User")
public class UserDto {
@Schema(name = "userName", description = "User Name", example = "Aman Raj", required = true)
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String userName;
@Schema(name = "password", description = "Password", example = "Aman@123", required = true)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Password
    private String password;
@Schema(name = "name", description = "Full Name", example = "Aman Raj", required = true)
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters")
    private String name;
@Schema(name = "email", description = "Email Id", example =" ")
    @ValidEmail
    private String email;
@Schema(name = "fathersName", description = "Father's Name", example = "Raj Kumar", required = true)
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    private String fathersName;
@Schema(name = "address", description = "Address", example = "Bihar", required = true)
    @Size(max = 255, message = "Address can't exceed 255 characters")
    private String address;
@Schema(name = "phoneNumber", description = "Phone Number", example = "1234567890", required = true)
    @Phone
    private String phoneNumber;
@Schema(name = "dateOfBirth", description = "Date of Birth", example = "1999-12-12", required = true)
    @Past
    @Column(name = "DOB", nullable = false)
    private LocalDate dateOfBirth;
@Schema(name = "pincode", description = "Pincode", example = "800001", required = true)
    @Pattern(regexp = "\\d{6}", message = "Invalid pincode format")
    private String pincode;
@Schema(name = "panCardNumber", description = "PAN Card Number", example = "ABCDE1234F", required = true)
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN card format")
    private String panCardNumber;
@Schema(name = "aadharCardNumber", description = "Aadhar Card Number", example = "123456789012", required = true)
    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    private String aadharCardNumber;
@Schema(name = "otp", description = "OTP", example = "123456", required = true)
    private String otp;
@Schema(name = "active", description = "Active", example = "true", required = true)
    private boolean active;
@Schema(name = "roles", description = "Roles", example = "ADMIN", required = true)
    private Set<Roles> roles;
@Schema(name = "profilePic", description = "Profile Picture", example = "profile.jpg", required = true)
    private String profilePic;
}
