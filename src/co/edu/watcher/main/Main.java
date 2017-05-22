package co.edu.watcher.main;

import java.awt.EventQueue;

import javax.swing.table.DefaultTableModel;

import co.edu.watcher.gui.WatcherGUI;
import co.edu.watcher.utils.WatcherUtil;

/**
 * Launcher Class
 * 
 * @author Sebastian A. 2017
 */
public class Main {
	private static WatcherUtil util = new WatcherUtil();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						WatcherGUI frame = new WatcherGUI();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if (args[0].equals("-h")) {
			help();
		} else if (args[0].equals("-s")) {
			welcome();
			terminalScan(args);
		} else if (args[0].equals("-p")) {
			portScan(args);
		} else if (args[0].equals("-c")) {
			serviceScan(args);
		}

	}

	private static void serviceScan(String[] args) {
		util.terminalService(args[1]);

	}

	private static void portScan(String[] args) {
		util.ports(args[1], new DefaultTableModel(), true);

	}

	private static void terminalScan(String[] args) {
		int a=0;
		int b=0;
		if (args.length > 1) {
			a = Integer.parseInt(args[1]);
			b = Integer.parseInt(args[2]);
		} else {
			a=0;
			b=255;
		}
		util.findPrivateAddress();
		util.terminalScan(a, b);
	}

	private static void help() {
		System.out.println("Usage Network-Watcher.jar [-s a b] | [-p ip] | [-c ip]");
		System.out.println("Arguments:");
		System.out.println("-s \t Scan the lAN");
		System.out.println("-p \t Ports Scan");
		System.out.println("-c \t Services Scan");
	}

	private static void welcome() {
		System.out.println("#    # ###### ##### #    #  ####  #####  #    # \n"
				+ "##   # #        #   #    # #    # #    # #   #  \n"
				+ "# #  # #####    #   #    # #    # #    # ####   \n"
				+ "#  # # #        #   # ## # #    # #####  #  #   \n"
				+ "#   ## #        #   ##  ## #    # #   #  #   #  \n"
				+ "#    # ######   #   #    #  ####  #    # #    # \n"
				+ "                                                \n"
				+ "                                                \n"
				+ "#    #   ##   #####  ####  #    # ###### #####  \n"
				+ "#    #  #  #    #   #    # #    # #      #    # \n"
				+ "#    # #    #   #   #      ###### #####  #    # \n"
				+ "# ## # ######   #   #      #    # #      #####  \n"
				+ "##  ## #    #   #   #    # #    # #      #   #  \n"
				+ "#    # #    #   #    ####  #    # ###### #    # \n");
	}

}
