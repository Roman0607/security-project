FROM eclipse-temurin:17.0.3_7-jdk

WORKDIR /app

COPY . .

ENTRYPOINT java \
    -cp app:app/lib/* \
    -Djava.security.egd=file:/dev/./urandom \
    -XX:+UseG1GC \
    -XX:+UseStringDeduplication \
    -XX:MinRAMPercentage=50 \
    -XX:MaxRAMPercentage=80 \
    $JAVA_OPTS \
    com.mvp.pet.PetProjectApplication
