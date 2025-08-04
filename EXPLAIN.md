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
### New API
**API Endpoints**:
- `POST /api/tms/company` - Create new company, and it dedicated DB
- `GET /api/tms/clients/search?name={name}` - Search company by name

### Changes
All below api changes to connect to it company DB by reading the [Tenant-name] from the request header, and operate the CRUL.

**API Endpoints**:
- `POST /api/corehr/employees` - Create new employee
- `GET /api/corehr/employees` - Get all employees
- `GET /api/corehr/employees/{id}` - Get employee by ID
- `GET /api/corehr/employees/employee-id/{employeeId}` - Get employee by employee ID
- `GET /api/corehr/employees/email/{email}` - Get employee by email
- `GET /api/corehr/employees/department/{department}` - Get employees by department
- `GET /api/corehr/employees/status/{status}` - Get employees by status
- `GET /api/corehr/employees/search?name={name}` - Search employees by name
- `PUT /api/corehr/employees/{id}` - Update employee
- `PATCH /api/corehr/employees/{id}/status` - Update employee status
- `DELETE /api/corehr/employees/{id}` - Delete employee
- `GET /api/corehr/employees/count/status/{status}` - Get employee count by status

