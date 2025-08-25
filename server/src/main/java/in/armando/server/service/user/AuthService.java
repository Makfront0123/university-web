package in.armando.server.service.user;

import java.util.List;

import in.armando.server.entity.UserEntity;
import in.armando.server.io.user.UserRequest;
import in.armando.server.io.user.UserResponse;

public interface AuthService {
    UserResponse register(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> getAllUsers();

    void delete(String userId);

    UserResponse verifyOtp(String email, String otp);

    UserResponse verifyOtpForgot(String email, String otp);

    UserEntity getUserByEmail(String email);

    UserResponse getUserById(String userId);

    String logout(String token);

    String logoutByEmail(String email);

    String resendOtp(String email);

    String forgot(String email);

    String resetPassword(String email, String password, String newPassword);

}