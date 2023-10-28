package com.quxue.template.task;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class LifeCycle implements SmartLifecycle {

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
