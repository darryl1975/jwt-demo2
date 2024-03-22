package sg.edu.nus.iss.jwtdemo2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.jwtdemo2.model.ERole;
import sg.edu.nus.iss.jwtdemo2.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
      Optional<Role> findByName(ERole name);
}
