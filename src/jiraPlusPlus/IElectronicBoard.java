package jiraPlusPlus;

import java.util.List;

public interface IElectronicBoard {
    public void Populate(List<List<Ticket>> tickets);
    public void Sync();
}
