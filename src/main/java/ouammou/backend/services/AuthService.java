package ouammou.backend.services;


import ouammou.backend.dtos.AuthRequestDTO;
import ouammou.backend.dtos.AuthResponseDTO;
import ouammou.backend.dtos.UserDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO);
    UserDTO register(UserDTO userDTO, String password);
}
