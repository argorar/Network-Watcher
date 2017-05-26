<img src="img/network.png" alt="Logo" align="right"/>

# Network Watcher

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7c70ceeb054a478ab6dc0ed8b3329b05)](https://www.codacy.com/app/argorar/Network-Watcher?utm_source=github.com&utm_medium=referral&utm_content=argorar/Network-Watcher&utm_campaign=badger)

Multiplatform application that scans the local network identifying host and which ports it has open, also verifies services such as HTTP, HTTPS, SMTP and FTP.

## Info about Host
From the host you get the following information:

 * IP Address
 * Device Name
 * Network Adapters

## Supported Network Interface
```
* eth  [0-9]
* wlo  [0-9]
* wlan [0-9]
* enp4s[0-9]

```

> **Note:**
>
>   * To add new network interface edit tellMyIP method in WatcherUtil class.
>     ```
>     if (Pattern.matches("eth[0-9]", ethr) || Pattern.matches("wlo[0-9]", ethr)
>	    || Pattern.matches("wlan[0-9]", ethr)|| Pattern.matches("enp4s[0-9]", ethr)) {
>     ```

## Ports
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


## Graphic Interface
The graphical interface has options at the top.

![gui](img/gui.png)

## Terminal Mode
You can also run the application by the command line as shown in the image below.

![terminal](img/terminal.png)

## Javadoc
You can check the javadoc [here](doc/index.html).

## License
[GPL v3](LICENSE).
> **Note:**
>
>   * [Author icon](https://www.iconfinder.com/webhostingmedia).
