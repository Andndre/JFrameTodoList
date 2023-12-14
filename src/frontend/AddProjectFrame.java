package frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.model.Project;

public class AddProjectFrame extends JFrame {
    private JTextField projectTitleField;

    public AddProjectFrame(Home parentFrame) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(parentFrame);
        setTitle("Add New Project");

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input Field
        JPanel inputPanel = new JPanel();
        JLabel titleLabel = new JLabel("Project Title: ");
        projectTitleField = new JTextField(20);
        inputPanel.add(titleLabel);
        inputPanel.add(projectTitleField);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Project");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProject();
                parentFrame.refreshTable(); // Refresh table in Home frame
                dispose(); // Close AddProjectFrame
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close AddProjectFrame
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addProject() {
        String projectTitle = projectTitleField.getText().trim();
        if (!projectTitle.isEmpty()) {
            Project.insertNew(projectTitle);
        }
    }
}
