package co.edu.watcher.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Scanner;

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
	 * 
	 * @param args:
	 *            Arguments
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
			getInfo();			
		} else
			help();

	}

	/**
	 * Launch info of network interface	 
	 */
	private static void getInfo() {
		try {
			seletionNI();			
			util.getInfo();
		} catch (Exception e) {
			System.err.println("Please only numbers.");
		}
	}

	/**
	 * Launch service scan
	 * 
	 * @param args:
	 *            Host
	 */
	private static void serviceScan(String[] args) {
		if (util.validateIPv4(args[1])) {
			for (int puerto = util.START; puerto <= util.END; puerto++) {
				util.terminalService(args[1], puerto);
			}
		} else
			System.err.println("Please enter a valid ip.");
	}

	/**
	 * Launch port scan
	 * 
	 * @param args:
	 *            Host
	 */
	private static void portScan(String[] args) {
		if (util.validateIPv4(args[1])) {
			util.ports(args[1], new DefaultTableModel(), true);
		} else
			System.err.println("Please enter a valid ip.");
	}

	/**
	 * Launch the scan of host
	 * 
	 * @param args
	 */
	private static void terminalScan(String[] args) {
		try {
			seletionNI();
			System.out.println("Scanning ...");
			util.terminalScan();
		} catch (Exception e) {
			System.err.println("Please only numbers.");
		}
	}

	/**
	 * Show the message of help
	 */
	private static void help() {
		System.out.println("Usage Network-Watcher.jar [-s ] | [-p ipHost] | [-c ipHost] | [-f]");
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

	/**
	 * Method that show all network interface
	 */
	private static void seletionNI() {
		System.out.println("Please select a network interface.\n");
		ArrayList<String> stockList = util.networkInterfaces();
		for (int i = 0; i < stockList.size(); i++) {
			System.out.println("\t[" + i + "] \t" + stockList.get(i));
		}
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);		
		int NI = input.nextInt();
		String resp = stockList.get(NI);
		String[] array = resp.split("/");
		util.setPrivateIP(array[1]);

	}

}
