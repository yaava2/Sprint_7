package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;
import java.util.Set;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CourierChecks {
    @Step("создался успешно")
    public void checkCreated(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("создать не получилось")
    public void checkFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Недостаточно данных для создания учетной записи", body.get("message"));
        assertEquals(Set.of("message"), body.keySet());
    }

    @Step("создание курьера с повторяющимся логином")
    public void checkFailedLoginRepeat(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .body().as(Map.class);

        assertEquals("Этот логин уже используется", body.get("message"));
        assertEquals(Set.of("message"), body.keySet());
    }

    @Step("залогинился")
    public int checkLoggedIn(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");

        assertNotEquals(0, id);

        return id;
    }

    @Step("попытка залогиниться без логина или пароля")
    public void checkFailedLoggedIn(ValidatableResponse loginResponse) {
        var id = loginResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Недостаточно данных для входа", id.get("message"));
        assertEquals(Set.of("message"), id.keySet());
    }

    @Step("залогинился с несуществующей парой логин-пароль")
    public void checkFailedLoggedInNonExistentLoginPassword(ValidatableResponse loginResponse) {
        var id = loginResponse
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .body().as(Map.class);

        assertEquals("Учетная запись не найдена", id.get("message"));
        assertEquals(Set.of("message"), id.keySet());
    }

    public void deleted(ValidatableResponse response) {

    }
}
