# Test 2
## Q1 DNS
- click a link in webbrowser for a webpage
- DNS lookup for the IP with `n` with `RTT_1, RTT_2, ... RTT_n`
- the Webpage references `3` small objects on the same server
  - Makes `4` objects (Websites and the others)
- `RTT_0` to server
- ? Time until receiving of the objects with 
  - non persistent HTTP, parallel
    - get HTML `1*RTT_0`
    - get `3` objects at once (parallel) `1*RTT_0` 
    - `2` TCP Handshakes: `1` for getHTML + `1` for getObjects: `2*RTT_0`
      - `1` for getObjects is actually `3`, but they are parallel
    - get IP `sum(RTT_1,..RTT_n)`
    - **SUM** `4*RTT_0 + sum(RTT_1,..RTT_n)`
  - non persistent HTTP, sequential
    - get HTML+Handshake  `2*RTT_0` 
    - get each of 3 Objects+Handshake each (sequential) `3*(RTT_0+RTT_0) = 6*RTT_0` 
    - get IP `sum(RTT_1,..RTT_n)`
    - **SUM** `8*RTT_0 + sum(RTT_1,..RTT_n)`
  - persistent HTTP (assuming parallel)
    - TCP Handshake `RTT_0`
    - get HTML `RTT_0`
    - get `3` Objects `RTT_0` (parallel)
    - get IP `sum(RTT_1,..RTT_n)`
    - **SUM** `3*RTT_0 + sum(RTT_1,..RTT_n)`
- ANSWER `4,8,3` each `1*(RTT_1+...+RTT_n)`
- ANSWER `T1=4RTT_0+RTT_1+...+RTT_n; T2=8RTT_0+RTT_1+...+RTT_n; T3=3RTT_0+RTT_1+...+RTT_n; ` 
  
## Q2 P2P vs ClientServer
- defining `xn` to `1024^n`
- distributing a file with size `F = 10 Gibi bit = 10x3`
- `N` peers
- server up `u_s = 20x2 bit/sec`
- peer down `d_i = 1x2 bit/sec`
- peer up `u`
- ? Calculate the minimum distribution time `T` for 
  - **ClientServer**
  - `N =   10` `u = 0.2x2 bit/sec`
    - `t_trans = F/d_i = 10x3/1x2 bit/bit*sec = 10x1 sec = 10240 sec`
    - can be done parallel 
    - `T = 10240 sec`
  - `N = 1000 u = 0.2x2 bit/sec`
    - `t_trans = F/d_i = 10x3/1x2 bit/bit*sec = 10x1 sec = 10240 sec`
    - `20` be done parallel 
    - `T = N/20 * t_trans = 1000/20 * t_trans = 50 * 10x1 sec = 512000 sec`
  - `N =   10 u = 1x2 bit/sec`
    - `T = 10240 sec` same as above
  - `N = 1000 u = 1x2 bit/sec`
    - `T = 512000 sec` same as above
  - **Peer2Peer**
  - `N =   10` `u = 0.2x2 bit/sec` 
    - `T = 10240 sec` same as CS
  - `N = 1000 u = 0.2x2 bit/sec` 
    - guess `47559.33`
  - `N =   10 u = 1x2 bit/sec`  
    - `T = 10240 sec` same as CS
  - `N = 1000 u = 1x2 bit/sec` 
    - guess `10240`
- ANSWER (guess) `CS: T(u=0.2, N=10)=10240; T(u=0.2, N=1000)=512000; T(u=1, N=10)=10240; T(u=1, N=1000)=512000; P2P: T(u=0.2, N=10)=10240; T(u=0.2, N=1000)=47559.33; T(u=1, N=10)=10240; T(u=1, N=1000)=10240`


## Q3
- [ ] **wrong** non persistent, single segment, 2 http requests
- [ ] **wrong** 3 files. one request, 3 answers
- [] ?? distinct webpages over one connection possible
  - alywas or sometimes possible???
- [ ] **wrong** date-field in http response indicates last modified
- [ ] **wrong** client connects to server on execution of .exe
- [x] **true** mail and webserver can have the same alias (use different ports)
- [x] **true** FTP uses two parallel TCP connections, 1 control, 1 data
- [ ] **wrong** cookie is used for encryption
  - well, its possible, but unsafe
- [x] **true** SMTP uses handshaking at Application layer, HTTP does not
- [ ] **wrong** Web caching can reduce the delay for uncached objects 
- [x] **true** TCP provides reliable byte-stream between C,S, UDP does not 
- [ ] **wrong** TCP and UDP guarantee certain throughput
- [x] **true** neither TCP nor UDP guarantee delivery within `time t`
- [] ?? TCP provides security while UDP does not
  - TCP with SSL does, but TCP on its own: no
- [ ] **wrong** SSL operates at the application layer
- [ ] **wrong** to do transaction as fast as possible: use TCP, not UDP
- [ ] **wrong** in P2P file sharing there is no notion of client/server sides in a communication session
  - yes, there is. the starting is always the client(definition from book)
- [x] **true** IP&Port used by a process on A to identify process on B
- [ ] **false** NNTP, SMTP, Remote Login are proprietary protocols.
- [ ] **wrong** Video Streaming and Instant Messaging are apps not suitable for P2P architectures
  - Chats often use P2P
  
## Q4 Web Caching 2
- access link `R_acc = 15Mb/s`
- LAN `R_lan = 100Mb/s`
- Avg object size `F = 9e5 bits`
- Avg request rate from browser to servers `f = 15  1/sec`
- hit rate `hr = 0.4`
- Router(Internet) delay from HTTP Request to Answer `d_net = 2 sec`
- avg access delay (internet router to company router): `d_acc = D/(1-B*D)`
  - avg time to send an object over access link `D = F/R_acc = 9e5 / 1.5e7 bit/bit * sec = 6e-2 sec`
  - arrival rate of objects to the access link `B = f = 15`
  - `d_acc = D/(1-(1-hr)*B*D) = 0.13 sec`
- avg response time: `d_response = d_acc + d_net = 0.13 + 2 sec = 2.13sec`
- **part2**
- `B = (1-0.4) * 15 = 9` real requests to the server
- `Tr_miss = d_repsonse ~ 2.12`
- `Tr_hit = F/R_lan = 0,009 secs`
- `Tr = 0.4 * Tr_hit + 0.6 * Tr_miss = 0,0036 + 1,278 sec = 1,2816 sec`
- ANSWER2 `Tr ~ 1.272 sec`
- **COMPLETE ANSWER** `Tr_miss = 2.12; Tr = 1.272 sec`

## Q5 Web Caching 1
- access link `R_acc = 15Mb/s`
- LAN `R_lan = 100Mb/s`
- Avg object size `F = 9e5 bits`
- Avg request rate from browser to servers `f = 15  1/sec`
- hit rate `hr = 0.4`
- Router(Internet) delay from HTTP Request to Answer `d_net = 2 sec`
- avg access delay (internet router to company router): `d_acc = D/(1-B*D)`
  - avg time to send an object over access link `D = F/R_acc = 9e5 / 1.5e7 bit/bit * sec = 6e-2 sec`
  - arrival rate of objects to the access link `B = f = 15`
  - `d_acc = D/(1-B*D) = 6e-2/(1-15*6e-2) sec = 0.06/(1-0.9) sec = 0.6 sec`
- avg response time: `d_response = d_acc + d_net = 0.6 + 2 sec = 2.6sec`
- ?Traffic Intensity `I = Delta * Beta = sec * 1/sec = no measure` 
- ANSWER `I = Delta * Beta; Tr_miss = d_repsonse = 2.6 sec`




