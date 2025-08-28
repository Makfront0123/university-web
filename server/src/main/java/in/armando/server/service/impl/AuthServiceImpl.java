package in.armando.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.armando.server.entity.RoleEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.students.StudentsRequest;
import in.armando.server.io.user.UserRequest;
import in.armando.server.io.user.UserResponse;
import in.armando.server.repository.RoleRepository;
import in.armando.server.repository.UserRepository;
import in.armando.server.service.ActiveSessionService;
import in.armando.server.service.EmailService;
import in.armando.server.service.ProfessorService;
import in.armando.server.service.StudentsService;
import in.armando.server.service.TokenBlacklistService;
import in.armando.server.service.user.AuthService;
import in.armando.server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;
    private final ActiveSessionService activeSessionService;
    private final RoleRepository roleRepository;
    private final StudentsService studentService;
    private final ProfessorService professorService;
    final JwtUtil jwtUtil;

    final EmailService emailService;

    @Override
    public UserResponse register(UserRequest request) {
        UserEntity newUser = convertToEntity(request);

        newUser.setVerified(false);
        String otp = String.format("%04d", new Random().nextInt(10000));

        newUser.setOtp(otp);
        newUser.setOtpExpiration(LocalDateTime.now().plusMinutes(10));
        emailService.sendOtp(newUser.getEmail(), otp);

        repository.save(newUser);

        return convertToResponse(newUser);
    }

    private UserEntity convertToEntity(UserRequest request) {
        RoleEntity roleEntity = roleRepository.findById(request.getRole())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + request.getRole()));

        return UserEntity.builder()
                .name(request.getName().trim())
                .lastName(request.getLastName().trim())
                .email(request.getEmail().trim())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .role(roleEntity)
                .build();
    }

    private UserResponse convertToResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .verified(user.isVerified())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public String getUserRole(String email) {
        UserEntity existingUser = repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado"));

        return existingUser.getRole().getName();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String userId) {
        UserEntity existingUser = repository.findByEmail(userId).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado"));

        repository.delete(existingUser);
    }

    @Override
    public UserResponse getUserById(String userId) {
        UserEntity existingUser = repository.findByEmail(userId).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado"));

        return convertToResponse(existingUser);
    }

    @Override
    public UserResponse verifyOtp(String email, String otp) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código OTP inválido o ya usado");
        }
        if (user.getOtpExpiration().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El OTP ha expirado");
        }
 
        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiration(null);
        repository.save(user);
        if ("STUDENTS".equals(user.getRole().getName())) {
            StudentsRequest studentRequest = new StudentsRequest();
            studentRequest.setUserId(user.getId());
            studentService.createStudent(studentRequest);
        } else if ("TEACHERS".equals(user.getRole().getName())) {
            ProfessorRequest professorRequest = new ProfessorRequest();
            professorRequest.setUserId(user.getId());
            professorService.createProfessor(professorRequest);
        }

        return convertToResponse(user);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    @Override
    public String logout(String token) {
        System.out.println("Token recibido para logout: " + token);

        try {
            String email = jwtUtil.extractUsername(token);
            tokenBlacklistService.blacklistToken(token);
            activeSessionService.removeSession(email);
            return "Logout successful";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido o expirado", e);
        }

    }

    @Override
    public String resendOtp(String email) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String otp = String.format("%04d", new Random().nextInt(10000));
        user.setOtp(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(10));
        repository.save(user);

        emailService.sendOtp(user.getEmail(), otp);

        return "OTP reenviado correctamente";
    }

    @Override
    public String forgot(String email) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String otp = String.format("%04d", new Random().nextInt(10000));
        user.setOtp(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(10));
        repository.save(user);

        emailService.sendOtp(user.getEmail(), otp);

        return "OTP reenviado correctamente";
    }

    @Override
    public UserResponse verifyOtpForgot(String email, String otp) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            throw new RuntimeException("Código OTP inválido o ya usado");
        }

        if (user.getOtpExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El OTP ha expirado");
        }

        user.setOtp(null);
        user.setOtpExpiration(null);
        repository.save(user);

        return convertToResponse(user);
    }

    @Override
    public String resetPassword(String email, String password, String newPassword) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPassword(passwordEncoder.encode(newPassword));

        repository.save(user);

        return "Password reset successfully";
    }

    @Override
    public String logoutByEmail(String email) {
        String token = jwtUtil.extractToken(email);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active session for this user.");
        }
        tokenBlacklistService.blacklistToken(token);
        activeSessionService.removeSession(email);
        return "Logout successful";
    }

}