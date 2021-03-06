# Test 4
## Q1 Address Planning
- ? Network mask for 193.30.147.249/29
  - The first `29` of the `32` bits are set to `1`
  - `1111 1111.1111 1111.1111 1111.1111 1000 = 255.255.255.248`
- ? Switches dont need IPs. Is the Address Space big enough?
  - We have 8 nodes which need IPs
  - The network mask gives us 3 bits, that means, we have 8 different IPs
  - But `.0` is for the network name and `.8` for broadcasting, so there are only
  - `6` real IPs left, which is not enough.
- ? functionality of B
  - B should be a Network Address Translator / NAT
- ? How many simultaneous connetions can device B support in principle
  - Since it's a NAT, as many as there are Ports
  - `=2^16`

# Q2 Distance vector routing
- Routers u,w,x,y. 
- Dw(x) = 2, Dw(y) = 2, Dw(u) = 5
- Dy(x) = 5, Dy(w) = 2, Dy(u) = 6
- ? Dx? 
  - Dx(w) = 2, Dx(y) = 4, Dx(u) = 7
- ? link cost change for c(w,x) or c(x,y) so that x will update
  - `c(x,w) > 6` because then x,y,u is shorter than `x,w,u`
- ? link cost change for c(w,x) or c(x,y) so that x will **not** update
  - `not possible` because x,y,u will still be as expensive as `x,w,u`, unless you choose c(x,w)>6 (as in previous question)
- ANSWER:  `Dx(w) = 2, Dx(y) = 4, Dx(u) = 7; 2: Change of c(x,w)>6 will cause updating the neighbors; 3: Any changes of c(x,y) as well as changes of 6>c(x,w)>1 will not cause updating the neighbors.` 

# Q3 Fragmentation
- Datagram, data: `L=3000 bytes`, Identification number `n = 422`
- `MTU = 500`
- ? How many fragments are generated?
  - Each fragment needs to be smaller or equal the size of `MTU`, including the `20` IP Header. So our Data is `3000 bytes` long without header.
  - Each fragment will be `480 bytes` long (60*8 has to be multiple of 8!) plus the header of `20 bytes` So we have `ceil(3000/480) = 7` fragments
  - `7 fragments`
- ? Characteristics of these fragments
  - ? Identification number: will be all the same, `n = 422`
  - ? Each Fragment except the last one will be: `S = 500 bytes`, including IP Header
  - ? The last one will be of size `120 bytes` (including IP header): Yes, coz `overhead = 7*20 = 120 bytes`, and `6*500+120=3120`, which is `L+overhead`
  - ? The offsets of the seven fragments will be `i * 480/8 = i * 60` => `0, 60, 120, 180, 240, 300, 360`. 
  - ? Each of the first 6 fragments will have `flag=1`, the last one `flag=0`
  
# Q4 IP overhead
- TCP+IP Header `H = 20 + 20 bytes`
- Data `L= 40bytes`
- Quote from RFC 793 (TCP)

> A sending TCP is allowed to collect data from the sending user and to
  send that data in segments at its own convenience, until the push
  function is signaled, then it must send all unsent data.

- so we don't know, how the chunks of data are put together in packets. 
  - ANSWER1: Not enough information
- if we assume, that every single chunk is sent in one packet:
  - ANSWER2: `L/(L+H) = 40/80 = 50%`
  
# Q5 Prefix notation
- ISP owns block `101.101.101.64/26`
- Wants to create `4` subnets, each having the same amount of Addrs
- ? What are the prefixes?
  - We have `32-26 = 6 bits`, that means `2^6 = 64` addresses. 
  - `2^6/4 = 2^4 = 16` per subnet, of which the first one is the network-name, and the last one is usede for broadcasting
  - So we just take `64+i*16` as the last byte of the IP addresses
  - while the Masks are all `32-4=28`
  - ANSWER `101.101.101.64/28, 101.101.101.80/28, 101.101.101.96/28, 101.101.101.112/28`

# Q6 True False
1. [x] the routers both in datagram networks and virtual circuit networks use forwarding tables
2. [ ] **false** ICMP is a protocol at the transport layer 
  - it works at network layer, with IP
3. [ ] **false** Host joins multicast group, it must change its IP addr to the one of the multicast
4. [ ] **false** IPv6 uses 64 bit addresses
  - its 128 bits
5. [x] **true** With the help of ICMP one can determine a minimum size MTU
  - its called Path MTU Algorithm or something like that.
6. [x] **true** link-state routing protocol OSPF implements Dijkstra routing algorithm
7. [x] **true** RIP based on Bellman Ford algorithm may create loops in the topology
  - maybe, we dont know. Packets age with each hop
8. [x] **true** ospF consumes more network resources than RIP for its operations
9. [ ] **false** a router must be configured with only one IP addr
  - Can have one IP address for each link
10. [x] **true** Routing protocols for Inter-AS and Intra-AS are different
11. [ ] **false** Head-of-line blocking occurs at the output port of a router
  - it occurs at the input line. wouldnt make sense at output.... S.330
12. [x] **true** 2x2 switch, to have no input port queuing, switching fabric should work at at least 2x line speed
  - what exactly is a 2x2 switch? If it has `n` input ports, switching fabric should run at `2n`
13. [x] **true** `/24` is larger than `/29` network
14. [ ] **false** In a network with mask `/30` there are maximum 8 IP enabled devices
  - With /30 there are 4 IPs, 2 for organizing, 2 for devices. 
  - Or with a NAT in there, way more than 8...
15. [ ] **false** Ethernet addresses are hierarchically structured while IP addresses are not
  - its the other way round. MACs dont have a hierarchy, but IPs do have it!
