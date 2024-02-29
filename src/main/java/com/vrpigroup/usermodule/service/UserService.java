package com.vrpigroup.usermodule.service;
import com.vrpigroup.usermodule.annotations.email.EmailValidation;
import com.vrpigroup.usermodule.annotations.email.EmailValidationServiceImpl;
import com.vrpigroup.usermodule.dto.UserDto;
import com.vrpigroup.usermodule.entity.ContactUs;
import com.vrpigroup.usermodule.entity.Roles;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.dto.loginDto;
import com.vrpigroup.usermodule.exception.UserAlreadyExistException;
import com.vrpigroup.usermodule.exception.UserNotFoundException;
import com.vrpigroup.usermodule.mapper.UserMapper;
import com.vrpigroup.usermodule.repo.ContactUsRepo;
import com.vrpigroup.usermodule.repo.UserRepository;
//import com.vrpigroup.usermodule.security.SecurityConfig;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

import static com.vrpigroup.usermodule.constants.UserConstants.USER_WITH_USERNAME_OR_EMAIL_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userModuleRepository;
    private final ContactUsRepo contactUsRepo;
    private final EmailValidationServiceImpl emailValidationService;
    private final EmailValidation emailValidation;
    private final PasswordEncoder passwordEncoder;


//    private final SecurityConfig securityConfig;


    public List<UserEntity> getAllUser() {
        try {
            return userModuleRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all users", e);
            return Collections.emptyList();
        }
    }

    public Optional<UserEntity> getUserById(Long id) {
        try {
            return userModuleRepository.findById(id);

        } catch (Exception e) {
            logger.error("Error while fetching user by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Transactional
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException{
        try {
            if (userModuleRepository.existsByUserNameOrEmail(userDto.getUserName(), userDto.getEmail())) {
                logger.warn("User with username or email already exists. Cannot create a new user.");
                throw new UserAlreadyExistException(USER_WITH_USERNAME_OR_EMAIL_ALREADY_EXISTS);
            }
            UserMapper userMapper = new UserMapper();
            String otp = generateOtp();
            userDto.setOtp(otp);
            userDto.setActive(false);
            Set<Roles> roles = new HashSet<>();
            roles.add(Roles.USER);
            userDto.setRoles(roles);
            emailValidation.isEmailValid(userDto.getEmail());
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            sendOtpByEmail(userDto.getEmail(), otp);
            UserEntity userEntity = new UserEntity();
            UserEntity user = userMapper.userDtoToUser(userEntity,userDto);
            userModuleRepository.save(user);
            logger.info("User created successfully for email: {}", userDto.getEmail());
            return userDto;

        } catch (Exception e) {
            logger.error("Error while creating user", e);
            return null;
        }
    }

    private void sendOtpByEmail(String email, String otp) {
        emailValidationService.sendVerificationEmail(email, otp);
        logger.info("Sending OTP to {}: {}", email, otp);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public UserEntity updateUser(Long id, UserEntity updatedUserModule) {
        if (userModuleRepository.existsById(id)) {
            updatedUserModule.setId(id);
            return userModuleRepository.save(updatedUserModule);
        } else {
            logger.warn("Failed to update user. User not found for ID: {}", id);
            return null;
        }
    }

    public void deleteUser(Long id) {
        try {
            userModuleRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", id, e);
        }
    }


    public UserEntity loginUser(loginDto userModule) {
        Optional<UserEntity> userByEmail = userModuleRepository.findByEmail(userModule.getEmail());
        Optional<UserEntity> userByUsername = userModuleRepository.findByUserName(userModule.getUserName());
        if (userByEmail.isPresent() && verifyLogin(userByEmail.get(), userModule)) {
            return userByEmail.get();
        }
        if (userByUsername.isPresent() && verifyLogin(userByUsername.get(), userModule)) {
            return userByUsername.get();
        }
        logger.warn("Unsuccessful login attempt for username: {}", userModule.getUserName());

        return null;
    }

    private boolean verifyLogin(UserEntity user, loginDto userModule) {
        if ( user.isActive()
                && passwordEncoder.matches(userModule.getPassword(), user.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean verifyEncryptedPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public void contactUs(ContactUs contactUs) {
        try {
            contactUsRepo.save(contactUs);
        } catch (Exception e) {
            logger.error("Error while processing contact us message", e);
        }
    }


    @Transactional
    public boolean verifyAccount(String email, String otp) {
        try {
            Optional<UserEntity> user = userModuleRepository.findByEmail(email);
            if (user.isPresent() && user.get().getOtp().equals(otp)) {
                UserEntity usr= user.get();
                usr.setActive(true);
                userModuleRepository.save(usr);
                logger.warn("Save data: {}", email);
                return true;
            }
            logger.warn("Failed to verify account. User not found for email: {}", email);
            return false;
        } catch (Exception e) {
            logger.error("Error while verifying account for email: {}", email, e);
            return false;
        }
    }

    public UserEntity updateUserProfileAndDetails(Long id, MultipartFile profilePhoto, UserEntity updatedUser) {
        Optional<UserEntity> optionalUser = userModuleRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setPincode(updatedUser.getPincode());

            try {
                if (profilePhoto != null && !profilePhoto.isEmpty()) {
                    user.setProfilePhoto(profilePhoto.getBytes());
                }
                return userModuleRepository.save(user);
            } catch (IOException e) {
                logger.error("Error while updating user profile photo", e);
            }
        } else {
            logger.warn("Failed to update user profile and details. User not found for ID: {}", id);
        }
        return null;
    }
}