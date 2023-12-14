package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import database.model.Todo;

public class TodoListFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
		private int projectId;

    public TodoListFrame(int projectId, String projectTitle) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Todo List - " + projectTitle);

				this.projectId = projectId;

        // Header
        JPanel header = new JPanel();
        JLabel label = new JLabel("Todo List - " + projectTitle);
        header.add(label);
        add(header, BorderLayout.NORTH);

        // Body
        tableModel = new DefaultTableModel(getTodoData(projectId), new String[]{"ID", "Judul", "Completed", "Deadline"});
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel body = new JPanel(new BorderLayout());
        body.add(scrollPane, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        JButton todoButton = new JButton("Todo");
        todoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTodoListFrame();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddTodoFrame();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedTodoId = (int) table.getValueAt(selectedRow, 0);
                    String selectedTodoTitle = (String) table.getValueAt(selectedRow, 1);
                    Date selectedTodoDeadline = (Date) table.getValueAt(selectedRow, 3);

                    EditTodoFrame editTodoFrame = new EditTodoFrame(TodoListFrame.this, selectedTodoId, selectedTodoTitle, selectedTodoDeadline);
                    editTodoFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(TodoListFrame.this, "Select a todo to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedTodo();
            }
        });

        footer.add(addButton);
        footer.add(editButton);
        footer.add(deleteButton);
        add(footer, BorderLayout.SOUTH);

        pack();
    }

    private Object[][] getTodoData(int projectId) {
        List<Todo> todos = Todo.getAllTodosInProject(projectId);
        Object[][] data = new Object[todos.size()][4];
        for (int i = 0; i < todos.size(); i++) {
            Todo todo = todos.get(i);
            data[i][0] = todo.getId();
            data[i][1] = todo.getJudul();
            data[i][2] = todo.isCompleted();
            data[i][3] = todo.getDeadline();
        }
        return data;
    }

		private void openAddTodoFrame() {
				AddTodoFrame addTodoFrame = new AddTodoFrame(this);
				addTodoFrame.setVisible(true);
		}

		private void deleteSelectedTodo() {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
						int selectedTodoId = (int) table.getValueAt(selectedRow, 0);
						String selectedTodoTitle = (String) table.getValueAt(selectedRow, 1);

						int confirmDialogResult = JOptionPane.showConfirmDialog(
										TodoListFrame.this,
										"Apakah benar anda ingin menghapus todo '" + selectedTodoTitle + "'?",
										"Konfirmasi Hapus Todo",
										JOptionPane.YES_NO_OPTION
						);

						if (confirmDialogResult == JOptionPane.YES_OPTION) {
								Todo.deleteTodo(selectedTodoId);
								refreshTable(); // Refresh table in TodoListFrame
						}
				} else {
						JOptionPane.showMessageDialog(TodoListFrame.this, "Select a todo to delete.", "Error", JOptionPane.ERROR_MESSAGE);
				}
		}

		private void openTodoListFrame() {
				TodoListFrame todoListFrame = new TodoListFrame(projectId, "");
				todoListFrame.setVisible(true);
		}

		void refreshTable() {
				tableModel.setDataVector(getTodoData(projectId), new String[]{"ID", "Judul", "Completed", "Deadline"});
		}

		public int getProjectId() {
				return projectId;
		}
}
