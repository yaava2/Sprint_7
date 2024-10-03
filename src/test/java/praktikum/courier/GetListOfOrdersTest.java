package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.order.OrderChecks;
import praktikum.order.OrderClient;

public class GetListOfOrdersTest {
    private OrderClient client = new OrderClient();
    private OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("получение списка заказов")
    public void getListOfOrders() {
        ValidatableResponse getResponse = client.getListOfOrders();
        check.checkGettingListOfOrders(getResponse);
    }
}
