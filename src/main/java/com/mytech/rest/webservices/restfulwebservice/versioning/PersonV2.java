package com.mytech.rest.webservices.restfulwebservice.versioning;

public class PersonV2 {
    private final Name name;

    PersonV2(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PersonV2{" +
                "name=" + name +
                '}';
    }
}
