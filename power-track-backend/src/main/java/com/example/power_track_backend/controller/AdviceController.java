package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.AdviceDto;
import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.service.AdviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    private final AdviceService adviceService;

    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping("/random")
    public ResponseEntity<CommonResponse<AdviceDto>> getRandomAdvice() {
        AdviceDto advice = adviceService.getRandomAdvice();

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                advice
        ));
    }

    @GetMapping("/random-list")
    public ResponseEntity<CommonResponse<List<AdviceDto>>> getRandomAdvices(@RequestParam(defaultValue = "3") int count) {
        List<AdviceDto> advices = adviceService.getRandomAdvices(count);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                advices
        ));
    }
}
