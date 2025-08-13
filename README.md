# AMF Dual-Mode AMF0 & AMF3 (Java 17, Spring Boot)

- **Dual response mode**:
  - Request parsed as **AMF0** ⇒ respond **AMF0 body** (no 0x11).
  - Request parsed as **NS/Flash AMF3** ⇒ respond **AMF3 body** (0x11 + AMF3).

## Build & Run
```bash
mvn spring-boot:run
# or
mvn clean package
java -jar target/backend-ninjasaga-rama-0.0.1-SNAPSHOT.jar
```
Endpoint: `POST http://localhost:8080/amf/` with `Content-Type: application/x-amf`.
## Discord
```
kenzoo6508
```
# REVIEW

https://github.com/user-attachments/assets/08da15e1-d526-46cc-979d-188c74c08931

### Rebuild From
```
Rebuild From https://github.com/raihanazis/ninjasaga
```
