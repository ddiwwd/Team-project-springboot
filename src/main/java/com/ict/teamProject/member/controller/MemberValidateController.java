package com.ict.teamProject.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ict.teamProject.member.service.MessageService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberValidateController {
    private final MessageService messageService;
    
    public MemberValidateController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @PostMapping("/send")
    @CrossOrigin(origins = "http://localhost:3333/", allowedHeaders = "*")
    public String userCheck(@RequestBody Map<String, String> params) {
    	String phone = params.get("phone");
        return messageService.sendAuthCode(phone);
       
    }

    
	@PostMapping("/checkPhoneNumber")
	@CrossOrigin(origins = "http://localhost:3333",  allowedHeaders = "*")
	public ResponseEntity<Map<String, Boolean>> checkPhoneNumber(@RequestBody Map<String, String> params) {
	    String phone = params.get("phone");

	    boolean exists = messageService.checkPhoneNumber(phone);

	    Map<String, Boolean> response = new HashMap();
	    response.put("exists", exists);

	    return ResponseEntity.ok().body(response);
	}
    
    @CrossOrigin(origins = "http://localhost:3333/")
    @PostMapping("/verify")
    public ResponseEntity<String> verifyAuthCode(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String inputAuthCode = params.get("authCode");

        String verificationResult = messageService.verifyAuthCode(phone, inputAuthCode);

        if (verificationResult.equals("인증 성공")) {
            return ResponseEntity.ok().body("인증 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(verificationResult);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:3333/")
    @PostMapping("/resendVerificationCode")
    public ResponseEntity<String> resendVerificationCode(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");

        // 인증번호를 만료하고 새로운 인증번호를 발급합니다.
        String newAuthCode = messageService.resendAuthCode(phone);

        if (newAuthCode != null) {
            return ResponseEntity.ok().body("인증번호 재발송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증번호 재발송 실패");
        }
    }  
}

