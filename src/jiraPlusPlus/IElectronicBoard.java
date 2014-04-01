package jiraPlusPlus;

import java.util.List;

public interface IElectronicBoard {
    public void populate(List<List<Ticket>> tickets);
    public void sync();
}
