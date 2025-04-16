package com.chatapp.chatApp.Controllers;


import com.chatapp.chatApp.Entity.User;
import com.chatapp.chatApp.Repository.UserRepo;
import com.chatapp.chatApp.Services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/qna")
public class AIController {
    @Autowired
    private AIService aiService;
    @Autowired
    private UserRepo userRepo;
    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String,String> payload){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userRepo.findByUserName(userName);
        String question = payload.get("question");
        String answere=aiService.getAnswere(question,user);
        return  new ResponseEntity<>(answere, HttpStatus.OK);
    }
}
