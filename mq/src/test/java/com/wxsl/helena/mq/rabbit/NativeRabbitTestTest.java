package com.wxsl.helena.mq.rabbit;

import com.wxsl.helena.base.BaseTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class NativeRabbitTestTest extends BaseTest {

    @Test
    void consume() {
        NativeRabbit nativeRabbit = applicationContext().getBean(NativeRabbit.class);
        nativeRabbit.consume();
    }

    @Test
    void provide() {
        NativeRabbit nativeRabbit = applicationContext().getBean(NativeRabbit.class);
        nativeRabbit.provide("生命之灯因热情而点燃, 生命之舟因拼搏而前行.");
    }
}