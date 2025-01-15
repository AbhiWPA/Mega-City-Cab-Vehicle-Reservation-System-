package lk.mcc.megacitycab.config;

import lk.mcc.megacitycab.audit.HeaderHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.context.WebApplicationContext;

/**
 * Title: Mega-City-Cab-Vehicle-Reservation-System-
 * Description: CommonConfig Class
 * Created by Abhishek Ashinsa on 1/15/2025
 * Email: abhi.ashinsa@gmail.com
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */

@Configuration
@RequiredArgsConstructor
public class CommonConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.failOnUnknownProperties(false);
        return builder;
    }

    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        return new ResourceBundleMessageSource();
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HeaderHolder headerHolder() {
        return new HeaderHolder();
    }
}
