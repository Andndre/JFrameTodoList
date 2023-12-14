package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import database.model.Project;

public class EditProjectFrame extends JFrame {
    private int projectId;
    private JTextField projectTitleField;

    public EditProjectFrame(Home parentFrame, int selectedProjectId, String selectedProjectTitle) {
        this.projectId = selectedProjectId;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(parentFrame);
        setTitle("Edit Project");

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input Field
        JPanel inputPanel = new JPanel();
        JLabel titleLabel = new JLabel("New Project Title: ");
        projectTitleField = new JTextField(selectedProjectTitle, 20);
        inputPanel.add(titleLabel);
        inputPanel.add(projectTitleField);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProject();
                parentFrame.refreshTable(); // Refresh table in Home frame
                dispose(); // Close EditProjectFrame
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close EditProjectFrame
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void editProject() {
        String newProjectTitle = projectTitleField.getText().trim();
        if (!newProjectTitle.isEmpty()) {
            // Mengubah nama project di database
            Project.updateProject(projectId, newProjectTitle);
        }
    }
}
