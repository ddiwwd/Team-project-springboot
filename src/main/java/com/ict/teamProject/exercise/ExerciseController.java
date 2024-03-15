package com.ict.teamProject.exercise;

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

import com.ict.teamProject.exercise.bbs.RoadPathDto;
import com.ict.teamProject.exercise.bbs.RoadPointDto;
import com.ict.teamProject.exercise.bbs.RoadSchDto;

@RequestMapping("/exercise")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class ExerciseController {
	ExerciseService service;
	public ExerciseController(ExerciseService service) {
		this.service = service;
	}
	
	@PostMapping("/upload")
	public void uploadPath(@RequestBody Map pathInfo) {
		//{id=sy0208, time=3, roadPoint=[{La=127.0282582883201, Ma=37.4925486874886}, {La=127.02914010009401, Ma=37.492034899129756}, {La=127.02994305893081, Ma=37.492305000535126}], roadPointName=[서초동 1419, 서초동 1337-14, 서초동 1337-30], 
		//roadPointAddrName=[서초구, 서초구, 서초구], schStart=2024-03-08 12:00:00, schEnd=2024-03-08 13:00:00, mate=rotkrlhh}
		System.out.println("메이트 인자:"+pathInfo.get("mate"));
		System.out.println("인자로 받은 값:"+pathInfo);
		String mate = String.valueOf(pathInfo.get("mate"));
		List roadPoints = new ArrayList();
		List pathPoints = (ArrayList)pathInfo.get("roadPoint");
		List pathPointsAddr = (ArrayList)pathInfo.get("roadPointAddrName");
		List pathPointsName = (ArrayList)pathInfo.get("roadPointName");
		//첫번째 원소에는 경로의 아이디 값 저장
		RoadPathDto roadPathDto = new RoadPathDto().builder()
									.id(String.valueOf(pathInfo.get("id")))
									.rpath_time(pathInfo.get("time"))
									.mainaddr(String.valueOf(pathPointsAddr.get(0)))
									.build();
		System.out.println("main주소 : " +roadPathDto.getMainaddr());
		roadPoints.add(roadPathDto); 
		
		//두번째 원소에는 스케줄 테이블 등록
		RoadSchDto roadSchDto = new RoadSchDto().builder()
									.id(String.valueOf(pathInfo.get("id")))
									.sch_start(String.valueOf(pathInfo.get("schStart")))
									.sch_end(String.valueOf(pathInfo.get("schEnd")))
									.mate(mate)
									.start_pos(String.valueOf(pathPointsName.get(0)))
									.end_pos(String.valueOf(pathPointsName.get(pathPointsName.size()-1)))
									.build();
		roadPoints.add(roadSchDto);
		
		System.out.println("스케줄 테이블 dto:"+ roadSchDto);
		
		//세번째 원소에는 경로의 포인트 값 저장
		
		for(int i=0; i<pathPoints.size(); i++) {
			RoadPointDto dto = new RoadPointDto().builder()
									.lat(((Map)pathPoints.get(i)).get("La"))
									.lng(((Map)pathPoints.get(i)).get("Ma"))
									.order_num(i)
									.pointname(String.valueOf(pathPointsName.get(i)))
									.build();
			roadPoints.add(dto);
		}
		service.uploadPathPoint(roadPoints);
		
		if(mate!=null) {//메이트가 null이 아닐 경우 메이트도 같이 등록
			roadPoints = new ArrayList();
			//첫번째 원소에는 경로의 아이디 값 저장
			roadPathDto = new RoadPathDto().builder()
										.id(mate)
										.rpath_time(pathInfo.get("time"))
										.mainaddr(String.valueOf(pathPointsAddr.get(0)))
										.build();
			roadPoints.add(roadPathDto); 
			
			//두번째 원소에는 스케줄 테이블 등록
			roadSchDto = new RoadSchDto().builder()
										.id(mate)
										.sch_start(String.valueOf(pathInfo.get("schStart")))
										.sch_end(String.valueOf(pathInfo.get("schEnd")))
										.mate(String.valueOf(pathInfo.get("id")))
										.build();
			roadPoints.add(roadSchDto);
			
			//세번째 원소에는 경로의 포인트 값 저장
			pathPoints = (ArrayList)pathInfo.get("roadPoint");
			pathPointsName = (ArrayList)pathInfo.get("roadPointName");
			for(int i=0; i<pathPoints.size(); i++) {
				RoadPointDto dto = new RoadPointDto().builder()
										.lat(((Map)pathPoints.get(i)).get("La"))
										.lng(((Map)pathPoints.get(i)).get("Ma"))
										.order_num(i)
										.pointname(String.valueOf(pathPointsName.get(i)))
										.build();
				roadPoints.add(dto);
			}
			service.uploadPathPoint(roadPoints);
		}
	}
	
	@GetMapping("/allpaths")
	public Map getAllPaths(String id) {
		List<RoadPathDto> allCates = service.getAllPathsCate(id);
		Map<String, Map<Integer,List>> allPathsInfo = new HashMap();
		for(RoadPathDto cateInfo:allCates) {
			String mainAdr = cateInfo.getMainaddr();
			if(!allPathsInfo.containsKey(mainAdr)) allPathsInfo.put(mainAdr, new HashMap<Integer, List>());
			List<RoadPointDto> points = service.getAllPointsByRpathNo(cateInfo.getRpath_no());
			allPathsInfo.get(mainAdr).put(cateInfo.getRpath_no(), new ArrayList());
			List<String> pointNames = new ArrayList(); //path를 이루는 각 좌표의 이름 목록
			List latlngs = new ArrayList(); //path를 이루는 좌표 목록
			for(RoadPointDto point : points) {
				pointNames.add(point.getPointname());
				Object[] latlng = {point.getLat(), point.getLng()};
				latlngs.add(latlng);
			}
			allPathsInfo.get(mainAdr).get(cateInfo.getRpath_no()).add(pointNames);
			allPathsInfo.get(mainAdr).get(cateInfo.getRpath_no()).add(latlngs);
			allPathsInfo.get(mainAdr).get(cateInfo.getRpath_no()).add(cateInfo.getRpath_time());
		}
		
		return allPathsInfo;
	}
	
	@PostMapping("/upload/schedule")
	public void uploadPathSchedule(@RequestBody Map map) {
		String end_pos = service.findFianlPointById(String.valueOf(map.get("rpath_no"))).getPointname();
		RoadSchDto dto = new RoadSchDto().builder()
						.id(String.valueOf(map.get("id")))
						.sch_start(String.valueOf(map.get("sch_start")))
						.sch_end(String.valueOf(map.get("sch_end")))
						.rpath_no(String.valueOf(map.get("rpath_no")))
						.end_pos(end_pos)
						.mate(String.valueOf(map.get("mate")))
						.build();
		service.uploadSchedulePath(dto);
		if(map.get("mate") != null) {
			dto = new RoadSchDto().builder()
					.id(String.valueOf(map.get("mate")))
					.sch_start(String.valueOf(map.get("sch_start")))
					.sch_end(String.valueOf(map.get("sch_end")))
					.rpath_no(String.valueOf(map.get("rpath_no")))
					.end_pos(end_pos)
					.mate(String.valueOf(map.get("id")))
					.build();
			service.uploadSchedulePath(dto);
		}
	}
	
	@GetMapping("/schedulepath")
	public List getSchedulePath(@RequestParam int path_no) {
		List result = new ArrayList();
		List<String> pointNames = new ArrayList(); //path를 이루는 각 좌표의 이름 목록
		List latlngs = new ArrayList();
		List<RoadPointDto> points = service.getAllPointsByRpathNo(path_no);
		for(RoadPointDto point : points) {
			pointNames.add(point.getPointname());
			Object[] latlng = {point.getLat(), point.getLng()};
			latlngs.add(latlng);
		}
		result.add(pointNames);
		result.add(latlngs);
		return result;
	}
}
