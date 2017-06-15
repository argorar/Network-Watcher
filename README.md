<img src="img/network.png" alt="Logo" align="right"/>

# Network Watcher

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7c70ceeb054a478ab6dc0ed8b3329b05)](https://www.codacy.com/app/argorar/Network-Watcher?utm_source=github.com&utm_medium=referral&utm_content=argorar/Network-Watcher&utm_campaign=badger)

Multiplatform application that scans the local network identifying host and which ports it has open, also verifies services such as HTTP, SMTP and FTP.

## Select Network Interface
Now you can select the network Interface when starting the application and change when you need it from the file | switchNi menu.

![ni](img/ni.png)

## Graphic Interface
The graphical interface has options at the top.

![gui](img/gui.png)

## Subnet Information
You can see the subnet information in the file|About menu.

![about](img/about.png)

## Port Analysis
You can see all the ports open.

![about](img/ports.png)

## Service Analysis
You can check the port for services such as HTTP, SMTP and FTP. It does not matter if they are in ports other than the default..

![about](img/services.png)

## Terminal Mode
Below you will find sample images of the operation by means of the terminal.

![help](img/help.png)

### Scan

![scant](img/scant.png)

### Services Scan

![servicesT](img/servicesT.png)

### Ports Scan

![portsT](img/portsT.png)

### Info Network

![networkt](img/networkt.png)

## Summary Ports
Below is a list of the most common ports with a brief description.

| Port | Name | Info |
| ------ | ------ | ------ |
| 20 | FTP Data | File Transfer Protocol (FTP) data transfer |
| 21 | FTP | File Transfer Protocol (FTP) control (command) |
| 22 | SSH | Secure Shell (SSH), secure logins, file transfers (scp, sftp) and port forwarding |
| 23 | Telnet | Telnet protocol—unencrypted text communications |
| 25 | SMTP | Simple Mail Transfer Protocol, used for email routing between mail servers |
| 53 | DNS | Domain Name System (DNS) |
| 59 | DCC | Direct Client-to-Client IRC software |
| 79 | Finger | Info about users |
| 80 | HTTP | Hypertext Transfer Protocol  |
| 110 | POP3 | Post Office Protocol, version 3 |
| 113 | IDENT | Ident, authentication service/identification protocol, used by IRC servers to identify users |
| 119 | NNTP | Network News Transfer Protocol, retrieval of newsgroup messages |
| 135 | NetBIOS | Service used to remotely manage services including DHCP server, DNS server and WINS |
| 139 | NETBIOS | NetBIOS Session Service |
| 143 | IMAP | Internet Message Access Protocol, management of electronic mail messages on a serve |
| 389 | LDAP | Lightweight Directory Access Protocol |
| 443 | HTTPS | Hypertext Transfer Protocol over TLS/SSL |
| 445 | MSFT DS | Microsoft-DS Active Directory, Windows shares |
| 563 | POP3 SSL | NNTP over TLS/SSL  |
| 993 | IMAP4 SSL | Internet Message Access Protocol over TLS/SSL |
| 995 | POP3 SSL | Post Office Protocol 3 over TLS/SSL |
| 1080 | Proxy | SOCKS proxy |
| 1723 | PPTP | Point-to-Point Tunneling Protocol |
| 3306 | MySQL | MySQL database system |
| 5000 | UPnP | Universal Plug'n'Play |
| 8080 | Proxy Web | Alternative port for HTTP |




## Javadoc
You can check the javadoc [here](doc/index.html).

## License
[GPL v3](LICENSE).
> **Note:**
>
>   * [Author icon](https://www.iconfinder.com/webhostingmedia).
