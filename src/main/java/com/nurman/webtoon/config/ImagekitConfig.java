package com.nurman.webtoon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;

@org.springframework.context.annotation.Configuration
public class ImagekitConfig {
    @Autowired
    private Environment env;

    @Bean
    public ImageKit imageKit() {
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = new Configuration(env.getProperty("imagekit.publicKey"),
                env.getProperty("imagekit.privateKey"), env.getProperty("imagekit.urlEndpoint"));
        imageKit.setConfig(config);
        return imageKit;
    }
}
