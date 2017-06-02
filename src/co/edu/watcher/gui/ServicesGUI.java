package co.edu.watcher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import co.edu.watcher.utils.WatcherUtil;
/**
 * Class services GUI
 * @author sebastian
 */
public class ServicesGUI extends JFrame implements Runnable {

	/**
	 * Global variables
	 */
	private static final long serialVersionUID = 7565675906280529449L;
	private JPanel contentPane;
	private JTable table;
	private JLabel label;
	private DefaultTableModel model;
	private String[] columnNames = { "Service", "Port"};
	private String ipHost;

	/**
	 * Create the frame.
	 * @param ipHost: Host
	 */
	@SuppressWarnings("serial")
	public ServicesGUI(String ipHost) {
		this.ipHost=ipHost;
		setTitle("Service Analysis "+ ipHost);		
		setBounds(100, 100, 439, 381);
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
		label = new JLabel("");
		label.setBounds(200, 329, 90, 15);
		contentPane.add(label);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Thread that scan the services
	 */
	public void run() {		
		WatcherUtil util = new WatcherUtil();
		for (int puerto = util.START; puerto <= util.END; puerto++) {
			label.setText(String.valueOf(puerto));			
				util.service(ipHost, puerto, model);			
		}
	}


	
}
