package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTest {
    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();

    int courierId;

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deleted(response);
        }
    }

    @Test
    @DisplayName("регистрация курьера")
    public void createdCourier() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. повторяющийся логин")
    public void cannotCreateLoginRepeat() {
        //создание курьера
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        //повторное создание с тем же логином
        var courierLoginRepeat = courier;
        ValidatableResponse createResponse1 = client.createCourier(courierLoginRepeat);
        check.checkFailedLoginRepeat(createResponse1);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. все поля пустые")
    public void cannotCreateWithoutLoginPasswordFirstName() {
        var courier = Courier.withoutLoginPasswordFirstName();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. без логина")
    public void cannotCreateWithoutLogin() {
        var courier = Courier.withoutLogin();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. без пароля")
    public void cannotCreateWithoutPassword() {
        var courier = Courier.withoutPassword();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. без имени")
    public void cannotCreateWithoutFirstName() {
        var courier = Courier.withoutFirstName();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. без логина и пароля")
    public void cannotCreateWithoutLoginPassword() {
        var courier = Courier.withoutLoginPassword();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. без логина и имени")
    public void cannotCreateWithoutLoginFirstName() {
        var courier = Courier.withoutLoginFirstName();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("ошибка регистрации курьера. без пароля и имени")
    public void cannotCreateWithoutPasswordFirstName() {
        var courier = Courier.withoutPasswordFirstName();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }
}
