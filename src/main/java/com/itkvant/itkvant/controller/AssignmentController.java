package com.itkvant.itkvant.controller;

import com.itkvant.itkvant.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

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


}
