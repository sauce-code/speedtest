package org.example.application.out;

import org.example.structure.EntityID;

public interface IDService<ID extends EntityID> {

    ID create();

}
