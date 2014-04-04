package jiraPlusPlus.electronicBoard;

import jiraPlusPlus.Ticket;

import java.util.List;

public interface IElectronicBoard {
    public void sync(List<Ticket> tickets) throws Exception;
}
