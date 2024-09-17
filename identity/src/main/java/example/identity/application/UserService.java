package example.identity.application;

import example.common.application.JwtTokenUtil;
import example.identity.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;

    public Optional<String> authenticate(String username,
                                         String password) {
        return userRepository.findUserByUsernameAndPassword(username, password)
                .map(jwtTokenUtil::generateToken);
    }
}
