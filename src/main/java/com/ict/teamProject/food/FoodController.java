package com.ict.teamProject.food;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ict.teamProject.food.dto.FoodListDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class FoodController {
	FoodService service;
	public FoodController(FoodService service) {
		this.service = service;
	}
	
	@GetMapping("/recipe/View.do") //조회
	public List<FoodListDto> findAllList(String id, String category){
		System.out.println("받은 이름:"+id);
		System.out.println("받은 카테고리:"+category);
		if(category == null || category.equals("전체")) {
			System.out.println("들어왔나..?");
			List<FoodListDto> foodlist = service.findAllrecipe(id);
			return foodlist;
		}else {
			List<FoodListDto> foodlist = service.findrecipe(id, category);
			return foodlist;
		}
	}
	
	@GetMapping("/foodlist/foodinfo.do") //조회
	public List<FoodListDto> getfoodinfo(String foodname){
		System.out.println("받은 음식:" + foodname);
		List<FoodListDto> foodlist = service.getfoodinfo(foodname);
		System.out.println("결과 : "+ foodlist);
		return foodlist;
	}	
}