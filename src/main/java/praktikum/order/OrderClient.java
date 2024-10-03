package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;


public class OrderClient extends Client {
    private static final String ORDERS_PATH = "orders";

    @Step("получение списка заказов")
    public ValidatableResponse getListOfOrders() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }

    @Step("создать заказ")
    public ValidatableResponse createdOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

}
