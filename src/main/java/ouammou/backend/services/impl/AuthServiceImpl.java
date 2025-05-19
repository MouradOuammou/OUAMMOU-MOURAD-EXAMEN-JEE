package ouammou.backend.services.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ouammou.backend.dtos.*;
import ouammou.backend.entites.*;
import ouammou.backend.mappers.UserMapper;
import ouammou.backend.repositories.*;
import ouammou.backend.security.JwtTokenProvider;
import ouammou.backend.services.AuthService;


@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            ClientRepository clientRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(user.getUsername());

        return new AuthResponseDTO(token, user.getUsername(), user.getRole());
    }

    @Override
    @Transactional
    public UserDTO register(UserDTO userDTO, String password) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username deja exists");
        }

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(password));

        if (userDTO.getClientId() != null) {
            Client client = clientRepository.findById(userDTO.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client n'existe pas avec id: " + userDTO.getClientId()));
            user.setClient(client);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}