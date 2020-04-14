package lv.dp.education.ps.common.country;

import lombok.extern.slf4j.Slf4j;
import lv.dp.education.ps.configuration.CacheConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;


@Slf4j
public abstract class CountryResolver {
    protected abstract String getCountryByIp(String ip);

    @Cacheable(value = CacheConfig.CACHE_COUNTRY_BY_IP)
    public String resolveCountry(String ip) {
        log.debug("Resolving country by IP: {}", ip);
        if (StringUtils.isBlank(ip)) {
            return "";
        }

        return getCountryByIp(ip);
    }
}
