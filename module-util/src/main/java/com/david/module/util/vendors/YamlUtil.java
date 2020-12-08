package com.david.module.util.vendors;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

public class YamlUtil {

    /**
     * yaml文件转Map
     *
     * @param file
     * @return
     */
    static public Map<String, Object> yamlToMap(String file) throws Exception {

        if (file == null) {
            throw new Exception();
        }

        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        ClassPathResource pathResource = new ClassPathResource(file);
        yamlMapFactoryBean.setResources(pathResource);

        Map<String, Object> yamlMap = null;
        try {
            yamlMap = yamlMapFactoryBean.getObject();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return yamlMap;
    }

    /**
     * yaml文件转properties
     *
     * @param file
     * @return
     */
    static public Properties yamlToProperties(String file) {
        YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
        ClassPathResource pathResource = new ClassPathResource(file);
        return propertiesFactoryBean.getObject();
    }
}
