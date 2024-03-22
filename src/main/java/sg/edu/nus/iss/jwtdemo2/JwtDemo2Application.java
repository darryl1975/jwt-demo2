package sg.edu.nus.iss.jwtdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// References: 
// Need to make some changes to get it work
// https://www.bezkoder.com/spring-boot-security-jwt/
// https://github.com/bezkoder/spring-boot-security-login/tree/master
// 
// Other examples
// https://github.com/ali-bouali/spring-boot-3-jwt-security/tree/main
//
// https://medium.com/@truongbui95/jwt-authentication-and-authorization-with-spring-boot-3-and-spring-security-6-2f90f9337421
// https://github.com/buingoctruong/springboot3-springsecurity6-jwt/tree/master
// https://medium.com/@xoor/jwt-authentication-service-44658409e12c

@SpringBootApplication
public class JwtDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemo2Application.class, args);
	}

}
