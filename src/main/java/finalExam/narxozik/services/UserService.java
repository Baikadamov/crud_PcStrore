package finalExam.narxozik.services;

import finalExam.narxozik.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);

    Users createUser(Users users);

}
