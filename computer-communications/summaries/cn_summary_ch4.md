#Chapter 4 - The Network Layer
Summary of Chapter 4 of Computer Networking - A Top Down Approach, 6th Edition

This is not complete, the second part will follow next week.
## Introduction
- Forwarding: From Input-Link to Output-Link in a single Router
- Routing: network wide process to find end2end route
  - Routing Algorithms
- Forwarding Table in every Router
  - Field in packet-header leads to interface that it is forwarded to
- Packet Switch = General packet-switching device, fwding from InLink to OutLink, regarding Header
  - Link-Layer Switches / Layer-2, regarding link-layer-frame 
  - Routers / Layer-3 "Switches", regarding network-layer fields
    - implementing L2 for L3 functionality
- connection setup
  - routers handshaking each other for ATM, frame relay, MPLS
- network service models
  - could guarantee
    - delivery
    - delivery with bounded delay
  - could provide: in order delivery, minimal bandwidth, maximum jitter, security, ...
  - **best effort service**
    - guarantees nothing :)
  - **CBR: Constant Bit Rate** ATM network service
  - **ABR: Available Bit Rate** ATM network service

## Virtual Ciruit and Datagram Networks
- Like TCP/UDP network-layer can also provide connectionless/connection service between two hosts
  - Connection only: **VC** virtual-circuit networks
  - Connectionless only: **datagram networks**
- **VC** networks, from telephony world, more complex (dumb hosts)
  - e.g. ATM, frame relay
  - setup, data transfer, teardown
  - signalin protocols
- **datagram** networks, new, simple(clever hosts)
  - the Internet is one
  - packet has an address&destination
    - prefix of address used for forwarding
      - several matches: **Longest prefix matching rule**

## What's inside a Router
- inside
  - Input ports : physical layer function (end of link)
  - switching fabric : connects input to output ports
  - Output ports : stores packets from switching fabric and transmit them (link- and physical layer functionality)
  - Routing processor, executes routing protocol, maintains routing/fwd tables etc
  - forwarding=switching function called **router forwarding plane**, **IN HARDWARE**, software would be too slow
  - **router control frame** in software on cpu
- input processing
- switching
  - via memory (by cpu)
  - via bus (accept by label)
  - via crossbar : 2N busses
- output processing
- where queuing occurs
  - Output port
  - HOL Head of the line blocking (Elements blocking other in queue, thouth they dont use their output ports)
  - AQM Active Queue Management algorithms
    - RED Random Early Detection (weighted avg for length of output queue)
- The Routing Control Plane
  - could partially be implemented in hardware and thus be faster (in research)
  
## The Internet Protocol (IP): Forwarding and Addressing in the Internet
- Datagram Format (IPv4) 20-24 Bytes
  - **Fields:** Version, Header-Len, Type of Service(TOS), Datagram len, Flags, Fragmentation, TTL, Upper Layer Protocol(~port), Checksum, Source IP, Dest IP, Options, Datagram
  - Fragmentation (IPv4 only), uses fragmentation and offset fields
    - MTU (max transmission unit) < MSS(max segm size) lets the IP Packet get split up into fragments. gets reassambled before transport layer at Host
  - Addressing
    - IP, Subnetmask 
    - Classless Interdomain Routing CIDR, "cider" a.b.c.d/x
    - Classfull Addressing (IP Addr has prefixes = Class A,B,C)
      - Waists lots of IPs
  - Obtain block of IPs from your ISP
  - DHCP Dynamic Host Configuration Protocol, a Plug-and-Play Protocol
    - assignes temporary IP address
    - can provide subnet mask, gateway, DNS-IP
    - DHCP Server Discovery: via special message, UDP to 255.255.255.255:67 from 0.0.0.0:68
    - DHCP Server Offer: offer message (also broadcast) with IP and lease-time
    - DHCP Request (I would like to have the offered IP)
    - DHCP Ack (Take it, man!)
- NAT Network Address Translation
  - LAN Lokal Area Networks can use the same addresses, covered behind one Address(Router). They are "translated" by NAT
  - These addresses are reserved for LAN usage only 
  - NAT translation table: External Port<->Internal Port:Internal IP for a connection. The external port is generated on demand (takes a free one) and replaces the internal IP:Port in the Packet Header (Sender IP:Port)
- UPnP Universal Plug and Play
  - to discover and configure nearby NATs
  - Software can configure the mapping of the NAT and get information about it. 
  - Now Software can tell others about its IP:Port (Torrent, ...)
- **ICMP** Internet Control MEssage Protocol
  - ping: Type8code0, PingEcho: Type0code0
  - TRACEROUTE
    - sends UDP Packets with TTL=1,2,...
    - The Router discover that the TTL expired, following IP they drop it and answer with a ICMP warning message (T11C0)
    - tracert chooses an unlikely port number, so the host will reply with ICMP Port unreachable (T3C3)

### IPv6
  - IPv4 was running out of addresses (last one 2011)
  - expanded addressing capabilities from `32` to `128 bits`
    - introducing **anycast-addr** (packet to any of a group of hosts)
    - `40 Bytes` Header const size for faster processings
    - flow labeling, priority
    - **no Fragmentation**/Reassembly at routers! must be done by SRC/DEST
    - No Header Checksum (is done by TCP/UDP and often Link-Layer), less redundancy
    - Options-Field dropped
  - Transition from IPv4 to v6
    - done gradually
    - **dual-stack**, IPv6 Nodes are backward-compatible and also deal with IPv4 packets
      - Alternative: Tunneling, Use IPv6 to send an IPv4 packet or vice versa
    - DNS returns v6IP if dest&sender can do it
- A brief foray into IP Security
  - VPN Virtual Private Networks
  - IPsec (backward compatbile to v4)
    - Connection oriented
    - SendSocket passes segments to IPsec, they get encrypted, sent, received, decrypted, passed to RecvSocket

## 4.5 Routing Algorithms 
This chapter will be done next week :)