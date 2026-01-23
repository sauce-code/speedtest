package org.example.structure;

public abstract class Entity<ID extends EntityID> {

    protected final ID id;

    protected Entity(ID id) {
        this.id = id;
    }

}
