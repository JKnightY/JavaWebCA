package sg.edu.nus.javawebca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sg.edu.nus.javawebca.repositories.AdminRepository;

@SpringBootApplication
public class JavaWebCaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebCaApplication.class, args);
    }

/*    @Bean
    CommandLineRunner loadData(AdminRepository adminRepository){
        return args -> {

        };
    }*/
}
