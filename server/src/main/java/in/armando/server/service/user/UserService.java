package in.armando.server.service.user;

import java.util.List;

import in.armando.server.io.user.UserRequest;
import in.armando.server.io.user.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);
}
