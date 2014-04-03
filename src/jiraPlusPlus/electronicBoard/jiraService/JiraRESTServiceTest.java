package jiraPlusPlus.electronicBoard.jiraService;

import org.junit.Test;
import org.junit.Assert;

public class JiraRESTServiceTest {
    @Test
    public void testUpdateStatus() throws Exception {
        JiraRESTService restService = new JiraRESTService("https://jira.dev.bbc.co.uk/rest/api/2/", "/Users/andret04/personal.p12", "/Users/andret04/jssecacerts", "password");

        restService.transition("TVMPINVENT-7", "4");

        Assert.assertTrue(true);
    }

    @Test
    public void testGetCurrentStatus() throws Exception {
        JiraRESTService restService = new JiraRESTService("https://jira.dev.bbc.co.uk/rest/api/2/", "/Users/andret04/personal.p12", "/Users/andret04/jssecacerts", "password");

        String result = restService.getCurrentStatus("TVMPINVENT-7");

        Assert.assertEquals("InProgress", result);
    }
}
