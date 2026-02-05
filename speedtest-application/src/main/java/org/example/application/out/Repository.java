package org.example.application.out;

import org.example.structure.Entity;
import org.example.structure.EntityID;

public interface Repository<I extends EntityID, E extends Entity<I>> {

    default void create(E entity) {
        throw new UnsupportedOperationException();
    }

    default E read(I id) {
        throw new UnsupportedOperationException();
    }

    default void update(E entity) {
        throw new UnsupportedOperationException();
    }

    default void delete(I id) {
        throw new UnsupportedOperationException();
    }

}
