package com.ssafy.gotcha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.gotcha.api.jwt.JwtAccessDeniedHandler;
import com.ssafy.gotcha.api.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.gotcha.api.jwt.TokenProvider;


/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	 	private final TokenProvider tokenProvider;
	    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	
	    public SecurityConfig(
	            TokenProvider tokenProvider,
	            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
	            JwtAccessDeniedHandler jwtAccessDeniedHandler
	    ) {
	        this.tokenProvider = tokenProvider;
	        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	    }

	    
//    @Autowired
//    private SsafyUserDetailService ssafyUserDetailService;
//    
//    @Autowired
//    private UserService userService;

	// Password 인코딩 방식에 BCrypt 암호화 방식 사용
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//    // DAO 기반으로 Authentication Provider를 생성
//    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
//    @Bean
//    DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(this.ssafyUserDetailService);
//        return daoAuthenticationProvider;
//    }
//
//    // DAO 기반의 Authentication Provider가 적용되도록 설정
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		
		  		.exceptionHandling() //예외처리 기능
		  		.authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증 실패시 (Spring Security에서 인증되지 않은 사용자)
		  		.accessDeniedHandler(jwtAccessDeniedHandler) //인가 실패시 (Spring Security에서 인증되었으나 권한이 없는 사용자)
		  		
		  		.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
				
				
				
				.and()
			    .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
			    .antMatchers(HttpMethod.OPTIONS, "/api/*").permitAll() //OPTIONS 메소드 허락 - preflight 요청에 대한 접근 허용
			    .antMatchers("/api/users/signup","/api/users/signin").permitAll() // 가입 및 인증 주소는 누구나 접근가능
			    // .antMatchers(HttpMethod.GET, "//refresh-access-token").permitAll() // 로그인이 유지중이라면 토큰이 만료되는 시점에 리프레쉬 해주는 요청. 추후 기능 추가 시 주석 해제
//			    .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능 배포시 주석 해제!
				

                
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
                
//				.and()
////                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
//				.authorizeRequests().anyRequest().permitAll().and().cors();
	}
}