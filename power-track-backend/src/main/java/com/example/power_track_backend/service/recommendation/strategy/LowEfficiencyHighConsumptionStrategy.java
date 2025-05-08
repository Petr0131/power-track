package com.example.power_track_backend.service.recommendation.strategy;

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
        String deviceCategory = device.getEnergyEfficiency().getLabel(); // ToDo нужно подумать о том чтобы вынести логику форматирования сообщения

        String messageTemplate =
                        "Устройство %s имеет низкий класс энергоэффективности - %s и высокое энергопотребление (%s кВт). " +
                        "Рекомендуется заменить его на более энергоэффективную модель. " +
                        "Потенциальная экономия составит - %s %s в месяц.";

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
        Integer power = device.getPower(); // Мощность в кВт
        Integer averageDailyUsageMinutes = device.getAverageDailyUsageMinutes(); // Время работы в минутах
        Integer count = device.getCount(); // Количество устройств

        // Класс энергоэффективности текущего устройства
        EnergyEfficiencyCategory currentEfficiency = device.getEnergyEfficiency();

        // Класс энергоэффективности нового устройства (высший класс)
        EnergyEfficiencyCategory newEfficiency = EnergyEfficiencyCategory.A;

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
        double dailyEnergyConsumptionCurrent = (averageDailyUsageMinutes / 60.0) * power;
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

        // Экономия в месяц (30 дней)
        double monthlySavings = (totalDailyCostCurrent - totalDailyCostNew) * 30;

        return monthlySavings > 0 ? monthlySavings : 0.0; // Возвращаем только положительную экономию
    }
}
