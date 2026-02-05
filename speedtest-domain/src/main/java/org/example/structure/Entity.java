package org.example.structure;

public abstract class Entity<I extends EntityID> {

    protected final I id;

    protected Entity(I id) {
        this.id = id;
    }

    public I id() {
        return id;
    }

}
