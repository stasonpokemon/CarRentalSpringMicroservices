package com.carrental.microservices.userservice.domain.mapper;

import com.carrental.microservices.userservice.domain.entity.Passport;
import com.carrental.microservices.userservice.domain.dto.request.PassportRequestDTO;
import com.carrental.microservices.userservice.domain.dto.response.PassportResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface presents the basic contract for converting Passport to PassportDTO and vice versa.
 */
@Mapper
public interface PassportMapper {

    Passport passportDTOtoPassport(PassportRequestDTO passportRequestDTO);


    PassportRequestDTO passportToPassportDTO(Passport passport);

    @Mapping(target = "userId", source = "user.id")
    PassportResponseDTO passportToPassportResponseDTO(Passport passport);
}
