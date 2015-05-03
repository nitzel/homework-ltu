#Chapter 4 - The Network Layer
Summary of Chapter 4 of Computer Networking - A Top Down Approach, 6th Edition

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

## Routing Algorithms 
- **default router** for host is it's **first-hop**
- goal: route between hosts **first-hop** and target's = **destination router**
- Network from Company Z may block Packets from Company Ys network
- **global routing algorithm** has complete info about connectivity and link costs
  - often referred to as **link state algorithsm**, because they need to know about the links state
- **decentralized routing algorithm**, in the beginning: knows costs to neighbors only
  - **distance vector algorithm**, keeping some estimated costs
- **static routing algorithms**: paths change slowly, e.g. after manual change of routing table
- **dynamic routing algorithms**: paths change depending on link-states/costs. can have problems with routing loops
- **load sensitive algorithms**: link costs reflect congestion
  - encounter several problems, so todays algorithms are **load INsensitive**

### Algorithms
- **LS** Link-State Routing Algorithm, uses global information
  - **Dijkstra's Algorithm** in `n*(n+1)/2 e O(n^2)`
  - Oscillations: using Path A, detecting better Path B. Now Path A is better, will be used in the next step. Now Path B is better, ...
    - can be avoided by having different (randomized) update times for each router
- **DV** Distance Vector Routing Algorithm, iterative, async, distributed
  - calculates and distributes the information
  - stops on its own
  - Bellman-Ford equation `d_x(y) = min_v{c(x,v) + d_v(y)}`
  - needs to know the next hop router on the shortest path for targets
  - examples: RIP, BGP, ISO IDRP, Novell IPX, ARPAnet
  - Link-Cost Changes & Link Failure
    - lower costs: informs neighbors if that changes the lowest cost path
    - higher costs: can induce routing loops, needs a long time to be fixed **BAD**
      - called **count to infinity problem**
      - adding poisoned reverse
        - A sends packet back to B, adding the information that A cant forward to target
        - B sends around, and claims, A has no connection to the target
        - later A sets it to a lower value
        -  does **not** solve the **count to infinity** problem completely, only for 2 adjacent nodes
- compare LS and DV
  - both are used, both have (dis)advantages
- other algorithms
  - circuit switching, ...

### Hierarchical Routing
- The internet is more complicated: in Scale, administrative autonomy(companies prefer their packets)
  - **AS** autonomous systems: several connected routers run all the same routing algorithm, controlled by one admin/ISP
  - intra autonomous system routing protocol
  - ASs are connected, some have to deal with destinations "outside": **gateway routers**
    - routers inside an AS need to learn which Gateway to forward to, depending on address
  - **hot potato routing** like the party game xD

## Routing int the Internet
- Intra-AS Routing (**interiour gateway protocols**)
  - **RIP** Routing Information Protocol (a Distance Vector Protocol)
    - uses `UDP Port 520`
    - updates every ~30sec via **RIP response messages/advertisements**
    - maintains a RIP/Routing table
      - `Destination Subnet, Next Router, Hops to Destination` 
    - neighbor considered dead after approx 180sec
  - **OSPF** Open Shortest Path First, successor to **RIP**
    - security: authentication, only trusted routers can take part in routing
    - multiple same cost paths
    - integrated support for: unicast, multicast
    - hierarchy in AS
    - **backbone**
- Inter AS Routing: **BGP** Border Gateway Protocol ("is extremely complex")
  - Tasks
    - obtain subnet reachability information from neighboring ASs
    - propagate the reachability to other routers inside the as
    - find **good** routs to subnets 
  - BGP Peers
  - **eBGP**/**iBGP** external/internal BGP session
  - **ASN** autonomous system number
  - uses routing policies!
  
### Broadcast and Multicast Routing
- **unicast**: src to one node
- **broadcast**: src to all nodes in network
  - as **N-way-unicast** (one packet to each) implementation simple but very inefficient
    - and not all receivers may be known to the sender
    - broadcast can be used to find unicast routes. when we use unicast for that ... well
  - as **uncontrolled flooding**
    - send to all neighbors, they send it to all neighbors, ... -> **broadcast storm** rendering the network useless
  - as **controlled flodding**
    - sequence number controlled flooding:
      - on receival: check if broadcast-sequencenumber is in list, if not: forward it to neighbors (except sender)
    - **RPF reverse path forwarding**
      - packets that dont come on the shortest way from the Source are dropped
  - Spanning Tree Broadcast
- Broadcast Algorithms in Practice
  - e.g. to broadcast Link-State Advertisments, service discovery, ...
- **multicast**: src to subset of network nodes
  - **IGMP** Internet Group Management Protocol, manages multicast groups
    - between Host and Router
  - via group shared tree
  - via source based tree
  - **DVMRP** Distance-Vector Multicast Routing Protocol
  - **PIM** Protocol Independent Multicast routing protocol, most widely used
  - **SSM** source specific multicast
