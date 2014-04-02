package jiraPlusPlus;

import java.util.List;

public interface IElectronicBoard {
    public void populate(List<Ticket> tickets);
    public void sync() throws Exception;
}
