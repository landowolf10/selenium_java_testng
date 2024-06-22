# Use a base image with Amazon Corretto 17
FROM amazoncorretto:17

# Install necessary Linux packages using yum
RUN yum update -y && \
    yum install -y curl jq p7zip p7zip-plugins unzip bzip2 wget gnupg tar && \
    yum install -y xorg-x11-server-Xvfb mesa-libGL mesa-libGLES dbus-glib dbus-glib-devel fontconfig && \
    yum clean all

# Install Gradle
RUN curl -sL https://services.gradle.org/distributions/gradle-8.0-bin.zip -o gradle.zip && \
    unzip gradle.zip && \
    mv gradle-8.0 /opt/gradle && \
    rm gradle.zip && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

# Download and install Google Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm && \
    yum localinstall -y google-chrome-stable_current_x86_64.rpm && \
    rm google-chrome-stable_current_x86_64.rpm && \
    yum clean all

# Download and install Firefox
RUN wget -O /tmp/firefox.tar.bz2 "https://download.mozilla.org/?product=firefox-latest&os=linux64&lang=en-US" && \
    tar -xjf /tmp/firefox.tar.bz2 -C /opt/ && \
    ln -s /opt/firefox/firefox /usr/bin/firefox && \
    rm /tmp/firefox.tar.bz2

# Set up your working directory
WORKDIR /usr/src/app

# Copy your project files
COPY . .

# Set permissions
RUN chmod +x gradlew

# Define a default value for the test runner
ARG TEST_RUNNER="all_tests"
ARG SELENIUM_GRID_ENABLED="false"
ARG GRID_HUB_HOST

# Run the test runner with gradlew
CMD ["sh", "-c", "./gradlew clean test -P\"$TEST_RUNNER\""]