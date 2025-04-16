package com.chatapp.chatApp.Controllers;

import com.chatapp.chatApp.Entity.Message;
import com.chatapp.chatApp.Repository.ChatRoomRepo;
import com.chatapp.chatApp.Services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    @MessageMapping("/sendMessage/{roomId}")
//    @SendTo("topic/room/{roomId}")
//    public Message sendMessage(@DestinationVariable String roomId, @RequestBody Message message){
    public Message sendMessage(@DestinationVariable String roomId, @Payload Message message){
        System.out.println("📨 Received message for room " + roomId + " : " + message.getMessage());
        chatRoomService.sendMessage(roomId,message);
        simpleMessagingTemplate.convertAndSend("/topic/room/"+roomId,message);
        return message;
    }
}