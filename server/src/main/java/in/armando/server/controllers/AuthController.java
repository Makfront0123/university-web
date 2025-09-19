package in.armando.server.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import in.armando.server.entity.UserEntity;
import in.armando.server.io.ApiResponse;
import in.armando.server.io.auth.AuthRequest;
import in.armando.server.io.auth.AuthResponse;
import in.armando.server.io.auth.OtpRequest;
import in.armando.server.io.user.UserRequest;
import in.armando.server.io.user.UserResponse;
import in.armando.server.service.ActiveSessionService;
import in.armando.server.service.user.AuthService;
import in.armando.server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final ActiveSessionService activeSessionService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody UserRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>("Usuario registrado exitosamente", response));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<UserResponse>> verifyOtp(@RequestBody OtpRequest request) {
        UserResponse response = authService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(new ApiResponse<>("Cuenta verificada exitosamente", response));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<String>> resendOtp(@RequestParam String email) {
        authService.resendOtp(email);
        return ResponseEntity.ok(new ApiResponse<>("OTP reenviado", null));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authService.forgot(email);
        return ResponseEntity.ok(new ApiResponse<>("OTP para resetear enviado", null));
    }

    @PostMapping("/verify-forgot")
    public ResponseEntity<ApiResponse<UserResponse>> verifyOtpForgot(@RequestBody OtpRequest request) {
        UserResponse response = authService.verifyOtpForgot(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(new ApiResponse<>("OTP de recuperación validado", response));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String newPassword = request.get("newPassword");

        authService.resetPassword(email, password, newPassword);
        return ResponseEntity.ok(new ApiResponse<>("Contraseña reseteada exitosamente", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok(new ApiResponse<>("Logout exitoso", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        try {
            final UserEntity user = authService.getUserByEmail(request.getEmail());

            if (!user.isVerified()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Esta cuenta no está verificada");
            }

            authenticate(request.getEmail(), request.getPassword());

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            String existingToken = activeSessionService.getToken(request.getEmail());
            if (existingToken != null && !jwtUtil.isTokenExpired(existingToken)) {
                AuthResponse response = new AuthResponse(
                        request.getEmail(),
                        authService.getUserRole(request.getEmail()),
                        existingToken,
                        user.isVerified());
                return ResponseEntity.ok(new ApiResponse<>("Sesión activa", response));
            }

            final String token = jwtUtil.generateToken(userDetails);
            activeSessionService.createSession(request.getEmail(), token);

            AuthResponse response = new AuthResponse(
                    request.getEmail(),
                    authService.getUserRole(request.getEmail()),
                    token,
                    user.isVerified());

            return ResponseEntity.ok(new ApiResponse<>("Login exitoso", response));

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", e);
        }
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("Usuario deshabilitado");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credenciales inválidas");
        }
    }

    @PostMapping("/encode")
    public ResponseEntity<ApiResponse<String>> encodePassword(@RequestBody Map<String, String> request) {
        String encoded = passwordEncoder.encode(request.get("password"));
        return ResponseEntity.ok(new ApiResponse<>("Contraseña encriptada", encoded));
    }
}