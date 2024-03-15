package com.ict.teamProject.eating_record;

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

import com.ict.teamProject.eating_record.dto.EatingRecordDto;
import com.ict.teamProject.eating_record.dto.ER_IngredientsDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class EatingRecordController {
	EatingRecordService service;
	public EatingRecordController(EatingRecordService service) {
		this.service = service;
	}
	
    @PostMapping("/Dietfood/SaveBulk.do")
    public void saveBulkDiet(@RequestBody List<Map<String, Object>> dataToSave) {
        System.out.println("Received request data: " + dataToSave);

        for (Map<String, Object> mealData : dataToSave) {
            String action = (String) mealData.get("action");
            if ("insert".equals(action)) {
                String id = (String) mealData.get("id");
                String mealtype = (String) mealData.get("mealtype");
                String eating_foodname = (String) mealData.get("eating_foodname");
                int eating_recipeCode = (int) mealData.get("eating_recipeCode");

                // 데이터베이스에 저장
                int affected = service.savediet(id, mealtype, eating_foodname, eating_recipeCode);
                System.out.println("Insert 처리 - "+affected);
            } else if ("update".equals(action)) {
                String id = (String) mealData.get("id");
                String mealtype = (String) mealData.get("mealtype");
                String eating_foodname = (String) mealData.get("eating_foodname");
                int eating_recipeCode = (int) mealData.get("eating_recipeCode");

                // 데이터베이스에 저장
                int affected = service.updatediet(id, mealtype, eating_foodname, eating_recipeCode);
                System.out.println("Update 처리 - "+affected);
            }
        }
    }
    @GetMapping("/Dietfood/DailyView.do")
    public List<EatingRecordDto> getDailydiet(@RequestParam String id){
        System.out.println("요청보낸 아이디다!!!!!!!!!!!!!!!!!!!!!!"+id);
        List<EatingRecordDto> ERList = service.getdailydiet(id);
        System.out.println("가져온 데이터를 보자 : ");
        for (EatingRecordDto record : ERList) {
            System.out.println(record.toString());
            List<ER_IngredientsDto> recipeNingredients = service.getrecipeNingredients(record.getEating_recipeCode());
            for(ER_IngredientsDto eri : recipeNingredients) {
            	System.out.println(eri.getIngredient());
            }
            record.setIngredients(recipeNingredients); // 조회한 재료를 EatingRecordDto에 설정
        }
        return ERList;
    }
    
    @GetMapping("/dietfood/search.do")
    public int checkDailydata(@RequestParam String id) {
    	System.out.println("들어온 아이디"+id);
    	int chkdata = service.checkdailydata(id);
    	int exercisechkdata = service.checkdailyexercisedata(id);
    	if(exercisechkdata == 0) {
    		service.defaultexercise(id);
    	}    	
    	int chkAlldata = service.checkAlldata(id);
    	if(chkdata == 0) { //오늘 데이터 미존재
        	if(chkAlldata == 0) {//어제, 오늘 말고 데이터가 없음..
        		//랜덤으로 값을 넣어줘야함
        		service.defaultbdata(id);
        		service.defaultldata(id);
        		service.defaultddata(id);
        		return 3;
        	}else if(chkAlldata > 0){
        		return 0;
        	}
    	}
    	return chkdata;
    }
}