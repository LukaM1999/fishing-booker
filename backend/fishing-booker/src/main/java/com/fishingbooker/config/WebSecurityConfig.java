package com.fishingbooker.config;

import com.fishingbooker.service.RegisteredUserService;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fishingbooker.security.auth.RestAuthEntryPoint;
import com.fishingbooker.security.auth.TokenAuthFilter;
import com.fishingbooker.util.TokenUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public WebSecurityConfig(RegisteredUserService registeredUserService, RestAuthEntryPoint restAuthEntryPoint, TokenUtils tokenUtils, PasswordEncoder passwordEncoder) {
        this.registeredUserService = registeredUserService;
        this.restAuthEntryPoint = restAuthEntryPoint;
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
    }

    // Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
    private final PasswordEncoder passwordEncoder;

    // Servis koji se koristi za citanje podataka o korisnicima aplikacije
    private final RegisteredUserService registeredUserService;

    // Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
    private final RestAuthEntryPoint restAuthEntryPoint;

    // Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Definisemo nacin utvrdjivanja korisnika pri autentifikaciji
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // Definisemo uputstva AuthenticationManager-u:

                // 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
                // prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
                .userDetailsService(registeredUserService)

                // 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu
                // da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
                .passwordEncoder(passwordEncoder);
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
    private final TokenUtils tokenUtils;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(new ArrayList<>(){{
            add("http://localhost:7000");
            add("https://fishing-booker.herokuapp.com");
        }});
        configuration.setAllowedMethods(new ArrayList<>(){{add("*");}});
        configuration.setAllowedHeaders(new ArrayList<>(){{
            add("*");
        }});
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").cors().configurationSource(corsConfigurationSource()).and()
                // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
                // ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru
                // ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthEntryPoint).and()

                // svim korisnicima dopusti da pristupe sledecim putanjama:
                .authorizeRequests().antMatchers("/auth/**").permitAll()		// /auth/**
                .antMatchers("/h2-console/**").permitAll()	// /h2-console/** ako se koristi H2 baza)
                .antMatchers("/api/foo").permitAll()		// /api/foo
                .antMatchers("/**").permitAll() //TODO: OBRISATI NA KRAJU SVOG TESTIRANJA, FOR MAXIMUM PROTECTION FROM EVIL SPIRITS
                .antMatchers("/cottage/**").permitAll()
                .antMatchers("/boat/all").permitAll()
                .antMatchers("/instructor/all").permitAll()

                // ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
                // koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
                // samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin:
                // .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")

                // za svaki drugi zahtev korisnik mora biti autentifikovan
                .anyRequest().authenticated().and()

                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
                .addFilterBefore(new TokenAuthFilter(tokenUtils, registeredUserService), BasicAuthenticationFilter.class);

        // zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
        http.csrf().disable();
    }

    // Definisanje konfiguracije koja utice na generalnu bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
        // Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u

        // Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");

        // Ovim smo dozvolili pristup statickim resursima aplikacije
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
