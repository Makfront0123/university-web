package in.armando.server.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import in.armando.server.entity.UserEntity;
import in.armando.server.io.auth.AuthRequest;
import in.armando.server.io.auth.AuthResponse;
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
    public UserResponse register(@RequestBody UserRequest request) {
        return authService.register(request);
    }

    @PostMapping("/verify-otp")
    public UserResponse verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return authService.verifyOtp(email, otp);
    }

    @PostMapping("/resend-otp")
    public String resendOtp(@RequestParam String email) {
        return authService.resendOtp(email);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        return authService.forgot(email);
    }

    @PostMapping("/verify-forgot")
    public UserResponse verifyOtpForgot(@RequestParam String email, @RequestParam String otp) {
        return authService.verifyOtpForgot(email, otp);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String newPassword) {
        return authService.resetPassword(email, password, newPassword);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            authenticate(request.getEmail(), request.getPassword());

            final UserEntity user = authService.getUserByEmail(request.getEmail());
            if (!user.isVerified()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not verified");
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            String existingToken = activeSessionService.getToken(request.getEmail());
            if (existingToken != null && !jwtUtil.isTokenExpired(existingToken)) {
                return new AuthResponse(
                        request.getEmail(),
                        authService.getUserRole(request.getEmail()),
                        existingToken,
                        user.isVerified());
            }

            final String token = jwtUtil.generateToken(userDetails);
            activeSessionService.createSession(request.getEmail(), token);

            return new AuthResponse(
                    request.getEmail(),
                    authService.getUserRole(request.getEmail()),
                    token,
                    user.isVerified());

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }
    }

    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String, String> request) {
        return passwordEncoder.encode(request.get("password"));
    }
}
