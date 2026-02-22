#---------------BUILD-----------------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /ShareMyRecipe
COPY build.gradle settings.gradle ./
COPY gradlew ./
COPY gradle ./gradle
# 3️⃣ Give permission
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon
COPY src ./src
RUN ./gradlew build --no-daemon
#-------------RUN---------------------
FROM eclipse-temurin:17-jdk
WORKDIR /ShareMyRecipe
COPY --from=build /ShareMyRecipe/build/libs/*.jar sharemyrecipe.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sharemyrecipe.jar"]
CMD ["--spring.profiles.active=prod"]
