package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.order.Order;
import praktikum.order.OrderChecks;
import praktikum.order.OrderClient;

@RunWith(Parameterized.class)
    public class CreateOrderTest {
    private OrderClient client = new OrderClient();
    private OrderChecks check = new OrderChecks();

    private String[] color;

        public CreateOrderTest(String[] color) {
            this.color = color;
        }

        @Parameterized.Parameters()
        public static Object[][] dataGenerator() {
            return new Object[][]{
                    {new String[]{"BLACK"} },
                    {new String[] {"GREY"} },
                    {new String[]{"BLACK","GREY"} },
                    {new String[]{}}
            };
        }

        @Test
        @DisplayName("создание заказа")
        public void createOrder() {
            var order = new Order("Иван", "Иванов", "Москва, ул. Третья, д. 1",
                    "4", "+7 800 355 35 35", 5, "2024-10-06",
                    "парковки нет", color);
            ValidatableResponse getResponse = client.createdOrder(order);
            check.checkCreatedOrder(getResponse);
        }
    }

