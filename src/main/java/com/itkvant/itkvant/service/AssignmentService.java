package com.itkvant.itkvant.service;

import com.itkvant.itkvant.model.Assignment;
import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.dto.AssignmentUser;
import com.itkvant.itkvant.repository.AssignmentRepository;
import com.itkvant.itkvant.service.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.*;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;



    TelegramBotsApi telegramBotsApi = null;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(long id) {
        Assignment assignment = null;
        Optional<Assignment> assignment1 = assignmentRepository.findById(id);

        if (assignment1.isPresent())
            assignment = assignment1.get();

        return assignment;
    }

    public void addAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    public void removeAssignment(long id) {
        assignmentRepository.deleteById(id);
    }


    public void checkAssignment(String code, User user) {

        AssignmentUser assignmentUser = new AssignmentUser();
        assignmentUser.setUserName(user.getName());
        assignmentUser.setUserLastname(user.getLastname());
        assignmentUser.setAssignmtName("Задача");
        assignmentUser.setAssignmtDescription("dscsdcds");
        assignmentUser.setAssignmtAward(12.3);
//
//        try {
//            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new TelegramBot(assignmentUser));
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
    }


}
