package com.snews.server.scheduledTasks;

import com.snews.server.services.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveInvalidPasswordRecoveryTokens {

    private final UserService userService;

    public RemoveInvalidPasswordRecoveryTokens(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 2 * * * ")
    public void runTask() {
        this.userService.removeInvalidPasswordRecoveryTokens();
    }
}
