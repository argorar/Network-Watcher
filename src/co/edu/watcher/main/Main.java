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
	 * @param args: Arguments 
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
		} else if (args[0].equals("-f")) {
			util.getInfo();
		}


	}

	/**
	 * Launch service scan
	 * @param args: Host
	 */
	private static void serviceScan(String[] args) {
		for (int puerto = util.START; puerto <= util.END; puerto++) {
			util.terminalService(args[1],puerto);
		}
	}

	/**
	 * Launch port scan
	 * @param args: Host
	 */
	private static void portScan(String[] args) {
		util.ports(args[1], new DefaultTableModel(), true);

	}

	/**
	 * Launch the scan of host
	 * @param args
	 */
	private static void terminalScan(String[] args) {
		util.findPrivateAddress();
		util.terminalScan();
	}

	/**
	 * Show the message of help
	 */
	private static void help() {
		System.out.println("Usage Network-Watcher.jar [-s ] | [-p ipHost] | [-c ipHost]");
		System.out.println("Arguments:");
		System.out.println("-s \t Scan the lAN");
		System.out.println("-p \t Ports Scan");
		System.out.println("-c \t Services Scan");
		System.out.println("-f \t Info network");
	}

	/**
	 * Message of welcome
	 */
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
