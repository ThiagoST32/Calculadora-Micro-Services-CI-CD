package com.trevisan.CalculadorMIcroServices.infra.Config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigFeign {

    @Bean
    public OkHttpClient client(){
        return new OkHttpClient();
    }
}
