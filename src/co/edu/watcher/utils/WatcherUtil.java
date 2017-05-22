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
import java.util.Arrays;
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
	public String privateIP;

	/**
	 * Method that find MAC and NIC
	 * 
	 * @param in
	 *            Object with the IP
	 * @return data Array with MAC and NIC
	 */
	public String[] findMAC(InetAddress in) {
		// StringBuilder sb = new StringBuilder();
		String[] data = new String[2];
		// NetworkInterface a;
		try {
			// a = NetworkInterface.getByInetAddress(in);
			// byte[] mac = a.getHardwareAddress();
			// for (int i = 0; i < mac.length; i++) {
			// sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ?
			// "-" : ""));
			// }
			@SuppressWarnings("static-access")
			Enumeration<NetworkInterface> nets = NetworkInterface.getByInetAddress(in).getNetworkInterfaces();
			String ni = "";
			while (nets.hasMoreElements()) {
				NetworkInterface networkInterface = nets.nextElement();
				ni += networkInterface.getDisplayName() + "-";
			}
			// System.out.println(nets.getName());
			// System.out.println(nets.getDisplayName());
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
		// int[] puertos = { 20, 21, 22, 23, 25, 53, 59, 79, 80, 110, 113, 119,
		// 135, 139, 143, 389, 443, 445, 563, 993,
		// 995, 1080, 1723, 3306, 5000, 8080 };
		Socket p1;
		// for (int puerto : puertos) {
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
				// Object[] row = { ipHost + ":" + puerto, "closed" };
				// model.addRow(row);
			}
		}
	}

	/**
	 * Method that find private IP address not cares operative system
	 * 
	 * @return myip private myip address
	 */
	public String tellMyIP() {
		NetworkInterface iface = null;
		String ethr;
		String myip = "";
		String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		try {
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces
					.hasMoreElements();) {
				iface = (NetworkInterface) ifaces.nextElement();
				ethr = iface.getDisplayName();
				// Ethernet or Wifi
				if (Pattern.matches("eth[0-9]", ethr) || Pattern.matches("wlo[0-9]", ethr)
						|| Pattern.matches("wlan[0-9]", ethr) || Pattern.matches("enp4s[0-9]", ethr)) {
					// System.out.println("Interface:" + ethr);
					InetAddress ia = null;
					for (Enumeration<InetAddress> ips = iface.getInetAddresses(); ips.hasMoreElements();) {
						ia = (InetAddress) ips.nextElement();
						if (Pattern.matches(regex, ia.getCanonicalHostName())) {
							myip = ia.getCanonicalHostName();
							return myip;
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return myip;
	}
	
	public void getInfo() {		
		NetworkInterface networkInterface;
		try {
			String ip=tellMyIP();
			InetAddress in = InetAddress.getByName(ip);
			networkInterface = NetworkInterface.getByInetAddress(in);	
			int numBits=networkInterface.getInterfaceAddresses().get(1).getNetworkPrefixLength();
			System.out.println(numBits);
			String subnet = ip+"/"+String.valueOf(numBits);
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
	        System.out.printf("Address List: %s\n\n", Arrays.toString(info.getAllAddresses()));
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
//	public String getNetwork(String maskNetwork,String ip){
//		String[] mask      = "255.192.0.0".split("\\.");
//		String[] ipAddress = ip.split("\\.");
//		StringBuffer ipSubnet  = new StringBuffer();
//		for(int i=0; i<4; i++)
//		    try{
//		        if(ipSubnet.length()>0)
//		            ipSubnet.append('.');
//		        ipSubnet.append(Integer.parseInt(ipAddress[i]) & Integer.parseInt(mask[i]));
//		    }catch(Exception x){ 
//		        //Integer parsing exception, wrong ipaddress or mask
//		        break;
//		    }
//		System.out.println(ipSubnet);
//		return ipSubnet.toString();
//	}
	
	public int getNumberOfHosts(int netmaskNumeric) {     
        Double x = Math.pow(2, (32 - netmaskNumeric));
        if (x == -1)
            x = 1D;
        return x.intValue()-2;
    }

//	 private void convertNumericIpToSymbolic(Integer host) {
//	        StringBuffer sb = new StringBuffer(15);
//
//	        for (int shift = 24; shift > 0; shift -= 8) {
//
//	            // process 3 bytes, from high order byte down.
//	            sb.append(Integer.toString((host >>> shift) & 0xff));
//
//	            sb.append('.');
//	        }
//	        sb.append(Integer.toString(host & 0xff));
//
//	        System.out.println(sb.toString());
//	    }

	/**
	 * Method that set the private address
	 */
	public void findPrivateAddress() {
		String[] array = tellMyIP().split("\\.");
		privateIP = array[0] + "." + array[1] + "." + array[2] + ".";
		// System.out.println(privateIP);
	}

	/**
	 * Method that scan ip and get hostname, NIC and MAC
	 * 
	 * @param x
	 *            host
	 * @param implementation
	 *            flag to print or not
	 * @return Array with the values
	 */
	public Object[] scan(String x, boolean implementation) {
		try {
			InetAddress in = InetAddress.getByName(privateIP + x);
			if (in.isReachable(1500)) {
				String[] dataNIC = findMAC(in);
				if (implementation) {
					System.out.println(in.getHostAddress() + " : " + in.getHostName() + " NI: " + dataNIC[1]);
					return null;
				} else {
					Object[] row = { in.getHostAddress(), in.getHostName(), dataNIC[1] };
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
	 * @param ipHost
	 *            ip of the host
	 * @param model
	 *            model of the table
	 * @param implementation
	 */
	public void service(String ipHost, DefaultTableModel model, boolean implementation) {
		// for (int puerto : puertos) {
		for (int puerto = 1; puerto < 81; puerto++) {
			if (ftp(ipHost, puerto)) {
				Object[] row = { "FTP", ipHost+":"+puerto };
				model.addRow(row);
			} else {
				Object[] row = { "FTP", "not Found" };
				model.addRow(row);
			}
				
			if (http(ipHost, puerto)) {
				Object[] row = { "HTTP", ipHost+":"+puerto };
				model.addRow(row);
			} else{
				Object[] row = { "HTTP", "not Found" };
				model.addRow(row);
			}
		}
	}

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
				if (line != null) {
					if (line.contains("200"))
						return true;
				}
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

	public void https() {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 443
			socket.connect(new InetSocketAddress("www.upv.es", 443), 1000);
			// obtiene los canales
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			System.out.println("error puerto 443");
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				// define el protocolo de aplicacion - HTTP
				Writer writer = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				writer.write("GET / HTTP/1.1\n");
				writer.write("Host: " + "www.upv.es" + "\n");
				writer.write("Connection: Keep-Alive\n");
				writer.write("Schema: https");
				writer.write(
						"User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:53.0) Gecko/20100101 Firefox/53.0\n\n");
				writer.flush();
				// Prepara la lectura de la pagina
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
				if (line != null) {
					if (line.contains("FTP"))
						return true;
				}
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

	@SuppressWarnings("resource")
	public boolean smtp() {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 21
			// socket = new Socket("ftp.upv.es", 21);
			socket.connect(new InetSocketAddress("smtp.gmail.com", 25), 1000);
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
				if (line != null) {
					if (line.contains("SMTP") || line.contains("smtp"))
						return true;
				}
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

	public void dns() {
		Socket socket = new Socket();
		OutputStream os = null;
		InputStream is = null;
		try {
			// Crea socket con host y puerto 21
			socket.connect(new InetSocketAddress("201.236.236.186", 53), 1000);
			// obtiene los canales
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (Exception e) {
			System.out.println("error puerto 25");
		}
		// mira se fue creado el socket
		if (socket != null && os != null && is != null) {
			try {
				// define el protocolo de aplicacion - HTTP
				// Writer writer = new
				// OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
				// writer.write("SSH-1.99-OpenSSH_2.9p1\n");
				// writer.flush();
				// Prepara la lectura de la pagina
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
	 * 
	 * @param a
	 *            : host start
	 * @param b
	 *            : host end
	 */
	public void terminalScan(int a, int b) {
		for (int i = a; i < b; i++) {
			String x = String.valueOf(i);
			scan(x, true);
		}
		System.out.println("Scan Finish");
	}

	public void terminalService(String ip) {
		service(ip, new DefaultTableModel(), true);

	}
}
