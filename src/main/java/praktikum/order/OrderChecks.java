package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import java.util.Map;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.*;

public class OrderChecks {

    @Step("создание заказа")
    public void checkCreatedOrder(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .body().as(Map.class);;

        assertNotEquals(null, body.get("track"));
    }

    @Step("получение списка заказов")
    public void checkGettingListOfOrders(ValidatableResponse response) {
        List getting = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("orders");
        assertFalse(getting.isEmpty());
    }
}
