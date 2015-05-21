#Chapter 6 - Wireless and mobile networks
Summary of Chapter 6 of Computer Networking - A Top Down Approach, 6th Edition

## Introduction
- wireless, mobile, and mobile&wireless
- IEEE 802.11 (WLAN) (unlicensed spectrum)
- 3G (expensive licenses)
- **wireless hosts** connect to the **base station** through **wireless communication links**
- **cell towers**/**access points** (cellular/WLAN)
- **ad hoc networks**
- types of infrastructure
  - single-hop, infrastructure-based
  - single-hop, infrastructure-less
  - multi-hop, infrastructure-based (wireless mesh networks)
  - multi-hop, infrastructure-less (MANETs, mobile ad hoc networks, VANET vehicular ANET)

## Wireless links and network characteristics
- disadvantages to wired
  - decreasing signal strength, path loss
  - interference from other sources
  - multipath propagation (reflection, material)
- **SNR** signal to noise ratio in **dB**, `20*log_10(amplitude)`, the greater, the better
- **BER** bit error rate, roughly anti-proportional to **SNR**, proportional to transmission rate
- **hidden terminal problem** (node blocked from one side)
- CDMA, coded: additive interference can be taken apart @receiver!

## WiFi 802.11 WLANs
- all 802.11 standards use CSMA/CA
  - b, 2.4-2.485GHz, 11Mbit/sec
  - a, 5.1-5.8GHz, 54Mbit/sec
  - g, 2.4-2.485GHz, 54Mbit/sec
  - n, Multiple input output antennas (MIMOs), X00 Mbit/sec
- 802.11 architecture
  - **BSS** basic service set
  - **AP** access point = base station
  - infrastructure WLANs: AP attached to LAN
  - Channels and association via **SSID** Service Set Identifier
  - **WiFi jungle** : spot where strong signals from more than 1 AP are received
  - **beacon frames** with SSID and MAC of AP
    - are received via **passive scanning**
    - **active scanning** asks for available APs

### 802.11 MAC Protocol
- CSMA/CA (collision avoidance)
- **link-layer acks**
- **SIF*S** Short Inter-frame Spacing (waiting for ack or resend)
- Distributed IFS
- hidden station problem, help:
  - **RTS** request to send frame
  - **CTS** clear to send frame
- 802.11 as Point to Point link

### IEEE 802.11 Frame
- Payload up to 2312 bytes, usually fewer than 1500bytes
- CRC fields
- 4x MAC Addresses
  - 2: MAC of sending station
  - 1: MAC of receiving station
  - 3: MAC of router

### Mobility in the same IP Subnet
- When you move within the same WLAN but connect to different stations
- Host can keep IP Address and ongoing connections
- AP switch by weakening/increasing signals
- the switch is self-learning, so it automatically adapts

### Advanced Featured in 802.11
- Rate Adaption
- Power Management

### Personal Area Networks: Bluetooth and Zigbee
- Bluetooth, IEEE 802.15.1
  - 2.4GHz band, TDM 625ms slots
  - **FHSS, frequency-hopping spread spectrum**, 79 Channels swapping after each timeslot in known but pseudo-random manner
- Zigbee IEEE 802.14.54Mbit/sec
  - less power consumption as Bluetooth
  - lower transmition rate thatn Bluetooth

## Cellular Internet Access
- maintain TCP sessions while on Bus/Train?
- **GSM** Global System for Mobile Communications (Groupe Special Mobile)
  - 1G, for Voice Traffic, analog FDMA
  - 2G, voice, digital
  - 2.5G, voice and data, digital, upgrade to 2G
  - 3G, voice and data(higher speed), digital
- **BSC** base station controller
- **paging** 
- **MSC** mobile switching center, for accounting (user allowed to use service)

### 3G Cellular Data Networks: Extending the Internet to Cellular Subscribers
- 3G Core
  - NodeTypes: **SGSNs**(serving gprs support), **GGSNs**(Gateway GPRS support)
    - **GPRS** Generalized Packet Radio Service
- Wireless Edge
  - **RNC** radio network controllers

### On to 4G: **LTE**
- **EPC** Evolved Packet Core
- LTE Radio Access Network, OFDM (orthogonal FDM, FDM on downstream)

## Mobility Management: Principles
- how mobile is a user?
  - as long as the same AP is used, not at all
  - by car, keeping connected
  - from one hotspot to the next, loosing connections
- keeping the IP the same important?
  - by car, keeping connections: very convenient
  - switching networks: usually not important

### Addressing
- foreign agent: **COA** care-of address
- Routing a mobile node
  - indirect routing approach: datagram to node's permanent address
    - may not be there, then the home agent forwards to foreign agent
    - triangle routing problem
  - direct routing approach
    - more complex
    - **mobile-user location protocol**

## Mobile IP, RFC5944 (IPv4)
- Agent discovery
  - agent advertisement
  - agent solicitation (ICMP+extension)
- Registration with home agent
- indirect routing of datagrams

## Managing Mobility in Cellular Networks
- **home PLMN** (public land mobile network)
- **HLR** home location register
- **GMSC** GAteway mobile services switiching center
- **VLR** visitor location register
- Routing calls to mobile users 
  - leading numbers of TelNo refer to mobile's home PLMN
  - **MSRN** mobile station roaming number
- handoffs in GSM
  - handoff: mobile station changes from one base station to another during a call
  - the old base station informs the MSC
  - MSC inits new path setup
  - informing mobile and connection establishment

## Wireless and Mobility: Impact on Higher-Layer Protocols
- TCP eg decreases congestion window if the packet got corrupted in Wireless link, thus leading to unnecessary decreased transmission rate
- approaches:
  - local recover
  - tcp sender awareness of wireless links 
  - split-connection approache: