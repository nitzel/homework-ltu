# Test 2
## Q1 pipelined utilization
- across the US
- transmissionrate `R = 1Gb/s = 1e9 bit/sec = 1.25e8 byte/sec`
- `RTT = 30ms = 0.03 sec`
- packetsize `L = 1000 bytes`
- acknowledgments sent in equally sized packets
- ? cwnd size that channel utilization > 90%
  - channel utilization of 90% means `U = 90% * R/L = 0.9 * 1.25e8 / 1e3 / sec = 112500 packets/sec`
  - windowsize `cwnd = U * RTT = 112500 * 0.03 packets/sec * sec = 3375 packets`
- ANSWER `approx. 3377 packets`
 
## Q2 SN Exhaustion
- `MMS = 1460 bytes`
- overhead/packet `sHead = 66 bytes`
- transmissionrate `R = 10Mb/s = 1e7 bit/sec = 1.25e6 byte/sec`
- bytes for storing sequence numbers (=bytenumbers) `4bytes`
  - we can identify up to `L = 4294967295 bytes ~ 4.19e9 byte`
- number of packets `N = L/MMS`
- data to transmit incl overhead `F = L + L/MMS*66 ~ 4.38e9 byte`
- transmissiontime `Ttr = F/R = 4.38e9 / 1.25e6 sec = 3504 sec ~ 59 min`
- ANSWER `L = approx 4.19 GB; Ttr = 59 minutes`

## Q3 TCP Congestion Control
1. Thr@18
  - ?
- Thr@24
  - ?
- TCP Slow intervals
  - `[1,6] [23,26]`
- packet loss after 26. by 3xACK, what is cwnd and Thr?
  - `4,4`
5. packet loss after 16. detected by 
  - 3 DUP ACK
6. packet loss after 22. detected by 
  - timeout
7. tcp congestion avoidance intervals
  - `[6,16] [17,22]`
8. Thr@1 (initial threshold)
  - ?
9. during which transmission round is the 70th segment sent?
  - 7 ... `(1+2+4+8+16+32+64)>70>(1+2+4+8+16+32)`
  
## G4 TCP Operation
