package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.LoginRequestDTO;
import com.deliverytech.delivery.dto.request.RegisterRequestDTO;
import com.deliverytech.delivery.dto.response.*;
import com.deliverytech.delivery.entity.Usuario;
import com.deliverytech.delivery.security.JwtUtil;
import com.deliverytech.delivery.security.SecurityUtils;
import com.deliverytech.delivery.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "Operações de autenticação e autorização")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @PostMapping("/login")
    @Operation(
            summary = "Fazer login",
            description = "Autentica um usuário e retorna um token JWT",
            tags = {"Autenticação"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Login bem-sucedido",
                                    value = """
                                            {
                                                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                                "tipo": "Bearer",
                                                "expiracao": 86400000,
                                                "usuario": {
                                                    "id": 1,
                                                    "nome": "João Silva",
                                                    "email": "joao@email.com",
                                                    "role": "CLIENTE"
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<ApiResponseWrapper<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

            // Autenticar usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getSenha()
                    )
            );
            // Carregar detalhes do usuário
            UserDetails userDetails = authService.loadUserByUsername(loginRequestDTO.getEmail());
            // Gerar token JWT
            String token = jwtUtil.generateToken(userDetails);
            // Criar resposta
            Usuario usuario = (Usuario) userDetails;
            UserResponseDTO userResponseDTO = new UserResponseDTO(usuario);
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, jwtExpiration,
                    userResponseDTO);

        ApiResponseWrapper<LoginResponseDTO> response =
                new ApiResponseWrapper<>(true, loginResponseDTO, "Login realizado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário no sistema",
            tags = {"Autenticação"}
    )
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            // Verificar se email já existe
            if (authService.existsByEmail(registerRequestDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email já está em uso");
            }
            // Criar novo usuário
            Usuario novoUsuario = authService.criarUsuario(registerRequestDTO);
            // Retornar dados do usuário (sem token - usuário deve fazer login)
            UserResponseDTO userResponseDTO = new UserResponseDTO(novoUsuario);
            return ResponseEntity.status(201).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @Operation(
            summary = "Dados do usuário",
            description = "Obtem dados do usuário logado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Usuário não Autenticado")
    })
    public ResponseEntity<?> getCurrentUser() {
        try {
            Usuario usuarioLogado = SecurityUtils.getCurrentUser();
            UserResponseDTO userResponseDTO = new UserResponseDTO(usuarioLogado);
            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
    }
}
