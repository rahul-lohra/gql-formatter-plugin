package com.rahul.gqlformat;

import com.intellij.openapi.project.Project;
import org.apache.http.util.TextUtils;

import javax.swing.*;

public class GqlView {
    private JTextArea textArea;
    private JButton btnImport;
    private JPanel panel;
    private JTextField tfVariableName;
    private JButton btnReplace;
    private JLabel labelError;
    private Project project;
    private EditorLogic editorLogic;
    Timer errorTimer;

    public GqlView(Project project) {
        this.editorLogic = new EditorLogic();
        this.project = project;

        btnImport.addActionListener(actionEvent -> {
            String variableName = tfVariableName.getText();
            if (TextUtils.isEmpty(variableName)) {
                showErrorMessage("Variable name must not be empty");
            } else {
                editorLogic.printSelectedFileName(variableName, project, textArea);
            }
        });

        btnReplace.addActionListener(actionEvent -> {
            String variableName = tfVariableName.getText();
            if (TextUtils.isEmpty(variableName)) {
                showErrorMessage("Variable name must not be empty");
            }else{
                editorLogic.replace(textArea);
            }

        });
    }

    private void showErrorMessage(String text) {
        labelError.setText(text);
        int delay = 4000;
        if (errorTimer != null) {
            errorTimer.stop();
        }
        errorTimer = new Timer(delay, actionEvent -> {
            labelError.setText("");
        });
        errorTimer.start();
    }

    public JPanel getPanel() {
        return panel;
    }
}
