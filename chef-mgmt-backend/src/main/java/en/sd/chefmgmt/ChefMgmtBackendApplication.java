package en.sd.chefmgmt;

import en.sd.chefmgmt.security.util.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class ChefMgmtBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChefMgmtBackendApplication.class, args);
    }
}
