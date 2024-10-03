package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {
    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();
    int courierId;
    Courier courier = Courier.random();

     @Before
     public void CreateCourier() {
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deleted(response);
        }
    }

    @Test
    @DisplayName("авторизация курьера")
    public void LoggedInCourier() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("ошибка авторизации курьера. без логина и пароля")
    public void cannotLoginWithoutLoginPassword() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        var courierFailed = Courier.withoutLoginPassword();
        var cred = CourierCredentials.fromCourier(courierFailed);
        ValidatableResponse loginResponseFailed = client.logIn(cred);
        check.checkFailedLoggedIn(loginResponseFailed);
    }

    @Test
    @DisplayName("ошибка авторизации курьера. без логина")
    public void cannotLoginWithoutLogin() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        var courierFailed = Courier.withoutLogin();
        var cred = CourierCredentials.fromCourier(courierFailed);
        ValidatableResponse loginResponseFailed = client.logIn(cred);
        check.checkFailedLoggedIn(loginResponseFailed);
    }

    @Test
    @DisplayName("ошибка авторизации курьера. без пароля")
    public void cannotLoginWithoutPassword() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        var courierFailed = Courier.withoutPassword();
        var cred = CourierCredentials.fromCourier(courierFailed);
        ValidatableResponse loginResponseFailed = client.logIn(cred);
        check.checkFailedLoggedIn(loginResponseFailed);
    }

    @Test
    @DisplayName("ошибка авторизации курьера. несуществующая пара логин-пароль")
    public void cannotLoginWithout() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        //удаление пользователя
        if (courierId != 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deleted(response);
        }

        //попытка входа с удаленными данными
        var cred = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponseFailed = client.logIn(cred);
        check.checkFailedLoggedInNonExistentLoginPassword(loginResponseFailed);
    }

}
