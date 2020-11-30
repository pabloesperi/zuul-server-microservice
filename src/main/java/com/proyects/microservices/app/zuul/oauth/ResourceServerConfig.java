package com.proyects.microservices.app.zuul.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

//	Protección de los endpoints
//	El hasAnyRole es para agregar varios roles.-
//	El hasRole es para uno sólo.-
//	Las rutas específicas tienen que ir al principio y luego las que van siendo más
//	genéricas
//	.anyRequest().authenticated() es la más genérica.	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/oauth_security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, "/api/persons/getAllPersons").permitAll()
		.antMatchers(HttpMethod.GET, "/api/products/products/").permitAll()
		.antMatchers(HttpMethod.GET, "/api/users/users/").permitAll()
		.antMatchers(HttpMethod.GET, "/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.GET, "/api/persons/getPersonById/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.POST, "/api/persons/savePerson").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/persons/deletePersonById/{id}").hasRole("ADMIN")
		.anyRequest().authenticated();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
		return jwtAccessTokenConverter;
	}
}
