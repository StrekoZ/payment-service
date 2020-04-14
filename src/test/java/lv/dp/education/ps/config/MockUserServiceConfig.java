package lv.dp.education.ps.config;

import lv.dp.education.ps.common.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("TestMock")
public class MockUserServiceConfig {

    @Bean
    @Primary
    public UserService userService() {
        return new UserService() {
            @Override
            public String getCurrentUser() {
                return "testUser";
            }
        };
    }
}
