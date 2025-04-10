# 🧪 Unicode Support Testing in Webadmin

This project uses **Java + RestAssured + TestNG** to verify **Unicode support** in Webadmin by:
- Injecting various Unicode characters into database fields.
- Validating API responses.
- Verifying database persistence.
- Uses `config.properties` for easy config management.

##Requirement to run this test script
- Java 11+
- Maven

##Step to run the script
- Clone this repository
- Navigate to that cloned location
- Edit the property file where needed
- Build the project using this command: `mvn clean install`
- Run the project using this command: `mvn test`



Or, use your IDE ( Intellij Idea) to see the test result

##Sample Output
- ✅ DB supports Unicode for Offering DetailDescription — proceeding with API request..............

  Unicode support is VERIFIED.
  
- ❌ DB does not support Unicode for the Catalog Description — skipping API request.


