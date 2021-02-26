package com.daimao.ui;

import com.daimao.model.FileMeta;
import com.daimao.service.DBService;
import com.daimao.service.FileService;
import com.daimao.util.LogUtil;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class AppController implements Initializable {
    @FXML
    public GridPane rootPane;
    @FXML
    public Label srcDirectory;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<FileMeta> fileTable;
    @FXML
    public TableColumn<FileMeta, String> nameColumn;
    @FXML
    public TableColumn<FileMeta, String> sizeColumn;
    @FXML
    public TableColumn<FileMeta, String> lastModifiedColumn;

    @Autowired
    private FileService fileService;
    @Autowired
    private DBService dbService;

    @FXML
    public void choose(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File root = chooser.showDialog(rootPane.getScene().getWindow());
        if (root == null) {
            return;
        }
        dbService.init();
        fileService.scan(root);

        List<FileMeta> fileList = fileService.queryAll();
        Platform.runLater(() ->{
            fileTable.getItems().clear();
            fileTable.getItems().addAll(fileList);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 会在 FXMLLoader.load 执行时，实例化好 AppController 后调用
        StringProperty stringProperty = searchField.textProperty();
        stringProperty.addListener((observable, oldValue, newValue) -> {
//            LogUtil.log("oldValue: " + oldValue + " ;");
            LogUtil.log("newValue: " + newValue + " ;");


            List<FileMeta> fileList = fileService.query(newValue.trim());
            Platform.runLater(() ->{
                fileTable.getItems().clear();
                fileTable.getItems().addAll(fileList);
            });
        });
    }

}
