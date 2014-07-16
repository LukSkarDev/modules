package org.motechproject.hub.mds;

import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;

/**
 * Stores the subscription details of a topic
 */
@Entity
public class HubSubscription implements java.io.Serializable {

    private static final long serialVersionUID = 811889421861700076L;

    @Field(required = true)
    private Integer hubTopicId;

    @Field(required = true)
    private Integer hubSubscriptionStatusId;

    @Field(required = true)
    private String callbackUrl;

    @Field
    private Integer leaseSeconds;

    @Field
    private String secret;

    public Integer getHubTopicId() {
        return hubTopicId;
    }

    public void setHubTopicId(Integer hubTopicId) {
        this.hubTopicId = hubTopicId;
    }

    public Integer getHubSubscriptionStatusId() {
        return hubSubscriptionStatusId;
    }

    public void setHubSubscriptionStatusId(Integer hubSubscriptionStatusId) {
        this.hubSubscriptionStatusId = hubSubscriptionStatusId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Integer getLeaseSeconds() {
        return leaseSeconds;
    }

    public void setLeaseSeconds(Integer leaseSeconds) {
        this.leaseSeconds = leaseSeconds;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public HubSubscription() {
    }

    public HubSubscription(Integer hubTopicId, Integer hubSubscriptionStatusId,
            String callbackUrl) {
        this.hubTopicId = hubTopicId;
        this.hubSubscriptionStatusId = hubSubscriptionStatusId;
        this.callbackUrl = callbackUrl;
    }

    public HubSubscription(Integer hubTopicId, Integer hubSubscriptionStatusId,
            String callbackUrl, Integer leaseSeconds, String secret) {
        this.hubTopicId = hubTopicId;
        this.hubSubscriptionStatusId = hubSubscriptionStatusId;
        this.callbackUrl = callbackUrl;
        this.leaseSeconds = leaseSeconds;
        this.secret = secret;
    }
}
