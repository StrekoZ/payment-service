package lv.dp.education.ps.common.api.rest;

import lv.dp.education.ps.common.country.CountryResolver;
import lv.dp.education.ps.common.country.MockCountryResolver;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseResourceTest {
    @LocalServerPort
    private int port;

    protected WebTarget withClient1;
    protected WebTarget withClient2;

    @Before
    public void before() {
        withClient1 = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic("client1", "123"))
                .target("http://localhost:" + port);
        withClient2 = ClientBuilder.newClient()
                .register(HttpAuthenticationFeature.basic("client2", "123"))
                .target("http://localhost:" + port);
    }

    protected String readResource(String resource) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(resource));
    }

    @Bean
    public CountryResolver countryResolver() {
        return new MockCountryResolver();
    }
}