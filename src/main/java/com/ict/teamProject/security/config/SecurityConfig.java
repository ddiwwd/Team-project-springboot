
package com.ict.teamProject.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ict.teamProject.member.service.MemberDetailService;
import com.ict.teamProject.security.config.auth.PrincipalDetailsService;
import com.ict.teamProject.security.config.jwt.JwtAuthenticationEntryPoint;
import com.ict.teamProject.security.config.jwt.JwtAuthenticationFilter;
import com.ict.teamProject.security.config.jwt.JwtAuthorizationFilter;
import com.ict.teamProject.security.config.jwt.OAuth2AuthenticationSuccessHandler;
import com.ict.teamProject.security.config.oauth.PrincipalOauth2UserService;
import com.ict.teamProject.security.handler.AuthenticationFailureHandler;
import com.ict.teamProject.security.handler.UserAccessDeniedHandler;
import com.ict.teamProject.security.handler.UserLogoutSeccessHandler;



@Configuration
@EnableWebSecurity 
@EnableMethodSecurity(prePostEnabled = true) 

public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	@Autowired
	private MemberDetailService service;
	
	@Autowired
	 private PrincipalDetailsService principalDetailsService;
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler();
    }
    
	private AuthenticationConfiguration authenticationConfiguration;
	
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
		this.authenticationConfiguration=authenticationConfiguration;
	}
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
    @Autowired
    CorsFilter corsFilter;
    
    @Autowired
    UserLogoutSeccessHandler userLogoutSeccessHandler;
    
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    
    @Autowired
    UserAccessDeniedHandler accessDeniedHandler;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    
 

    
	@Bean
	public SecurityFilterChain userfilterChain(HttpSecurity http) throws Exception{
		http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
		
		http.csrf( (csrf) -> csrf.disable());
		http.exceptionHandling((exceptionHandler) -> 
		exceptionHandler
		.accessDeniedHandler(accessDeniedHandler)
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				);
		http.authorizeHttpRequests( (requests)->requests
				
				.requestMatchers("/forgot-id","/forgot-password","/login-password",
				"/forgot-password-phone","/forgot-password-email").permitAll()
				.requestMatchers("/admin/**", "/access-control").hasAuthority("ROLE_ADMIN")
				
				.requestMatchers("/main","/exer/getData.do").permitAll()
				.anyRequest().permitAll() 
				)
				
			.httpBasic( httpBasic->httpBasic.disable() )
			.addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(),service))
			.sessionManagement((sessionManagement) ->
				sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin((formLogin) ->
				formLogin
					
					.usernameParameter("id")
					.passwordParameter("pwd")
					.loginProcessingUrl("/login")
			)

			.oauth2Login( (oauth2)-> 
				oauth2
					.userInfoEndpoint( userInfoEndpoint -> userInfoEndpoint 
					.userService(principalOauth2UserService) )
					.successHandler(oAuth2AuthenticationSuccessHandler())
			)
 			.logout((logout) ->
				logout
					.deleteCookies("User-Token")
					.invalidateHttpSession(true)
					.logoutSuccessHandler(userLogoutSeccessHandler)
					
			)
 			
 			;
		System.out.println();
		return http.build();
	}
	
	
	
}
