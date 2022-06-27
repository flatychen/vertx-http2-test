package cn.flaty.http2bug;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Http2ClientTest {

  @BeforeEach
  public void before() {
    HttpClientOptions httpClientOptions = new HttpClientOptions();
    httpClientOptions.setProtocolVersion(HttpVersion.HTTP_2);
    // if comment this line or set setHttp2ClearTextUpgrade to true , it will be ok
    httpClientOptions.setHttp2ClearTextUpgrade(false);
    httpClientOptions.setConnectTimeout(2000);
    httpClientOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
    client = Vertx.vertx().createHttpClient(httpClientOptions);
  }

  private HttpClient client;

  @Test
  public void send() throws IOException, InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    client.request(
        HttpMethod.GET,
        8080,
        "127.0.0.1",
        "",
        ar -> {
          log.info("vertx client handler start! ");
          if (ar.succeeded()) {
            log.info("connect success");
          }else {
            log.error("connect fail",ar.cause());
          }
          countDownLatch.countDown();
        });
    countDownLatch.await();
  }
}
