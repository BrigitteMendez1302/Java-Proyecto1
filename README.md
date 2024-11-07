# Sistema de Gestión de Clientes y Cuentas Bancarias

Este proyecto es una aplicación de consola desarrollada en **Java** con **Spring Boot** que permite gestionar clientes y sus cuentas bancarias. Utiliza **MySQL** para almacenar datos y está diseñado para ejecutarse en un entorno de consola donde el usuario puede realizar operaciones bancarias básicas.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Ejecución](#ejecución)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)

## Características

- **Registro de Clientes**: Permite registrar clientes con atributos como nombre, apellido, DNI y correo electrónico. El correo es opcional, pero debe tener un formato válido si se proporciona.
- **Gestión de Cuentas Bancarias**: Un cliente puede tener múltiples cuentas, las cuales pueden ser de tipo Ahorros o Corriente.
  - Las cuentas de **Ahorros** no pueden tener un saldo negativo.
  - Las cuentas **Corriente** permiten un sobregiro de hasta -500.00.
- **Operaciones Financieras**:
  - **Depósito de Dinero**: Aumenta el saldo de la cuenta.
  - **Retiro de Dinero**: Permite retirar dinero respetando las reglas de saldo negativo.
  - **Consulta de Saldo**: Muestra el saldo actual de la cuenta.

## Requisitos

- **Java 11**: Asegúrate de que `JAVA_HOME` esté configurado en tu sistema.
- **MySQL 8**: Para almacenar los datos de clientes y cuentas.
- **Maven**: Para gestionar las dependencias del proyecto.
  
## Instalación

1. **Clona el Repositorio**:

   ```bash
   git clone [https://github.com/tu_usuario/tu_repositorio.git](https://github.com/BrigitteMendez1302/Java-Proyecto1)
   cd Java_Proyecto1
   ```
   
2. **Configura la Base de Datos en MySQL**

Inicia sesión en MySQL y crea la base de datos:

  ```sql
  CREATE DATABASE bank_management;
  ```

3. **Configura `application.properties`**

Abre el archivo `src/main/resources/application.properties` y reemplaza los valores por los de tu base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_management?useSSL=false&serverTimezone=UTC
spring.datasource.username=bank_user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

4. **Instala las Dependencias y Compila el Proyecto

Ejecuta el siguiente comando para compilar el proyecto:

```bash
mvn clean package
```

5. Ejecución

Para ejecutar la aplicación desde la consola:

```bash
java -jar target/mi_primera_app-1.0-SNAPSHOT.jar
```

Esto iniciará la aplicación en modo consola, permitiendo interactuar mediante un menú de opciones.

6. Uso

Una vez que la aplicación esté en ejecución, verás un menú con las siguientes opciones:

1. **Registrar Cliente**: Permite crear un nuevo cliente ingresando el nombre, apellido, DNI y correo electrónico.
2. **Abrir Cuenta Bancaria**: Abre una cuenta de tipo Ahorros o Corriente para un cliente existente.
3. **Depositar Dinero**: Ingresa dinero en una cuenta específica.
4. **Retirar Dinero**: Retira una cantidad de dinero de la cuenta, respetando las restricciones de saldo.
5. **Consultar Saldo**: Muestra el saldo actual de una cuenta.
6. **Salir**: Cierra la aplicación.

### Ejemplo de Uso

- Al seleccionar "Registrar Cliente", ingresa los datos solicitados. Si el cliente ya existe (por DNI) o si el email no tiene el formato correcto, verás un mensaje de error.
- Al seleccionar "Abrir Cuenta Bancaria", se te pedirá el tipo de cuenta. Para las cuentas de Ahorros, el saldo no puede ser negativo, y las cuentas Corriente tienen un límite de sobregiro de -500.00.

## Estructura del Proyecto

```plaintext
src
└── main
    └── java
        └── org
            └── example
                ├── MainApplication.java           // Clase principal con el menú
                ├── domain
                │   ├── model
                │   │   ├── Customer.java          // Entidad Customer
                │   │   └── BankAccount.java       // Entidad BankAccount
                │   ├── repository
                │   │   ├── CustomerRepository.java
                │   │   └── BankAccountRepository.java
                │   └── service
                │       ├── CustomerService.java   // Lógica de negocio para clientes
                │       └── BankAccountService.java // Lógica de negocio para cuentas
    └── resources
        └── application.properties                 // Configuración de la base de datos
```
## Tecnologías Utilizadas

- **Java 11**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL 8**
- **Maven**

## Notas

- Asegúrate de que el puerto 8080 esté disponible o configura otro puerto en `application.properties` si está en uso.
