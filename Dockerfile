FROM amazoncorretto:21

# Install necessary Linux packages
RUN yum update -y && \
    yum install -y \
        curl jq unzip bzip2 wget gnupg tar xz \
        xorg-x11-server-Xvfb \
        mesa-libGL mesa-libGLES \
        dbus-glib dbus-glib-devel \
        fontconfig \
        p7zip p7zip-plugins && \
    yum clean all

# Install Gradle 8.2 y conservar el zip para el wrapper
RUN wget -q https://services.gradle.org/distributions/gradle-8.2-bin.zip \
        -O /opt/gradle-8.2-bin.zip && \
    unzip -q /opt/gradle-8.2-bin.zip -d /opt/ && \
    ln -s /opt/gradle-8.2/bin/gradle /usr/bin/gradle

ENV GRADLE_HOME=/opt/gradle-8.2
ENV PATH=$GRADLE_HOME/bin:$PATH
ENV GRADLE_USER_HOME=/root/.gradle

# Install Google Chrome
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm && \
    yum localinstall -y google-chrome-stable_current_x86_64.rpm && \
    rm google-chrome-stable_current_x86_64.rpm && \
    yum clean all

# Install Firefox (latest, con reintentos por inestabilidad de red)
RUN FIREFOX_VERSION=$(curl -s "https://product-details.mozilla.org/1.0/firefox_versions.json" | \
        grep -o '"LATEST_FIREFOX_VERSION": "[^"]*"' | grep -o '[0-9.]*') && \
    echo "Installing Firefox $FIREFOX_VERSION" && \
    for i in 1 2 3 4 5; do \
        wget -c --tries=3 --waitretry=10 --timeout=60 \
            -O /tmp/firefox.tar.xz \
            "https://releases.mozilla.org/pub/firefox/releases/${FIREFOX_VERSION}/linux-x86_64/en-US/firefox-${FIREFOX_VERSION}.tar.xz" \
        && break \
        || echo "Intento $i fallido, reintentando..." && sleep 10; \
    done && \
    tar -xJf /tmp/firefox.tar.xz -C /opt/ && \
    ln -s /opt/firefox/firefox /usr/bin/firefox && \
    rm /tmp/firefox.tar.xz

# Verify installations
RUN java -version && \
    gradle -version && \
    google-chrome --version && \
    firefox --version

WORKDIR /usr/src/app

COPY . .

RUN chmod +x gradlew

# Apuntar wrapper al zip local: evita descarga en runtime
RUN sed -i 's|distributionUrl=.*|distributionUrl=file\:///opt/gradle-8.2-bin.zip|' \
    gradle/wrapper/gradle-wrapper.properties

# Pre-cachear dependencias en build time
RUN gradle dependencies --no-daemon || true

ARG TEST_RUNNER="all_tests"
ARG SELENIUM_GRID_ENABLED="false"
ARG GRID_HUB_HOST

ENV TEST_RUNNER=${TEST_RUNNER}
ENV SELENIUM_GRID_ENABLED=${SELENIUM_GRID_ENABLED}
ENV GRID_HUB_HOST=${GRID_HUB_HOST}
ENV DISPLAY=:99

CMD ["sh", "-c", "Xvfb :99 -screen 0 1920x1080x24 & sleep 1 && ./gradlew test -P${TEST_RUNNER} --no-daemon; ./gradlew allureReport --no-daemon"]