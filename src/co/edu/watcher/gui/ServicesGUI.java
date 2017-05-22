package co.edu.watcher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import co.edu.watcher.utils.WatcherUtil;

public class ServicesGUI extends JFrame {

	/**
	 * Global variables
	 */
	private static final long serialVersionUID = 7565675906280529449L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private String[] columnNames = { "Service", "Port"};

	/**
	 * Create the frame.
	 * @param ipHost 
	 */
	@SuppressWarnings("serial")
	public ServicesGUI(String ipHost) {
		setTitle("Service Analysis "+ ipHost);		
		setBounds(100, 100, 439, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		table = new JTable();
		model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
		table.setModel(model);
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setPreferredScrollableViewportSize(new Dimension(620, 280));		
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(12, 12, 405, 317);
		contentPane.add(scrollpane, BorderLayout.CENTER);
		WatcherUtil util = new WatcherUtil();
		util.service("imdb.com",model,false);
	}


	
}
