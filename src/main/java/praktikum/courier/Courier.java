package praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    static Courier random() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                "P@ssw0rd123", "Sparrow");
    }

    static Courier withoutPassword() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                null, "Sparrow");
    }

    static Courier withoutLogin() {
        return new Courier(null,
                "P@ssw0rd123", "Sparrow");
    }

    static Courier withoutFirstName() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                "P@ssw0rd123", null);
    }

    static Courier withoutLoginPassword() {
        return new Courier(null,
                null, "Sparrow");
    }

    static Courier withoutLoginFirstName() {
        return new Courier(null,
                "P@ssw0rd123", null);
    }

    static Courier withoutPasswordFirstName() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                null, null);
    }

    static Courier withoutLoginPasswordFirstName() {
        return new Courier(null,
                null, null);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
