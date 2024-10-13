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

# link para Wildfly diretório: 

- O driver necessário do PostgreSQL (versão `42.7.4`) já está incluído e injetado no servidor WildFly.

### Configuração do WildFly: https://drive.google.com/drive/folders/1JGMwDbKTWve-cCXN4F6-Ymp736WK8nZh?usp=sharing

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
```
# Funcionalidades

- Listagem de Salários Consolidados: Exibe uma tabela com a lista paginada de salários de todos os funcionários.
- Recalcular Salários: Botão que realiza o recálculo dos salários com base nos vencimentos e cargos.
- CRUD de Pessoa: Funcionalidades de criação, atualização e exclusão de funcionários (em desenvolvimento).
- Executando o Projeto

# Pré-requisitos
- WildFly 24.0.0.Final configurado.
- PostgreSQL 13 instalado e rodando.
- Banco de dados esigTB criado e configurado conforme instruções acima.
  
# Passos para Rodar

- Configurar o WildFly: Certifique-se de que o driver PostgreSQL está configurado no WildFly conforme o exemplo de datasource acima.
- Subir o Banco de Dados: Certifique-se de que o PostgreSQL está rodando na porta 5420 e o banco esigTB está acessível com as credenciais configuradas.
- Deploy da Aplicação: Faça o deploy do arquivo .war no WildFly.
- Acessar o Sistema: Acesse a aplicação no navegador utilizando a URL padrão fornecida pelo WildFly (geralmente http://localhost:8080).

# OBSERVAÇÃO Configuração do PostgreSQL (Mudança de Autenticação SCRAM-SHA-256 para MD5)

- Durante o desenvolvimento, foi necessário desabilitar a autenticação SCRAM-SHA-256 no PostgreSQL, devido à incompatibilidade com algumas versões do driver JDBC. Para contornar esse problema, alteramos a autenticação para MD5 no arquivo pg_hba.conf.

- Aqui estão os passos para fazer essa modificação:

-Localize o arquivo pg_hba.conf:

- No Linux, geralmente está em: /etc/postgresql/13/main/pg_hba.conf.
- No Windows, o local comum é: C:\Program Files\PostgreSQL\13\data\pg_hba.conf.
- Edite o arquivo pg_hba.conf:

```xml
Substitua as linhas contendo scram-sha-256 por md5. Por exemplo:
plaintext
Copy code
# "local" is for Unix domain socket connections only
local   all             all                                     md5
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
# IPv6 local connections:
host    all             all             ::1/128                 md5
Reinicie o PostgreSQL:
```

# No Linux, use o comando:
```xml
sudo systemctl restart postgresql
```
- No Windows, reinicie o serviço via Painel de Controle ou usando a linha de comando:
- powershell
```xml
net stop postgresql && net start postgresql
```
- Com isso, o PostgreSQL passará a utilizar a autenticação MD5, resolvendo possíveis problemas de conexão com o driver JDBC.

# Melhorias Futuras

- Completar a implementação do CRUD na interface do usuário.
- Adicionar testes unitários e de integração para maior robustez.
- Implementar autenticação e autorização para controle de acesso.

#Resultado em tela:

![image](https://github.com/user-attachments/assets/cbe0fac4-e75b-43da-bcd7-7065a8def0d2)


Contribuições:
Sinta-se à vontade para fazer um fork deste repositório, abrir issues ou enviar pull requests para melhorias no projeto.







