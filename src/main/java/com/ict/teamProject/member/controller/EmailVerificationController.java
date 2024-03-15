package com.ict.teamProject.member.controller;

import com.ict.teamProject.member.service.EmailService;
import com.ict.teamProject.member.service.MemberDto;
import com.ict.teamProject.member.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailVerificationController {
    private final EmailService emailService;
    
    @Autowired
    private MemberService service;
    
    
    public EmailVerificationController(EmailService emailService) {
        this.emailService = emailService;
        
    }
    
    

    @PostMapping("/email-verification-request")
    @CrossOrigin(origins = "http://localhost:3333/", allowedHeaders = "*")
    public ResponseEntity<?> requestEmailVerification(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        String name = params.get("name");
        String b_day = params.get("b_day");
        String email = params.get("email");
        MemberDto dto = null;
        System.out.println("실행? id"+id);
        String token = null;
        
        if (id != null) { // 아이디를 기준으로 회원 찾기
            System.out.println("비밀번호찾기");
            dto = service.getMemberByIdAndEmail(id);
            if (dto != null) {
                id = dto.getId(); // MemberDto에서 아이디를 가져옵니다.
                System.out.println("id" + id);
                token = emailService.sendPasswordResetEmail(email, id); // 비밀번호 찾기 요청이므로 sendPasswordResetEmail 메서드를 호출
            }
        } else if (name != null && b_day != null) { // 이름과 생년월일을 기준으로 회원 찾기
            System.out.println("아이디 찾기");
            dto = service.getMemberByNameAndBday(name, b_day);
            if (dto != null) {
                id = dto.getId(); // MemberDto에서 아이디를 가져옵니다.
                System.out.println("id" + id);
                token = emailService.sendVerificationEmail(email, id); // 아이디 찾기 요청이므로 sendVerificationEmail 메서드를 호출
            }
        }

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보가 존재하지 않습니다.");
        } else if (token != null) {
            return ResponseEntity.ok().body("이메일 전송 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송 실패");
        }
    }

    @GetMapping("/email-verificationID")
    @CrossOrigin(origins = "http://localhost:3333/", allowedHeaders = "*")
    public void verifyEmailID(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        String id = emailService.verifyEmail(token);

        if (id != null) { // 인증 성공 시
            response.sendRedirect("http://localhost:3333/forgot-password?id=" + id); // 아이디를 쿼리 파라미터로 추가
        } else { // 인증 실패 시
            System.out.println("왜 인증실패? " + id);
            response.sendRedirect("http://localhost:3333/forgot-id");
        }
    }
    
    @GetMapping("/email-verificationPWD")
    @CrossOrigin(origins = "http://localhost:3333/", allowedHeaders = "*")
    public void verifyEmailPWD(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        String id = emailService.verifyEmail(token);

        if (id != null) { // 인증 성공 시 //AuthCallback
            response.sendRedirect("http://localhost:3333/AuthCallback?id=" + id+ "&resetPassword=true"); // 아이디를 쿼리 파라미터로 추가
        } else { // 인증 실패 시
            System.out.println("왜 인증실패? " + id);
            response.sendRedirect("http://localhost:3333/forgot-password-email");
        }
    }
}
