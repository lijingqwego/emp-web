package com.kaisn.utils;

import com.kaisn.utils.encry.SymmetricEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class EncrypPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        String username = props.getProperty("jdbc.username");
        if (username != null) {// 将加密的username解密后塞到props
            props.setProperty("jdbc.username", SymmetricEncoder.AESDncode(username));
        }
        String password = props.getProperty("jdbc.password");
        if (username != null) {
            props.setProperty("jdbc.password", SymmetricEncoder.AESDncode(password));
        }
        super.processProperties(beanFactoryToProcess, props);
    }

}
