package com.example.power_track_backend.service;

import com.example.power_track_backend.dto.response.DeviceDto;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.exception.DeviceAlreadyExistsException;
import com.example.power_track_backend.exception.DeviceNotFoundException;
import com.example.power_track_backend.exception.HouseAlreadyExistException;
import com.example.power_track_backend.exception.HouseNotFoundException;
import com.example.power_track_backend.mapper.DeviceMapper;
import com.example.power_track_backend.repository.DeviceRepo;
import com.example.power_track_backend.repository.HouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final DeviceRepo deviceRepo;
    private final HouseRepo houseRepo;
    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo, HouseRepo houseRepo, DeviceMapper deviceMapper) {
        this.deviceRepo = deviceRepo;
        this.houseRepo = houseRepo;
        this.deviceMapper = deviceMapper;
    }

    @Transactional
    public DeviceDto addDeviceToHouse(Long houseId, DeviceDto deviceDto){
        HouseEntity house = houseRepo.findById(houseId)
                .orElseThrow(()-> new HouseNotFoundException("House not found with id: " + houseId));

        if (deviceRepo.existsByNameAndHouseEntityId(deviceDto.getName(), houseId)) {
            throw new HouseAlreadyExistException("Device with name '" + deviceDto.getName() + "' already exists for this house");
        }
        DeviceEntity device = deviceMapper.toEntity(deviceDto);
        device.setHouseEntity(house);

        deviceRepo.save(device);

        return deviceMapper.toDto(device);
    }

    public DeviceDto getDeviceById(Long id){

        DeviceEntity device = deviceRepo.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("Device not found with id: " + id));
        return deviceMapper.toDto(device);
    }

    public DeviceDto getDeviceByNameAndHouseId(String name, Long houseId){

        DeviceEntity device = deviceRepo.findByNameAndHouseEntityId(name, houseId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with name: " + name));
        return deviceMapper.toDto(device);
    }

    public List<DeviceDto> getDeviceListByHouseId(Long houseId) {
        // Получаем список устройств из репозитория
        List<DeviceEntity> deviceEntities = deviceRepo.findAllByHouseEntityId(houseId);

        // Преобразуем сущности в DTO
        return deviceEntities.stream()
                .map(deviceMapper::toDto)
                .collect(Collectors.toList());
    }

    public DeviceDto updateDevice(Long id, DeviceDto deviceDto) {
        // Находим существующее устройство по ID
        DeviceEntity device = deviceRepo.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id: " + deviceDto.getId()));

        // Проверяем, что новое имя устройства уникально для дома за которым оно закреплено (если оно изменилось)
        if (!device.getName().equals(deviceDto.getName()) &&
                deviceRepo.existsByNameAndHouseEntityId(deviceDto.getName(), device.getHouseEntity().getId())) {
            throw new DeviceAlreadyExistsException("Device with name '" + deviceDto.getName() + "' already exists in this house");
        }

        // Обновляем данные устройства
        device.setDeviceProfile(deviceDto.getDeviceProfile());
        device.setName(deviceDto.getName());
        device.setPower(deviceDto.getPower());
        device.setAverageDailyUsageMinutes(deviceDto.getAverageDailyUsageMinutes());
        device.setEnergyEfficiency(deviceDto.getEnergyEfficiency());

        // Сохраняем обновленное устройство
        DeviceEntity updatedDevice = deviceRepo.save(device);

        // Преобразуем сущность обратно в DTO
        return deviceMapper.toDto(updatedDevice);
    }

    public List<DeviceDto> getDeviceListByUserId(Long userId) {
        // Получаем список устройств из репозитория
        List<DeviceEntity> deviceEntities = deviceRepo.findAllByHouseEntityId(userId);

        // Преобразуем сущности в DTO
        return deviceEntities.stream()
                .map(deviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteDeviceById(Long id) {
        DeviceEntity device = deviceRepo.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id: " + id));

        deviceRepo.deleteById(id);

        return device.getName();
    }
}
