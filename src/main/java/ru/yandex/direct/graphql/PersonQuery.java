package ru.yandex.direct.graphql;

import java.util.List;

import io.leangen.graphql.annotations.GraphQLQuery;

public class PersonQuery {
    private final List<Person> allPersons;

    public PersonQuery(List<Person> allPersons) {
        this.allPersons = allPersons;
    }

    @GraphQLQuery(name = "persons")
    public List<Person> getPersons() {
        return allPersons;
    }
}
