package com.ict.teamProject.diary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.teamProject.command.FileUtils;
import com.ict.teamProject.diary.dto.DiaryDto;
import com.ict.teamProject.diary.dto.DiaryImagesDto;

@RestController
@RequestMapping("/manage")
@CrossOrigin(origins = "http://localhost:3333")
public class ManageController {
	private ManageService service;
	public ManageController(ManageService service) {
		this.service = service;
	}
	//다이어리 페이지에 값을 뿌려주기 위한 메소드
	@GetMapping("/diary")
	public DiaryDto getDiaryContent(@RequestParam String userNDate) {
		return service.findDiaryById(userNDate);
	}
	//일기 내용을 DB에 저장하기 위한 메소드
	@PostMapping("/diary/upload")
	public int uploadDiary(@RequestParam Map map, @RequestParam(name="files",required = false) MultipartFile[] files) {
		System.out.println("이미지 파일 확인:" + files);
		System.out.println("diary id 확인:" + String.valueOf(map.get("diaryId")));
		//다이어리 텍스트 저장
		DiaryDto diary = new DiaryDto().builder()
						.id(String.valueOf(map.get("id")))
						.diaryId(String.valueOf(map.get("diaryId")))
						.diary_content(String.valueOf(map.get("diary_content")))
						.emotion(String.valueOf(map.get("emotion")))
						.build();
		
		//다이어리 사진 저장
		List<DiaryImagesDto> imgs = new ArrayList<DiaryImagesDto>();
		
		String uploadDirectory = "E:/images/";  // 파일을 저장할 디렉토리
		String uploadimages = "src/main/resources/static/images/";
		
		if (files != null) {
			try {
		        Path uploadPath = Paths.get(uploadDirectory);
		        Path uploadimagePath = Paths.get(uploadimages);
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);// 디렉토리가 없으면 생성
		        }
		        if (!Files.exists(uploadimagePath)) {
		            Files.createDirectories(uploadimagePath);// 디렉토리가 없으면 생성
		        }
		        for (MultipartFile file : files) {
		            String filename = file.getOriginalFilename();
		            String newFilename = FileUtils.getNewFileName(uploadDirectory, filename);
		            Path filePath = uploadPath.resolve(newFilename);  // 파일이 저장될 경로
		            Path fileimgaePath = uploadimagePath.resolve(newFilename);  // 파일이 저장될 경로
		            String filePathStr = filePath.toString().replace("\\", "/");  // 역슬래시를 슬래시로 바꾸기
		            
		            String baseUrl = "http://localhost:4000";  // 기본 URL
		            String imagePath = filePathStr.substring(filePathStr.indexOf("/images"));
		            imagePath = filePathStr.replace("E:/images", "/images");
		            DiaryImagesDto dto = new DiaryImagesDto().builder()
		            					.diaryId(String.valueOf(map.get("diaryId")))
		            					.imgUrl(baseUrl+imagePath)
		            					.name(newFilename)
		            					.build();
		            file.transferTo(filePath);  // 파일 저장
		            file.transferTo(fileimgaePath);  // 파일 저장
		            imgs.add(dto);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		return service.uploadDiaryContentsById(diary, imgs);
	}
	
	@GetMapping("/diartext/alltext")
	public DiaryDto getUsertext(@RequestParam String id) {
		System.out.println("다이어리 : "+id);
		return service.findAllusertext(id);
	}
}
