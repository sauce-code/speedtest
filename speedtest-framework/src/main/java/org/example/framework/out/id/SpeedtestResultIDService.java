package org.example.framework.out.id;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.IDService;
import org.example.domain.SpeedtestResultID;

import java.util.UUID;

@ApplicationScoped
public class SpeedtestResultIDService implements IDService<SpeedtestResultID> {

    @Override
    public SpeedtestResultID create() {
        var uuid = UUID.randomUUID();
        return new SpeedtestResultID(uuid);
    }

}
