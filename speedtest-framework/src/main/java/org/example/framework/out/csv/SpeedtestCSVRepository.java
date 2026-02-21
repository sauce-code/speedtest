package org.example.framework.out.csv;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.Logger;
import org.example.application.out.Repository;
import org.example.domain.SpeedtestResult;
import org.example.domain.SpeedtestResultID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class SpeedtestCSVRepository implements Repository<SpeedtestResultID, SpeedtestResult> {

    private final Properties properties;
    private final Logger logger;
    private final Clock clock;

    public SpeedtestCSVRepository(
            Properties properties,
            Logger logger,
            Clock clock) {
        this.properties = properties;
        this.logger = logger;
        this.clock = clock;
    }

    @Override
    public void create(SpeedtestResult entity) {
        Objects.requireNonNull(entity);
        File csvOutputFile = properties.file();
        if (!csvOutputFile.exists()) {
            File parentFile = csvOutputFile.getParentFile();
            if (parentFile.mkdirs()) {
                logger.infov("Created Directory: {0}", parentFile);
            }
            try (FileWriter pw = new FileWriter(csvOutputFile, properties.charset(), true)) {
                String s = Stream.of(
                                "ID",
                                "Start Time",
                                "End Time",
                                "Client IP-Address",
                                "ISP",
                                "Server Host",
                                "Server City",
                                "Server Country",
                                "Distance [km]",
                                "Latency [ms]",
                                "Download Rate [Mbit/s]",
                                "Upload Rate [Mbit/s]",
                                "Share URL")
                        .collect(Collectors.joining(properties.delimiter()));
                pw.append(s);
                pw.append(properties.rowSeparator());
            } catch (IOException e) {
                logger.error(e);
            }
        }
        String s = Stream.of(
                        entity.id().uuid(),
                        entity.startTime().atZone(clock.getZone()),
                        entity.endTime().atZone(clock.getZone()),
                        entity.client().ipAddress(),
                        entity.client().isp(),
                        entity.server().host(),
                        entity.server().city(),
                        entity.server().country(),
                        entity.latency().distance().kilometers(),
                        entity.latency().latency(),
                        entity.download().rateInMbps(),
                        entity.upload().rateInMbps(),
                        entity.shareUrl().uri())
                .map(String::valueOf)
                .collect(Collectors.joining(properties.delimiter()));
        try (FileWriter pw = new FileWriter(csvOutputFile, properties.charset(), true)) {
            pw.append(s);
            pw.append(properties.rowSeparator());
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
