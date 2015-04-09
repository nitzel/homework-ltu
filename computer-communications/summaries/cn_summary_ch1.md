#Chapter 1 - Computer Networks and the Internet
Summary of Chapter 1 of Computer Networking - A Top Down Approach, 6th Edition

- Application Layer / Physical Layer
- Hosts/EndSystems
- Internets Edge (Connecting medias like copper&fiber&radio)

## network core
- packet switching
  - store and forward (packets buffered at routers)
  - queuing: router has an output buffer for each link
    - congestion -> queuing delays 
    - if buffer is full, packets get dropped: **packet loss**
  - forwarding tables
    - matches IP addresses to outbound links
    - just the directions (e.g. the 256 sub IPs and maybe one upper)
    - routing protocols: how to set forwarding tables
- circuit switching 
  - circuit link with
    - frequency-division multiplexing **FDM**
      - each slot continously 1/n of bandwidth
    - time-division multiplexing **TDM**
      - each slot for 1/n of time: full bandwidth
- circuit vs packet switching
  - circuit takes time to establish a connection, wastes capacities. but constant capacities and speeds
  - packet can take different routes/congestion -> different delays. but more efficient (3x)
    - easier to implement
  - `Generally speaking, people who do not like to hassle with restaurant reservations prefer packet switching to circuit switching`
- a network of networks
  - multi-homing: an ISP is connected to several higherTier-ISPs to prevent failure
  - network structure 5
    - Tier1 ISPs or Content Providers
      - IXPs(Internet Exchange Points)  / Regional ISPs / directions
        - Access ISPs
    - major content providers (like google) established their own networks to bypass the payment to higherTier IPSs and directly get to IXPs/Access ISPs

## delay loss and throughputs
- Overview of Delay in Packet-Switched Networks 
  - delay: `total nodal = nodal processing, queuing, transmission, propagation`
    - processing`<microsecs`: check for bit errors, examine header, choose link 
    - queuing`~micro-millisecs`
    - transmission`~micro-millisecs`: `L bits, R bits/sec, d_trans=L/R`
    - propagation`~millisecs`: `distance d, speed s~2e8-3e8m/sec, d_prop=d/s`
- Queuing Delay and Packet Loss
  - traffic intensity
    - `if La/R close to 1` Queue grows, `packet length L, packets/sec a, transmission rate R`
    - `if La/R > 1` queue grows to infinity
- End to End Delay
  - `d_e2e = N*(d_proc + d_trans + d_prop), N-1 Nodes between A and B`
  - traceroute `# name ip RTT1 RTT2 RTT3`
- Throughput in Computer Networks
  - **bottleneck link** (slowest link in connection `min{R_1,..,R_n} if alone`)

## Protocol Layers and Their Service Models
- Internet Protocol Stack
  - Application Layer `message`
    - HTTP, SMTP, FTP, ...
  - Transport Layer `segment`
    - UDP(user datagram), TCP(transmission control)
  - Network Layer `datagram`
    - IP, routing protocols,
    - gets direction by Transport Layer
  - Link Layer `frame`
    - Ethernet, WiFi, DOCSIS, PPP(Point2Point)
  - Physical Layer `individual bits`
    - different protocols depending on transmission medium (for twisted-pair copper wires, coax, single-mode fiber optics, ...)
- OSI (Open Systems Interconnections)
  - 7 Layers: Application, Presentation, Session, Transport, Network, Data Link, Physical
    - Presentation: interpret meaning of data (compression, encryption, description)
    - Session: delimiting and synchronization
    - Presentation and Session can be implemented in the Application Layer in the Internet Protocol Stack (where they are missing)
- Encapsulation
  - Layer pass their data down/up to other layers adding/removing **header fields** to the **payload fields** (packet from layer above)

# Networks under Attack
- bad stuff
  - virus: user interaction necessary
  - worms: no (explicit) user interaction necessary
  - (Distributed) Denial-of-Service, **DDoS**
    - Vulnerability (malformed messages)
    - Bandwidth flooding
    - Connection flooding (server has max. number of connections)
  -  **packet sniffers** (in WiFi or cellular internet, LAN(broadcast/in the middle))
  - **IP spoofing** (packet.from replaced by trustworthy address)
    - **end-point authentication**

# History of Computer Networking and the Internet
- Packet Switching 1961-72
  - ARPAnet 4-15 nodes (Advanced Research Projects Agency)
- Proprietary Networks and Internetworking 1972-80
  - other networks grew
  - **internetting**, a network of networks
  - IP, TCP, UDP
- A Proliferation of Networks 1980-90
  

  