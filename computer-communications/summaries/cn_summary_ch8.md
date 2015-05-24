#Chapter 8 - Security in Computer Networks
Summary of Chapter 8 of Computer Networking - A Top Down Approach, 6th Edition

## What is Network Security?
- What could Network Security be
  - **Confidentiality** only sender and receiver understand the transmitted message
  - **Integrity** make sure the message is not altered on the way to the destination
  - **End-point authentication** identify the communication partner at the other end of the line
  - **operational security** safety from attacks to e.g. company's networks
- How to obtain information as an attacker
  - eavesdropping: sniffing control and data messages
  - modification, insertion and deletion of messages/content

## principles of cryptography
- **plaintext/cleartext** -> encryption algorithm -> **ciphertext**
- personal **key**s used for the usually public encryption methods
- **symmetric key systems** one key works for en-/decryption
  - **Caesar Cipher** (shifting)
  - **monoalphabetic cipher** (letter substitution)
  - **known plaintext attack** (attacker knows some words in the text or expects commons words)
  - **chosen-plaintext attack** (attacker can choose which plaintext to take to get the full key)
  - **polyalphabetic encryption** (e.g. 2x Caesar)
  - **block ciphers** (WLAN, PGP, SSL, IPsec)
    - break data up in N-bit chunks, the y get **one-to-one** encrypted or rather mapped to other chunks in the same size
    - Brute-force not applicable, takes way too long to try all mappings
    - tables are very big, so they are simulated with random mapping-functions
  - **CBC, Cipher-Block Chaining**
    - mixed in randomness to block ciphers
    - so that the same text in a longer message is not encrypted the same way over and over again
- **public/asymmetric key systems** two keys, one for each: en-/decryption
  - **RSA** Rivest Shamir Adleman
  - **DES** about 1000/10.000x faster than RSA (software/hardware)
  - combine: Encrypt the data with DES and encrypt the used **session key** with RSA.
    - receiver encrypts the key slowly and the message quickley

## Message Integrity and Digital Signatures
- Cryptgraphic Hash Functions
  - Many-to-one hash functions (MD5, SHA-1)
- Message Authentication Code
  - Added secretly to the message, which is then hashed and receiver will know it was really the expected sender
- Digital Signatures
  - public key certification
    - Hash the Message, Encrypt the hash with private key and add it to the message
    - certification authority: **CA**

## Endpoint Authentication
- prove identity to anotherone
- e.g.
  - A sends msg to B, claiming to be A
  - B answers with **nonce** R (a value used only once in a runtime)
  - A encrypts B with shared key, sends it back
  - B decrypts, sees A(R)=R, done :)
  - no playback etc possible

## Securing E-Mail
- **PGP** Pretty Good Privacy by Phil Zimmermann

## Securing TCP Connections: SSL

## Network Layer Security with IPsec and VPNs
- IKE Key Management

## Securing WLANs
- WEP wired equivalent privacy, outdated!
- IEEE 802.11i

## operational security: firewall and intrusion detection
- Firewalls
  - Hardware & software combination
  - all traffic from/to external goes through it
  - only allowed traffic will pass
  - stateful packet filters
  - app gateway
- Intrusion detection systems
  - deep packet inspection: loop beyond headers into app-data
  - DMZ demilitarized zone
  - SNORT, open source IDS
  