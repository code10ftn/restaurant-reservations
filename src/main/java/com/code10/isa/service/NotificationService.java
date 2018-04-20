package com.code10.isa.service;

import com.code10.isa.model.Notification;
import com.code10.isa.repository.NotificationRepository;
import com.code10.isa.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private SimpMessagingTemplate messagingTemplate;

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate, NotificationRepository notificationRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
    }

    public void saveAndSendNotification(Notification notification) {
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/" + notification.getTopic(), JsonUtil.json(notification));
    }

    public List<Notification> findByTopic(String topic) {
        return notificationRepository.findByTopic(topic);
    }
}
