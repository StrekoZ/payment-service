package lv.dp.education.ps.common.country;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class AsyncCountryLogger {
    @Autowired
    private CountryResolver countryResolver;

    @Async
    public void printRequestCountry(String ip, UUID requestUUID) {
        log.debug("Request {} country is {}", requestUUID, countryResolver.resolveCountry(ip));
    }
}
