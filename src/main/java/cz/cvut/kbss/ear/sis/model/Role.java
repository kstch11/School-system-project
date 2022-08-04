package cz.cvut.kbss.ear.sis.model;

public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), STUDENT("ROLE_STUDENT"), TEACHER("ROLE_TEACHER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
