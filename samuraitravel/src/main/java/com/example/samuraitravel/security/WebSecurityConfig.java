package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 設定用のクラスにする
@EnableWebSecurity // Spring Securityによるセキュリティ機能を有効にする
@EnableMethodSecurity // メソッドレベルでのセキュリティ機能を有効にする
public class WebSecurityConfig {
	@Bean // メソッドの戻り値がDIコンテナに登録される
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests // AuthorizeHttpRequestsConfigurerのインスタンス
						.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**")
						.permitAll() // すべてのユーザーにアクセスを許可するURL
						.anyRequest().authenticated() // 上記以外のURLは認証が必要
				)
				.formLogin((form) -> form
						.loginPage("/login") // ログインページのURL
						.loginProcessingUrl("/login") // ログインフォームの送信先URL(フォームのaction属性に設定するURL)
						.defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
						.failureUrl("/login?error") // ログイン失敗時のリダイレクト先URL
						.permitAll())
				.logout((logout) -> logout
						.logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
						.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // パスワードのハッシュアルゴリズムをBCryptに設定
	}
}