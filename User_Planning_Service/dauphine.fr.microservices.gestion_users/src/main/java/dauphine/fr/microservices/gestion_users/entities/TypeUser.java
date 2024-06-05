package dauphine.fr.microservices.gestion_users.entities;

public enum TypeUser {
    SPECTATOR("Spectator"),
    JOURNALIST("Journalist");

    private final String displayName;

    TypeUser(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
