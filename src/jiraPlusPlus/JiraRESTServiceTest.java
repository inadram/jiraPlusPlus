package jiraPlusPlus;

import org.junit.Test;
import org.junit.Assert;

public class JiraRESTServiceTest {
    @Test
    public void testUpdateStatus() throws Exception {
        JiraRESTService restService = new JiraRESTService("https://jira.dev.bbc.co.uk/rest/api/2/", "/Users/andret04/personal.p12", "/Users/andret04/jssecacerts", "password");

        int responseCode = restService.updateStatus("TVMPINVENT-7", "3");

        Assert.assertEquals(200, responseCode);
    }
}
