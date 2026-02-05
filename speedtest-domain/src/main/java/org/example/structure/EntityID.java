package org.example.structure;

import java.util.Objects;
import java.util.UUID;

public abstract class EntityID {

    protected final UUID uuid;

    protected EntityID(UUID uuid) {
        Objects.requireNonNull(uuid);
        this.uuid = uuid;
    }

    public UUID uuid() {
        return uuid;
    }

}
