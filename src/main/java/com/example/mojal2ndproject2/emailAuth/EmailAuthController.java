package com.example.mojal2ndproject2.emailAuth;

import com.example.mojal2ndproject2.emailAuth.model.dto.request.EmailAuthReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailAuthService;

    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    public ResponseEntity<String> verify(String email, String uuid){
        String result = emailAuthService.verify(email, uuid);
        return ResponseEntity.ok(result);
    }
}
