package br.com.alura.forum.config.security;

import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final AutenticacaoService autenticacaoService;

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    public SecurityConfigurations(AutenticacaoService autenticacaoService, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.autenticacaoService = autenticacaoService;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Configurações de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    // Configurações de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/public/**").permitAll()
                .antMatchers(HttpMethod.POST, "/public/auth").permitAll()
                .antMatchers(HttpMethod.DELETE, "/topicos/**").hasAnyRole("MODERADOR")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    // Configurações de recursos estaticos (js, css, imagens e etc)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v3/api-docs/**", "/webjars/**", "/configurations/**", "/swagger-resources/**", "/swagger-ui/**");
    }

}
