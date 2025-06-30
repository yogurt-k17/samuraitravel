package com.example.samuraitravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles") // テーブルの指定	
@Data
public class Role {
	@Id // 主キーに指定
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーを自動生成
	@Column(name = "id")
	private Integer id;

	@Column(name = "name") // マッピングされるカラムを指定
	private String name;
}