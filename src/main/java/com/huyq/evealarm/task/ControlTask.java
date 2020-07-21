package com.huyq.evealarm.task;

import com.huyq.evealarm.core.ConstantHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author huyiqi
 * @date 2020/7/21
 */
@Component
public class ControlTask {

    @Scheduled(cron = "*/1 * * * * ?")
    public void controlStatus() {
        ConstantHolder.NEED_ALARM =
                ConstantHolder.STATUS_0 ||
                ConstantHolder.STATUS_5 ||
                ConstantHolder.STATUS_10 ||
                ConstantHolder.STATUS_WAR;
    }
}
