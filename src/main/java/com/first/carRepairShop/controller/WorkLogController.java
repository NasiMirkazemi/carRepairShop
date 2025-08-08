package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.dto.TotalHoursForWorKLog;
import com.first.carRepairShop.dto.WorkLogDto;
import com.first.carRepairShop.entity.WorkLogStatus;
import com.first.carRepairShop.services.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workLog")
@RequiredArgsConstructor
@Slf4j
public class WorkLogController {
    private final WorkLogService workLogService;

    @PostMapping("/add")
    public ResponseEntity<WorkLogDto> addWorkLog(@RequestBody @Valid WorkLogDto workLogDto
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        if (workLogDto.getWorkLogSource() != null && workLogDto.getWorkLogSource().equalsIgnoreCase("system")) {
            log.info("System-triggered WorkLog creation for assignmentId={}", workLogDto.getAssignmentId());
        } else if (workLogDto.getWorkLogSource() != null && workLogDto.getWorkLogSource().equalsIgnoreCase("mechanic")) {
            log.info("Mechanic-triggered WorkLog creation by mechanicId={}", workLogDto.getMechanicId());
        }
        WorkLogDto savedWorkLog = workLogService.addWorkLog(workLogDto);
        return ResponseEntity.ok(savedWorkLog);
    }

    @PutMapping("/start/{workLogId}")
    public ResponseEntity<String> startWorkLog(@PathVariable("workLogId") Integer workLogId
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        workLogService.startWorkLog(workLogId);
        return ResponseEntity.ok().body(" Work Log with id: " + workLogId + " Successfully started ");
    }

    @PutMapping("/pause/{workLogId}")
    public ResponseEntity<String> pauseWorkLog(@PathVariable("workLogId") Integer workLogId
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        workLogService.pauseWorkLog(workLogId);
        return ResponseEntity.ok().body("Work Log Successfully paused");
    }

    @PutMapping("/resume/{workLogId}")
    public ResponseEntity<String> resumeWorkLog(@PathVariable("workLogId") Integer workLogId
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        workLogService.resumeWorkLog(workLogId);
        return ResponseEntity.ok().body("Work Log Successfully resume");
    }

    @PutMapping("/complete/{workLogId}")
    public ResponseEntity<String> completeWorkLog(@PathVariable("workLogId") Integer workLogId
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        workLogService.completeWorkLog(workLogId);
        return ResponseEntity.ok().body("Work Log Successfully complete");

    }

    @PutMapping("/cancel/{workLogId}")
    public ResponseEntity<String> cancelWorkLog(@PathVariable("workLogId") Integer workLogId
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        workLogService.cancelWorkLog(workLogId);
        return ResponseEntity.ok().body("Work Log Successfully canceled");

    }

    @PostMapping("/addItem/{workLogId}/used-item")
    public ResponseEntity<ItemDetailDto> addUsedItem
            (@PathVariable("workLogId") Integer workLogId
                    , @RequestBody ItemDetailDto itemDetailDto
                    , @RequestParam("quantityUsed") int quantityUsed
                    , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        ItemDetailDto itemDetailDto1 = workLogService.addToUsedItem(workLogId, itemDetailDto, quantityUsed);
        return ResponseEntity.ok(itemDetailDto1);
    }

    @PutMapping("/addService/{workLogId}/add-service")
    public ResponseEntity<ServiceDetailDto> addServiceToPerformedServices(@PathVariable("workLogId") Integer workLogId
            , @RequestBody ServiceDetailDto serviceDetailDto
            , @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        ServiceDetailDto serviceDetailDto1 = workLogService.addServiceToPerformedServices(workLogId, serviceDetailDto);
        return ResponseEntity.ok(serviceDetailDto1);
    }

    @GetMapping("/get/{workLogId}")
    public ResponseEntity<WorkLogDto> getWorkLogById(@PathVariable("workLogId") Integer workLogId) {
        WorkLogDto workLogDto = workLogService.getWorkLogById(workLogId);
        return ResponseEntity.ok(workLogDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WorkLogDto>> getAllWorkLog() {
        List<WorkLogDto> workLogDtoList = workLogService.getAllWorkLog();
        return ResponseEntity.ok(workLogDtoList);
    }

    @GetMapping("/getAllByStatus")
    public ResponseEntity<List<WorkLogDto>> listWorkLogsByStatus(@RequestBody WorkLogStatus workLogStatus) {
        List<WorkLogDto> workLogDtoList = workLogService.listWorkLogsByStatus(workLogStatus);
        return ResponseEntity.ok(workLogDtoList);
    }

    @GetMapping("/getByMechanicId/{mechanicId}")
    public ResponseEntity<List<WorkLogDto>> listWorkLogsByMechanic(@PathVariable("mechanicId") Integer mechanicId) {
        List<WorkLogDto> workLogDtoList = workLogService.listWorkLogsByMechanic(mechanicId);
        return ResponseEntity.ok(workLogDtoList);
    }

    @GetMapping("/getByDateRange")
    public ResponseEntity<List<WorkLogDto>> listWorkLogsByDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from
            , @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<WorkLogDto> workLogDtoList = workLogService.listWorkLogsByDateRange(from, to);
        return ResponseEntity.ok(workLogDtoList);
    }

    @GetMapping("/getTotalHours/{workLogId}")
    public ResponseEntity<TotalHoursForWorKLog> getTotalHours(@PathVariable("workLogId") Integer workLogId) {
        TotalHoursForWorKLog totalHoursForWorKLog = workLogService.calculateTotalHourForView(workLogId);
        return ResponseEntity.ok(totalHoursForWorKLog);

    }

}
