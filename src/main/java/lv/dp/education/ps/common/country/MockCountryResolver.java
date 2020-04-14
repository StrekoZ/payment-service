package lv.dp.education.ps.common.country;

import org.springframework.stereotype.Component;

@Component
public class MockCountryResolver extends CountryResolver {
    @Override
    protected String getCountryByIp(String ip) {
        return "Mars";
    }
}
