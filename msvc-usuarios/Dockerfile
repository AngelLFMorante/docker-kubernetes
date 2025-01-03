## OPTIMIZAR DOCKERFILE

#Como da problemas cuando compilamos por el hecho de si tocamos codigo tenga que generar todo de nuevo, lo que hacemos es:
# desde donde es el jdk que encontramos en dockhub en este caso ponemos alpine que es mas liviano de linux
FROM openjdk:17-jdk-alpine AS builder

# la ruta de trabajo, ahora queremos meter a msvc-usuarios
WORKDIR /app/msvc-usuarios

# copiamos el jar que hemos generado en el target al empaquetar, al poner "." se copia en la carpeta de destino
# ahora como queremos utilizar el proyecto y tenemos en el pom parent debemos incluirlo
COPY ./pom.xml /app
#Tambien debemos copiar el micro de usuarios junto con el maven y lo llevamos a la ubicacion
COPY ./msvc-usuarios/.mvn ./.mvn
#Incluimos el mvn ejecutable
COPY ./msvc-usuarios/mvnw .
#Copiamos el pom de usuarios
COPY ./msvc-usuarios/pom.xml .

#Ahora tenemos que compilar saltando el test y la compilacion del test como el main, plugin del pom repackage, y eliminamos target
#Esto se va a quedar en el cache
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

#Seguidos los pasos ahora creamos el src
COPY ./msvc-usuarios/src ./src

#Ahora tenemos que compilar saltando el test
RUN ./mvnw clean package -DskipTests

#El from llega hasta aqui para construir la imagen, ahora creamos otro ( otro constructor )
FROM openjdk:17-jdk-alpine

WORKDIR /app

#Indicamos que copiamos, en este caso es el builder que le dimos mas arriba, la ubicacion y donde se va a copiar en este caso app
COPY --from=builder /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

# Es el puerto donde tenemos configurado en la aplicación
EXPOSE 8001

# el directorio de trabajo, como se levantaria normalmente como en CMD
ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

# RECORDAR MUY BIEN EL TEMA DE VERSION HAY QUE TENER BIEN CONFIGURADO Y NO CONFUNDIR CON OTRA VERSION

# Para construir la imagen en docker, lo hacemos desde la terminal, desde la ruta del micro
# docker build -t "etiqueta" -f "fichero"
# ahora para crear la imagen -> docker build . -t usuarios -f .\msvc-usuarios\Dockerfile

#---------------------------------------------------------------------------------------------------------
# desde donde es el jdk que encontramos en dockhub
#FROM openjdk:17

# la ruta de trabajo, ahora queremos meter a msvc-usuarios
#WORKDIR /app/msvc-usuarios

# copiamos el jar que hemos generado en el target al empaquetar, al poner "." se copia en la carpeta de destino
# ahora como queremos utilizar el proyecto y tenemos en el pom parent debemos incluirlo
#COPY ./pom.xml /app
#Tambien debemos copiar el micro de usuarios
#COPY ./msvc-usuarios .

#Ahora tenemos que compilar
#RUN ./mvnw clean package -DskipTests

# Es el puerto donde tenemos configurado en la aplicación
#EXPOSE 8001

# el directorio de trabajo, como se levantaria normalmente como en CMD
#Ahora le indicamos que el jar se va  encontrar en el target
#ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]

# RECORDAR MUY BIEN EL TEMA DE VERSION HAY QUE TENER BIEN CONFIGURADO Y NO CONFUNDIR CON OTRA VERSION

# Para construir la imagen en docker, lo hacemos desde la terminal, desde la ruta del micro
# docker build -t "etiqueta" -f "fichero"
# ahora para crear la imagen -> docker build . -t usuarios -f .\msvc-usuarios\Dockerfile


#---------------------------------------------------------------------------------------------------------

# desde donde es el jdk que encontramos en dockhub
#FROM openjdk:17

# la ruta de trabajo
#WORKDIR /app

# copiamos el jar que hemos generado en el target al empaquetar, al poner "." se copia en la carpeta de destino
#COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

# Es el puerto donde tenemos configurado en la aplicación
#EXPOSE 8001

# el directorio de trabajo, como se levantaria normalmente como en CMD
#ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

# RECORDAR MUY BIEN EL TEMA DE VERSION HAY QUE TENER BIEN CONFIGURADO Y NO CONFUNDIR CON OTRA VERSION

# Para construir la imagen en docker, lo hacemos desde la terminal, desde la ruta del micro
# docker build .