package com.example.power_track_backend.service;

import com.example.power_track_backend.dto.response.AdviceDto;
import com.example.power_track_backend.entity.AdviceEntity;
import com.example.power_track_backend.exception.AdviceNotFoundException;
import com.example.power_track_backend.mapper.AdviceMapper;
import com.example.power_track_backend.repository.AdviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class AdviceService {

    private final AdviceRepo adviceRepo;
    private final AdviceMapper adviceMapper;

    @Autowired
    public AdviceService(AdviceRepo adviceRepo, AdviceMapper adviceMapper) {
        this.adviceRepo = adviceRepo;
        this.adviceMapper = adviceMapper;
    }

    public AdviceDto getRandomAdvice() {
        List<AdviceEntity> advices = adviceRepo.findAll().stream().toList();
        if (advices.isEmpty()) {
            throw new AdviceNotFoundException("Advice not found in the database");
        }

        Random random = new Random();
        AdviceEntity randomAdvice = advices.get(random.nextInt(advices.size()));

        return adviceMapper.toDto(randomAdvice);
    }

    public List<AdviceDto> getRandomAdvices(int count) {
        List<AdviceEntity> advices = adviceRepo.findAll();
        if (advices.isEmpty()) {
            throw new AdviceNotFoundException("Advices not found in the database");
        }

        Collections.shuffle(advices); // Перемешиваем список
        return advices.stream()
                .limit(count)
                .map(adviceMapper::toDto)
                .toList();
    }
}
