package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.response.UserResponse;
import com.MKaaN.OtelBackend.dto.request.UserCreateRequest;
import com.MKaaN.OtelBackend.dto.request.UserUpdateRequest;
import com.MKaaN.OtelBackend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateRequest request);
    void updateUser(UserUpdateRequest request, @MappingTarget User user);
    UserResponse toResponse(User user);
}
