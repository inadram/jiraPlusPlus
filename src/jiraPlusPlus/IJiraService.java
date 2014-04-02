package jiraPlusPlus;

public interface IJiraService {
    public String getCurrentStatus(String key) throws Exception;
    public void transition(String key, String transitionId) throws Exception;
}
