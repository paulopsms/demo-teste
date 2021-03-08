Para execução do projeto Backend, é necessário ter instalado em sua máquina o Maven, Postgresql e Redis Server.

#Banco de Dados 
 #####Execute os SQLs:
 
 Criando o database:
 
    CREATE DATABASE atlanticodb
        WITH 
        OWNER = atlantico
        ENCODING = 'UTF8'
        CONNECTION LIMIT = -1;
    
Criando um User/Role
    
    CREATE USER atlantico WITH
        LOGIN
        SUPERUSER
        CREATEDB
        CREATEROLE
        INHERIT
        NOREPLICATION
        CONNECTION LIMIT -1
        PASSWORD 'atlantico';
    
Criando a tabela de usuários:

    CREATE TABLE USERS
    (
        id          serial primary key,
        name        varchar(100)       not null,
        login       varchar(50) unique not null,
        password    varchar(255)       not null,
        createdDate timestamp          not null,
        updatedDate timestamp,
        email       varchar(255)       not null,
        admin       boolean            not null
    );

Criando a tabela de permissões de usuários:

    create table authorities
    (
        id          serial primary key,
        name        varchar(200),
        description varchar(255),
        path        varchar(255)
    );

Criando a tabela associativa de Usuários e Permissões

    create table users_authorities
    (
        users_authorities_id           serial  not null primary key,
        users_authorities_user_id      integer not null references users (id),
        users_authorities_authority_id integer not null references authorities (id)
    );

#Redis
Execute o Redis Server na porta padrão.



###Executando a Aplicação:
Para buildar e executar a aplcação, vá ao diretório do projeto e execute 
    
    mvn clean package
    
depois é só executar java -jar /target/
