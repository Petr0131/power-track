package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.DeviceDto;
import com.example.power_track_backend.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/houses/{houseId}/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<DeviceDto>> addDeviceToHouse(
            @PathVariable Long houseId,
            @RequestBody DeviceDto deviceDto) {
        DeviceDto newDeviceDto = deviceService.addDeviceToHouse(houseId, deviceDto);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.CREATED.value(),
                newDeviceDto
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<DeviceDto>> getDeviceById(@PathVariable Long id) {
        DeviceDto deviceDto = deviceService.getDeviceById(id);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                deviceDto
        ));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<CommonResponse<DeviceDto>> getDeviceByNameAndHouseId(
            @PathVariable String name,
            @PathVariable Long houseId) {
        DeviceDto deviceDto = deviceService.getDeviceByNameAndHouseId(name, houseId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                deviceDto
        ));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<DeviceDto>>> getDeviceListByHouseId(@PathVariable Long houseId) {
        List<DeviceDto> deviceDtoList = deviceService.getDeviceListByHouseId(houseId);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                deviceDtoList
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<DeviceDto>> updateDevice(
            @PathVariable Long id,
            @RequestBody DeviceDto deviceDto) {
        DeviceDto updatedDeviceDto = deviceService.updateDevicePut(id, deviceDto);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                updatedDeviceDto
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteDevice(@PathVariable Long id) {
        String message = deviceService.deleteDeviceById(id);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                message
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<DeviceDto>> updateDevicePartially(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        DeviceDto updatedDeviceDto = deviceService.updateDevicePartially(id, updates);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                updatedDeviceDto
        ));
    }
}
