package lv.dp.education.ps.configuration;

import lv.dp.education.ps.common.country.AsyncCountryLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Configuration
public class RequestLoggingFilterConfig {

    @Autowired
    private AsyncCountryLogger countryLogger;

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
            @Override
            protected void beforeRequest(HttpServletRequest request, String message) {
                var requestUUID = UUID.randomUUID();
                super.beforeRequest(request, message + " request id: " + requestUUID);
                countryLogger.printRequestCountry(request.getRemoteAddr(), requestUUID);
            }
        };
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false);
        filter.setIncludeHeaders(false);
        return filter;
    }
}