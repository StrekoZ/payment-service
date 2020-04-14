package lv.dp.education.ps.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {
    public final static String CACHE_COUNTRY_BY_IP = "cacheOne";

    @Bean
    public Cache countryByIpCache() {
        return new ConcurrentMapCache(
                CACHE_COUNTRY_BY_IP,
                CacheBuilder.newBuilder()
                        .expireAfterAccess(60, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .build()
                        .asMap(),
                true
        );
    }
}