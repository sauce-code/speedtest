package org.example.framework.in.rest;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.example.domain.SpeedtestResult;

import java.time.LocalDateTime;

@RegisterForReflection
public record SpeedtestResultView(
        String id,
        LocalDateTime startTime,
        double download,
        double upload,
        String shareUrl
) {

    public static SpeedtestResultView from(SpeedtestResult speedtestResult) {
        return new SpeedtestResultView(
                speedtestResult.id().uuid().toString(),
                speedtestResult.startTime(),
                speedtestResult.download().rateInMbps(),
                speedtestResult.upload().rateInMbps(),
                speedtestResult.shareUrl().uri().toString());
    }

}
