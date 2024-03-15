package com.ict.teamProject.member.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final Map<String, String> emailTokenMap = new HashMap<>();
    private final Map<String, String> tokenIdMap = new HashMap<>();

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendVerificationEmail(String email, String id) {
        String token = UUID.randomUUID().toString();
        emailTokenMap.put(token, email);
        tokenIdMap.put(token, id);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[HealthReal 아이디 찾기]");
            String url = "http://localhost:4000/email-verificationID?token=" + token + "&id=" + id; 

            // 텍스트 링크
            String link = "<a href=\"" + url + "\">[HealthyReal]</a>";

            // 이미지에 링크 
            String cid = "logo";
            String linkedImage = "<a href=\"" + url + "\"><img src='cid:logo'></a>";

            helper.setText("다음 링크를 클릭하여 이메일 인증을 완료해주세요: " + link + "<br>" + linkedImage, true);


            // 이미지 첨부
            ClassPathResource resource = new ClassPathResource("static/images/logo.png");
            helper.addInline(cid, resource);

        };

        this.mailSender.send(preparator);
        return token;
    }


    public String verifyEmail(String token) {
        String email = emailTokenMap.get(token);

        if (email != null) {
            emailTokenMap.remove(token);
            String id = tokenIdMap.get(token); // 토큰에 연결된 아이디를 가져옵니다.
            tokenIdMap.remove(token);
            System.out.println("id"+id);
            return id; // 아이디를 반환합니다.
            
        } else {
            return "인증 실패";
        }
    }
    
    
    public String sendPasswordResetEmail(String email, String id) {
        String token = UUID.randomUUID().toString();
        emailTokenMap.put(token, email);
        tokenIdMap.put(token, id);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[HealthReal 비밀번호 재설정]");
            String url = "http://localhost:4000/email-verificationPWD?token=" + token + "&id=" + id; 
            // 텍스트 링크
            String link = "<a href=\"" + url + "\">[HealthyReal]</a>";
            // 이미지에 링크 
            String cid = "logo";
            String linkedImage = "<a href=\"" + url + "\"><img src='cid:logo'></a>";
            helper.setText("다음 링크를 클릭하여 비밀번호 재설정을 완료해주세요: " + link + "<br>" + linkedImage, true);
            // 이미지 첨부
            ClassPathResource resource = new ClassPathResource("static/images/logo.png");
            helper.addInline(cid, resource);

        };

        this.mailSender.send(preparator);
        return token;
    }

    public String verifyPasswordReset(String token) {
        String email = emailTokenMap.get(token);

        if (email != null) {
            emailTokenMap.remove(token);
            String id = tokenIdMap.get(token); // 토큰에 연결된 아이디를 가져옵니다.
            tokenIdMap.remove(token);
            System.out.println("id"+id);
            return id; // 아이디를 반환합니다.
        } else {
            return "인증 실패";
        }
    }

}
