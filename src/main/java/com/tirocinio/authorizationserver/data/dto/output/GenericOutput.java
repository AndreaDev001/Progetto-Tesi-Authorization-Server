package com.tirocinio.authorizationserver.data.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Relation(collectionRelation = "content")
public  abstract class GenericOutput<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    public void addLinks(Object... params) {

    }
}
