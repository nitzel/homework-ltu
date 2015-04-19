Chapter 3 completion: 1/2, will be completed next week
#Chapter 3 - Transport Layer
Summary of Chapter 3 of Computer Networking - A Top Down Approach, 6th Edition

## Introduction and Transport-Layer Services
- routers act only on network-layer fields, not on transport!
- relationship between transport and network layers
  - Transport between Application and Network Layer
  - Transport provides logical communication between processes
  - Network provides logical communication between hosts
  - Services by Transport sometimes depend on Network
- Overview of the Transport Layer in the Internet
  - Network Protocol: IP
     - logical communication between hosts
     - **best-effort delivery service**
     - **unreliable** no guarantees
  - UDP provides
     - process2process
     - error checking
  - TCP provides
     - reliable data transfer
     - flow control, sequence numbers, acks, timers, congestion control

## Multiplexing / Demultiplexing
- 16bit Port (source and destination)
- Port 0-1023 are **well-known** and resevered
- (De)multiplexing Host<->Sockets
- Port-Scanning

## Connectionless Transport UDP
- Takes Message, attaches source/destination port and 2other fields, forwards to IP
- connectionless (not handshaking)
- example: DNS
- Pro UPD over TCP:
  - Finer application-level control about what/when data is sent
    - no throttling
    - no automatic resend
    - TCP-Services can be implemented on top of UDP
  - no connection established (faster)
  - no connection state (support for more active clients)
  - smaller packet header overhead: 8bytes vs 20bytes
- UDP segment structure
  - source port, destination port, length, checksum, app-data
- UDP Checksum
  - on end-on-end basis
  - inverted sum of all 16bit words(overflow wrapover)
  - packets with errors may be discarded or given to the application with a warning

## Principles of reliable Data Transfer
- reliability on top of unreliable layers (IP,...)
- Building a reliable data transfer protocol
  1. over a perfectly reliable channel
  2. over a channel with bit errors (ARQ and Stop&Wait Protocol)
    - understood? OK : ARQ (Automatic Repeat reQuest)
    - Error detection (Checksum)
    - Receiver Feedback (Acknowledgments 1/0=ACK/NAK)
    - Retransmissions
    - !! Acks can be corrupted, too !!
      - Adding a **sequence number** for each packet
  3. over lossy channel with bit errors
    - how to detect packet loss?
       - wait for a ACK until loss is likely, then resend
          - may have duplicate packets (sequence numbers deal with that)
       - needs a timer (start on send, respond to timer interrupt, stop timer on ACK receive)
    - sequence numbers alternate between 0,1 -> **alternating-bit protocol**

### pipelined reliable data transfer protocols
- the last protocol was functionally correct, but with a bad performance  
  - it was a **stop and wait** protocol
- **pipelining**
  - greater sequence number range
    - determined by the use of **Go-Back-N** or **selective repeat** 
  - buffering sent/received packets at sender/receiver to wait for acks or ordering
- **Go-Back-N** (GBN Protocol)
  - sender transmits multiple packets without waiting for acks
    - maximal _N_ unacknowledged packets
  - `N=Window Size`, GBN is a **sliding window protocol**
  - Sender Events
    - **send** (invocation from application)
    - receipt of `Ack n` (all packets up to `n` are correctly received) 
    - timeouts
  - Receiver Events
    - On receive (uses cumulative acks)
      - `if packet is in order` ACK for this packet
      - `else` discard and ACK for last correctly received packet

### Selective Repeat (SR)
- GBN can unnecessarily flood the line if the window size is large and only few packets were lost
- to avoid that, we can ACK certain packets and only resend packets without ACKS instead of the whole window.
- Works like **GBN**
  - but with a window on the receiver
  - ACKs for received packets in window
  - retransmit only for packets without ACK (instead of the whole window)
- `windowSize <= sequenceNumberSpace/2` or retransmissions and new data may be confused
- maximal lifetime in networks `~3min` so that old packets that arrive very late wont collide with new information

## Connection Oriented Transport: TCP
- The TCP Connection
  - TCP is **connection-oriented** (handshake, state variables)
  - connection is known only to the end hosts
  - **full duplex** (transfer in both directions)
  - point-to-point (multicasting not possible with TCP)
  - **3-way-handshake** (forth, back, forth again (last may carry data/payload))
  - `send -> data to buffer -> sending data from the buffer at own convenience -> others host receive buffer -> receive`
  - **MSS** maximum segment size (maximal app-data, headersize must not be included)
    - usually `= MTU` (maximum transmission unit=largest link-layer frame) (mind tcp header!)
    - Ethernet/PPP: `MTU = 1500 bytes`
- TCP Segment Structure 20 bytes
  - source&destination port numbers
  - checksum
  - sequence number
    - tcp enumerates the bytes in the bytestream (of first in segment) and not the segments
    - starts at random number to minimize effect of still-in-network packets received late
  - acknowledgment number
    - the next byte the host is expecting to receive (received till byte `500`, will send `501`)
    - cumulative acks (acknowledges several packets at once)
  - receive window,
  - header length field
  - options (optionally)
  - flag field 6 Bits(ACK, RST, SYN, FIN, PSH, URG)
  - urgent data pointer (last byte of urgent data)
- acks can be **piggybacked** on top of data segments 
- there is no direct NAK (but indicated by doubled, for sure with tripled ACKS for the same sequence number -> **fast retransmit**)

### RTT Estimation and Timeout
- RTT Estimated is averaged over samples `(1-a) * EstimatedRTT+a*SampleRTT`, recommanded `a=1/8` (**EWMA**exponential weighted moving average)
  - SampleRTT: delta `t_sent, t_ack`
  - Sampling RTT only for one segment at a time
    - -> roughly every RTT
  - not sampling retransmitted segments
- RTT variability `DevRTT = (1-b) * DevRTT + b*|SampleRTT - EstimatedRTT|`, recommanded `b=1/4` (GER: gemittelte Abweichung)
- RTT Timeout Interval `= EstimatedRTT + 4 * DevRTT`, recommended initial value `1 sec`
  - on timeout, TimeoutInterval is **doubled** (kind of a congestion control: congestion->timeouts->resent data(doublicates)->more congestion... so double the timeout)

### Reliable Data Transfer
- Timer
  - only one timer (because of severe overhead of multiple timers)
  - timer is for the oldest unacknowledged segment
- Fast Retransmit
  - 3 ACKS for the same sequence number
- GoBackN or SelectiveRepeat?
  - TCP is **not** GBN, but rather some kind hybrid **GBN+SR**

### Flow Control
- Match senders output to the rate the receiver processes the data
- receive window `rwnd` `~ how much free buffer space @receiver`
  - `rwnd` is send with every segment
  - if `rwnd=0` the sender continues with 1byte segments to get informed when there is free space in the buffer again
  
### TCP Connection Management
- Handshake
  1. Client sends `SYN-Segment`: SYN-Flag set to `1`
  2. Server receives SYN Segment, allocates buffer+vars, sends `SYNACK-Segment`
  3. Client receives SYNACK, allocates buffer+vars, sends another segment (maybe with payload)
- Client States: 
  1. `CLOSED`
  2. `SYN_SENT`(SYN-segm sent) 
  3. `ESTABLISHED`(SYNACK received) 
  4. `FIN_WAIT_1` (FIN sent)
  5. `FIN_WAIT_2` (FINACK received, waiting for FIN from server)
  6. `TIME_WAIT` (FIN received, ACK sent, can resend ACK)
- Server States;
  1. `CLOSED`
  2. `LISTEN` (waiting for SYN segments)
  3. `SYN_RCVD` (SYN received, send SYNACK) 
  4. `ESTABLISHED` (ACK received)
  5. `CLOSE_WAIT` (FIN received, FINACK sent)
  6. `LAST_ACK` (FIN sent, waiting for FINACK)
- request for unused ports: server answers with `RST-Flag` set (reset)
  - UDP: special ICMP (ping) packet
  - **nmap** on receival of `SYNACK`:open, `RST`:closed, nothing:blocked by firewall
  
### SYN Flood Attack
- a lot of handshakes, missing the 3rd step let the server allocate its resources but never use them for fake clients
  - real clients cannot connect because the server has no resources for them
- Defense:
  - SYN Cookies. No resource-alloc before complete handshake. The sequence ID from the server includes a cookie (client ip/port/secret number). on receival of an ack for a cookie, a connection will be established and resources allocated
  
## Principles of Congestion Control
- **ABR** available bit-rate
- **ATM** asynchronous transfer mode
- Causes and Costs of Congestion
  - throughput near `R` is good for throughput but bad for delays
- Approaches to Congestion Control
  - End-To-End
  - Network-assisted (routers provide explicit feedback to the sender regarding congestion state e.g. fast via **choke packets** (i am congested) or slow via a field in the header)
- Network-Assisted Congestion-Control Example: ATM ABR Congestion Control
  - Virtual Circuits (routers keep track/state of connections)
  - **RM** resource management cells (messages sent to informa about the congestion state) direct/indirect via receiver
    - Fields: EFCI, CI, NI, ER, Explicit forward congestion indication, Congestion Indication, No Increase, Explicit Rate

## TCP Congestion Control
- much/little congestion -> de-/increase send-rate
- `cwnd` congestion window
  - **self-clocking** (adjusts `cwnd` on its own)
  - inited with `1 MSS` (sth small) = **SLOW START**
- Rate `~ cwnd/RTT`
  - `cnwd bits` put into the link until acknowledged
- avoidance mode : **AIMD** adding-increase multiplicative-decrease
  - on ACK: increase `cwnd` by `1 MSS` (linear)
  - on NAK: decrease `cwnd` to half
  - generally increasing until drastic decrease, then increasing again
- SLOW START mode
  - `cwnd=1 MSS` and then doubling on ACK (growing exponentially)
  - on Timeout: restart SLOW START or change to avoidance mode
- Fast Recovery
  - `cwnd = 6 MSS`, linear growth 
- average throughput of TCP
  - the saw-toothed behavior of TCP results in
  - `3/4 * W/RTT`, `W=cwnd size inducing loss`
- TCP over High-Bandwidth Paths
  - since there are a lot of bytes in the line, one loss can induce a huge amount of resent segments (that also have to be buffered until acknowledged!)

## Fairness
- is TCP's AIMD algorithm fair?
  - Connections with lower RTT can gain a bigger piece of the bandwidth more quickly, so it's **not absolutely fair**, but gives other connections still room
- UDP fair?
  - nope ...
- Multiple parallel connections and Fairness?
  - If we had Fairness for UDP/TCP connections, a software could still establish several connections, thus gaining more bandwidth
