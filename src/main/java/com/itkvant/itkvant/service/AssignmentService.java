package com.itkvant.itkvant.service;

import com.itkvant.itkvant.model.Assignment;
import com.itkvant.itkvant.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

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
}
