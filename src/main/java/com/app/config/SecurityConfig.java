package com.app.config;

import com.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    1 primero se configura el SECURITY FILTER CHAIN
//    httpSecurity: pasa por todos los filtros


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // configuramos la administracion de seguridad

        return httpSecurity
                .csrf(csrf->csrf.disable()) // investigar, pero en mvc no lo necesitamos
                .httpBasic(Customizer.withDefaults()) // para el logeo por usuario y contraseña
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no guardaremnos sesiones en memoria, sino de un token
                .authorizeHttpRequests(http->{
                    // endpoints publicos
                    http.requestMatchers(HttpMethod.GET,"/auth/hello").permitAll();
                    // endpoints privados
                    http.requestMatchers(HttpMethod.GET,"/auth/hello-secure").hasAnyRole("DEVELOPER");
                    // el resto de endpoins sin configurar se pide autenticacion
                    http.anyRequest().authenticated();

                    // rechaza los endoints sin especificar aunque esten autenticados
                    //http.anyRequest().denyAll();
                })
                .build();
    }
//   2 pasamos a configurar el authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
//  3 configuraremos el authentication provider (pueden ser varios)
    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService){
        // dependiendo de la ubicacion de las credenciales de los usuarios puede cambiar el provider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);

        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //   3.1 configuramos la forma de cifrado de datos.
    // esto usarlo solo en pruebas y para aprender
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }


    // 3.2 configuramos el user details, este se encarga de ir a la base de datos a traer las credenciales
    // esto es para hacer pruebas
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails = User.withUsername("edwin")
//                .password("secret")
//                .roles("ADMIN")
//                .authorities("READ","CREATE")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

}
