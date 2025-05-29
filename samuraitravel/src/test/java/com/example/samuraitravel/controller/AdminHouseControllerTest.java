package com.example.samuraitravel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest // コンテキストを起動（実際の実行環境に近い条件）
@AutoConfigureMockMvc
@ActiveProfiles("test") // テスト用の設定ファイルを指定
public class AdminHouseControllerTest {
	@Autowired
	private MockMvc mockMvc; // フィールドインジェクションによる注入

	@Test
	public void 未ログインの場合は管理者用の民宿一覧ページからログインページにリダイレクトする() throws Exception {
		mockMvc.perform(get("/admin/houses"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@WithUserDetails("taro.samurai@example.com") // そのユーザーとしてログインする
	public void 一般ユーザーとしてログイン済みの場合は管理者用の民宿一覧ページが表示されずに403エラーが発生する() throws Exception {
		mockMvc.perform(get("/admin/houses"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithUserDetails("hanako.samurai@example.com")
	public void 管理者としてログイン済みの場合は管理者用の民宿一覧ページが正しく表示される() throws Exception {
		mockMvc.perform(get("/admin/houses"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/houses/index"));
	}
}