package sg.edu.nus.iss.jwtdemo2.dao.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String username;

    private String email;

    private String password;

    private Set<String> role;
}
