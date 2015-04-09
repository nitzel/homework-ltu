# Test 1
## Q1 Transmission vs Propagation delay
- distance: M
- prop speed: S m/s
- ANSWER d\_prop: `M/S`

## Q2 Transmission vs Propagation delay
- transmission rate: R bits/s
- data: L bits
- ANSWER d\_trans delay: `L/R`

## Q3 Transmission vs Propagation delay
- sum of Q1 and Q2
- ANSWER end-to-end delay d\_e2e:  `M/S + L/R`

## Q4 Transmission vs Propagation delay
- at t=d\_trans the **last** bit is
- ANSWER `just leaving Host A`

## Q5 Transmission vs Propagation delay
- d\_prop > d\_trans
- at t=d\_trans the **first** bit is
- ANSWER `In the link and has not reached Host B`

## Q6 Transmission vs Propagation delay
- d\_propr < d\_trans
- at t=d\_trans 
- ANSWER `the first bit has reached Host B`

## Q7 Transmission vs Propagation delay
- S=2.5e8 m/s
- L=120bits
- R= 56kbit/s
- `d_prop = d_trans `
  - `M/S = L/R`
  - `M/2.5e8 s/m = 120/56e3 bit*s/bit  `
  - `M/2.5e8 s/m = 120/56e3 s`
  - `M = 2.5e8*120/56e3 m`
- ANSWER `M = 535.714285714km ~ 536km`

## Q8 BDP and bit length 1
- dist AB `M = 10,000km`
- transmissionrate `R=1Mbit/sec`
- propagation speed `S = 2.5e8 m/sec`
- ? Bandwidth-delay-Product `BDP=R*d_prop`, Bit-length `bitlen = M/BDP`
  - `d_prop = M/S = 1e7/2.5e8 sec = 4e-2 sec`
  - `BDP = R*d_prop = 1e6 * 4e-2 bit/sec * sec = 4e4 bit = 40,000 bits`
  - `bitlen = M/BDP = 1e7/4e4 m/bit = 250m/bit`
- ? `bitlen = M`, what is `R` ?
  - `R = BDP/d_prop = M/bitlen/d_prop = 1e7/1e7/4e-2 m*bit/m/sec = 1/ 4e-2 bit/sec = 2.5e1 bit/sec = 25bit/sec`
- ANSWER `40,000bits, 250m/bit, 25bit/sec`

## Q9 BDP and bit length 2
- continuous message with length `L = 50.000 bits`
- ? how many bits in link at a time? 
- ANSWER `= BDP = 40,000`

## Q10 BDP and bit length 3
- `R = 10Mbit/sec`
- propagation speed `S = 2.4e8 m/sec`
- `M = 35,786km`
- ? `BDP = R*d_prop`
  - `d_prop = M/S = 35768e3/2.4e8 m*sec/m ~ 1,5e-1sec`
  - `= R * d_prop ~ 1e7 * 1.5e-1 bit/sec*sec = 1,500,000 bit`
- ANSWER `1,500,000 bit`

## Q11 BDP and bit length 4
- ? `d_prop = ?` see Q10
- ANSWER `150millisec`

## Q12 BDP and bit length 5
- one picture of size `X` per minute. 
- ? choose `X` that the sattelite is always transmitting
- ANSWER `X = 60sec * R = 60sec * 1e7bit/sec = 6e8 bits = 6e8/8 bytes = 75,000,000 bytes` 

## Q13 Message segmentation 1
- Source - Switch1 - Switch2 - Destination
- Message `L = 7.5e6 bit`
- Links `R = 1.5e6 bit/sec`
- Ignore propagation, queuing and processing
- without message segmentation
- ? how long from `Source` to `Switch1` ?
  - `t_s1 = L/R = 7.5e6/1.5e6 bit*sec/bit = 5sec` 
- ? how long from `Source` to `Destination` ?
  - `t_e2e = t_s1 * 3 = 15sec` 
- ANSWER `5sec 15sec`

## Q14 Message segmentation 2
- Source - Switch1 - Switch2 - Destination
- Message `L = 7.5e6 bit`
- Links `R = 1.5e6 bit/sec`
- Ignore propagation, queuing and processing
- message segmentation on, `N = 5000` packets
- ? how long for one packet from `Source` to `Switch1` ?
  - `t_p1 = L/N/R = 7.5e6/5e3/1.5e6 bit*sec/bit = 5sec/5e3 = 1e-3sec = 1millisec` 
- ? when is the 2nd packet fully received by the first switch?
  - `t_p2 = t_p1 * 2 = 1e-3 * 2 sec = 2millisec`
- ANSWER `1ms 2ms`

## Q15 Minimizing the delay
- Ignore queuing and propagation
- Large File with Size `F bits` send from A to B
- A - Switch1 - Switch2 - B
- Segmentation in `N = F/S` packets, each size `L = S+40 bits`
- Transmission Rate `R`
- ? Minimize time/delay to send the file
  - `T = (N+2) * t`
    - `t = L/R = (S+40)/R`
  - `= (F/S +2) * (S+40)/R = (2S+80+F)/R + 40F/(SR)`
  - ableitung = 0: ` 0 = 2/R - 40F/(S^2*R)`
    - ` S = sqrt(20F)`
- ANSWER `S = sqrt(20F)`

## Q16 Queuing delay
- `N` packets arrive at the same time
- Packet Size `L`
- Transmission Rate `R`
- Queue is empty
- ? Average queuing delay ?
  - time to send 1 packet `d_t1 = L/R`
  - Packet `i` waits `i * d_t1`
    - sum of waited time of all packets (gauss) `t_all = N/2 * (N-1) * d_t1` (-1 because first packet does not wait)
  - Avg: `t_all / N = N/2 * (N-1) * d_t1 / N = (N-1) * L / R / 2 = (N-1)*L/(2R)`
- ANSWER `(N-1)*L/(2R)`

## Q17
- [ ] host/endsystem
- [x] ethernet lan R e {10,100,1000,10000}Mbit/s/bit
- [x] circuit switching guarantees certain end to end bandwidth
- [ ] packet switching guarantees certain end to end bandwidth
- [ ] tier2 ISP connects to all tier1 ISPs
- [x] in packet switching, packets on link dont follow fixed pattern
- [x] TDM circuit switching, each host gets the same slot in the TimeDivisionMultiplexed frame
- [x] on a fixed route: processing, transmission and propagation are fixed, but queuing is variable
- [ ] a link layer switch processes even information inside ip headers
- [x] OSI network stack has 7 layers 
