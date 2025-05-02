package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.HouseDto;
import com.example.power_track_backend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/houses")
// @CrossOrigin(origins = "http://localhost:3000")  // Указываем фронтенд
public class HouseController {
    @Autowired
    HouseService houseService;

    @PostMapping
    public ResponseEntity<CommonResponse<HouseDto>> addHouseToUser(
            @PathVariable Long userId,
            @RequestBody HouseDto houseDto) { // Todo Убрать 1 из названия houseDto1
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

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<HouseDto>> updateHouse(@PathVariable Long id, @RequestBody HouseDto house) {
        HouseDto houseDto = houseService.updateHouse(id, house);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                houseDto
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
}
