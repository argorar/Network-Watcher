package co.edu.watcher.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

/**
 * Class of Utils
 * 
 * @author Sebastian A. 2017
 */
public class WatcherUtil {
	/**
	 * Global variables
	 */
	public final int START = 5;
	public final int END = 9000;
	public String privateIP;

	/**
	 * Method that list information about all the network interfaces
	 * 
	 * @return list: All the network interfaces
	 */
	public ArrayList<String> networkInterfaces() {
		ArrayList<String> list = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)) {
				if (!"lo".equals(netint.getDisplayName()))// ignore looback
					list.add(displayInterfaceInformation(netint));
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Method that get information about network interface
	 * 
	 * @param netint
	 *            : network interface
	 * @return info : information about network interface
	 * @throws SocketException
	 */
	public String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
		String info = "";
		info += netint.getName();// name
		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
		for (InetAddress inetAddress : Collections.list(inetAddresses)) {
			if (validateIPv4(inetAddress.getHostAddress()))
				info += inetAddress;
		}
		return info;
	}

	/**
	 * Method that find MAC and NIC
	 * 
	 * @param in
	 *            Object with the IP
	 * @return data Array with MAC and NIC
	 */
	public String[] findMAC(InetAddress in) {
		StringBuilder sb = new StringBuilder();
		String[] data = new String[2];
		NetworkInterface a;
		try {
			a = NetworkInterface.getByInetAddress(in);
			byte[] mac = a.getHardwareAddress();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			@SuppressWarnings("static-access")
			Enumeration<NetworkInterface> nets = NetworkInterface.getByInetAddress(in).getNetworkInterfaces();
			String ni = "";
			while (nets.hasMoreElements()) {
				NetworkInterface networkInterface = nets.nextElement();
				ni += networkInterface.getName();
				if (nets.hasMoreElements())
					ni += "-";
			}
			data[0] = "";
			data[1] = ni;
		} catch (Exception e) {
		}
		return data;
	}

	/**
	 * Method that scans ports and verifies their status
	 * 
	 * @param ipHost
	 *            ip of the host
	 * @param model
	 *            model of the table
	 * @param implementation
	 *            flag true if is terminal, false if is GUI
	 */
	public void ports(String ipHost, DefaultTableModel model, boolean implementation) {
		Socket p1;
		for (int puerto = 5; puerto < 9000; puerto++) {
			try {
				p1 = new Socket();
				p1.connect(new InetSocketAddress(ipHost, puerto), 1000);
				if (implementation)
					System.out.println(ipHost + ":" + puerto + " open");
				else {
					Object[] row = { ipHost + ":" + puerto, "open" };
					model.addRow(row);
				}
				p1.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Method that show in the terminal information about network
	 */
	public void getInfo() {
		NetworkInterface networkInterface;
		try {
			String ip = privateIP;
			InetAddress in = InetAddress.getByName(ip);
			networkInterface = NetworkInterface.getByInetAddress(in);
			int numBits = networkInterface.getInterfaceAddresses().get(1).getNetworkPrefixLength();
			String subnet = ip + "/" + String.valueOf(numBits);
			SubnetUtils utils = new SubnetUtils(subnet);
			SubnetInfo info = utils.getInfo();
			System.out.printf("Subnet Information for %s:\n", subnet);
			System.out.println("--------------------------------------");
			System.out.printf("IP Address:\t\t\t%s\t[%s]\n", info.getAddress(),
					Integer.toBinaryString(info.asInteger(info.getAddress())));
			System.out.printf("Netmask:\t\t\t%s\t[%s]\n", info.getNetmask(),
					Integer.toBinaryString(info.asInteger(info.getNetmask())));
			System.out.printf("CIDR Representation:\t\t%s\n\n", info.getCidrSignature());
			System.out.printf("Supplied IP Address:\t\t%s\n\n", info.getAddress());
			System.out.printf("Network Address:\t\t%s\t[%s]\n", info.getNetworkAddress(),
					Integer.toBinaryString(info.asInteger(info.getNetworkAddress())));
			System.out.printf("Broadcast Address:\t\t%s\t[%s]\n", info.getBroadcastAddress(),
					Integer.toBinaryString(info.asInteger(info.getBroadcastAddress())));
			System.out.printf("Low Address:\t\t\t%s\t[%s]\n", info.getLowAddress(),
					Integer.toBinaryString(info.asInteger(info.getLowAddress())));
			System.out.printf("High Address:\t\t\t%s\t[%s]\n", info.getHighAddress(),
					Integer.toBinaryString(info.asInteger(info.getHighAddress())));
			System.out.printf("Total usable addresses: \t%d\n", Long.valueOf(info.getAddressCountLong()));
		} catch (SocketException | UnknownHostException e) {
		}
	}

	/**
	 * Method that identifies available hosts
	 * 
	 * @return AddressList: Host availables in network
	 */
	public String[] getAddressList() {
		NetworkInterface networkInterface;
		try {
			String ip = privateIP;
			InetAddress in = InetAddress.getByName(ip);
			networkInterface = NetworkInterface.getByInetAddress(in);
			int numBits = networkInterface.getInterfaceAddresses().get(1).getNetworkPrefixLength();
			String subnet = ip + "/" + String.valueOf(numBits);
			SubnetUtils utils = new SubnetUtils(subnet);
			SubnetInfo info = utils.getInfo();
			return info.getAllAddresses();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Method that gets information about the network and concatenates it in a
	 * string
	 * 
	 * @return allInfo about the network
	 */
	public String getInfoString() {
		NetworkInterface networkInterface;
		String allInfo = null;
		try {
			String ip = privateIP;
			InetAddress in = InetAddress.getByName(ip);
			networkInterface = NetworkInterface.getByInetAddress(in);
			int numBits = networkInterface.getInterfaceAddresses().get(1).getNetworkPrefixLength();
			String subnet = ip + "/" + String.valueOf(numBits);
			SubnetUtils utils = new SubnetUtils(subnet);
			SubnetInfo info = utils.getInfo();
			allInfo = ("Subnet Information for : " + subnet + "\n");
			allInfo += ("--------------------------------------\n");
			allInfo += ("IP Address:\t\t\t" + info.getAddress() + "\n");
			allInfo += ("Netmask:\t\t\t" + info.getNetmask() + "\n");
			allInfo += ("CIDR Representation:\t\t" + info.getCidrSignature() + "\n");
			allInfo += ("Supplied IP Address:\t\t" + info.getAddress() + "\n");
			allInfo += ("Network Address:\t\t" + info.getNetworkAddress() + "\n");
			allInfo += ("Broadcast Address:\t\t" + info.getBroadcastAddress() + "\n");
			allInfo += ("Low Address:\t\t\t" + info.getLowAddress() + "\n");
			allInfo += ("High Address:\t\t\t" + info.getHighAddress() + "\n");
			allInfo += ("Total usable addresses: \t\t" + Long.valueOf(info.getAddressCountLong()) + "\n");
			return allInfo;
		} catch (SocketException | UnknownHostException e) {
		}
		return allInfo;
	}

	/**
	 * Method that get number of host
	 * 
	 * @param netmaskNumeric:
	 *            mask network
	 * @return number of hosts
	 */
	public int getNumberOfHosts(int netmaskNumeric) {
		Double x = Math.pow(2, (32 - netmaskNumeric));
		if (x == -1)
			x = 1D;
		return x.intValue() - 2;
	}

	/**
	 * Method that scan ip and get hostname, NIC and MAC
	 * 
	 * @param host
	 *            host
	 * @param implementation
	 *            flag to print or not
	 * @return Array with the values
	 */
	public Object[] scan(String host, boolean implementation) {
		try {
			InetAddress in = InetAddress.getByName(host);
			if (in.isReachable(1500)) {
				if (implementation) {
					System.out.println(in.getHostAddress() + " : " + in.getHostName());
					return null;
				} else {
					Object[] row = { in.getHostAddress(), in.getHostName() };
					return row;
				}
			}
			// else {
			// System.out.println(in.getHostAddress() + ": apagado");
			// }
		} catch (IOException e) {
			System.out.println("Error scan");
		}
		return null;
	}

	/**
	 * Method that scans ports and verifies their status
	 * 
	 * @param ipHost:
	 *            ip of the host
	 * @param model:
	 *            model of the table
	 * @param puerto:
	 *            Port
	 */
	public void service(String ipHost, int puerto, DefaultTableModel model) {

		if (ftp(ipHost, puerto)) {
			Object[] row = { "FTP", ipHost + ":" + puerto };
			model.addRow(row);
		} else if (http(ipHost, puerto)) {
			Object[] row = { "HTTP", ipHost + ":" + puerto };
			model.addRow(row);
		} else if (https(ipHost, puerto)) {
			Object[] row = { "HTTPS", ipHost + ":" + puerto };
			model.addRow(row);
		} else if (smtp(ipHost, puerto)) {
			Object[] row = { "SMTP", ipHost + ":" + puerto };
			model.addRow(row);
		}

	}

	/**
	 * Method that check the service http
	 * 
	 * @param ipHost:
	 *            Host
	 * @param puerto:
	 *            Port
	 * @return true if the service is on
	 */
	@SuppressWarnings("resource")
	public boolean http(String ipHost, int puerto) {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 80
			socket.connect(new InetSocketAddress(ipHost, puerto), 1000);
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			return false;
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				// define el protocolo de aplicacion - HTTP
				Writer writer = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				writer.write("GET / HTTP/1.1\n");

				writer.write("Accept-Encoding:gzip, deflate, br\n");
				writer.write("Connection: Keep-Alive\n");
				writer.write("Host: " + ipHost + "\n");
				writer.write(
						"User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:53.0) Gecko/20100101 Firefox/53.0\n\n");
				writer.flush();
				// Prepara la lectura de la pagina
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
				// Le cada linea del response
				String line = reader.readLine();
				if (line != null && line.contains("200"))
					return true;
				// cierra los canales
				os.close();
				is.close();
				socket.close();
			} catch (Exception e) {
				return false;
			}
		}
		return false;

	}

	/**
	 * Method that check the https service
	 * 
	 * @param ipHost:
	 *            Host
	 * @param puerto:
	 *            Port
	 * @return true if the service is on
	 */
	@SuppressWarnings("resource")
	public boolean https(String ipHost, int puerto) {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 443
			socket.connect(new InetSocketAddress(ipHost, puerto), 1000);
			// obtiene los canales
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			return false;
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				// define el protocolo de aplicacion - HTTP
				Writer writer = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				writer.write("GET / HTTP/1.1\n");
				writer.write("Host: " + ipHost + "\n");
				writer.write("Connection: Keep-Alive\n");
				writer.write("Schema: https");
				writer.write(
						"User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:53.0) Gecko/20100101 Firefox/53.0\n\n");
				writer.flush();
				// Prepara la lectura de la pagina
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
				// Le cada linea del response
				String line = reader.readLine();
				if (line != null && line.contains("HTTP"))
					return true;
				// cierra los canales
				os.close();
				is.close();
				socket.close();
			} catch (Exception e) {
				return false;
			}
		}
		return false;

	}

	/**
	 * Method that check the ftp service
	 * 
	 * @param ipHost:
	 *            Host
	 * @param puerto:
	 *            Port
	 * @return true of the service is on
	 */
	@SuppressWarnings("resource")
	public boolean ftp(String ipHost, int puerto) {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 21
			// socket = new Socket("ftp.upv.es", 21);
			socket.connect(new InetSocketAddress(ipHost, puerto), 1000);
			// obtiene los canales
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			return false;
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				// define el protocolo de aplicacion - HTTP
				Writer writer = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				writer.write("SSH-1.99-OpenSSH_2.9p1\n");
				writer.flush();
				// Prepara la lectura de la pagina
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
				// Le cada linea del response
				String line = reader.readLine();
				if (line != null && line.contains("FTP"))
					return true;
				// cierra los canales
				os.close();
				is.close();
				socket.close();
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Method that check the smtp service
	 * 
	 * @param ipHost:
	 *            Host
	 * @param puerto:
	 *            Port
	 * @return true if the service is on
	 */
	@SuppressWarnings("resource")
	public boolean smtp(String ipHost, int puerto) {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 21
			// socket = new Socket("smtp.gmail.com", 25);
			socket.connect(new InetSocketAddress(ipHost, puerto), 1000);
			// obtiene los canales
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			return false;
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				// define el protocolo de aplicacion - HTTP
				Writer writer = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				writer.write("SSH-1.99-OpenSSH_2.9p1\n");
				writer.flush();
				// Prepara la lectura de la pagina
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
				// Le cada linea del response
				String line = reader.readLine();
				if (line != null && (line.contains("SMTP") || line.contains("smtp")))
					return true;
				// cierra los canales
				os.close();
				is.close();
				socket.close();
			} catch (Exception e) {
				return false;
			}

		}
		return false;
	}

	/**
	 * MEthod that check the dns service
	 */
	public void dns() {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			socket.connect(new InetSocketAddress("8.8.4.4", 53), 1000);
			// obtiene los canales
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			System.out.println("error puerto 53");
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				Writer writer = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				writer.write("SSH-1.99-OpenSSH_2.9p1\n");
				writer.flush();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
				// Le cada linea del response
				String line = reader.readLine();
				while (line != null) {
					System.out.println(line);
					line = reader.readLine();
				}
				// cierra los canales
				os.close();
				is.close();
				socket.close();
			} catch (Exception e) {
				System.out.println("nodefine");
			}
		}
	}

	/**
	 * Method that starts scan of the host in the network on the terminal
	 */
	public void terminalScan() {
		String[] list = getAddressList();
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null)
				scan(list[i], true);
		}
		System.out.println("Scan Finish");
	}

	/**
	 * Method that check all the service in terminal mode
	 * 
	 * @param ipHost:
	 *            Host
	 * @param puerto:
	 *            Port
	 */
	public void terminalService(String ipHost, int puerto) {
		if (ftp(ipHost, puerto)) {
			System.out.println("FTP " + ipHost + ":" + puerto);
		} else if (http(ipHost, puerto)) {
			System.out.println("HTTP " + ipHost + ":" + puerto);
		} else if (https(ipHost, puerto)) {
			System.out.println("HTTPS " + ipHost + ":" + puerto);
		} else if (smtp(ipHost, puerto)) {
			System.out.println("SMTP " + ipHost + ":" + puerto);
		}

	}

	/**
	 * Method that validate if the ip is v4
	 * 
	 * @param ip
	 *            : Host
	 * @return true: if is v4
	 */
	public boolean validateIPv4(String ip) {
		Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		return PATTERN.matcher(ip).matches();
	}

	/**
	 * Method that set the private address
	 * @param privateIP : local host
	 */
	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
	}

	/**
	 * Method that get address of the local host
	 * @return privateAddress
	 */
	public String getPrivateIP() {
		return privateIP;
	}
}
