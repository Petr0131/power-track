package com.example.power_track_backend.service;

import com.example.power_track_backend.dto.response.ReportDto;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.ReportDeviceConsumptionEntity;
import com.example.power_track_backend.entity.ReportEntity;
import com.example.power_track_backend.exception.HouseNotFoundException;
import com.example.power_track_backend.exception.ReportNotFoundException;
import com.example.power_track_backend.mapper.ReportMapper;
import com.example.power_track_backend.repository.DeviceRepo;
import com.example.power_track_backend.repository.HouseRepo;
import com.example.power_track_backend.repository.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepo reportRepo;
    private final HouseRepo houseRepo;
    private final ReportMapper reportMapper;
    private final DeviceRepo deviceRepo;

    @Autowired
    public ReportService(ReportRepo reportRepo, HouseRepo houseRepo, ReportMapper reportMapper, DeviceRepo deviceRepo) {
        this.reportRepo = reportRepo;
        this.houseRepo = houseRepo;
        this.reportMapper = reportMapper;
        this.deviceRepo = deviceRepo;
    }

    // Генерация отчета для дома
    @Transactional
    public ReportDto generateReportForHouse(Long houseId) {
        // Находим дом по ID
        HouseEntity house = houseRepo.findById(houseId)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id: " + houseId));

        // Получаем список устройств, закрепленных за домом
        List<DeviceEntity> devices = deviceRepo.findAllByHouseEntityId(houseId);

        // Задаем период отчета: последний месяц
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();

        double totalConsumption = 0;
        double totalCost = 0;

        // Создаем пустой отчет и сразу задаем базовые поля
        ReportEntity report = new ReportEntity();
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setHouseEntity(house);

        // Список потребления по каждому устройству
        List<ReportDeviceConsumptionEntity> deviceConsumptions = new ArrayList<>();

        for (DeviceEntity device : devices) {
            double deviceConsumption = calculateDeviceConsumption(device, startDate, endDate);
            double dayConsumption = deviceConsumption * 0.7;
            double nightConsumption = deviceConsumption * 0.3;
            double estimatedCost = dayConsumption * house.getDayTariff() + nightConsumption * house.getNightTariff();

            // Накопление общих значений
            totalConsumption += deviceConsumption;
            totalCost += estimatedCost;

            // Создание записи по устройству
            ReportDeviceConsumptionEntity deviceReport = new ReportDeviceConsumptionEntity();
            deviceReport.setDeviceEntity(device);
            deviceReport.setTotalConsumption(deviceConsumption);
            deviceReport.setDayConsumption(dayConsumption);
            deviceReport.setNightConsumption(nightConsumption);
            deviceReport.setEstimatedCost(estimatedCost);
            deviceReport.setReportEntity(report); // привязка к отчету

            deviceConsumptions.add(deviceReport);
        }

        // Привязываем список потреблений к отчету
        report.setDeviceConsumptions(deviceConsumptions);
        report.setTotalConsumption(totalConsumption);
        report.setTotalCost(totalCost);

        // Сохраняем отчет вместе с устройствами благодаря CascadeType.ALL
        ReportEntity savedReport = reportRepo.save(report);

        // Возвращаем DTO
        return reportMapper.toDto(savedReport);
    }


    // Пример расчета потребления устройства за период
    private double calculateDeviceConsumption(DeviceEntity device, LocalDate startDate, LocalDate endDate) {
        int daysInPeriod = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return device.getPower() * device.getAverageDailyUsageMinutes() / 60.0 * daysInPeriod;
    }

    // Пример расчета стоимости потребления устройства
    private double calculateDeviceCost(DeviceEntity device, double consumption, double dayTariff, double nightTariff) {
        double dayConsumption = consumption * 0.7; // 70% потребления днем
        double nightConsumption = consumption * 0.3; // 30% потребления ночью

        return dayConsumption * dayTariff + nightConsumption * nightTariff;
    }

    // Получение отчета по ID
    @Transactional // ToDo избавиться ли от @Transactional? Ленивая загрузка
    public ReportDto getReportById(Long reportId) {
        ReportEntity report = reportRepo.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + reportId));
        return reportMapper.toDto(report);
    }

    // Получение списка отчетов для дома
    @Transactional
    public List<ReportDto> getReportsByHouseId(Long houseId) {
        List<ReportEntity> reports = reportRepo.findAllByHouseEntityId(houseId);
        return reports.stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

    // Получение отчета за указанный период
    @Transactional
    public List<ReportDto> getReportByDateRange(Long houseId, LocalDate startDate, LocalDate endDate) {
        HouseEntity house = houseRepo.findById(houseId)
                .orElseThrow(() -> new HouseNotFoundException("House not found with id: " + houseId));

        List<ReportEntity> reports = reportRepo.findByHouseEntityIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(houseId, startDate, endDate)
                .orElseThrow(() -> new ReportNotFoundException("Report not found for the specified date range"));

        return reports.stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

    // Удаление отчета
    @Transactional
    public String deleteReport(Long reportId) {
        ReportEntity report = reportRepo.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + reportId));
        reportRepo.delete(report);
        return "Report deleted successfully: " + report.getId();
    }

    // Вспомогательный метод для расчета метрик отчета
    private void calculateReportMetrics(ReportEntity report) {
        // Пример расчета (замените на реальную логику)
        report.setTotalConsumption(500.0); // Расчет общего потребления
        report.setTotalCost(150.0);       // Расчет общей стоимости
    }
}
