package com.ict.teamProject.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:3333/",allowedHeaders = "*")
public class StabilityVideoDefusionController {

    @Value("${stability.ai.api_key}")
    private String apiKey;

    @PostMapping
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", image.getResource());
        body.add("seed", 42);
        body.add("cfg_scale", 1.8);
        body.add("motion_bucket_id", 5.0);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = null;
        try {
            response = restTemplate.exchange(
                "https://api.stability.ai/v2alpha/generation/image-to-video",
                HttpMethod.POST,
                requestEntity,
                Map.class
            );
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error communicating with Stability AI API."));
        }
        System.out.println("Response from stability.ai: " + response.getBody()); 

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String generationId = response.getBody().get("id") != null ? response.getBody().get("id").toString() : null;
            if (generationId == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Did not receive generation ID from Stability AI API."));
            }
            HttpHeaders headersForVideo = new HttpHeaders();
            headersForVideo.set("Accept", "video/*");
            headersForVideo.set("Authorization", "Bearer " + apiKey);

            int attempts = 0;
            while (attempts < 10) {
                ResponseEntity<byte[]> videoResponse;
                try {
                    videoResponse = restTemplate.exchange(
                        "https://api.stability.ai/v2alpha/generation/image-to-video/result/" + generationId,
                        HttpMethod.GET,
                        new HttpEntity<>(headersForVideo),
                        byte[].class
                    );
                } catch (RestClientException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error communicating with Stability AI API."));
                }

                if (videoResponse.getStatusCode() == HttpStatus.OK) {
                    String encodedVideo = Base64.getEncoder().encodeToString(videoResponse.getBody());
                    Map<String, Object> result = new HashMap<>();
                    result.put("video", encodedVideo);
                    System.out.println("video");
                    return ResponseEntity.ok(result);
                } else if (videoResponse.getStatusCode() == HttpStatus.ACCEPTED) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Interrupted while waiting for video generation."));
                    }
                } else {
                    return ResponseEntity.status(videoResponse.getStatusCode()).body(Collections.singletonMap("error", "Image upload failed with status code " + videoResponse.getStatusCode()));
                }
                attempts++;
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Video generation did not complete within the expected time."));
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(Collections.singletonMap("error", "Image upload failed with status code " + response.getStatusCode()));
        }
    }
}
