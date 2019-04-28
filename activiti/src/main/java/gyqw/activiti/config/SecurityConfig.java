package gyqw.activiti.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fred
 * 2019-04-28 11:33 AM
 */
@SpringBootApplication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final String[][] usersGroupsAndRoles = {
            {"applicant", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"leader1", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"leader2", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
            {"system", "password", "ROLE_ACTIVITI_USER"},
            {"admin", "password", "ROLE_ACTIVITI_ADMIN"},
    };

    @Bean
    public List<String> userList() {
        List<String> userList = new ArrayList<>();
        for (String[] user : this.usersGroupsAndRoles) {
            userList.add(user[0]);
        }
        return userList;
    }

    @Bean
    public UserDetailsService myUserDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        for (String[] user : this.usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            logger.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }

        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll().and().csrf().disable();
    }

}
