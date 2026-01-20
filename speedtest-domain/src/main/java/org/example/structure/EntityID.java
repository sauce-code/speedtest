package org.example.structure;

import java.util.UUID;

public abstract class EntityID {

    protected final UUID uuid;

    protected EntityID(UUID uuid) {
        this.uuid = uuid;
    }

}
