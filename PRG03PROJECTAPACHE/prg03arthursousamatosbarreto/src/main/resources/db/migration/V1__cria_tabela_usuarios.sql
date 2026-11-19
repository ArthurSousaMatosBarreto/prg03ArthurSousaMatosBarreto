/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  PC
 * Created: 19 de nov. de 2026
 */

CREATE TABLE usuarios (
    id              BIGSERIAL PRIMARY KEY,
    nome         VARCHAR(150) NOT NULL,
    cpf          VARCHAR(14)  NOT NULL,
    genero         VARCHAR(30),
    dataNascimento VARCHAR(10),
    telefone    VARCHAR(20),
    email        VARCHAR(150),
    login        VARCHAR(50)  NOT NULL,
    senha        VARCHAR(100) NOT NULL,
    tipoUsuario   VARCHAR(20)
);