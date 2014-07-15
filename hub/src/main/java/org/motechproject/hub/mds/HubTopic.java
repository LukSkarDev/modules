package org.motechproject.hub.mds;

import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;

/**
 * Stores the topic urls
 */
@Entity
public class HubTopic implements java.io.Serializable {

    private static final long serialVersionUID = -5048963496204264339L;

    @Field(required = true)
    private String topicUrl;

    public HubTopic() {
        this(null);
    }

    public HubTopic(String topicUrl) {
        this.topicUrl = topicUrl;
    }

    public String getTopicUrl() {
        return this.topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }

}
