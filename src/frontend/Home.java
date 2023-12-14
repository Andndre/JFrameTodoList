package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.model.Project;

public class Home extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public Home() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
				setVisible(true);
        setTitle("Your Projects");

        // Header
        JPanel header = new JPanel();
        JLabel label = new JLabel("Your Projects");
        header.add(label);
        add(header, BorderLayout.NORTH);

        // Body
        tableModel = new DefaultTableModel(getData(), new String[] { "ID", "Nama Project" });
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel body = new JPanel(new BorderLayout());
        body.add(scrollPane, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        // Action Panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
							openAddProjectFrame();
					}
				});
        JButton editButton = new JButton("Edit");
				editButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								int selectedRow = table.getSelectedRow();
								if (selectedRow != -1) {
										int selectedProjectId = (int) table.getValueAt(selectedRow, 0);
										String selectedProjectTitle = (String) table.getValueAt(selectedRow, 1);

										EditProjectFrame editProjectFrame = new EditProjectFrame(Home.this, selectedProjectId, selectedProjectTitle);
										editProjectFrame.setVisible(true);
								} else {
										JOptionPane.showMessageDialog(Home.this, "Pilih project terlebih dahulu!.", "Error", JOptionPane.ERROR_MESSAGE);
								}
						}
				});
        JButton deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								deleteSelectedProject();
						}
				});
				JButton todoButton = new JButton("Todo");
        todoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTodoListFrame();
            }
        });
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(todoButton);
        add(actionPanel, BorderLayout.SOUTH);
        pack();
    }

    public Object[][] getData() {
        List<Project> projects = Project.getAllProject();
        Object[][] data = new Object[projects.size()][2];
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            data[i][0] = project.getId();
            data[i][1] = project.getJudul();
        }
        return data;
    }

		private void openAddProjectFrame() {
				AddProjectFrame addProjectFrame = new AddProjectFrame(this);
				addProjectFrame.setVisible(true);
		}

		private void openTodoListFrame() {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
						int selectedProjectId = (int) table.getValueAt(selectedRow, 0);
						String selectedProjectTitle = (String) table.getValueAt(selectedRow, 1);

						TodoListFrame todoListFrame = new TodoListFrame(selectedProjectId, selectedProjectTitle);
						todoListFrame.setVisible(true);
				} else {
						JOptionPane.showMessageDialog(Home.this, "Select a project to view todos.", "Error", JOptionPane.ERROR_MESSAGE);
				}
		}

		private void deleteSelectedProject() {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
						int selectedProjectId = (int) table.getValueAt(selectedRow, 0);
						String selectedProjectTitle = (String) table.getValueAt(selectedRow, 1);

						int confirmDialogResult = JOptionPane.showConfirmDialog(
										Home.this,
										"Apakah benar anda ingin menghapus project '" + selectedProjectTitle + "'?",
										"Konfirmasi Hapus Project",
										JOptionPane.YES_NO_OPTION
						);

						if (confirmDialogResult == JOptionPane.YES_OPTION) {
								Project.deleteProject(selectedProjectId);
								refreshTable(); // Refresh table in Home frame
						}
				} else {
						JOptionPane.showMessageDialog(Home.this, "Select a project to delete.", "Error", JOptionPane.ERROR_MESSAGE);
				}
		}

		void refreshTable() {
			tableModel.setDataVector(getData(), new String[] { "ID", "Nama Project" });
		}
}
