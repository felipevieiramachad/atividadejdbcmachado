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
### Tabelas:
* **Cliente** (id\_cliente, nome, cpf, telefone)
* **Imovel** (id\_imovel, endereco, tipo, valor\_aluguel, status)
* **Contrato** (id\_contrato, id\_imovel, id\_cliente, valor, data\_inicio, data\_fim, status)
