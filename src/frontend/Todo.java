package frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.model.Project;

public class Todo extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public Todo(int idProject, String judul) {
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

        // Button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle("Your Projects");
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

    private void refreshTable() {
        tableModel.setDataVector(getData(), new String[] { "ID", "Nama Project" });
    }
}
