package com.example.power_track_backend.service.recommendation.strategy;

import com.example.power_track_backend.CalculationConstants;
import com.example.power_track_backend.EnergyEfficiencyCategory;
import com.example.power_track_backend.RecommendationPriority;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.RecommendationEntity;
import com.example.power_track_backend.entity.ReportEntity;
import com.example.power_track_backend.mapper.RecommendationMapper;
import com.example.power_track_backend.repository.RecommendationRepo;
import com.example.power_track_backend.service.recommendation.RecommendationFactory;
import com.example.power_track_backend.service.recommendation.specification.HighConsumptionSpecification;
import com.example.power_track_backend.service.recommendation.specification.LowEfficiencySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LowEfficiencyHighConsumptionStrategy extends AbstractStrategy {
    private final LowEfficiencySpecification lowEfficiencySpecification;
    private final HighConsumptionSpecification highConsumptionSpecification;
    private final RecommendationFactory recommendationFactory;

    @Autowired
    public LowEfficiencyHighConsumptionStrategy(LowEfficiencySpecification lowEfficiencySpecification, HighConsumptionSpecification highConsumptionSpecification, RecommendationMapper recommendationMapper, RecommendationRepo recommendationRepo, RecommendationFactory recommendationFactory) {
        super(recommendationMapper, recommendationRepo);
        this.lowEfficiencySpecification = lowEfficiencySpecification;
        this.highConsumptionSpecification = highConsumptionSpecification;
        this.recommendationFactory = recommendationFactory;
    }

    @Override
    protected boolean isSatisfiedBy(DeviceEntity device) {
        return lowEfficiencySpecification.isSatisfiedBy(device) && highConsumptionSpecification.isSatisfiedBy(device);
    }

    @Override
    protected RecommendationEntity createRecommendation(ReportEntity report, DeviceEntity device) {

        Double potentialSavings = calculatePotentialSavings(device);
        RecommendationPriority priority = RecommendationPriority.HIGH;
        String userCurrencySymbol = report.getHouseEntity().getUserEntity().getCurrencyType().getSymbol();
        String deviceCategory = device.getEnergyEfficiency().getLabel();

        String messageTemplate =
                        "Устройство %s имеет низкий класс энергоэффективности - %s и высокое энергопотребление (%s кВт). " +
                        "Рекомендуется заменить его на более энергоэффективную модель. " +
                        "Потенциальная экономия составит - %.2f %s в месяц.";

        String formattedMessage = String.format(messageTemplate, device.getName(), deviceCategory, device.getPower(), potentialSavings, userCurrencySymbol);

        return recommendationFactory.createBaseRecommendation(report, device, formattedMessage, potentialSavings, priority);
    }
    private Double calculatePotentialSavings(DeviceEntity device) {
        // Получаем дом, к которому принадлежит устройство
        HouseEntity house = device.getHouseEntity();

        // Тарифы из дома
        Double dayTariff = house.getDayTariff();
        Double nightTariff = house.getNightTariff();

        // Параметры текущего устройства
        Integer power = device.getPower();
        Integer averageDailyUsageMinutes = device.getAverageDailyUsageMinutes();
        Integer count = device.getCount();

        // Класс энергоэффективности текущего устройства
        EnergyEfficiencyCategory currentEfficiency = device.getEnergyEfficiency();

        // Класс энергоэффективности нового устройства (высший класс)
        EnergyEfficiencyCategory newEfficiency = EnergyEfficiencyCategory.A;

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

        // Рассчитываем потребление энергии
        double dailyEnergyConsumptionCurrent = (averageDailyUsageMinutes / CalculationConstants.MINUTES_IN_HOUR) * powerInKw;
        double dailyEnergyConsumptionNew = dailyEnergyConsumptionCurrent * (newEfficiency.getCoefficient() / currentEfficiency.getCoefficient());

        // Разделяем потребление на дневное и ночное
        double dayEnergyConsumptionCurrent = dailyEnergyConsumptionCurrent * dayUsageFraction;
        double nightEnergyConsumptionCurrent = dailyEnergyConsumptionCurrent * nightUsageFraction;

        double dayEnergyConsumptionNew = dailyEnergyConsumptionNew * dayUsageFraction;
        double nightEnergyConsumptionNew = dailyEnergyConsumptionNew * nightUsageFraction;

        // Рассчитываем затраты на энергию
        double dayCostCurrent = dayEnergyConsumptionCurrent * dayTariff;
        double nightCostCurrent = nightEnergyConsumptionCurrent * nightTariff;

        double dayCostNew = dayEnergyConsumptionNew * dayTariff;
        double nightCostNew = nightEnergyConsumptionNew * nightTariff;

        // Общая стоимость использования устройств в день
        double totalDailyCostCurrent = (dayCostCurrent + nightCostCurrent) * count;
        double totalDailyCostNew = (dayCostNew + nightCostNew) * count;

        // Экономия в месяц
        double monthlySavings = (totalDailyCostCurrent - totalDailyCostNew) * CalculationConstants.DAYS_IN_MONTH;

        return monthlySavings > 0 ? monthlySavings : 0.0; // Возвращаем только положительную экономию
    }
}
