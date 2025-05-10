package com.example.power_track_backend.service.recommendation.strategy;

import com.example.power_track_backend.RecommendationPriority;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.RecommendationEntity;
import com.example.power_track_backend.entity.ReportEntity;
import com.example.power_track_backend.mapper.RecommendationMapper;
import com.example.power_track_backend.repository.RecommendationRepo;
import com.example.power_track_backend.service.recommendation.RecommendationFactory;
import com.example.power_track_backend.service.recommendation.specification.AlwaysOnDeviceSpecification;
import com.example.power_track_backend.service.recommendation.specification.LongRunningDeviceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LongRunningDeviceStrategy extends AbstractStrategy {
    private final AlwaysOnDeviceSpecification alwaysOnDeviceSpecification;
    private final LongRunningDeviceSpecification longRunningDeviceSpecification;
    private final RecommendationFactory recommendationFactory;

    @Autowired
    public LongRunningDeviceStrategy(AlwaysOnDeviceSpecification alwaysOnDeviceSpecification, LongRunningDeviceSpecification longRunningDeviceSpecification, RecommendationMapper recommendationMapper, RecommendationRepo recommendationRepo, RecommendationFactory recommendationFactory) {
        super(recommendationMapper, recommendationRepo);
        this.alwaysOnDeviceSpecification = alwaysOnDeviceSpecification;
        this.longRunningDeviceSpecification = longRunningDeviceSpecification;
        this.recommendationFactory = recommendationFactory;
    }

    @Override
    protected boolean isSatisfiedBy(DeviceEntity device){
        return !alwaysOnDeviceSpecification.isSatisfiedBy(device) && longRunningDeviceSpecification.isSatisfiedBy(device);
    }

    @Override
    protected RecommendationEntity createRecommendation(ReportEntity report, DeviceEntity device) {

        Double potentialSavings = calculatePotentialSavings(device);
        RecommendationPriority priority = RecommendationPriority.HIGH;
        String userCurrencySymbol = report.getHouseEntity().getUserEntity().getCurrencyType().getSymbol();
        final int targetUsageMinutes = device.getAverageDailyUsageMinutes() - 120; // ToDo нужно подумать о том чтобы вынести логику форматирования сообщения

        String messageTemplate =
                        "Устройство %s (%s кВт) работает более %d часов в день. " +
                        "Рекомендуется попробовать сократить время работы до %d часов, чтобы снизить энергопотребление. " +
                        "Потенциальная экономия составит - %.2f %s в месяц.";

        String formattedMessage = String.format(messageTemplate, device.getName(),
                device.getPower(),
                device.getAverageDailyUsageMinutes() / 60, // Текущее время работы в часах
                targetUsageMinutes / 60, // Целевое время работы в часах
                potentialSavings,
                userCurrencySymbol);

        return recommendationFactory.createBaseRecommendation(report, device, formattedMessage, potentialSavings, priority);
    }

    private Double calculatePotentialSavings(DeviceEntity device) {
        // Получаем дом, к которому принадлежит устройство
        HouseEntity house = device.getHouseEntity();

        // Тарифы из дома
        Double dayTariff = house.getDayTariff();
        Double nightTariff = house.getNightTariff();

        // Параметры текущего устройства
        Integer power = device.getPower(); // Мощность в кВт
        Integer averageDailyUsageMinutes = device.getAverageDailyUsageMinutes(); // Время работы в минутах
        Integer count = device.getCount(); // Количество устройств

        // Определяем новое время работы (уменьшение на 2 часа)
        int targetUsageMinutes = Math.max(0, averageDailyUsageMinutes - 2 * 60); // Уменьшаем на 2 часа (120 минут)

        // Определяем доли времени работы днем и ночью
        double dayUsageFraction = 0.0;
        double nightUsageFraction = 0.0;

        switch (device.getUsageTimePeriod()) {
            case DAY_ONLY:
                dayUsageFraction = 1.0;
                nightUsageFraction = 0.0;
                break;
            case NIGHT_ONLY:
                dayUsageFraction = 0.0;
                nightUsageFraction = 1.0;
                break;
            case BOTH_DAY_NIGHT:
                dayUsageFraction = 0.6; // 60% днем
                nightUsageFraction = 0.4; // 40% ночью
                break;
        }

        // Рассчитываем потребление энергии (в кВт·ч)
        double currentDailyEnergyConsumption = (averageDailyUsageMinutes / 60.0) * power;
        double newDailyEnergyConsumption = (targetUsageMinutes / 60.0) * power;

        // Разделяем потребление на дневное и ночное
        double currentDayEnergyConsumption = currentDailyEnergyConsumption * dayUsageFraction;
        double currentNightEnergyConsumption = currentDailyEnergyConsumption * nightUsageFraction;

        double newDayEnergyConsumption = newDailyEnergyConsumption * dayUsageFraction;
        double newNightEnergyConsumption = newDailyEnergyConsumption * nightUsageFraction;

        // Рассчитываем затраты на энергию
        double currentDayCost = currentDayEnergyConsumption * dayTariff;
        double currentNightCost = currentNightEnergyConsumption * nightTariff;

        double newDayCost = newDayEnergyConsumption * dayTariff;
        double newNightCost = newNightEnergyConsumption * nightTariff;

        // Общая стоимость использования устройств в день
        double totalCurrentDailyCost = (currentDayCost + currentNightCost) * count;
        double totalNewDailyCost = (newDayCost + newNightCost) * count;

        // Экономия в месяц (30 дней)
        double monthlySavings = (totalCurrentDailyCost - totalNewDailyCost) * 30;

        return monthlySavings > 0 ? monthlySavings : 0.0;
    }
}
