Web automation framework made with Java and TestNG implementing Page Object Model (POM) and cross browser testing on 
Chrome and Firefox, it can be executed locally in a computer, on Docker or with Selenium Grid on Docker
as well.

1. To run locally on the computer use: ./gradlew clean test -P<xml_file>
2. To run on Docker: docker run -e TEST_RUNNER=<xml_file> -e SELENIUM_GRID_ENABLED=false <docker_image>
3. To run on Docker with Selenium Grid: docker run -e TEST_RUNNER=<xml_file> -e SELENIUM_GRID_ENABLED=true -e 
GRID_HUB_HOST=<host_ip> <docker_image>