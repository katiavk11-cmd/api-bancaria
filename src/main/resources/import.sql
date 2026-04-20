-- 1. LIMPEZA (A ordem importa por causa das chaves estrangeiras)
DELETE FROM transacao;
DELETE FROM conta;
DELETE FROM cliente;

-- 2. CLIENTES
INSERT INTO cliente (id, nome, cpf, email, senha, role) VALUES
(1, 'Alice Silva', '000.000.000-01', 'alice.silva@bancada.com.br', '$argon2id$v=19$m=4096,t=3,p=1$c2FsdHNhbHQ$e6Y9vPj3M/oZ6Osh7Yy0Zg', 'GERENTE'),
(2, 'Bob Santos', '000.000.000-02', 'bob.santos@bancada.com.br', '$argon2id$v=19$m=4096,t=3,p=1$c2FsdHNhbHQ$e6Y9vPj3M/oZ6Osh7Yy0Zg', 'GERENTE'),
(3, 'Carlos Oliveira', '000.000.000-03', 'carlos.oliveira@bancada.com.br', '$argon2id$v=19$m=4096,t=3,p=1$c2FsdHNhbHQ$e6Y9vPj3M/oZ6Osh7Yy0Zg', 'CLIENTE');

-- 3. CONTAS
INSERT INTO conta (id, numero, tipo, cliente_id) VALUES
(1, '0001-1', 'CORRENTE', 1),
(2, '0002-2', 'POUPANCA', 2),
(3, '0003-3', 'ELETRONICA', 3);

-- 4. TRANSAÇÕES (Importante: conta_origem_id fica NULL em depósitos)
INSERT INTO transacao (id, tipo, valor, data_hora, conta_destino_id, conta_origem_id) VALUES
(1, 'DEPOSITO', 500.00, CURRENT_TIMESTAMP, 1, NULL),
(2, 'DEPOSITO', 800.00, CURRENT_TIMESTAMP, 2, NULL),
(3, 'DEPOSITO', 150.00, CURRENT_TIMESTAMP, 3, NULL);

-- 5. ATUALIZAÇÃO DAS SEQUÊNCIAS (Evita erro de ID duplicado no futuro)
ALTER TABLE cliente ALTER COLUMN id RESTART WITH 4;
ALTER TABLE conta ALTER COLUMN id RESTART WITH 4;
ALTER TABLE transacao ALTER COLUMN id RESTART WITH 4;