CREATE DATABASE saep_db;

CREATE TABLE usuario (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE produto (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    material VARCHAR(100),
    sku VARCHAR(50) UNIQUE NOT NULL,
    categoria VARCHAR(100),
    tamanho INT,
    peso INT,
    revestimento_isolante VARCHAR(100)
);

CREATE TABLE estoque (
    id VARCHAR(36) PRIMARY KEY,
    quantidade INT NOT NULL,
    localizacao VARCHAR(100),
    produto_id VARCHAR(36),
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);

INSERT INTO usuario (id, nome, email, senha) VALUES
('11111111-1111-1111-1111-111111111111', 'Rafael Macharete', 'rafael@saep.com', '$2a$12$R9h/cIPz0gi.URNNX3cl2upOX90TMcO3p8piCwLp8Zq.bOSie/.iO'),
('11111111-1111-1111-1111-111111111112', 'Lindomar Batistao', 'lindomar@saep.com', '$2a$12$R9h/cIPz0gi.URNNX3cl2upOX90TMcO3p8piCwLp8Zq.bOSie/.iO'),
('11111111-1111-1111-1111-111111111113', 'Usuario Teste', 'teste@saep.com', '$2a$12$R9h/cIPz0gi.URNNX3cl2upOX90TMcO3p8piCwLp8Zq.bOSie/.iO');

INSERT INTO produto (id, nome, material, sku, categoria, tamanho, peso, revestimento_isolante) VALUES
('22222222-2222-2222-2222-222222222221', 'Martelo', 'Aço', 'MAR-001', 'Manuais', 30, 500, 'Nenhum'),
('22222222-2222-2222-2222-222222222222', 'Chave de Fenda', 'Aço', 'CHV-002', 'Manuais', 15, 100, 'Isolante'),
('22222222-2222-2222-2222-222222222223', 'Alicate', 'Aço', 'ALI-003', 'Manuais', 20, 300, 'Borracha');

INSERT INTO estoque (id, quantidade, localizacao, produto_id) VALUES
('33333333-3333-3333-3333-333333333331', 50, 'Setor A', '22222222-2222-2222-2222-222222222221'),
('33333333-3333-3333-3333-333333333332', 120, 'Setor B', '22222222-2222-2222-2222-222222222222'),
('33333333-3333-3333-3333-333333333333', 5, 'Setor C', '22222222-2222-2222-2222-222222222223');