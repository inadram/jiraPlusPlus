package jiraPlusPlus;

public interface IJiraService {
    public int transition(String key, String newStatus) throws Exception;
}
