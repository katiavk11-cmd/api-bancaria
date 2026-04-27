📘 API Bancária — Projeto Final (Quarkus)
🧠 Contexto

API REST desenvolvida em Java 21 + Quarkus 3 + PostgreSQL, simulando um sistema bancário com clientes, contas e transações financeiras.

🧱 Tecnologias
Java 21
Quarkus 3.x
Hibernate ORM + Panache
PostgreSQL
Maven
JWT (autenticação)
BCrypt (hash de senha)
🔐 Autenticação

A API utiliza autenticação via JWT (JSON Web Token).

🔑 Login

POST /auth/login

{
"email": "alice@banco.com",
"senha": "senha123"
}
📤 Response
{
"token": "eyJhbGciOiJIUzI1NiJ9..."
}
🔒 Segurança
Senhas são armazenadas utilizando BCrypt
Nenhuma senha é salva em texto puro
O login valida senha utilizando hash BCrypt
Autenticação via JWT protege os endpoints
BCrypt é utilizado para garantir o armazenamento seguro de senhas, impedindo exposição de credenciais mesmo em caso de vazamento do banco de dados.
👤 Clientes
Criar cliente

POST /clientes

{
"nome": "Alice Souza",
"cpf": "123.456.789-00",
"email": "alice@banco.com",
"senha": "senha123"
}
Listar clientes

GET /clientes

Buscar cliente

GET /clientes/{id}

Atualizar cliente

PUT /clientes/{id}

CPF não pode ser alterado (retorna 400)

🏦 Contas
Criar conta

POST /contas

{
"numero": "0010-10",
"tipo": "POUPANCA",
"cliente": { "id": 1 }
}
Tipos de conta
CORRENTE
POUPANCA
ELETRONICA
🚫 Conta ELETRONICA
❌ Não permite depósito
❌ Não permite saque
✔ Permite transferências (envio e recebimento)
💰 Operações financeiras
💳 Depósito

POST /transacoes/depositos

{
"contaId": 1,
"valor": 500.00
}
💸 Saque

POST /transacoes/saques

{
"contaId": 1,
"valor": 200.00
}
🔁 Transferência

POST /transacoes/transferencias

{
"contaOrigemId": 1,
"contaDestinoId": 2,
"valor": 300.00
}
📜 Extrato

GET /transacoes/extrato/{contaId}

Retorna histórico completo de movimentações da conta.

⚠️ Regras de negócio
Saldo obrigatório para saque e transferência
Conta eletrônica não permite saque e depósito
Transferências são registradas para origem e destino
Todas as operações geram registro em Transacao
🚨 Códigos de erro
Situação	HTTP
Conta não encontrada	404
Saldo insuficiente	422
Conta eletrônica bloqueada	422
Dados inválidos	400
Sem permissão	403
🧱 Estrutura do projeto
resource/
service/
entity/
dto/
▶️ Como rodar o projeto
./mvnw quarkus:dev
📊 Status

✔ Clientes
✔ Contas
✔ Depósito
✔ Saque
✔ Transferência
✔ Extrato
✔ JWT
✔ BCrypt

🎯 Objetivo do projeto

Simular um sistema bancário com foco em:

arquitetura REST
regras de negócio
segurança
persistência com ORM
boas práticas de backend
👩‍💻 Observação final

Projeto desenvolvido para avaliação final com foco em backend Java e arquitetura de APIs REST seguras e consistentes.