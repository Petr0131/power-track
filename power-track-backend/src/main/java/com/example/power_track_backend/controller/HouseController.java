package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.HouseDto;
import com.example.power_track_backend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/houses")
public class HouseController {
    @Autowired
    HouseService houseService;

    @PostMapping
    public ResponseEntity<CommonResponse<HouseDto>> addHouseToUser(
            @PathVariable Long userId,
            @RequestBody HouseDto houseDto) {
        HouseDto newHouseDto = houseService.addHouseToUser(userId, houseDto);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                newHouseDto
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<HouseDto>> getHouseById(@PathVariable Long id){
        HouseDto houseDto = houseService.getHouseById(id);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                houseDto
        ));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<CommonResponse<HouseDto>> getHouseByNameAndUserId(
            @PathVariable String name,
            @PathVariable Long userId){
        HouseDto houseDto = houseService.getHouseByNameAndUserId(name, userId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                houseDto
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<HouseDto>>> getHouseListByUserId(@PathVariable Long userId) {
        List<HouseDto> houseDtoList = houseService.getHouseListByUserId(userId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                houseDtoList
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteHouse(@PathVariable Long id) {
        String message = houseService.deleteHouseById(id);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                message
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<HouseDto>> updateHouse(@PathVariable Long id, @RequestBody HouseDto house) {
        HouseDto houseDto = houseService.updateHouse(id, house);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                houseDto
        ));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<HouseDto>> updateHousePartially(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        HouseDto updatedHouseDto = houseService.updateHousePartially(id, updates);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                updatedHouseDto
        ));
    }

}
