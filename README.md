📘 Projeto Final — API Bancária com Quarkus
📌 Contexto

API REST para gerenciamento de clientes, contas bancárias e transações (depósito, saque e transferência), com autenticação JWT e regras de negócio bancárias.

🚀 Tecnologias
Java 21
Quarkus 3.x
Maven
PostgreSQL / H2 (desenvolvimento)
JWT (autenticação)
BCrypt (hash de senhas)
Hibernate + Panache
🧱 Entidades
Cliente
id, nome, cpf, email, senha
Conta
id, numero, tipo (CORRENTE | POUPANCA | ELETRONICA), cliente_id

📌 Regra importante:

Conta ELETRÔNICA só permite TRANSFERÊNCIAS
Saque e depósito são bloqueados (erro 422)
Transação
id, tipo (DEPOSITO | SAQUE | TRANSFERENCIA),
valor, dataHora,
conta_origem_id,
conta_destino_id
🔐 Autenticação
POST /auth/login
Request
{
"email": "alice.silva@bancada.com.br",
"senha": "senha123"
}
Response 200
{
"token": "eyJhbGciOiJSUzI1NiJ9..."
}
📌 Regras
Senhas armazenadas com BCrypt
Autenticação via JWT
Token deve ser enviado:
Authorization: Bearer <token>
👤 Usuário de teste
Email: alice.silva@bancada.com.br
Senha: senha123
👤 Clientes
POST /clientes

Cria cliente

GET /clientes

Lista clientes

GET /clientes/{id}

Busca cliente

PUT /clientes/{id}

Atualiza cliente (CPF não pode ser alterado)

🏦 Contas
POST /contas
{
"numero": "0010-10",
"tipo": "POUPANCA",
"cliente": { "id": 1 }
}
GET /contas/{id}

Retorna conta com saldo e cliente.

💰 Transações
📥 Depósito
POST /transacoes/depositos
{
"contaId": 1,
"valor": 100.00
}
Regras
❌ Conta ELETRÔNICA não permite depósito
✔ Conta normal permite
📤 Saque
POST /transacoes/saques
{
"contaId": 1,
"valor": 50.00
}
Regras
❌ Conta ELETRÔNICA não permite saque
❌ saldo insuficiente retorna erro
🔁 Transferência
POST /transacoes/transferencias
{
"contaOrigemId": 1,
"contaDestinoId": 2,
"valor": 100.00
}

✔ Permitido para qualquer tipo de conta (inclusive ELETRÔNICA)

📜 Extrato
GET /transacoes/extrato/{contaId}

Retorna histórico completo da conta.

⚠️ Regras de Negócio
Conta ELETRÔNICA:
❌ não permite saque
❌ não permite depósito
✔ permite transferência
Saldo nunca pode ficar negativo
Operações inválidas retornam erro 400 ou 422
Recursos inexistentes retornam 404
🔐 Segurança
JWT protege endpoints
Roles:
GERENTE
CLIENTE
BCrypt usado para senha
▶️ Como rodar
./mvnw quarkus:dev

A aplicação sobe em:

http://localhost:8080
📊 Resumo de Avaliação

✔ CRUD completo
✔ autenticação JWT
✔ regras de negócio bancárias
✔ transações consistentes
✔ validação de erros
✔ arquitetura em camadas

🧠 Observação final

Este projeto implementa um sistema bancário simplificado com separação entre clientes, contas e transações, aplicando segurança e regras de domínio.