package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import database.model.Todo;

public class AddTodoFrame extends JFrame {
    private JTextField titleField;
    private JCheckBox completedCheckbox;
    private JSpinner deadlineSpinner;

    public AddTodoFrame(TodoListFrame parentFrame) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(parentFrame);
        setTitle("Add New Todo");

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input Field
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel titleLabel = new JLabel("Title: ");
        titleField = new JTextField(20);
        JLabel completedLabel = new JLabel("Completed: ");
        completedCheckbox = new JCheckBox();
        JLabel deadlineLabel = new JLabel("Deadline: ");
        deadlineSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(deadlineSpinner, "yyyy-MM-dd HH:mm:ss");
        deadlineSpinner.setEditor(dateEditor);

        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(completedLabel);
        inputPanel.add(completedCheckbox);
        inputPanel.add(deadlineLabel);
        inputPanel.add(deadlineSpinner);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Todo");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTodo(parentFrame);
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close AddTodoFrame
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addTodo(TodoListFrame parentFrame) {
        String title = titleField.getText().trim();
        boolean completed = completedCheckbox.isSelected();
        Date deadline = (Date) deadlineSpinner.getValue();

        if (!title.isEmpty()) {
            // Menambahkan todo baru ke dalam database
            Todo.insertNew(completed ? 1 : 0, title, deadline, parentFrame.getProjectId());
            parentFrame.refreshTable(); // Refresh table in TodoListFrame
            dispose(); // Close AddTodoFrame
        } else {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
