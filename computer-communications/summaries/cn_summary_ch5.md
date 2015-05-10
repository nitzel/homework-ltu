#Chapter 5 - The Link Layer
Summary of Chapter 5 of Computer Networking - A Top Down Approach, 6th Edition

## Introduction
- PPP Point to Point Protocol
- **node** any device at link layer
- **links** communication channels
- **link-layer frame** encapsulated datagram
- Services 
  - Framing: surrounding like header
  - link access: rules by MAC
  - reliable delivery: unnecessary for low-bit-error links
  - error detection (& correction)
- implementation mostly in the 
  - **network adapter** aka **NIC** network interface card

## Error Detection and Correction Techniques
- even with err-detect we can have **undetected bit errors**
  - the more sophisticated, the more data&calc overhead
- parity checks
  - parity bit `= even amount of 1 ? 0 : 1`
  - realize error
- 2D parity checks
  - **FEC** forward error correction
- Checksumming Methods
  - Internet Checksum
- CRC Cyclic Redundancy Check 
  - based on CRC Codes / polynomial codes
  - interpret data as coefficients to a polynom
  - XOR CRC Code

## Multiple Acces Links and Protocols
- **broadcast link** (contrary to P2P links)
- **multiple access protocols** solving **multiple access problem**, eg
  - channel partitioning
    - TDM or FDM (time/frequ div multiplexing)
    - CDMA code division multiple access
  - random access
    - (slotted) ALOHA
    - **CSMA**(Carrier sense multiple access)**/CD** collision detection
      - efficiency `= 1/(1+5d_prop / d_trans)`
  - taking-turns
    - **polling protocol**, master node polls each node in cyclic manner
    - **token passing protocol**, "with token you may speak"
- **DOCSIS** for Cable Internet, **Data-Over-Cable Service Inteface Specification**
  - FDM, TDM, random access, central allocated time slots: all used

## Switched LANs
- Link-Layer Addressing and ARP
  - **MAC/Link-Layer/Physical Address** (all the same, medium access controll)
    - `6` bytes, `48` bits
    - MAC Broadcast (to `FF-FF-FF-ff-ff-ff`)
  - **ARP** Address Resolution Protocol
    - ARP table maps IP to MAC addrs, including TTL after which entries expire
- Ethernet
  - switches (implementing ARP)
  - frame-structure
    - Data, DestMAC, SourceMAC, Type, CRC, Preamble
  - technologies: 10BASE-T, 10BASE-2, 100BASE-T, 1000BASE-LX, 10GBASE-T; 100BASE-FX, 100BASE-SX, 10BASE-BX
    - Mbit/sec, BASE=baseband ethernet, T=Twisted pair copper wires, -2/-5: Coax
    - Coax length limit `500m`, need for **repeaters**
    - 100mbps over T limited to `100m`, over fiber several `km`
    - full duplex :)  
- Link Layer Switches, **plug and play devices**
  - **filtering**, decide to drop/forward frames
  - **forwarding**, by **switch table**
  - self-learning: switch-table empty, fills by connected computers, refreshes by time
  - properties:
    - eliminations of collisions: no hubs/ not several computers on one line
    - heterogeneous links: all links separated: different link speeds/medias allowed
    - management: can disconnect malfunctioning adapters etc
  - switches vs routers
    - SW+ Plug and play
    - SW+ high filtering&forwarding capacities
    - SW- limited to spanning tree
    - SW- large ARP tables would slow down
    - SW- broadcast storms/loops
    - R+ limited cycling
    - R+ no spanning tree restriction
    - R+ Layer2 firewall against eg Broadcast storms
    - R- not plug and play

### VLANs (virtual LANs)
- to provide traffic isolation, makes switches also more efficient in use
- managing users and their access to groups (really hard without vlans)
- **VLAN trunking** 
  - Switches have a special port which is used to forward everything and connected to other switches
- **VLAN tag** added to header makes makes switch distinguish the targeted LAN / which ports are allowed to get it
  - at the ports the usually is removed/added, so that the clients get normal data but senders are distinguished correctly
- Link Virtualization: A Network as a Link Layer
  - **MPLS** multiprotocol label switching
  - **label switched router** - MPLS enabled router

## Data Center Networking
- data center network: interconnects hosts in center and data center with internet
- **blades**: other name for hosts, because of pizza-box-form
- **TOR** top of rack switch
- **border routers**: between datacenter and internet
- **load balancing**
  - **load balancer** distributes requests to hosts depending on their load
    - layer-4 switch can load-balance different applications to different servers (cloud apps)
- Trends
  - fully connected topology: every tier1 switch connects to every tier2 switch
