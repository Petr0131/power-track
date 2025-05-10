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
import com.example.power_track_backend.service.recommendation.specification.CheaperAtNightSpecification;
import com.example.power_track_backend.service.recommendation.specification.HighConsumptionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheaperAtNightStrategy extends AbstractStrategy {
    private final CheaperAtNightSpecification cheaperAtNightSpecification;
    private final HighConsumptionSpecification highConsumptionSpec;
    private final AlwaysOnDeviceSpecification alwaysOnSpecification;
    private final RecommendationFactory recommendationFactory;

    @Autowired
    public CheaperAtNightStrategy(
            CheaperAtNightSpecification cheaperAtNightSpecification,
            HighConsumptionSpecification highConsumptionSpec,
            AlwaysOnDeviceSpecification alwaysOnSpecification,
            RecommendationMapper recommendationMapper,
            RecommendationRepo recommendationRepo,
            RecommendationFactory recommendationFactory) {
        super(recommendationMapper, recommendationRepo);
        this.cheaperAtNightSpecification = cheaperAtNightSpecification;
        this.highConsumptionSpec = highConsumptionSpec;
        this.alwaysOnSpecification = alwaysOnSpecification;
        this.recommendationFactory = recommendationFactory;
    }

    @Override
    protected boolean isSatisfiedBy(DeviceEntity device) {
        return cheaperAtNightSpecification.isSatisfiedBy(device) &&
                highConsumptionSpec.isSatisfiedBy(device) &&
                !alwaysOnSpecification.isSatisfiedBy(device);
    }

    @Override
    protected RecommendationEntity createRecommendation(ReportEntity report, DeviceEntity device) {
        Double potentialSavings = calculatePotentialSavings(device);
        RecommendationPriority priority = RecommendationPriority.MEDIUM;
        String userCurrencySymbol = report.getHouseEntity().getUserEntity().getCurrencyType().getSymbol();
        Double dayTariff = device.getHouseEntity().getDayTariff();
        Double nightTariff = device.getHouseEntity().getNightTariff();

        String messageTemplate =
                        "Тариф на электроэнергию ночью (%.2f %s/кВт) ниже, чем днем (%.2f %s/кВт). " +
                        "Рекомендуется использовать устройство %s преимущественно в ночное время. " +
                        "Потенциальная экономия составит - %.2f %s в месяц.";

        String formattedMessage = String.format(
                messageTemplate,
                nightTariff, userCurrencySymbol,
                dayTariff, userCurrencySymbol,
                device.getName(),
                potentialSavings,
                userCurrencySymbol
        );

        return recommendationFactory.createBaseRecommendation(report, device, formattedMessage, potentialSavings, priority);
    }

    private Double calculatePotentialSavings(DeviceEntity device) {

        HouseEntity house = device.getHouseEntity();

        Double dayTariff = house.getDayTariff();
        Double nightTariff = house.getNightTariff();

        Integer averageDailyUsageMinutes = device.getAverageDailyUsageMinutes();
        Integer power = device.getPower();
        Integer count = device.getCount();

        double dailyEnergyConsumption = (averageDailyUsageMinutes / 60.0) * power;

        double tariffDifference = dayTariff - nightTariff;

        double dailySavings = dailyEnergyConsumption * tariffDifference;

        return dailySavings * count * 30;
    }
}
