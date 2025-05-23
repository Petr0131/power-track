package com.example.power_track_backend.service.recommendation.strategy;

import com.example.power_track_backend.CalculationConstants;
import com.example.power_track_backend.RecommendationPriority;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.RecommendationEntity;
import com.example.power_track_backend.entity.ReportEntity;
import com.example.power_track_backend.mapper.RecommendationMapper;
import com.example.power_track_backend.repository.RecommendationRepo;
import com.example.power_track_backend.service.recommendation.RecommendationFactory;
import com.example.power_track_backend.service.recommendation.specification.HighConsumptionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HighConsumptionStrategy extends AbstractStrategy {
    private final HighConsumptionSpecification highConsumptionSpecification;
    private final RecommendationFactory recommendationFactory;

    @Autowired
    public HighConsumptionStrategy(HighConsumptionSpecification highConsumptionSpecification, RecommendationMapper recommendationMapper, RecommendationRepo recommendationRepo, RecommendationFactory recommendationFactory) {
        super(recommendationMapper, recommendationRepo);
        this.highConsumptionSpecification = highConsumptionSpecification;
        this.recommendationFactory = recommendationFactory;
    }

    @Override
    protected boolean isSatisfiedBy(DeviceEntity device) {
        return highConsumptionSpecification.isSatisfiedBy(device);
    }

    @Override
    protected RecommendationEntity createRecommendation(ReportEntity report, DeviceEntity device) {

        Double potentialSavings = calculatePotentialSavings(device);
        RecommendationPriority priority = RecommendationPriority.MEDIUM;
        String userCurrencySymbol = report.getHouseEntity().getUserEntity().getCurrencyType().getSymbol();

        String messageTemplate =
                        "Устройство %s имеет высокое энергопотребление (%s кВт). " +
                        "Рекомендуется оптимизировать его использование за счет снижения мощности/частоты использования. " +
                        "Потенциальная экономия составит - %s %s в месяц.";

        String formattedMessage = String.format(messageTemplate, device.getName(), device.getPower(), potentialSavings, userCurrencySymbol);

        return recommendationFactory.createBaseRecommendation(report, device, formattedMessage, potentialSavings, priority);
    }

    // ToDo возможно стоит вынести общую логику calculatePotentialSavings из всех стратегий.
    // ToDo нужно вынести общую логику форматирования сообщения
    private Double calculatePotentialSavings(DeviceEntity device) {

        HouseEntity house = device.getHouseEntity();
        // Получаем тарифы из дома
        Double dayTariff = house.getDayTariff();
        Double nightTariff = house.getNightTariff();

        // Получаем мощность устройства и время работы
        Integer power = device.getPower();
        Integer averageDailyUsageMinutes = device.getAverageDailyUsageMinutes();
        Integer count = device.getCount();

        double powerInKw = power / CalculationConstants.WATT_TO_KILOWATT;

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
                dayUsageFraction = 0.6;
                nightUsageFraction = 0.4;
                break;
        }

        // Рассчитываем потребление энергии (в кВт·ч)
        double dailyEnergyConsumption = (averageDailyUsageMinutes / CalculationConstants.MINUTES_IN_HOUR) * powerInKw;
        double dayEnergyConsumption = dailyEnergyConsumption * dayUsageFraction;
        double nightEnergyConsumption = dailyEnergyConsumption * nightUsageFraction;

        // Рассчитываем затраты на энергию
        double dayCost = dayEnergyConsumption * dayTariff;
        double nightCost = nightEnergyConsumption * nightTariff;

        // Общая стоимость использования устройства в день
        double totalDailyCost = (dayCost + nightCost) * count;

        return totalDailyCost * 0.2;
    }
}
