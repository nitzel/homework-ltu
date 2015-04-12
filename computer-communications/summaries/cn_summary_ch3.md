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
** coming next week ** :)