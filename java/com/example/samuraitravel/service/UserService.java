package com.example.samuraitravel.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Role;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.form.UserEditForm;
import com.example.samuraitravel.repository.RoleRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional // トランザクション化(処理がすべて成功したらコミット)
	public User createUser(SignupForm signupForm) { // フォームクラスのフィールド値をセッターを使ってインスタンス化したエンティティのフィールドにセット
		User user = new User();
		Role role = roleRepository.findByName("ROLE_GENERAL");

		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setPostalCode(signupForm.getPostalCode());
		user.setAddress(signupForm.getAddress());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword())); // パスワードのハッシュ化
		user.setRole(role); // 先に取得したroleをセット
		user.setEnabled(false); // メール認証済みかの判定（無効状態で保存）

		return userRepository.save(user);
	}

	@Transactional
	public void updateUser(UserEditForm userEditForm, User user) {
		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setPostalCode(userEditForm.getPostalCode());
		user.setAddress(userEditForm.getAddress());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setEmail(userEditForm.getEmail());

		userRepository.save(user);
	}

	// メールアドレスが登録済みかどうかをチェックする
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}

	// パスワードとパスワード(確認用)の入力値が一致するかどうかをチェックする
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

	// ユーザーを有効にする
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	// メールアドレスが変更されたかどうかをチェックする
	public boolean isEmailChanged(UserEditForm userEditForm, User user) {
		return !userEditForm.getEmail().equals(user.getEmail());
	}

	// 指定されたメールアドレスを持つユーザーを取得する
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// すべてのユーザーをページングされた状態で取得する
	public Page<User> findAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	// 指定されたキーワードを氏名またはフリガナに含むユーザーを、ページングされた状態で取得する
	public Page<User> findUserByNameLikeOrFuriganaLike(String nameKeyword, String furiganaKeyword, Pageable pageable) {
		return userRepository.findByNameLikeOrFuriganaLike("%" + nameKeyword + "%", "%" + furiganaKeyword + "%",
				pageable);
	}

	// 指定したidを持つユーザーを取得する
	public Optional<User> findUserById(Integer id) {
		return userRepository.findById(id);
	}
}