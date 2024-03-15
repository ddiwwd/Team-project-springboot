package com.ict.teamProject.security.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ict.teamProject.security.config.auth.PrincipalDetails;
import com.ict.teamProject.member.service.RegisterService;
import com.ict.teamProject.member.service.MemberDetailService;
import com.ict.teamProject.member.service.MemberDto;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	@Autowired
	private MemberDetailService service;
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		MemberDto userEntity = new MemberDto();
		boolean isUser = false;
		
		String provider = userRequest.getClientRegistration().getRegistrationId(); 
		if(provider.equals("google")) {
			String id= oauth2User.getAttribute("sub");
			String profileimage = oauth2User.getAttribute("picture");
			String name = oauth2User.getAttribute("name");
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);
			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			isUser = service.isPreviousUser(id);
		}
		else if(provider.equals("kakao")) {
			Long longid= (Long)oauth2User.getAttributes().get("id");
			String id = longid.toString();
			String profileimage = (String)((Map)oauth2User.getAttributes().get("properties")).get("profile_image");
			String name = (String)((Map)oauth2User.getAttributes().get("properties")).get("nickname");
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);
			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			isUser = service.isPreviousUser(id);
		}
		else if(provider.equals("naver")) {
			Map<String, Object> attributes = oauth2User.getAttributes();
			Map<String, Object> response = (Map<String, Object>) attributes.get("response");
			String id = (String) response.get("id");
			String profileimage = (String) response.get("profile_image");
			String name = (String) response.get("name");
			if (profileimage == null) {
			    profileimage = "/src/assets/images/userProfile/default.png";  // 기본 이미지 경로
			}
			userEntity.setId(id);
			userEntity.setName(name);
			userEntity.setProfileimage(profileimage);

			userEntity.setAuthority("ROLE_USER");
			userEntity.setProvider(provider);
			isUser = service.isPreviousUser(id);
			System.out.println(profileimage+"profile_image");
			System.out.println(response);
		}
		
		if(!isUser) {
			service.joinSocialMember(userEntity);
		}
		else {
			System.out.println("이미 로그인했던 회원");
			//여기다가 해줘야할거같고,
			service.updateUser(userEntity);
		}
		
		System.out.println("여기가 로그인맞을껄?");
		
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
	}
	
}
