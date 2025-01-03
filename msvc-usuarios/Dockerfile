# desde donde es el jdk que encontramos en dockhub
FROM openjdk:17

# la ruta de trabajo
WORKDIR /app

# copiamos el jar que hemos generado en el target al empaquetar, al poner "." se copia en la carpeta de destino
COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

# Es el puerto donde tenemos configurado en la aplicación
EXPOSE 8001

# el directorio de trabajo, como se levantaria normalmente como en CMD
ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

# RECORDAR MUY BIEN EL TEMA DE VERSION HAY QUE TENER BIEN CONFIGURADO Y NO CONFUNDIR CON OTRA VERSION

# Para construir la imagen en docker, lo hacemos desde la terminal, desde la ruta del micro
# docker build .