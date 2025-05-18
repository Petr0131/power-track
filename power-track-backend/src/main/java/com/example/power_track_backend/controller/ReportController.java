package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.ReportDto;
import com.example.power_track_backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/houses/{houseId}/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/new-report")
    public ResponseEntity<CommonResponse<ReportDto>> createReport(@PathVariable Long houseId) {
        ReportDto createdReport = reportService.generateReportForHouse(houseId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.CREATED.value(),
                createdReport
        ));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<CommonResponse<ReportDto>> getReportById(@PathVariable Long reportId) {
        ReportDto reportDto = reportService.getReportById(reportId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                reportDto
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ReportDto>>> getReportsByHouseId(@PathVariable Long houseId) {
        List<ReportDto> reportDtos = reportService.getReportsByHouseId(houseId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                reportDtos
        ));
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<CommonResponse<List<ReportDto>>> getReportByDateRange(
            @PathVariable Long houseId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<ReportDto> reportDtos = reportService.getReportByDateRange(houseId, startDate, endDate);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                reportDtos
        ));
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<CommonResponse<String>> deleteReport(@PathVariable Long reportId) {
        String message = reportService.deleteReport(reportId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                message
        ));
    }
}
