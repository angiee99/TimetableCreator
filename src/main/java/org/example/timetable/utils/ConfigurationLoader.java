package org.example.timetable.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class ConfigurationLoader {
    @Autowired
    private ConfigurableEnvironment env;
    public Map<String, String> loadAll(){
        Map<String, String> configs = new LinkedHashMap<>();
        env.getPropertySources()
                .stream()
                .filter(ps -> ps instanceof MapPropertySource && ps.getName().contains("application.properties"))
                .map(ps -> ((MapPropertySource) ps).getSource().keySet())
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .forEach(key -> configs.put(key, env.getProperty(key)));
        return configs;
    }
}
