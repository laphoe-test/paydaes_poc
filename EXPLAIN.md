### Getting Started

### step 1
run below command to create the key properties
```bash
openssl enc -aes-256-cbc -nosalt -k "password" -P > key.properties
```
### step 2
run below command to copy the key.properties to both corehr and tms source
```bash
cp key.properties tms/src/main/resources/ && cp key.properties corehr/src/main/resources/
```