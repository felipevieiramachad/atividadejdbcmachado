CREATE DATABASE imobiliaria;
USE imobiliaria;

CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE Imovel (
    id_imovel INT AUTO_INCREMENT PRIMARY KEY,
    endereco VARCHAR(150) NOT NULL,
    tipo VARCHAR(50),
    valor_aluguel DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'disponivel'
);

CREATE TABLE Contrato (
    id_contrato INT AUTO_INCREMENT PRIMARY KEY,
    id_imovel INT,
    id_cliente INT,
    valor DECIMAL(10,2) NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    status VARCHAR(20) DEFAULT 'ativo',
    FOREIGN KEY (id_imovel) REFERENCES Imovel(id_imovel),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

INSERT INTO Cliente (nome, cpf, telefone) VALUES
('João Silva', '12345678900', '1199999999'),
('Maria Souza', '98765432100', '1198888888');

INSERT INTO Imovel (endereco, tipo, valor_aluguel, status) VALUES
('Rua A, 100', 'Apartamento', 1500.00, 'disponivel'),
('Rua B, 200', 'Casa', 2000.00, 'disponivel');

SELECT c.id_cliente, c.nome, COUNT(co.id_contrato) AS total_contratos
FROM Cliente c
JOIN Contrato co ON c.id_cliente = co.id_cliente
GROUP BY c.id_cliente, c.nome
ORDER BY total_contratos DESC;

SELECT *
FROM Contrato
WHERE data_fim BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
AND status = 'ativo';
