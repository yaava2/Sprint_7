package praktikum;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;

import static io.restassured.RestAssured.given;

public class Client {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String BASE_PATH = "/api/v1";

    public RequestSpecification spec() {
        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 1000));

        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                ;
    }
}
