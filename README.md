# Memeticame Mobile
Aplicación móvil base para proyecto de curso IIC3380 - Taller de aplicaciones en plataformas móviles.

## Instalación del ambiente de desarrollo

Considerar que el proceso de instalación puede ser tedioso y demorar una o dos horas.

### Windows

1. Descargar [Android Studio](https://developer.android.com/studio/index.html).
2. Instalar siguiendo instrucciones.

### Linux

1. Descargar [Android Studio](https://dl.google.com/dl/android/studio/ide-zips/2.1.2.0/android-studio-ide-143.2915827-linux.zip).
2. Instalar Oracle JDK

  ```
  sudo apt-get install python-software-properties
  sudo add-apt-repository ppa:webupd8team/java
  sudo apt-get update
  sudo apt-get install oracle-java8-installer
  ```
3. Iniciar Android Studio

  ```
  sudo apt-get install unzip
  sudo tar xvzf /path/to/zipfile/android-studio-ide-143.2915827-linux.zip
  cd android-studio-ide-143.2915827-linux
  ./studio.sh
  ```
4. Seguir instrucciones (instalar Android SDK).

### OSX

1. Si no tienes instalado Java, descargar e instalar desde [acá](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).
2. Descargar [Android Studio](https://developer.android.com/studio/index.html).
3. Seguir instrucciones.

## Partir con proyecto base

1. Clonar repositorio

  ```
  git clone https://github.com/iic2154-uc-cl/mcc-memeticame-mobile.git
  ```
2. Importar proyecto en Android Studio.
3. Ejecutar en emulador de android o en dispositivo físico.
