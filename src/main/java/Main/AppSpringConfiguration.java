package Main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("Randomizers")
@PropertySource("classpath:randomize.properties")
public class AppSpringConfiguration {

}
