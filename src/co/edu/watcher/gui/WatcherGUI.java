package co.edu.watcher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import co.edu.watcher.utils.WatcherUtil;

/**
 * Class main GUI
 * 
 * @author Sebastian A. 2017
 */
public class WatcherGUI extends JFrame implements Runnable {

	/**
	 * Global variables
	 */
	private static final long serialVersionUID = 2301685011176900485L;
	private WatcherUtil util = new WatcherUtil();
	private Thread thread = new Thread(this);
	private JPanel contentPane;
	private JTable table;
	private JPanel panel_main;
	private JLabel lblscan;
	private JLabel lblip;
	private DefaultTableModel model;
	private boolean status = false;
	private String[] columnNames = { "IP Address", "Device Name" };

	/**
	 * Create the frame.
	 */
	public WatcherGUI() {
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Network Watcher");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(WatcherGUI.class.getResource("/co/edu/watcher/resources/network.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 407);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("File");
		menuBar.add(mnArchivo);

		JMenuItem mntmSalir = new JMenuItem("Quit");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(ABORT);
			}
		});

		JMenuItem mntmAcercaDe = new JMenuItem("About");
		mntmAcercaDe.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AboutGUI frame = new AboutGUI(util.getPrivateIP());
				frame.setVisible(true);
			}
		});
		mnArchivo.add(mntmAcercaDe);

		JMenuItem mntmSwitchNi = new JMenuItem("Switch NI");
		mntmSwitchNi.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seletionNI(1);
			}
		});
		mnArchivo.add(mntmSwitchNi);
		mnArchivo.add(mntmSalir);

		JMenu mnstart = new JMenu("Start");
		mnstart.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("serial")
			@Override
			public void mousePressed(MouseEvent arg0) {
				model = new DefaultTableModel(columnNames, 0) {
					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
				};
				table.setModel(model);
				start();
			}
		});
		menuBar.add(mnstart);

		JMenu mnStop = new JMenu("Stop");
		mnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				stop();
				lblscan.setText("Scan Stoped :");
				lblip.setText("0");
			}
		});
		menuBar.add(mnStop);

		JMenu mnViewPorts = new JMenu("View Ports");
		mnViewPorts.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (seletion()) {
					String ipHost = (String) model.getValueAt(table.getSelectedRow(), 0);
					PortGUI frame = new PortGUI(ipHost);
					frame.setVisible(true);
				}
			}
		});
		menuBar.add(mnViewPorts);

		JMenu mnViewServices = new JMenu("View Services");
		mnViewServices.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (seletion()) {
					String ipHost = (String) model.getValueAt(table.getSelectedRow(), 0);
					ServicesGUI frame = new ServicesGUI(ipHost);
					frame.setVisible(true);
				}
			}
		});
		menuBar.add(mnViewServices);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel footer = new JPanel();
		contentPane.add(footer, BorderLayout.SOUTH);

		lblscan = new JLabel("Items");
		footer.add(lblscan);

		lblip = new JLabel("0");
		footer.add(lblip);

		panel_main = new JPanel();
		contentPane.add(panel_main, BorderLayout.CENTER);
		createTable();
		seletionNI(0);
		thread.start();

	}

	/**
	 * Method that show all network interface
	 * @param call : Kind of execution , 0 is the beginning
	 */
	private void seletionNI(int call) {
		ArrayList<String> stockList = util.networkInterfaces();
		String[] stockArr = new String[stockList.size()];
		stockArr = stockList.toArray(stockArr);
		ImageIcon icon = new ImageIcon("img/network.png");
		String resp = (String) JOptionPane.showInputDialog(null, "Please select a network interface.",
				"Network Interfaces", JOptionPane.CLOSED_OPTION, icon, stockArr, stockArr[0]);
		if (resp == null && call == 0)
			System.exit(ABORT);
		if (resp != null) {
			String[] array = resp.split("/");
			util.setPrivateIP(array[1]);
		}
	}

	/**
	 * Methos that create the table
	 */
	@SuppressWarnings("serial")
	private void createTable() {
		// Creacion de la tabla
		table = new JTable();
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
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
		// Agregamos el scrollpanel al contenedor
		panel_main.add(scrollpane, BorderLayout.CENTER);
	}

	/**
	 * Thread that does the scan
	 */
	public void run() {
		while (true) {
			try {
				String[] list = util.getAddressList();
				for (int i = 0; i < list.length && state() == true; i++) {
					lblscan.setText("Scanning :");
					lblip.setText(list[i]);
					Object[] row = util.scan(list[i], false);
					if (row != null)
						model.addRow(row);
				}
				stop();
				lblscan.setText("Scan Finish :");
				lblip.setText("0");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Something Happend.");
			}
		}
	}

	/**
	 * Method that verifies the selection of a host
	 * 
	 * @return If there is a selection true, false in other case
	 */
	private boolean seletion() {
		int s = table.getSelectedRow();
		if (s == -1) {
			JOptionPane.showMessageDialog(null, "Please select a Host", "Alert", JOptionPane.WARNING_MESSAGE);
			return false;
		} else
			return true;
	}

	/**
	 * Method that changes status to true
	 */
	private synchronized void start() {
		status = true;
	}

	/**
	 * Method that changes status to false
	 */
	private synchronized void stop() {
		status = false;
	}

	/**
	 * Method that return status
	 * 
	 * @return status
	 */
	private synchronized boolean state() {
		return status;
	}

}
