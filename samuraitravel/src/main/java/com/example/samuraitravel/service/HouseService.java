package com.example.samuraitravel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.repository.HouseRepository;

@Service
public class HouseService {
	private final HouseRepository houseRepository;

	public HouseService(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}

	// すべての民宿を取得する
	public List<House> findAllHouses() {
		return houseRepository.findAll();
	}

}
