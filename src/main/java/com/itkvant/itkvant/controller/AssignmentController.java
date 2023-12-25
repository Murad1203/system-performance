package com.itkvant.itkvant.controller;

import com.itkvant.itkvant.model.Assignment;
import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.dto.AssignmentUser;
import com.itkvant.itkvant.service.AssignmentService;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    Assignment assignmentOld = new Assignment();
    @GetMapping("/assignments")
    public String assignments(Model model) {
        model.addAttribute("task", assignmentService.getAllAssignments());
        return "all-assignments";

    }

    @GetMapping("/assignment/{id}")
    public String checkAssignment(@PathVariable long id, Model model) {
        model.addAttribute("assignment", assignmentService.getAssignmentById(id));
        return "assignment";
    }

    @PostMapping("/assignment-users")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> handlePostRequest(@RequestBody AssignmentUser requestData,
                                                    Authentication authentication) {


        User user = (User) authentication.getPrincipal();
        requestData.setUserName(user.getName());
        requestData.setUserLastname(user.getLastname());
        assignmentService.checkAssignment(requestData, user.getId());
        return new ResponseEntity<>("Data received successfully", HttpStatus.OK);
    }


}
