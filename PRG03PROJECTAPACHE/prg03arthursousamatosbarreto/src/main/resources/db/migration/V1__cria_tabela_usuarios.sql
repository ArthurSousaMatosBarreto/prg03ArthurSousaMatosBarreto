CREATE TABLE usuarios (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    txtnome         VARCHAR(150) NOT NULL,
    txtcpf          VARCHAR(14)  NOT NULL,
    combgen         VARCHAR(30),
    data_nascimento VARCHAR(10),
    txt_telefone    VARCHAR(20),
    txtemail        VARCHAR(150),
    txtlogin        VARCHAR(50)  NOT NULL,
    txtsenha        VARCHAR(100) NOT NULL,
    tipo_usuario    ENUM('ALUNO', 'PROFESSOR', 'ADMINISTRADOR')
);