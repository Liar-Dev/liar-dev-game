package liar.gamemvcservice.common.config;

import liar.gamemvcservice.common.auth.token.filter.AuthenticationGiveFilter;
import liar.gamemvcservice.common.auth.token.tokenprovider.TokenProviderPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProviderPolicy tokenProviderPolicy;

    private final AntPathMatcher antPathMatcher;
    private static final String[] webServiceWhiteList = {
            "/static/**", "/static/js/**", "/static/images/**",
            "/static/css/**", "/static/scss/**", "/static/docs/**",
            "/h2-console/**", "/favicon.ico", "/error"
    };

    private static final String[] socketWhiteList = {
            "/game-service/game-websocket",
            "/game-service/game-websocket/info",
            "/game-service/game-websocket/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(webServiceWhiteList);
    }

    @Bean
    SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(socketWhiteList)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin().disable()
                .addFilterBefore(authenticationGiveFilter(), UsernamePasswordAuthenticationFilter.class)

                .httpBasic().disable()
                .csrf(AbstractHttpConfigurer::disable)
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .frameOptions()
                .sameOrigin();

        return http.build();
    }

    @Bean
    public AuthenticationGiveFilter authenticationGiveFilter() {
        return new AuthenticationGiveFilter(tokenProviderPolicy, antPathMatcher);
    }
}

