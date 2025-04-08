FROM maven:3.9.9-eclipse-temurin-21 AS java-build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests -Djooq.codegen.skip=true

FROM nvidia/cuda:12.1.1-cudnn8-runtime-ubuntu22.04 AS whisper-build
WORKDIR /whisper
RUN apt-get update && apt-get install -y git cmake build-essential && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN git clone https://github.com/ggml-org/whisper.cpp.git
WORKDIR /whisper/whisper.cpp

RUN mkdir build && cd build && cmake .. && cmake --build . --config Release

FROM openjdk:21-slim
WORKDIR /app

COPY --from=java-build /app/target/vidsummize-0.0.1-SNAPSHOT.jar app.jar

COPY .env .

RUN mkdir -p /opt/whisper/whisper.cpp/build/bin && mkdir -p /opt/whisper/whisper.cpp/models
COPY --from=whisper-build /whisper/whisper.cpp/build/bin/whisper-cli /opt/whisper/whisper.cpp/build/bin/whisper-cli
COPY --from=whisper-build /whisper/whisper.cpp/models /opt/whisper/whisper.cpp/models

RUN apt-get update && apt-get install -y \
      ffmpeg \
      python3 \
      python3-pip \
   && pip3 install --break-system-packages yt-dlp \
   && apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
