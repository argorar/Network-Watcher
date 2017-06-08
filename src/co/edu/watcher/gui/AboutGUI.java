package co.edu.watcher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import co.edu.watcher.utils.WatcherUtil;

/**
 * Class about GUI
 * 
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
	 * @param privateIP 
	 */
	public AboutGUI(String privateIP) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		WatcherUtil util = new WatcherUtil();
		util.setPrivateIP(privateIP);
		InetAddress in;
		try {
			in = InetAddress.getByName(privateIP);
			String[] dataNIC = util.findMAC(in);
			JTextArea txtpn = new JTextArea();
			txtpn.setBackground(new Color(204, 204, 255));
			txtpn.setLineWrap(true);
			txtpn.setEditable(false);
			txtpn.setText(util.getInfoString() + "\nNetwork Interfaces: "+dataNIC[1]);			
			contentPane.add(txtpn, BorderLayout.CENTER);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
