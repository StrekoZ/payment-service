package lv.dp.education.ps.common.logging;

import ch.qos.logback.classic.PatternLayout;

public class PatternLayoutWithUserContext extends PatternLayout {
    static {
        PatternLayout.defaultConverterMap.put("user", UserConverter.class.getName());
        PatternLayout.defaultConverterMap.put("userIp", UserIPConverter.class.getName());
        PatternLayout.defaultConverterMap.put("userCountry", UserCountryConverter.class.getName());
    }
}