package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 設定用のクラスにする
@EnableWebSecurity // Spring Securityによるセキュリティ機能を有効にする
@EnableMethodSecurity // メソッドレベルでのセキュリティ機能を有効にする
public class WebSecurityConfig {
	@Bean // メソッドの戻り値がDIコンテナに登録される
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests // AuthorizeHttpRequestsConfigurerのインスタンス
						.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses",
								"/houses/{id}", "/stripe/webhook", "/houses/{houseId}/revieiws")
						.permitAll() // すべてのユーザーにアクセスを許可するURL
						.requestMatchers("/admin/**").hasRole("ADMIN") // 管理者にのみアクセスを許可するURL
						.anyRequest().authenticated() // 上記以外のURLは認証が必要（認証済みでなければアクセスできない）
				)
				.formLogin((form) -> form
						.loginPage("/login") // ログインページのURL
						.loginProcessingUrl("/login") // ログインフォームの送信先URL(フォームのaction属性に設定するURL)
						.defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
						.failureUrl("/login?error") // ログイン失敗時のリダイレクト先URL
						.permitAll())
				.logout((logout) -> logout
						.logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
						.permitAll())
				.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/stripe/webhook")));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // パスワードのハッシュアルゴリズムをBCryptに設定
	}
}