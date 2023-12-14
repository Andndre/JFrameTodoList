package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import database.model.Todo;

public class EditTodoFrame extends JFrame {
    private int todoId;
    private JTextField titleField;
    private JCheckBox completedCheckbox;
    private JSpinner deadlineSpinner;

    public EditTodoFrame(TodoListFrame parentFrame, int todoId, String todoTitle, Date todoDeadline) {
        this.todoId = todoId;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(parentFrame);
        setTitle("Edit Todo");

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input Field
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel titleLabel = new JLabel("Title: ");
        titleField = new JTextField(todoTitle, 20);
        JLabel completedLabel = new JLabel("Completed: ");
        completedCheckbox = new JCheckBox();
        JLabel deadlineLabel = new JLabel("Deadline: ");
        deadlineSpinner = new JSpinner(new SpinnerDateModel(todoDeadline, null, null, Calendar.MINUTE));
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
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTodo(parentFrame);
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close EditTodoFrame
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void editTodo(TodoListFrame parentFrame) {
        String title = titleField.getText().trim();
        boolean completed = completedCheckbox.isSelected();
        Date deadline = (Date) deadlineSpinner.getValue();

        if (!title.isEmpty()) {
            // Mengedit todo di dalam database
            Todo editedTodo = new Todo(todoId, completed ? 1 : 0, title, deadline, parentFrame.getProjectId());
            editedTodo.save(); // Metode save() untuk mengupdate todo di database
            parentFrame.refreshTable(); // Refresh table in TodoListFrame
            dispose(); // Close EditTodoFrame
        } else {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
