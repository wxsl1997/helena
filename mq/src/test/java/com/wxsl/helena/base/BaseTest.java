package com.wxsl.helena.base;

import com.wxsl.helena.TestMain;
import lombok.NonNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(classes = {TestMain.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(value = {"classpath:config/application-unit.properties"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

    @NonNull
    public ApplicationContext applicationContext() {
        return applicationContext;
    }
}
