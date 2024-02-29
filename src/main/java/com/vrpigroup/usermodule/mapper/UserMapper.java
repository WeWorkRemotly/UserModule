package com.vrpigroup.usermodule.mapper;

import com.vrpigroup.usermodule.dto.UserDto;
import com.vrpigroup.usermodule.entity.UserEntity;

public class UserMapper {
    public  UserEntity userDtoToUser(UserEntity userEntity, UserDto userDto){
       userEntity.setName(userDto.getName());
       userEntity.setUserName(userDto.getUserName());
       userEntity.setPassword(userDto.getPassword());
       userEntity.setAddress(userDto.getAddress());
       userEntity.setAadharCardNumber(userDto.getAadharCardNumber());
       userEntity.setActive(userDto.isActive());
       userEntity.setAadharCardNumber(userDto.getAadharCardNumber());
       userEntity.setPanCardNumber(userDto.getPanCardNumber());
       userEntity.setPhoneNumber(userDto.getPhoneNumber());
       userEntity.setPincode(userDto.getPincode());
       userEntity.setRoles(userDto.getRoles());
       userEntity.setOtp(userDto.getOtp());
       userEntity.setDateOfBirth(userDto.getDateOfBirth());
       userEntity.setEmail(userDto.getEmail());
       return userEntity;
    }

    public UserDto userToUserDto(UserEntity userEntity, UserDto userDto){

        userDto.setName(userEntity.getName());
        userDto.setFathersName(userEntity.getFathersName());
        userDto.setUserName(userEntity.getUserName());
        userDto.setPassword(userEntity.getPassword());
        userDto.setAddress(userEntity.getAddress());
        userDto.setAadharCardNumber(userEntity.getAadharCardNumber());
        userDto.setActive(userEntity.isActive());
        userDto.setAadharCardNumber(userEntity.getAadharCardNumber());
        userDto.setPanCardNumber(userEntity.getPanCardNumber());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setPincode(userEntity.getPincode());
        userDto.setRoles(userEntity.getRoles());
        userDto.setOtp(userEntity.getOtp());
        userDto.setDateOfBirth(userEntity.getDateOfBirth());
        userDto.setEmail(userEntity.getEmail());
        return userDto;
    }
}
