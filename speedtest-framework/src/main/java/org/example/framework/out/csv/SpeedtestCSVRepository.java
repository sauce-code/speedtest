package org.example.framework.out.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.application.out.Repository;
import org.example.domain.SpeedtestResult;
import org.example.domain.SpeedtestResultID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpeedtestCSVRepository implements Repository<SpeedtestResultID, SpeedtestResult> {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void create(SpeedtestResult entity) {
        File csvOutputFile = new File("target/results.csv");
        if (!csvOutputFile.exists()) {
            if (csvOutputFile.getParentFile().mkdirs()) {
                logger.info("created csv file");
            }
            try (FileWriter pw = new FileWriter(csvOutputFile)) {
                pw.append("id,downloadrate,uploadrate\n");
            } catch (IOException e) {
                logger.error(e);
            }
        }
        String s = Stream.of(
                        entity.id().uuid(),
                        entity.download().rateInMbps(),
                        entity.upload().rateInMbps())
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        try (FileWriter pw = new FileWriter(csvOutputFile, true)) {
            pw.append(s);
            pw.append("\n");
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
