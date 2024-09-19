package example.assignment;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"example.*"}) //To locate JWTTokenUtil in common (if authentication added to controller)
@EntityScan("example.*")//To locate AppUser in common (if authentication added to controller)
@EnableRabbit
@SpringBootApplication
public class AssignmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }
}
