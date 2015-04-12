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
  - `T1=4RTT_0+RTT_1+...+RTT_n; T2=8RTT_0+RTT_1+...+RTT_n; T3=3RTT_0+RTT_1+...+RTT_n; ` 
  
## Q2 P2P vs ClientServer
- distributing a file with size `F=1e10 bit`
- `N` peers
- server up `u_s = 2e7 bit/sec`
- peer down `d_i = 1e6 bit/sec`
- peer up `u`
