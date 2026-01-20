package org.example.application.out;

import org.example.structure.Entity;
import org.example.structure.EntityID;

public interface Repository<E extends Entity<ID>, ID extends EntityID> {

    void create(E entity);

    E read(ID id);

    void update(E entity);

    void delete(ID id);

}
