package co.edu.watcher.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import co.edu.watcher.utils.WatcherUtil;
/**
 * Class about GUI
 * @author sebastian
 */
public class AboutGUI extends JFrame {

	/**
	 * Global variables
	 */
	private static final long serialVersionUID = -6881832192355147810L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutGUI() {		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		WatcherUtil util = new WatcherUtil();
		JTextArea txtpn = new JTextArea();
		txtpn.setBackground(new Color(204, 204, 255));
		txtpn.setLineWrap(true);
		txtpn.setEditable(false);
		txtpn.setText(util.getInfoString());
		contentPane.add(txtpn, BorderLayout.CENTER);
	}

}
