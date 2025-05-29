package com.example.samuraitravel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.service.HouseService;

@Controller
@RequestMapping("/admin/houses") // ルートパスの基準値
public class AdminHouseController {
	private final HouseService houseService;

	public AdminHouseController(HouseService houseService) {
		this.houseService = houseService;
	}

	@GetMapping
	public String index(Model model) {
		List<House> houses = houseService.findAllHouses();

		model.addAttribute("houses", houses);

		return "admin/houses/index";
	}
}