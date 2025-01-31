package edu.neu.csye7374;
import edu.neu.csye7374.logger.Logger;
import edu.neu.csye7374.services.KitchenService;
import edu.neu.csye7374.services.MenuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public Logger logger(){
        return Logger.getInstance();
    }

    @Bean
    public MenuService menuService(){
        return MenuService.getInstance();
    }

    @Bean
    public KitchenService kitchenService(){
        return KitchenService.getInstance();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
