package jiraPlusPlus;

public interface IJiraService {
    public int updateStatus(String key, String newStatus) throws Exception;
}
