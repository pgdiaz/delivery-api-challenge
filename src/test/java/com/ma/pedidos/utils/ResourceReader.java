package com.ma.pedidos.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

public class ResourceReader {

    private Resource resource;

    public ResourceReader(String resourcePath) {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String location = ResourceUtils.CLASSPATH_URL_PREFIX + resourcePath;

        this.resource = resourceLoader.getResource(location);
    }

    public String asString() throws Exception {
        
        InputStream inputStream = this.resource.getInputStream();

        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
            .lines()
            .collect(Collectors.joining(System.lineSeparator()));
    }
}
