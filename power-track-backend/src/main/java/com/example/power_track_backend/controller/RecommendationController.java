package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.RecommendationDto;
import com.example.power_track_backend.service.recommendation.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<CommonResponse<List<RecommendationDto>>> getRecommendations(@PathVariable Long reportId) {
        List<RecommendationDto> recommendations = recommendationService.generateRecommendationsForReport(reportId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                recommendations
        ));
    }
}
