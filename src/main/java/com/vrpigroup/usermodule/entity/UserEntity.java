package com.vrpigroup.usermodule.entity;

import com.vrpigroup.usermodule.annotations.email.ValidEmail;
import com.vrpigroup.usermodule.annotations.passwordAnnotation.Password;
import com.vrpigroup.usermodule.annotations.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserEntity", description = "Entity for User")
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "id", description = "Id", example = "1", required = true)
    @Column(name = "User_ID")
    private Long id;

    @Schema(name = "userName", description = "User Name", example = "Aman Raj", required = true)
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(name = "user_Name", nullable = false, unique = true)
    private String userName;

    @Schema(name = "password", description = "Password", example = "Aman@123", required = true)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Password
    @Column(name = "Password", nullable = false)
    private String password;

    @Schema(name = "name", description = "Full Name", example = "Aman Raj", required = true)
    private String hashedPassword;

    @Schema(name = "name", description = "Full Name", example = "Aman Raj", required = true)
    @Size(min = 3, max = 50, message = "Full Name must be between 3 and 50 characters")
    @Column(name = "Full_Name", nullable = false)
    private String name;

    @Schema(name = "email", description = "Email Id", example = " ")
    @Email
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Schema(name = "fathersName", description = "Father's Name", example = "Raj Kumar", required = true)
    @Size(min = 3, max = 50, message = "Father's name must be between 3 and 50 characters")
    @Column(name = "Fathers_Name")
    private String fathersName;

    @Schema(name = "address", description = "Address", example = "Bihar", required = true)
    @Size(max = 255, message = "Address can't exceed 255 characters")
    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Schema(name = "phoneNumber", description = "Phone Number", example = "1234567890", required = true)
    @Column(name = "Phone_Number", nullable = false, unique = true)
    @Phone
    private String phoneNumber;

    @Schema(name = "dateOfBirth", description = "Date of Birth", example = "1999-12-12", required = true)
    @Past
    @Column(name = "DOB", nullable = false)
    private LocalDate dateOfBirth;

    @Schema(name = "pincode", description = "Pincode", example = "800001", required = true)
    //@NotBlank(message = "Pincode can't be blank")
    @Pattern(regexp = "\\d{6}", message = "Invalid pincode format")
    @Column(name = "Pincode", nullable = false)
    private String pincode;

    @Schema(name = "panCardNumber", description = "PAN Card Number", example = "ABCDE1234F", required = true)
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN card format")
    @Column(name = "Pan_Card_Number", unique = true)
    private String panCardNumber;

    @Schema(name = "aadharCardNumber", description = "Aadhar Card Number", example = "123456789012", required = true)
    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhar card format")
    @Column(name = "Aadhar_Card_Number", nullable = false, unique = true)
    private String aadharCardNumber;

    @Schema(name = "profilePhoto", description = "Profile Photo", example = "profile.jpg", required = true)
    @Lob
    private byte[] profilePhoto;

    @Schema(name = "otp", description = "OTP", example = "123456", required = true)
    private String otp;

    @Schema(name = "active", description = "Active", example = "true", required = true)
    private boolean active;
@Schema(name = "roles", description = "Roles", example = "ADMIN", required = true)
    @ElementCollection(targetClass = Roles.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

}
