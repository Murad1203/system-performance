package com.itkvant.itkvant.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AssignmentUser {

    private String UserName;
    private String UserLastname;

    private String AssignmtName;
    private String AssignmtDescription;
    private Double AssignmtAward;

    public AssignmentUser() {

    }
}
