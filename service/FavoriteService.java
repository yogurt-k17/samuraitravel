package com.example.samuraitravel.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;

	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}

	public Page<Favorite> findFavoritesByUserOrderByCreatedAtDesc(User user, Pageable pageable) {
		return favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
	}

	public Optional<Favorite> findFavoriteById(Integer id) {
		return favoriteRepository.findById(id);
	}

	public Favorite findFavoriteByHouseAndUser(House house, User user) {
		return favoriteRepository.findByHouseAndUser(house, user);
	}

	@Transactional
	public void createFavorite(House house, User user) {
		Favorite favorite = new Favorite();

		favorite.setHouse(house);
		favorite.setUser(user);

		favoriteRepository.save(favorite);
	}

	@Transactional
	public void deleteFavorite(Favorite favorite) {
		favoriteRepository.delete(favorite);
	}

	public boolean isFavorite(House house, User user) {
		return favoriteRepository.findByHouseAndUser(house, user) != null;
	}

}
