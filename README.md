# atividadejdbcmachado
# Sistema de Gestão Imobiliária
Este projeto foi desenvolvido como atividade acadêmica para praticar **Java** e **Banco de Dados**.
O sistema permite o gerenciamento de imóveis, clientes e contratos de aluguel, com relatórios acessíveis via **terminal**.
## Funcionalidades
* Cadastrar **imóveis** com suas características (endereço, tipo, valor de aluguel, status).
* Cadastrar **clientes** (nome, CPF, telefone).
* Cadastrar **contratos de aluguel** (imóvel, cliente, valor, data de início, data de fim, status).
### Relatórios disponíveis:
1. Listar imóveis disponíveis para aluguel.
2. Listar contratos ativos.
3. Mostrar clientes com mais contratos.
4. Contratos que expiram nos próximos 30 dias.
## Tecnologias Utilizadas
* **Java** (JDK 17+)
* **MySQL** (ou outro banco relacional compatível)
* **JDBC** para integração entre Java e o banco de dados.
## Banco de Dados
O script `schema.sql` contém a criação das tabelas e inserção de dados de exemplo.
### Criação do banco:
```sql
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
```
### Como Executar
1. Importar o projeto no seu IDE (Eclipse, IntelliJ ou NetBeans).
2. Configurar o banco de dados:
* Criar o banco rodando o script `schema.sql` no MySQL.
* Ajustar usuário e senha no arquivo Database.java:
```sql
Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/imobiliaria",
    "root", "sua_senha"
);
```
3. Executar o programa:
* Rodar a classe Main.
* O menu aparecerá no terminal.
### Tabelas:
* **Cliente** (id\_cliente, nome, cpf, telefone)
* **Imovel** (id\_imovel, endereco, tipo, valor\_aluguel, status)
* **Contrato** (id\_contrato, id\_imovel, id\_cliente, valor, data\_inicio, data\_fim, status)
