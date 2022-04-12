package Repo;
import Exceptions.EmptyStackException;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.MyList;

import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    //PrgState getCrtPrg() throws MyException;
    void logPrgStateExec(PrgState p) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}
