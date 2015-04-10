# Wireshark Lab1

## Questions
1. HTTP Version Browser `1.1` Server `1.1`
- Languages: German, English (`de-DE, en-US`)
- IPs
  - mine: `46.162.93.6`
  - gaia: `128.119.245.12`
- Status `200 OK`
- Last modified `Fri, 10 Apr 2015 05:59:01 GMT`
- Bytes returned `128`
- Headers in raw-data not shown in packet listing:
  - `Ethernet II`, `IPv4`, `TCP`
- Do I see `if-modified-since` in the first `GET`? **no**
- Did the server return the contents of the file? **yes**, it is visible in the `data` field
- Do I see `if-modified-since` in the second `GET`? **yes** `Fri, 10 Apr 2015 05:59:01 GMT` the time of the last change
- HTTP Status of 2nd response? `304 Not Modified`. Did the server explicitly return the file contents? **no**, because the file was not modified and the local copy of the browser can be used instead.
12. How many `GET` Requests? **2** one for the `.html`, one for `favicon.ico`
13. How many TCP-Packets for the response? **4**
14. Status Code: `200 OK`
15. Response saying its split in several TCP Packets? No, why should it? that would mix the layers
16. How many `HTTP GET` messages? **4** `1html, 3jpg, 1favicon`
  - sent to `gaia`, `pearsonhighgered`, `manic`, `caite`, `gaia`
17. parallel? **yes**, because before the responses arrive, the requests are made. 
18. what response? `401 Authorization Required`
19. new field in 2nd request? `Authorization` with value `Basic ZXRoLXN0dWRlbnRzOm5ldHdvcmtz`
