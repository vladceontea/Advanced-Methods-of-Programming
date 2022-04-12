package View;

import Controller.Controller;
import Exceptions.MyException;

public class RunCommand extends Command {
    private Controller ctr;
    public RunCommand(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute() throws MyException {
        try{
            ctr.allStep();
        }
        catch(MyException | InterruptedException e){
            throw new MyException(e.getMessage());
        }
    }
}
