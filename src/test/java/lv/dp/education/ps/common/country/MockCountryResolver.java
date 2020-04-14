package lv.dp.education.ps.common.country;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MockCountryResolver extends CountryResolver {
    @Override
    protected String getCountryByIp(String ip) {
        return "Mars";
    }
}
