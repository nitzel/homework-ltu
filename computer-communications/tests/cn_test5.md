#Test 5
## Q1 Aloha
- slotted Aloha
- 3 active nodes A,B,C competing for access 
- each nodes wants to send an infinite number of packets
- each node attempts to transmit in each slot with probability `p`
- ? probability `P1` that node A succeeds for the first time in slot 4?
- ? probability `P2` that any node succeeds in slot 2?
- ? efficiency E of this three-node system (all nodes succeed)?
  - success `S = p(1-p)^(N-1)`
  - fail (collision/empty) `F = 1-p(1-p)^(N-1)`
  - `N = 3`
  - one node succeeds only 1 out of 4 times: `P1 = S*F^3 = (1 – p(1 – p)^2)^3 p(1 – p)^2`
  - any of the 3 nodes succeeds: `P2 = 3*S = 3p*(1 - p)^2`
  - efficiency: `E = P2 = 3p * (1 - p)^2`
  - ANSWER: `P1=(1 – p(1 – p)^2)^3 p(1 – p)^2; P2=3 p(1-p)^2; E=3 p(1-p)^2`
- page 450

## Q2 Broadcast channel
- broadcast channel wit N nodes
- uses polling (with additional polling node) for multiple access
- transmission rate `R`
- `d_poll` polling delay (from one node finnishes to when next node gets permission to transmit)
- per round, max `Q` bits to transmit.
- ? Maximum throughput of broadcast channel
  - `Q/R` time spent transmitting
  - `d_poll` time spent waiting
  - time per transmission `t = Q/R + d_poll`
  - transmitted data per transmission only `Q`, but takes `t` sec
  - so it is `Q/(Q/R + d_poll)`
  - extend it with 1: `Q/(Q/R + d_poll) * (R/Q)/(R/Q)`
  - ANSWER `R/(1+d_poll*R/Q)`

## Q3 CSMA/CD
- A and B on opposite ends of a `900m` cable
- Max frame size `1000bit`, including headers&preambles
- Both start transmitting at `t0`
- there are `4` repeaters in between, each adding a `20bit` delay.
- `R = 10Mbit/sec`
- CSMA/CD with backoff interval of multiples of 512 bits
- After the first collision, `A has K=0` and `B has K=1` for backoff
- ignore the jam and the 96bit time delay
- ? What is the one way propagation delay (incl repeaters) between A,B in sec? Assume `prop_speed = 2e8 m/sec`
  - `d_prop = 900 / 2e8 m/m*sec + 4 * 20bit/R = 4.5e-6 + 4*2e-6 = 1.25e-5 = 0.0000125 sec = 12.5 microsec`
- ? At what time in seconds is A's packet completely delivered at B?
  - `d_trans = 1000 / 10e6 bit/bit*sec = 1e-4 sec = 100 microsec`
  - `d_backoff = 0`
  - time till collision detection `d_prop`
  - additional prop delay `d_prop`
  - ANSWER `d_trans + 2d_prop = 100 microsec + 25 microsec = 125 microsec`
- **WHY?** ANSWER `12.5 microsec, 137.5 microsec`
  
## Q4 Spanning Tree
- Network like in Image
- `2` Hosts A,B
- `8` Bridges/Switches
- ? Does transparent bridge store a packet before sending it to next hop?
  - I would guess: not really, only for checksumming or if queued
- ? Does the spanning tree protocol calculate a shortest path between any 2 devices in a LAN?
  - No, that would be Shortest Path Bridging
- ? What would be the root bridge for the spanning tree protocol in the image?
  -`BridgePriority + MAC -> BID`, lowest `BID` becomes Root
  - `A21 < B21`
  - ANSWER `A21`
- ? use spanning tree protocol for bridges: determine root, designated and blocked ports for each bridge. What status have ports 1,2,3 on bridge A24 ?(r/b/d, root/blocked/designated)
  - calculate starting from root
  - path costs from root to node
  - redundant paths with higher costs are blocked
  - shortest path from node to root, link becomes `root-port`
  - shortest path from network to root, outgoing link from the switch to this network on the way becomes `designated port`
  - the rest becomes `blocked port`
  - could be RDD
- ANSWER `NO, NO, A21, r,b,d`
  
## Q5 true false
- [ ] **false** TCP reliable service redundant if all links would be reliable
  - maybe not, because not possible to have total reliability
- [x] **true** framestructure of 10Base-T, 100Base-T and Gigabit Ethernet is the same
  - i think there was another field
- [x] **true** transparent bridges may have IP addresses
  - nope nope nope
- [x] **true** Efficiency(slotted aloha) > 2*efficiency(unslotted aloha) (nearly)
  - pure: `18%`, slotted: `36.8` 
- [ ] **false** 10mbps ethernet entwork can be arbitrary long
  - nope, up to 500m
- [ ] **false** Gigabit Ethernet uses CSMA/CD
  - 10-100 mbps, but 1gbps does not use csma/cd anymore (-slide8)
- [ ] **false** Ethernet Addresses (=MAC) are hierarchically structured
- [ ] **false** ARP is used to find out IP address of first router
  - its used to get MAC from IP, every host/router has an ARP table
- [ ] **false** nodes connected to different ports of a learning switch belong to the same collision domain
  - if they were on the same link/port it would be true (slide8)
- [x] **true** Spanning Tree Protocol was invented to avoid loops in LANs




