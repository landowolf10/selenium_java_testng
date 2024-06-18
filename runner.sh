#!/bin/bash

#-------------------------------------------------------------------
#  This script expects the following environment variables
#     TEST_SUITE
#     SELENIUM_GRID_ENABLED
#     HUB_HOST
#-------------------------------------------------------------------

# Let's print what we have received
echo "-------------------------------------------"
echo "TEST_SUITE            : ${TEST_SUITE}"
echo "SELENIUM_GRID_ENABLED : ${SELENIUM_GRID_ENABLED:-false}"
echo "HUB_HOST              : ${HUB_HOST}"
echo "-------------------------------------------"

# Do not start the tests immediately. Hub has to be ready with browser nodes
echo "Checking if hub is ready..!"
count=0
while [ "$( curl -s http://${HUB_HOST:-hub}:4444/status | jq -r .value.ready )" != "true" ]
do
  count=$((count+1))
  echo "Attempt: ${count}"
  if [ "$count" -ge 30 ]
  then
      echo "**** HUB IS NOT READY WITHIN 30 SECONDS ****"
      exit 1
  fi
  sleep 1
done

# At this point, selenium grid should be up!
echo "Selenium Grid is up and running. Running the test...."

# Start the java command
#./gradlew clean test -P${TEST_SUITE}
docker run -e TEST_RUNNER=${TEST_SUITE} -e SELENIUM_GRID_ENABLED=${SELENIUM_GRID_ENABLED -e GRID_HUB_HOST=${HUB_HOST} landowolf/selenium-java-testng:latest