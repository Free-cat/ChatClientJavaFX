package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    public TextArea textArea1;
    public TextField port;
    public TextField ip;
    public TextField name;
    static Chat chatApp;
    public Button offline;
    public Button join;
    Thread thread;

    public void pressbutton(ActionEvent actionEvent) {
        offline.setDisable(false);
        join.setDisable(true);
        chatApp = new Chat(ip.getText(), port.getText(), name.getText(), textArea1);
        thread = new Thread(chatApp);
        thread.start();
    }

    public void pressOfline(ActionEvent actionEvent) {
        offline.setDisable(true);
        join.setDisable(false);
        thread.interrupt();
    }
}
