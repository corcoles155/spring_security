package org.sanchez.corcoles.ana.pruebasconcepto.enums;

public enum Role {
    ADMIN("ADMIN"),
    SUPPORT("SUPPORT"),
    USER("USER");

     String name;


     Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
