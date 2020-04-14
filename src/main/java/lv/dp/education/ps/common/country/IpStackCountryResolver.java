package lv.dp.education.ps.common.country;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Component
public class IpStackCountryResolver extends CountryResolver {
    @Override
    protected String getCountryByIp(String ip) {
        var result = getClient()
                .target("http://api.ipstack.com/").path(ip)
                .queryParam("access_key", "96463bcc65a979744826034c7f8c8988")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<HashMap<String, Object>>() { });

        return StringUtils.defaultString("" + result.get("country_code"), "");
    }

    private Client getClient() {
        var configuration = new ClientConfig();
        configuration.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        configuration.property(ClientProperties.READ_TIMEOUT, 1000);

        return ClientBuilder.newClient(configuration);
    }
}
