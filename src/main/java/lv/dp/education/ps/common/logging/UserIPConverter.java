package lv.dp.education.ps.common.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserIPConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest().getRemoteAddr();
        } catch (Exception e) {
            return "";
        }
    }
}