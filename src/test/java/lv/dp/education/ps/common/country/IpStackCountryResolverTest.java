package lv.dp.education.ps.common.country;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IpStackCountryResolverTest {
    private IpStackCountryResolver countryResolver = new IpStackCountryResolver();

    @Test
    public void testGetCountryByIp() {
        assertEquals("", countryResolver.resolveCountry(null));
        assertEquals("", countryResolver.resolveCountry(""));
        assertEquals("LV", countryResolver.resolveCountry("94.30.255.117"));
    }
}