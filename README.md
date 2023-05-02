# Spring security

### Swagger url

http://localhost:8080/swagger-ui/index.html#

### Autenticación con Spring security

Si la dependencia spring-boot-starter-security se encuentra en el classpath, la aplicación será segura por defecto a través de basic authentication y form login, con las siguientes credenciales:
- User: user
- Password: Al iniciar la aplicación se imprimirá en el log algo asÍ: ```Using generated security password: e1cf64a3-cc68-4357-a497-35a04afa86a7```

- Podemos cambiar el usuario y la contraseña por defecto incluyendo las propiedades "spring.security.user.name" y "spring.security.user.password" en el application.yml.

### Autenticación en memoria

````
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(encoder().encode("admin")).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
````

### Autenticación contra base de datos
En el ejemplo anterior se msotró como autenticar contra credenciales definidas en memoria, en caso de que se desee realizar contra una base de datos se deberá implementar la interface UserDetails.

La clase anterior define un nuevo user, password y rol, el bean PasswordEncoder nos permite cifrar en SHA-1.

## Definición de recursos a proteger

Incluimos el método para definir los recursos que se deberán proteger:

````
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
    .authorizeHttpRequests()
    .antMatchers("/users/**").hasRole("ADMIN")
    .and()
    .httpBasic();
}
````

La definición anterior indica que:

- Se deshabilita el soporte para csrf
- authorizeHttpRequests() permite restringir el acceso a recursos
- antMatchers() define los recursos a proteger
- hasRole() indica el rol que debe de tener el usuario para acceder al recurso especificado
- httpBasic() configura Basic Authentication

.antMatchers("/users/**").hasRole("ADMIN") --> sólo pueden acceder al recurso "/users/**" los usuarios que tengan rol "
ADMIN"
.antMatchers("/roles/**").permitAll().anyRequest().authenticated() --> pueden acceder al recurso "/roles/**" todos los
usuarios que se hayan autenticado.