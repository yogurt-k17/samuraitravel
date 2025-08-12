package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> { // エンティティのクラス型, 主キーのデータ型
	public Role findByName(String name);
}
