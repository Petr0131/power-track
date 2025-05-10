package com.example.power_track_backend.service;

import com.example.power_track_backend.dto.response.HouseDto;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.HouseAlreadyExistException;
import com.example.power_track_backend.exception.HouseNotFoundException;
import com.example.power_track_backend.exception.UserNotFoundException;
import com.example.power_track_backend.mapper.HouseMapper;
import com.example.power_track_backend.repository.HouseRepo;
import com.example.power_track_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private final HouseRepo houseRepo;
    private final UserRepo userRepo;
    private final HouseMapper houseMapper;

    @Autowired
    public HouseService(HouseRepo houseRepo,UserRepo userRepo, HouseMapper houseMapper){
        this.houseRepo = houseRepo;
        this.userRepo = userRepo;
        this.houseMapper = houseMapper;
    }

    @Transactional
    public HouseDto addHouseToUser(Long userId, HouseDto houseDto) {
        // Проверяем существование пользователя
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Проверяем уникальность имени дома для конкретного пользователя
        if (houseRepo.existsByNameAndUserEntityId(houseDto.getName(), userId)) {
            throw new HouseAlreadyExistException("House with name '" + houseDto.getName() + "' already exists for this user");
        }

        // Создаем новый дом и закрепляем его за пользователем
        HouseEntity house = houseMapper.toEntity(houseDto);
        house.setUserEntity(user);

        houseRepo.save(house);

        return houseMapper.toDto(house);
    }

    public HouseDto getHouseById(Long id){

        HouseEntity house = houseRepo.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id: " + id));
        return houseMapper.toDto(house);
    }

    public HouseDto getHouseByNameAndUserId(String name, Long userId){

        HouseEntity house = houseRepo.findByNameAndUserEntityId(name, userId)
                .orElseThrow(() -> new HouseNotFoundException("House not found with name: " + name));
        return houseMapper.toDto(house);
    }

    public List<HouseDto> getHouseListByUserId(Long userId) {
        // Получаем список домов из репозитория
        List<HouseEntity> houseEntities = houseRepo.findAllByUserEntityId(userId);

        // Преобразуем сущности в DTO
        return houseEntities.stream()
                .map(houseMapper::toDto)
                .collect(Collectors.toList());
    }

    public HouseDto updateHouse(Long id, HouseDto houseDto) { //Todo убрать все 3 метода PUT из контроллеров и сервисов. House User Device.
        // Находим существующий дом по ID
        HouseEntity house = houseRepo.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id: " + id));

        // Проверяем, что новое имя дома уникально для пользователя за которым оно закреплено(если оно изменилось)
        if (!house.getName().equals(houseDto.getName()) && houseRepo.existsByNameAndUserEntityId(houseDto.getName(), house.getUserEntity().getId())) {
            throw new HouseAlreadyExistException("House with name '" + houseDto.getName() + "' already exists");
        }

        // Обновляем данные дома
        house.setName(houseDto.getName());
        house.setRooms(houseDto.getRooms());
        house.setResidents(houseDto.getResidents());
        house.setDayTariff(houseDto.getDayTariff());
        house.setNightTariff(houseDto.getNightTariff());

        HouseEntity updatedHouse = houseRepo.save(house);

        return houseMapper.toDto(updatedHouse);
    }

    @Transactional
    public String deleteHouseById(Long id) {
        HouseEntity house = houseRepo.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id: " + id));

        houseRepo.deleteById(id);

        return house.getName();
    }

    public HouseDto updateHousePartially(Long id, Map<String, Object> updates) {
        // Находим дом по ID
        HouseEntity houseEntity = houseRepo.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id: " + id));

        // Создаем мапу обработчиков для каждого поля
        Map<String, Consumer<Object>> fieldUpdaters = Map.of(
                "name", value -> houseEntity.setName(value.toString()),
                "rooms", value -> houseEntity.setRooms(Integer.parseInt(value.toString())),
                "residents", value -> houseEntity.setResidents(Integer.parseInt(value.toString())),
                "dayTariff", value -> houseEntity.setDayTariff(Double.parseDouble(value.toString())),
                "nightTariff", value -> houseEntity.setNightTariff(Double.parseDouble(value.toString()))
        );

        // Обновляем поля дома на основе входных данных
        updates.forEach((key, value) -> {
            Consumer<Object> updater = fieldUpdaters.get(key);
            if (updater != null) {
                updater.accept(value);
            } else {
                throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Сохраняем обновленный дом в базе данных
        HouseEntity updatedHouseEntity = houseRepo.save(houseEntity);

        // Преобразуем сущность в DTO и возвращаем
        return houseMapper.toDto(updatedHouseEntity);
    }
}
