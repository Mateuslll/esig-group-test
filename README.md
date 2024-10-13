# Sistema de Gestão de Salários Consolidados

Este projeto é uma aplicação web Java EE desenvolvida utilizando **JSF (JavaServer Faces)** com **Facelets** no frontend e **PostgreSQL** como banco de dados para persistência. O sistema exibe uma lista paginada de salários consolidados de funcionários e fornece funcionalidades para recalcular os salários, além de permitir a gestão de registros de funcionários (operações CRUD).

O backend utiliza um serviço stateless que separa a lógica de negócios da camada de repositório, que interage com o banco de dados via JPA. O banco de dados e as configurações de conexão são injetados diretamente no servidor WildFly, utilizando PostgreSQL como fonte de dados.

## Tecnologias Utilizadas

- **Java EE** (JSF, CDI)
- **JPA** para persistência de dados
- **WildFly 24.0.0.Final** como servidor de aplicação
- **PostgreSQL 13**
- **Driver PostgreSQL 42.7.4**
- **JDK 8**

## Configuração do Projeto

### Pré-requisitos

1. **WildFly 24.0.0.Final**: Certifique-se de que o WildFly está instalado e configurado corretamente.
2. **PostgreSQL 13**: Instale o PostgreSQL e configure o banco de dados e as credenciais de usuário necessárias.
3. **JDK 8**: Garanta que o Java 8 está instalado e configurado como o JDK padrão.

### Configuração do Banco de Dados

- **Nome do banco**: `esigTB`
- **Porta do PostgreSQL**: `5420`
- **Usuário**: `postgres`
- **Senha**: `admin123`

O driver necessário do PostgreSQL (versão `42.7.4`) já está incluído e injetado no servidor WildFly.

### Configuração do WildFly

Para usar o datasource e o driver do PostgreSQL injetados, adicione a seguinte configuração no arquivo `standalone.xml` do WildFly, na seção `<datasources>`:

```xml
<datasource jndi-name="java:jboss/datasources/PostgresDS" pool-name="PostgresDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
    <connection-url>jdbc:postgresql://localhost:5420/esigTB</connection-url>
    <driver>postgresql</driver>
    <security>
        <user-name>postgres</user-name>
        <password>admin123</password>
    </security>
</datasource>

<drivers>
    <driver name="h2" module="com.h2database.h2">
        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
    </driver>
    <driver name="postgresql" module="org.postgresql">
        <driver-class>org.postgresql.Driver</driver-class>
        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
    </driver>
</drivers>






