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

## Acceder a la información del rol
Spring permite acceder a la información tanto del usuario como del rol a través de la clase SecurityContextHolder. 

## Configurar seguridad a nivel de método
No sólo podemos controlar el acceso a los controllers de nuestra aplicación, también podemos restringir el acceso a los métodos de acuerdo a un rol. Podemos usar la anotación:
````
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
````
- securedEnabled = true, habilita la anotación @Secured
- prePostEnabled = true, habilita las anotaciones @PreAuthorize y @PostAuthorize
- jsr250Enabled = true, habilita la anotación @RolesAllowed

## @Secured
Permite definir la lista de roles permitidos al ejecutar un método

## @RolesAllowed
Es equivalente a usar @Secured, la diferencia es que la primera no es propia de String, se recomienda utilizarla en caso de que se considere la migración de Spring a otra tecnología.

## @PreAuthorize y @PostAuthorize
Permiten urilizar expression-based access control a través de SpEL.

La anotación @PreAuthorize permite realizar una evaluación antes de ejecutar el método, dependiendo del resultado de la evaluación decide si ejecutarlo o no.

La anotación @PostAuthorize permite realizar una evaluación después de ejecutar el método, dependiendo del resultado de la evaluación decide devolver su valor o no. @PostAuthorize esutilizado en caso de que los valores a utilizar se tomen en tiempo de ejecución y se desee validar si el objeto puede ser accedido por el usuario.

## Uso de meta anotaciones

Podemos crear una meta anotación en el caso de usar el mismo grupo de anotaciones repetidas veces, por ejemplo si creamos la metaanotación
````
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@PostAuthorize("hasRole('ROLE_ADMIN')")
public @interface SecurityRule {

}
````
Podremos marcar un método/clase como ````@SecurityRule```` en lugar de poner ````@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")```` y ````@PostAuthorize("hasRole('ROLE_ADMIN')")````

## Test de integración
Podemos usar la dependencia:
````
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
````

- @SpringBootTest colocada a nivel de clase para iniciar el contexto de Spring
- @WithMockUser() para simular el usuario autenticado, podemos usarla a nivel de clase o método, dependiendo de si queremos usar diferentes usuarios o uno sólo.
- Spring security test permite definir un UserDetails personalizado, probar con meta anotaciones, entre muchas otras funcionalidades.