package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.RoleEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.role.RoleResponse;
import in.armando.server.io.user.UserRequest;
import in.armando.server.io.user.UserResponse;
import in.armando.server.repository.RoleRepository;
import in.armando.server.repository.UserRepository;
import in.armando.server.service.user.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse createUser(UserRequest request) {
        RoleEntity role = roleRepository.findById(request.getRoleId()).orElseThrow();
        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(role)
                .build();

        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
        return mapToResponse(entity);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        response.setVerified(entity.isVerified());
        response.setRole(
                RoleResponse.builder()
                        .id(entity.getRole().getId())
                        .name(entity.getRole().getName())
                        .build());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

}
