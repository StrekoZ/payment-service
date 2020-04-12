package lv.dp.education.ps.common.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class UserCountryConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        //todo implement country print
        return "";
    }
}
