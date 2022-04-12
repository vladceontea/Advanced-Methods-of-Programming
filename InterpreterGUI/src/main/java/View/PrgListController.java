package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.*;
import Model.PrgState;
import Model.adt.*;
import Model.stmt.*;
import Model.exp.*;
import Model.stmtFile.*;
import Model.value.*;
import Model.type.*;
import Repo.Repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PrgListController implements Initializable {

    static Repo myFirstRepository, mySecondRepository, myThirdRepository, myFourthRepository, myLastRepository;
    static Controller myFirstController, mySecondController, myThirdController, myFourthController, myLastController;
    //static IStmt firstProgram, secondProgram, thirdProgram, fourthProgram, lastProgram;
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public void setUp() {
        myFirstRepository = new Repo("firstProgramLog.txt");
        myFirstController = new Controller(myFirstRepository);
        mySecondRepository = new Repo("secondProgramLog.txt");
        mySecondController = new Controller(mySecondRepository);
        myThirdRepository = new Repo("thirdProgramLog.txt");
        myThirdController = new Controller(myThirdRepository);
        myFourthRepository = new Repo("fourthProgramLog.txt");
        myFourthController = new Controller(myFourthRepository);
        myLastRepository = new Repo("lastProgramLog.txt");
        myLastController = new Controller(myLastRepository);
        IStmt firstProgram = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
                //new IfStmt(new ValueExp(new IntValue(10)), new AssignStmt("v",new ValueExp(new IntValue(5))), new CompStmt (new AssignStmt("m", new ValueExp(new IntValue(6))), new CompStmt(new PrintStmt(new ArithExp('/', new VarExp("v"), new ValueExp(new IntValue(3)))), new PrintStmt(new ValueExp(new IntValue(100))))));
        IStmt secondProgram = new CompStmt(new VarDeclStmt("varf",new StringType()), new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                        new CloseRFileStmt(new VarExp("varf"))))))))));
                //new CompStmt(new VarDeclStmt("f",new StringType()), new CompStmt(new AssignStmt("f",new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExp("f")), new CompStmt(new ReadFileStmt(new VarExp("f"), "c"), new CompStmt(new PrintStmt(new VarExp("c")), new CompStmt(new IfStmt(new VarExp("c"), new CompStmt(new ReadFileStmt(new VarExp("f"), "c"), new PrintStmt(new VarExp("c"))), new PrintStmt(new ValueExp(new IntValue(0)))), new CloseRFileStmt(new VarExp("f"))))))));
        IStmt thirdProgram = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))), new PrintStmt(new ArithExp('+',new HeapReadExp(
                                new HeapReadExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
                // new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))), new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))), new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new PrintStmt(new VarExp("a")), new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("a"))), new AssignStmt("a", new ValueExp(new IntValue(0)))))))));
        IStmt fourthProgram = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelatExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(
                        new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                        new PrintStmt(new VarExp("v")))));
                //new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(6))), new WhileStmt(new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(4))), new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(10)))))));
        IStmt lastProgram = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a")))))))));
                //new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a"))))))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new  HeapReadExp(new VarExp("a")))))));
        IStack<IStmt> exeStack1 = new MyStack<IStmt>();
        IDict<String, IValue> symTable1 = new MyDict<String, IValue>();
        IList<IValue> out1 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable1 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable1 = new MyHeap<IValue>();
        PrgState myPrgState1 = new PrgState(exeStack1, symTable1, out1, fileTable1, heapTable1, firstProgram);
        myFirstRepository.addPrg(myPrgState1);
        IStack<IStmt> exeStack2 = new MyStack<IStmt>();
        IDict<String, IValue> symTable2 = new MyDict<String, IValue>();
        IList<IValue> out2 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable2 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable2 = new MyHeap<IValue>();
        PrgState myPrgState2 = new PrgState(exeStack2, symTable2, out2, fileTable2, heapTable2, secondProgram);
        mySecondRepository.addPrg(myPrgState2);
        IStack<IStmt> exeStack3 = new MyStack<IStmt>();
        IDict<String, IValue> symTable3 = new MyDict<String, IValue>();
        IList<IValue> out3 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable3 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable3 = new MyHeap<IValue>();
        PrgState myPrgState3 = new PrgState(exeStack3, symTable3, out3, fileTable3, heapTable3, thirdProgram);
        myThirdRepository.addPrg(myPrgState3);
        IStack<IStmt> exeStack4 = new MyStack<IStmt>();
        IDict<String, IValue> symTable4 = new MyDict<String, IValue>();
        IList<IValue> out4 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable4= new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable4 = new MyHeap<IValue>();
        PrgState myPrgState4 = new PrgState(exeStack4, symTable4, out4, fileTable4, heapTable4, fourthProgram);
        myFourthRepository.addPrg(myPrgState4);
        IStack<IStmt> exeStack5 = new MyStack<IStmt>();
        IDict<String, IValue> symTable5 = new MyDict<String, IValue>();
        IList<IValue> out5 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable5 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable5 = new MyHeap<IValue>();
        PrgState myLastPrgState = new PrgState(exeStack5, symTable5, out5, fileTable5, heapTable5, lastProgram);
        myLastRepository.addPrg(myLastPrgState);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUp();
        ObservableList<Controller> myData = FXCollections.observableArrayList();
        myData.add(myFirstController);
        myData.add(mySecondController);
        myData.add(myThirdController);
        myData.add(myFourthController);
        myData.add(myLastController);
        myPrgList.setItems(myData);
        myPrgList.getSelectionModel().selectFirst();
        myPrgList.setCellFactory(param-> new ListCell<Controller>(){
            @Override
            protected void updateItem(Controller item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null || item.getRepo().getPrgList().get(0).getOriginalProgram().toString()==null){
                    setText(null);
                }else{
                    setText(item.getRepo().getPrgList().get(0).getOriginalProgram().toString());
                }
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class) {
                        return new PrgRunController(myPrgList.getSelectionModel().getSelectedItem());
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ProgramLayout.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
