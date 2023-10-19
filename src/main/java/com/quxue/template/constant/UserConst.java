package com.quxue.template.constant;

import java.time.Duration;

public interface UserConst {
    Integer IS_ROOT = 1;
    String ACTIVE_USER = "user:active:";
    Duration CODE_DURATION = Duration.ofMinutes(10);
    Integer RANDOM_CODE_LENGTH = 8;
}
