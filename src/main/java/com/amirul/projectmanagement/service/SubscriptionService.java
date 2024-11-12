package com.amirul.projectmanagement.service;

import com.amirul.projectmanagement.model.PlanType;
import com.amirul.projectmanagement.model.Subscription;
import com.amirul.projectmanagement.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
