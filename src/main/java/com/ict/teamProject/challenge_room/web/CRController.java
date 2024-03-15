package com.ict.teamProject.challenge_room.web;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.challenge_room.service.CPDto;
import com.ict.teamProject.challenge_room.service.CRDto;
import com.ict.teamProject.challenge_room.service.CRService;
import com.ict.teamProject.challenge_room.service.ImplDto;
import com.ict.teamProject.challenge_room.service.SuccessPeopleDto;


//24.02.18 생성
@Controller
@RequestMapping("/croom")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class CRController {
	
	//서비스 주입
	@Autowired
	private CRService<CRDto> service;
	
	//방 찾기]
	@PostMapping("/myRoomNum.do")
	@ResponseBody
	public Integer myRoomNum(@RequestBody Map<String, String> map) {
		String id = map.get("id");
		Integer room = null;
		System.out.println("id-----:"+id);
		room = service.selectMyRoom(id);
		System.out.println("너의 방 번호는?"+room);
		return room;
	}/////
	
	//방 생성]
	@PostMapping("/createRoom.do")
	@ResponseBody
	public int createRoom(@RequestBody Map room) throws Exception {
		System.out.println("room.get(\"selectedCheckbox\"):"+room.get("selectedCheckbox"));
		System.out.println("room.get(\"sliderValues\"):"+room.get("sliderValues"));
		System.out.println("room.get(\"selectedOption1\"):"+room.get("selectedOption1"));
		System.out.println("room.get(\"openRoomYN\"):"+room.get("openRoomYN"));
		System.out.println("room.get(\"dateRange\"):"+room.get("dateRange"));
		System.out.println("room.get(\"content\"):"+room.get("content"));
		CRDto dto = new CRDto();
		System.out.println("room.get(\"goal\"):"+room.get("goal"));
		Map<String, String> goalMap = (Map<String, String>) room.get("goal");
		String goalValue = goalMap.get("value");

		dto.setGoal(goalValue);
		System.out.println("dto.dto.getGoal():"+dto.getGoal());
		
		dto.setChallCapacity(Integer.parseInt(room.get("userset").toString()));
		System.out.println("dto.getChallCapacity():"+dto.getChallCapacity());
		
		dto.setImplementation(Integer.parseInt(room.get("achievementset").toString()));
		System.out.println("dto.getImplementation():"+dto.getImplementation());
		
		dto.setChallArea(room.get("areaSet").toString());
		System.out.println("dto.getChallArea():"+dto.getChallArea());	
		
		dto.setChallTitle(room.get("title").toString());
		System.out.println("dto.getChallTitle():"+dto.getChallTitle());
		
	    ObjectMapper mapper = new ObjectMapper();
	    int[] selectedCheckbox = mapper.readValue(room.get("selectedCheckbox").toString(), int[].class);
	    
	    int gLimit = 0;
	    if (selectedCheckbox.length == 1) {
	        gLimit = selectedCheckbox[0];
	    }
	    dto.setGLimit(gLimit);
		System.out.println("dto.getGLimit():"+dto.getGLimit());
		
	    int[] ageRange = new int[2];
	    if (!room.get("sliderValues").toString().equals("[]")) {
	        ageRange = mapper.readValue(room.get("sliderValues").toString(), int[].class);
	    }
	    dto.setAgeMin(ageRange[0]); // 나이 최소값
		System.out.println("dto.getAgeMin():"+dto.getAgeMin());
	    dto.setAgeMax(ageRange[1]); // 나이 최대값
		System.out.println("dto.getAgeMax():"+dto.getAgeMax());
		
	    String feeWithCurrency = room.get("selectedOption1").toString();
	    String feeOnlyDigits = feeWithCurrency.replaceAll("[^0-9]", ""); // 숫자만 추출
	    dto.setPFee(Integer.parseInt(feeOnlyDigits));
		System.out.println("dto.getPFee():"+dto.getPFee());
		
	    String openRoomYN = Boolean.parseBoolean(room.get("openRoomYN").toString()) ? "Y" : "N";
	    dto.setCYN(openRoomYN);
		System.out.println("dto.getCYN():"+dto.getCYN());
		
	    String[] dateRange = room.get("dateRange").toString().split(" to ");
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date startDateUtil = formatter.parse(dateRange[0]);
	    java.util.Date endDateUtil = formatter.parse(dateRange[1]);
	    
	    Date startDate = new Date(startDateUtil.getTime()); 
	    Date endDate = new Date(endDateUtil.getTime());

	    dto.setCStartDate(startDate); // 챌린지 시작일
	    System.out.println("dto.getCStartDate():"+dto.getCStartDate());
	    dto.setCEndDate(endDate); // 챌린지 종료일
	    System.out.println("dto.getCEndDate():"+dto.getCEndDate());
	    
		dto.setChallContent(room.get("content").toString());
		int affected = 0;
		int seqValue = service.getSeqValue()+1;
		System.out.println("seqValue---"+seqValue);
		service.insert(dto);
		System.out.println("성공했으면 1 아니면 0?"+affected);
		CPDto cpdto = new CPDto();
		System.out.println("room.get(\"id\")"+room.get("id"));
		cpdto.setId(room.get("id").toString());
		cpdto.setChallNo(seqValue);
		affected = service.insertP(cpdto);
		return seqValue;
	}/////
	
	//마지막 사람이 방 나갈때]
	@DeleteMapping("/deleteRoom.do")
	@ResponseBody
	public int deleteRoom(@RequestBody Map<String, String> map) {
		System.out.println("난 마지막사람~~~");
		String id = map.get("id");
		int room = 0;
		int affected = 0;
		System.out.println("id:"+id);
		room = service.selectMyRoom(id);
		service.deletep(id);
		System.out.println("room:"+room);
		affected = service.delete(room);
		System.out.println("너의 삭제된 방 번호는?"+room);
		return affected;
	}/////
	
	
	//챌린지 방 가져오기]
	@GetMapping("/listChall.do")
	@ResponseBody
	public List listChall() {
		List<CRDto> record = new ArrayList();
		record = service.selectAll();
		for (CRDto item : record) {
		    System.out.println("방장은??----" + item.getManager());
		    System.out.println("방번호는???---"+item.getChallNo());
		    System.out.println("챌린지 끝나는 날짜는????---"+item.getCEndDate());
		    
		 // item.getCEndDate()와 오늘 날짜를 비교하여 로직 실행
		    if (item.getCEndDate() != null) { // item의 CEndDate가 null이 아닌지 확인
		        Date today = new Date(System.currentTimeMillis()); // 오늘 날짜를 가져옴

		        // item의 CEndDate가 오늘 날짜보다 이후인지 확인
		        if (item.getCEndDate().after(today)) {
		            // CEndDate가 오늘 날짜를 지나지 않았으므로 로직 실행
		        	System.out.println("체크1 - 오늘 날짜 지나지 않았다??");
		            List result = service.participantsdata(item.getChallNo());
		            System.out.println("너는 뭐지..?"+result);
		            item.setParticipantsData(result);
		        } else {		        	
		            // CEndDate가 오늘 날짜를 지났으므로 해당 방을 없앰
		        	service.deletePeople(item.getChallNo()); //참여자 삭제
		        	service.delete(item.getChallNo()); //방 삭제
		        }
		    }
		}
		return record;
	}/////
	
	//내 정보 가져오기]
	@GetMapping("/myData.do")
	@ResponseBody
	public Map myData(@RequestParam String id) {
		System.out.println("받은 아이디 값:"+id);
		Map map = new HashMap();
		map = service.findmyData(id);
		System.out.println("map.get(\"name\")" + (map.get("name") != null ? map.get("name").toString() : "null"));
		System.out.println("map.get(\"GENDER\")" + (map.get("GENDER") != null ? map.get("GENDER").toString() : "null"));
		System.out.println("map.get(\"B_DAY\")" + (map.get("B_DAY") != null ? map.get("B_DAY").toString() : "null"));
		System.out.println("map.get(\"PRO_FILEPATH\")" + (map.get("PRO_FILEPATH") != null ? map.get("PRO_FILEPATH").toString() : "null"));
		return map;
	}/////
	
	
	//참여자 정보 가져오기]
	@GetMapping("/participantsData.do")
	@ResponseBody
	public List participantsData(@RequestParam int challNo) {
		System.out.println("받은 방 번호는???----"+challNo);
		List<String> people = service.getId(challNo);
		List<ImplDto> cal = service.implcal(challNo);
		Date start = service.startchall(challNo);
		String goal = service.findGoalOfNum(challNo);
		System.out.println("챌린지 시작 날짜는???----"+start);
		for (String p : people) {
		    int eating = 0;
		    int exercise = 0;
		    for (ImplDto record : cal) {
		        if (record.getRecordDate() != null && start.before(record.getRecordDate())) {
			        if (record != null && record.getId() != null && p.equals(record.getId())) {
			            String eatingStr = record.getEatting();
			            if (eatingStr != null) {
			                eating += eatingStr.length();
			            }
			            String exerciseStr = record.getExercise();
			            if (exerciseStr != null) {
			                exercise += exerciseStr.length();
			            }
					    System.out.println("EATING : " + eating/3);
					    System.out.println("EXERCISE : " + exercise/3);
				        System.out.println("record.getRecordDate()"+record.getRecordDate());
				        System.out.println("goal----"+goal);
			        }
		        }
		    }
		    // 추출한 값들을 활용하여 원하는 작업 수행
		    System.out.println("ID: " + p);
		    if(goal.contains("감량") || goal.contains("식단")) {
		    	Map map = new HashMap();
		    	map.put("rate", eating/3);
		    	map.put("id", p);
		    	service.implinsert(map);
		    }else if(goal.contains("증가") || goal.contains("강화") || goal.contains("관리")) {
		    	Map map = new HashMap();
		    	map.put("rate", exercise/3);
		    	map.put("id", p);
		    	service.implinsert(map);
		    }
		}
		List records = service.participantsdata(challNo);
		return records;
	}/////
	
	//방 참가]
	@PostMapping("/joinRoom.do")
	@ResponseBody
	public int joinRoom(@RequestBody Map map) {
	    int record = 0;
	    System.out.println("map.get(\"challNo\").toString()"+map.get("challNo").toString());
	    System.out.println("map.get(\"id\").toString()"+map.get("id").toString());
	    CPDto dto = new CPDto();
	    dto.setChallNo(Integer.parseInt(map.get("challNo").toString()));
	    dto.setId(map.get("id").toString());
	    record = service.join(dto);
	    return record;
	}/////
	
	//방의 데이타 가져오기]
	@PostMapping("/roomData.do")
	@ResponseBody
	public CRDto roomData(@RequestBody Map map) {
	    System.out.println("challNo----"+Integer.parseInt(map.get("challNo").toString()));
	    int challNo = Integer.parseInt(map.get("challNo").toString());
	    CRDto dto = new CRDto();
	    dto = service.findRoomData(challNo);
	    
		 // item.getCEndDate()와 오늘 날짜를 비교하여 로직 실행
	    if (dto.getCEndDate() != null) { // item의 CEndDate가 null이 아닌지 확인
	        Date today = new Date(System.currentTimeMillis()); // 오늘 날짜를 가져옴

	        // item의 CEndDate가 오늘 날짜보다 이후인지 확인
	        if (dto.getCEndDate().after(today)) {
	            // CEndDate가 오늘 날짜를 지나지 않았으므로 로직 실행
	        	return dto;
	        } else {
	        	System.out.println("체크2 - 오늘 날짜!!");
	        	List<SuccessPeopleDto> successYN = service.successPeople(dto.getChallNo());
	        	int successCount = service.successCount(dto.getChallNo());	        	
	        	System.out.println("성공한 유저 수 - "+successCount);
	        	System.out.println(successYN);
	        	for(SuccessPeopleDto list : successYN) {
	        		if("성공".equals(list.getResult())) {
	        			int point = list.getTotal_fee() / successCount;
	        			int affected = service.givePoint(list.getId(), point);
	        			System.out.println("지급된 행의 수 :" + affected);
	        		}
	        		System.out.println(list.getId());
	        		System.out.println(list.getTotal_fee());
	        		System.out.println(list.getResult());
	        	}
	            // CEndDate가 오늘 날짜를 지났으므로 해당 방을 없앰
	        	service.deletePeople(dto.getChallNo()); //참여자 삭제
	        	service.delete(dto.getChallNo()); //방 삭제
	        }
	    }
	    return dto;
	}/////
	
	//방장이 방 나갔을때]
	@DeleteMapping("/deleteManager.do")
	@ResponseBody
	public int deleteManager(@RequestBody Map<String, String> map) {
		int room = 0;
		int affected = 0;
		System.out.println("난 방장~~~");
		String id = map.get("id");
		System.out.println("id:"+id);
		room = service.selectMyRoom(id);
		service.deletep(id);
		String manager = service.selectManager(room);
		System.out.println("manager:"+manager);
		affected = service.update(manager);
		System.out.println("너의 수정된 방 번호는?"+room);
		return affected;
	}/////
	
	//일반 사람이 방 나갈때]
	@DeleteMapping("/deletePeople.do")
	@ResponseBody
	public int deletePeople(@RequestBody Map<String, String> map) {
		System.out.println("일반 사람~~~");
		String id = map.get("id");
		int room = 0;
		int affected = 0;
		System.out.println("id:"+id);
		room = service.selectMyRoom(id);
		service.deletep(id);
		System.out.println("나간 방 번호는?"+room);
		return affected;
	}/////
	
	//이행률 반영]
	@PostMapping("/implementationFood.do")
	@ResponseBody
	public void implementationFood(@RequestBody Map<String, Object> data) {
		if(data.get("foodCheckCount") != null) {
			System.out.println("data.get(\"foodCheckCount\").toString()"+data.get("foodCheckCount").toString());
			String foodCheckCount = data.get("foodCheckCount").toString();
			String id = data.get("id").toString();
			ImplDto dto = new ImplDto();
			Date now = service.findImpl(id);
			System.out.println("입력 날짜?---"+now);
			if(now == null) {
				dto.setId(id);
				dto.setEatting(foodCheckCount);
				service.insertEattingImpl(dto);
			} else {
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(now);
				cal2.setTime(new Date(System.currentTimeMillis()));

				if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && 
				   cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && 
				   cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
					dto.setId(id);
					dto.setEatting(foodCheckCount);
					dto.setRecordDate(now);
					service.updateEattingImpl(dto);
				} else {
					dto.setId(id);
					dto.setEatting(foodCheckCount);
					service.insertEattingImpl(dto);
				}
			}
		}
	}
	//이행률 반영]
		@PostMapping("/implementationExercise.do")
		@ResponseBody
		public void implementationExercise(@RequestBody Map<String, Object> data) {
			if(data.get("exerciseCheckCount") != null) {
				System.out.println("data.get(\"exerciseCheckCount\").toString()"+data.get("exerciseCheckCount").toString());
				String exerciseCheckCount = data.get("exerciseCheckCount").toString();
				String id = data.get("id").toString();
				ImplDto dto = new ImplDto();
				Date now = service.findImpl(id);
				System.out.println("입력 날짜?---"+now);
				if(now == null) {
					dto.setId(id);
					dto.setExercise(exerciseCheckCount);
					service.insertExerciseImpl(dto);
				} else {
					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					cal1.setTime(now);
					cal2.setTime(new Date(System.currentTimeMillis()));

					if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && 
					   cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && 
					   cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
						dto.setId(id);
						dto.setExercise(exerciseCheckCount);
						dto.setRecordDate(now);
						service.updateExerciseImpl(dto);
					} else {
						dto.setId(id);
						dto.setExercise(exerciseCheckCount);
						service.insertExerciseImpl(dto);
					}
				}			
			}
		}
	
	//이행률 세팅]
	@PostMapping("/implementationSetting.do")
	@ResponseBody
	public ImplDto implementationSetting(@RequestBody Map<String, Object> map) {
		System.out.println("이행률 페이지 여기 들어와?---"+map.get("id").toString());
		String id = map.get("id").toString();
		ImplDto dto = new ImplDto();
		Date now = service.findImpl(id);

		if(now != null) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(now);
			cal2.setTime(new Date(System.currentTimeMillis()));
			if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && 
			   cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && 
			   cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
				dto = service.findImplAll(id);
				System.out.println("dto.getEatting()"+dto.getEatting());
				System.out.println("dto.getExercise()"+dto.getExercise());
			} 
		}
		return dto;
	}
	
	@GetMapping("/dateupdate")
	@ResponseBody
	public int updatEndDate(@RequestParam int challNo) {
		System.out.println("받은 방 번호 값:"+challNo);
		int affected = service.updatEndDate(challNo);
		System.out.println(affected);
		return affected;
	}/////
}
