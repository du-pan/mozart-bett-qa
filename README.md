## Automated test project for Mattress and Bed webshop.

### Requirements:
- JAVA JDK 21
- JAVA_HOME set as Environmental variable
- JAVA_HOME/bin set as Path

### Test run:
- Play button next to the TestNG test name
- Or using Maven command:

  This will run tests on MS Edge browser with English language and on Staging server
  ```
  mvn clean test -Dtest=TestClassName#testName -Dbrowser=edge -Dlanguage=eng -Dserver=mozart_env_stag
  ```
  **This will run default configuration (What needed)**
  ```
  mvn clean test -Dtest=TestClassName#testName
  ```

  Default configuration: Google Chrome browser, German language, Development server

