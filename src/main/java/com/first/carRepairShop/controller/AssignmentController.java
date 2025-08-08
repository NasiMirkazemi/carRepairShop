package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.AssignmentDto;
import com.first.carRepairShop.entity.AssignmentStatus;
import com.first.carRepairShop.services.impl.AssignmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentServiceImpl assignmentServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<AssignmentDto> addAssignment(@RequestBody AssignmentDto assignmentDto) {

        AssignmentDto savedAssignment = assignmentServiceImpl.createAssignment(assignmentDto);
        return ResponseEntity.ok(savedAssignment);
    }

    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("assignmentId") Integer assignmentId) {

        assignmentServiceImpl.deleteAssignmentById(assignmentId);
        return ResponseEntity.ok().body("Assignment has been deleted with id :" + assignmentId);

    }

    @PostMapping("assign/{assignmentId}/mechanic/{mechanicId}")
    public ResponseEntity<AssignmentDto> reassignToMechanic(@PathVariable("assignmentId") Integer assignmentId,
                                                            @PathVariable("mechanicId") Integer mechanicId) {
        AssignmentDto assignmentDto = assignmentServiceImpl.reassignToMechanic(assignmentId, mechanicId);
        return ResponseEntity.ok(assignmentDto);
    }

    @PutMapping("/update/{assignmentId}/status")
    public ResponseEntity<AssignmentDto> updateAssignmentStatus(@PathVariable("assignmentId") Integer assignmentId, @RequestBody AssignmentStatus status) {
        AssignmentDto assignmentDto = assignmentServiceImpl.updateAssignmentStatus(assignmentId, status);
        return ResponseEntity.ok(assignmentDto);
    }

    @GetMapping("/getByMechanicId/{mechanicId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentByMechanicId(@PathVariable("mechanicId") Integer mechanicId) {
        List<AssignmentDto> assignmentDtoList = assignmentServiceImpl.getAssignmentByMechanicId(mechanicId);
        return ResponseEntity.ok(assignmentDtoList);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AssignmentDto>> getAllAssignment() {
        List<AssignmentDto> assignmentDtoList = assignmentServiceImpl.getAllAssignment();
        return ResponseEntity.ok(assignmentDtoList);
    }

    @GetMapping("/get/{assignmentId}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable("assignmentId") Integer assignmentId) {
        AssignmentDto assignmentDto = assignmentServiceImpl.getAssignmentById(assignmentId);
        return ResponseEntity.ok(assignmentDto);
    }

}
