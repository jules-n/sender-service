FROM gcr.io/distroless/java:11-debug
SHELL ["/busybox/sh", "-c"]
WORKDIR /app
COPY build/libs/sender-service.jar /app
CMD ["sender-service.jar"]