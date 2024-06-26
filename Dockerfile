#? Builder
FROM --platform=$BUILDPLATFORM debian:bookworm-slim AS builder
RUN apt update -y && apt upgrade -y && apt autoremove -y
RUN apt install -y git gradle
WORKDIR /src
COPY . /src
RUN git submodule init && git submodule update
RUN chmod +x gradlew && ./gradlew dist

#? Runner
FROM --platform=$BUILDPLATFORM amazoncorretto:22-alpine-jdk AS runner
RUN apk update && apk upgrade
VOLUME ["/data"]
WORKDIR /data
COPY --from=builder /src/build/dist /app
ARG GATEWAY_PORT=5001
ARG SERVER_PORT=5002
ARG PORTAL_PORT=5003
EXPOSE $GATEWAY_PORT $SERVER_PORT $PORTAL_PORT
CMD ["sh", "-c", "java -jar /app/brainwine.jar disablegui"]

LABEL org.opencontainers.image.title="Brainwine"
LABEL org.opencontainers.image.description=" A portable private server for Deepworld."
LABEL org.opencontainers.image.source="https://github.com/kuroppoi/brainwine"
LABEL org.opencontainers.image.licenses="MIT"
LABEL org.opencontainers.image.documentation="https://github.com/kuroppoi/brainwine"
LABEL org.opencontainers.image.vendor=""
