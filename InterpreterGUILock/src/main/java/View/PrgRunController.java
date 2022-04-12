package View;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Controller.Controller;
import Controller.GarbageCollector;
import Exceptions.MyException;
import Model.PrgState;
import Model.stmt.IStmt;
import Model.value.IValue;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PrgRunController implements Initializable {

    Controller myController;
    @FXML
    Label nrPrgStates;
    @FXML
    Button runButton;
    @FXML
    TableView<HashMap.Entry<Integer, IValue>> heapTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,IValue>, String> heapTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,IValue>, String> heapTableValue;
    @FXML
    ListView<String> outList;
    @FXML
    TableView<HashMap.Entry<IValue, BufferedReader>> fileTable;
    @FXML
    TableColumn<HashMap.Entry<IValue, BufferedReader>, String> fileTableIdentifier;
    @FXML
    TableColumn<HashMap.Entry<IValue, BufferedReader>, String> fileTableFileName;
    @FXML
    ListView<String> prgStateList;
    @FXML
    TableView<HashMap.Entry<String, IValue>> symTable;
    @FXML
    TableColumn<HashMap.Entry<String, IValue>, String> symTableVariable;
    @FXML
    TableColumn<HashMap.Entry<String, IValue>, String> symTableValue;
    @FXML
    TableView<HashMap.Entry<Integer, Integer>> lockTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Integer>, String> lockTableVariable;
    @FXML
    TableColumn<HashMap.Entry<Integer, Integer>, String> lockTableValue;
    @FXML
    ListView<String> exeStackList;

    public PrgRunController(Controller myController) {
        this.myController = myController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialRun();
        List<PrgState> prgList = myController.removeCompletedPrg(myController.getRepo().getPrgList());
        prgStateList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSymTableAndExeStack();
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                try {
                    myController.executor = Executors.newFixedThreadPool(2);
                    List<PrgState> prgList = myController.removeCompletedPrg(myController.getRepo().getPrgList());
                    myController.getRepo().getPrgList().get(0).getHeapTable().setHeap(myController.garbageCollector.unsafeGarbageCollector(
                            myController.garbageCollector.getAddrFromSymTable(myController.getRepo().getPrgList()),
                            myController.garbageCollector.getAddrFromHeapTable(prgList.get(0).getHeapTable().getHeap().values()),
                            prgList.get(0).getHeapTable().getHeap()));
                    myController.oneStepForAll(prgList);
                    //myController.getRepo().getPrgList().get(0).oneStep();
                    prgList = myController.removeCompletedPrg(myController.getRepo().getPrgList());
                } catch (MyException | InterruptedException | IndexOutOfBoundsException e1) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toy Language - Current program finished");
                    alert.setHeaderText(null);
                    alert.setContentText("Program successfully finished!");
                    Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) heapTable.getScene().getWindow();
                    stage.close();
                }
                updateUIComponents();
            }
        });
    }

    public void initialRun() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setLockTable();
        setPrgStateList();
        prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    public void updateUIComponents() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setLockTable();
        setPrgStateList();
        if(prgStateList.getSelectionModel().getSelectedItem() == null) {
            prgStateList.getSelectionModel().selectFirst();
        }
        setSymTableAndExeStack();
    }

    public void setNumberLabel() {
        nrPrgStates.setText("The number of PrgStates: " + myController.getRepo().getPrgList().size());
    }

    public void setLockTable(){
        ObservableList<HashMap.Entry<Integer, Integer>> lockTableList = FXCollections.observableArrayList();
        lockTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        lockTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        lockTableList.addAll(myController.getRepo().getPrgList().get(0).getLockTable().getContent().entrySet());
        lockTable.setItems(lockTableList);
        lockTable.refresh();
    }

    public void setHeapTable() {
        ObservableList<HashMap.Entry<Integer, IValue>> heapTableList = FXCollections.observableArrayList();
        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        heapTableList.addAll(myController.getRepo().getPrgList().get(0).getHeapTable().getHeap().entrySet());
        heapTable.setItems(heapTableList);
        heapTable.refresh();
    }

    public void setOutList() {
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        for(IValue e : myController.getRepo().getPrgList().get(0).getOut().getList()) {
            outObservableList.add(e.toString());
        }
        outList.setItems(outObservableList);
    }

    public void setFileTable() {
        ObservableList<HashMap.Entry<IValue, BufferedReader>>fileTableList = FXCollections.observableArrayList();
        fileTableIdentifier.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        fileTableFileName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        fileTableList.addAll(myController.getRepo().getPrgList().get(0).getFileTable().getDict().entrySet());
        fileTable.setItems(fileTableList);
        fileTable.refresh();
    }

    public void setPrgStateList() {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        for(PrgState e : myController.getRepo().getPrgList()) {
            prgStateIdList.add(Integer.toString(e.getId()));
        }
        prgStateList.setItems(prgStateIdList);
    }

    public void setSymTableAndExeStack() {
        ObservableList<HashMap.Entry<String, IValue>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();
        symTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        List<PrgState> allPrgs = myController.getRepo().getPrgList();
        PrgState prgResult = null;
        for(PrgState e : allPrgs) {
            if(e.getId() == Integer.parseInt(prgStateList.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }
        if(prgResult != null) {
            symTableList.addAll(prgResult.getSymTable().getDict().entrySet());
            for(IStmt e : prgResult.getExeStack().getStack()) {
                exeStackItemsList.add(e.toString());
            }
            //System.out.println(symTableList);
            symTable.setItems(symTableList);
            exeStackList.setItems(exeStackItemsList);
            symTable.refresh();
            exeStackList.refresh();
        }
    }

}
