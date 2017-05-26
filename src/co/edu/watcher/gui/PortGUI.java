package co.edu.watcher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import co.edu.watcher.utils.WatcherUtil;

/**
 * Class ports GUI
 * @author sebastian
 * @author harold
 */
public class PortGUI extends JFrame implements Runnable{

	/**
	 * Global variables
	 */
	private static final long serialVersionUID = 7565675906280529449L;
	private JPanel contentPane;
	private JTable table;
	private JLabel label;
	private DefaultTableModel model;
	private String[] columnNames = { "Port", "Status"};
	private String ipHost;

	/**
	 * Create the frame.
	 * @param ipHost: Host
	 */
	@SuppressWarnings("serial")
	public PortGUI(String ipHost) {
		this.ipHost=ipHost;
		setTitle("Port Analysis "+ ipHost);		
		setBounds(100, 100, 439, 386);
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
		// Creamos un scrollpanel y se lo agregamos a la tabla
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(12, 12, 405, 317);
		// Agregamos el scrollpanel al contenedor
		contentPane.add(scrollpane, BorderLayout.CENTER);
		
		label = new JLabel("");
		label.setBounds(200, 329, 90, 15);
		contentPane.add(label);
		//util.ports(ipHost,model,false);
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Thread that scan ports
	 */
	public void run() {		
		WatcherUtil util = new WatcherUtil();
		Socket p1;
		// for (int puerto : puertos) {
		for (int puerto = util.START; puerto <= util.END; puerto++) {
			label.setText(String.valueOf(puerto));
			try {
				p1 = new Socket();
				p1.connect(new InetSocketAddress(ipHost, puerto), 1000);
				System.out.println(ipHost + ":" + puerto + " open");
				
					Object[] row = { ipHost + ":" + puerto, "open" };
					model.addRow(row);
				
				p1.close();
			} catch (IOException e) {				
			}
		}
	}
}
