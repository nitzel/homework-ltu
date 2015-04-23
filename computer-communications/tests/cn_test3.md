# Test 2
## Q1 pipelined utilization
- across the US
- transmissionrate `R = 1Gb/s = 1e9 bit/sec`
- `RTT = 30ms = 0.03 sec`
- packetsize `L = 1000 bytes = 8e3 bit`
- acknowledgments sent in equally sized packets
- ? cwnd size that channel utilization > 90%
  - transmission time `d_trans = L/R = 8e3/1e9 = 8e-6`
  - channel utilization `U_sender = d_trans / (d_trans + RTT) = (8e-6)/(8e-6 + 0.03) = 0.000267 = 2.67e-4`
  - we want `U > 90%, U = U_sender * cwnd`
    - `cwnd > 0.9/U_sender = 0.9 / 2.67e-4 ~ 3376`
- ANSWER `approx. 3377 packets`
 
## Q2 SN Exhaustion
- `MMS = 1460 bytes`
- overhead/packet `sHead = 66 bytes`
- transmissionrate `R = 10Mb/s = 1e7 bit/sec = 1.25e6 byte/sec`
- bytes for storing sequence numbers (=bytenumbers) `4bytes`
  - we can identify up to `L = 4294967296 bytes ~ 4.19e9 byte`
- number of packets `N = L/MMS`
- data to transmit incl overhead `F = L + L/MMS*66 ~ 4.38e9 byte`
- transmissiontime `Ttr = F/R = 4.38e9 / 1.25e6 sec = 3504 sec ~ 59 min`
- ANSWER `L = approx 4.19 GB; Ttr = 59 minutes`

## Q3 TCP Congestion Control
1. Thr@18
  - 21
- Thr@24
  - 13
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
  - 32
9. during which transmission round is the 70th segment sent?
  - 7 ... `(1+2+4+8+16+32+64)>70>(1+2+4+8+16+32)`
- ANSWER `Thr=21; 2: Thr=13; 3: [1,6] and [23,26]; 4: CWND=TRH=4; 5:3 DUP ACK; 6: Timeout; 7: [6,16] and [17,22]; 8: Thr=32; 9: Round 7` 

## G4 TCP Operation
- B has received all bytes up through `248` from ACK
- A sends 2 segments to B (`40bytes`, `60bytes`), first one has `SN=249, SPN=503, DPN=80` (sequence number, source/dest port number)
- ? `SN, SPN, DPN` of segment 2
  - `SN=249+40=289`, `SPN=503, DPN=80`
- ? ACK Number `AN` of the ACK of the first received segment if the second arrives before the first
  - `SN=249` because seg1 is still missing
- ? ACK Number `AN`, `SPN`, `DPN` in the ACKmsg for the first segment if the first arrives before the second
  - `SN=289` because 289th byte is the next expected

##Q5 Tcp performance
- large file from A to B via Tcp having no loss
  1. TCP with AIMD for congestion control with 
    - congestion avoidance (increase by `1MSS`)
    - `const RTT`
    - ? when did the congestion window `cwnd = 6 MSS`?
      - `6+5+4+3+2+1 -> 5` transmission rounds -> `5 RTT` 
  2. ? average throughput for this connection up through `t=5RTT`
    - packets per round sum up: `5+4+3+2+1 = 15`, `15*MSS/5/RTT = 3MSS/RTT`
- ANSWER `5RTT,     3MSS/RTT`

##Q6 TCP Rate
- Hosts A,B connected by `R = 200Mb/s` link, A can send at `100Mb/s`, B can receive at `50Mb/s` so they will end up in ..
- ANSWER `50Mb/s`

##Q7 Slow Start
- ignore protocol headers
- **slow start**
- Client and Webserver connected directly by link with `R`
- `RTT` round trip time 
- `S` Maximum Segment Size
- Client retrieves object `F = 15S`
- determine time `t` to retrieve the object (including TCP establishment) when
  - 4S/R > S/R + 0RTT > 2S/R **f...u**
  - 8S/R > S/R + 1RTT > 4S/R
    - 
  - S/R > RTT
    - 
- `S/R` transmission time for `S`
# WHAAAAAA NO IDEA

##Q8 true false
1. reception of 3 DUP ACK used by TCP to switch between GBN and SR and to switch between different congestion strategies
  - dunno 
2. 2 TCP connections over same bottleneck of Rate `R`, transmissions start at same time, TCP will give them `R/2` after long time
  - **wrong** because of different RTT times, making TCP able to grab more if RTT is low
3. Acks are only sent piggybacked
  - **wrong** acks on itself can also be sent (alone) 
4. `SN1 = 90, SN2 = 110` means `20 bytes` of data in Segment 1
  - **true**
5. A sends file to B, unacknowledged bytes@A cannot exceed receiveBuffer@B
  - **true**, thats flow control, p.251
6. `SN2=SN1+1` always. 
  - **false** TCP counts bytes, so `delta` equals the amount of data in seg1
7. size of available buffer @receiver is a tcp-header-field
  - **true** (*receive window*)assuming that the receiver has sent the segment ...
8. ACKno is related to SN (in the same packet)
  - **false**
9. TCP congestion control, timeout halves `cwnd`
  - **false**, that would be done at 3 DUP ACK. timout=reset
10. increase rate in congestion avoidance > slow start
  - **false**
