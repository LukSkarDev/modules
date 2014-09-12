package org.motechproject.ivr.domain;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Config Unit Tests
 */
public class ConfigTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);

    @Test
    public void shouldIgnoreFields() {

        Config config = new Config(null, new ArrayList<>(Arrays.asList("foo")), null, null, null);
        assertTrue(config.shouldIgnoreField("foo"));
        assertFalse(config.shouldIgnoreField("bar"));
    }

    @Test
    public void verifyMappedStatusFields() {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("foo", "bar");

        Config config = new Config(null, null, statusMap, null, null);
        assertEquals("bar", config.mapStatusField("foo"));
        assertEquals("zee", config.mapStatusField("zee"));
    }

    @Test
    public void configToJson() {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("foo", "bar");

        Config config1 = new Config("myConfig1", Arrays.asList("foo", "bar"), statusMap, HttpMethod.GET,
                "http://foo.com/bar");
        Config config2 = new Config("myConfig2", Arrays.asList("foo", "bar"), statusMap, HttpMethod.GET,
                "http://foo.com/bar");
        List<Config> configs = Arrays.asList(config1, config2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(configs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("config = {}", json);
    }
}
