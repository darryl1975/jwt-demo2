package sg.edu.nus.iss.jwtdemo2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.jwtdemo2.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
  
    Boolean existsByEmail(String email);
}
