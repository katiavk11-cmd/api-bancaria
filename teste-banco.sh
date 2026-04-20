#!/usr/bin/env bash
# =============================================================================
# teste-banco.sh — Script de Teste Integrado da API Bancária
# =============================================================================
# Requisitos: Ter o 'jq' instalado para processar os JSONs.
# =============================================================================
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"

separator() {
    echo -e "\n\033[1;34m─────────────────────────────────────────────────────\033[0m"
}

# 1. LOGIN — OBTENÇÃO DE TOKENS
separator
echo "==> [1] Login como CLIENTE (Bob)"
# Simulando o login com os campos que definimos no LoginRequest
RESPONSE_BOB=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H 'Content-Type: application/json' \
  -d '{"username":"bob.santos@email.com","password":"senha123"}')

echo "$RESPONSE_BOB" | jq .
TOKEN_BOB=$(echo "$RESPONSE_BOB" | jq -r '.token')

separator
echo "==> [2] Login como GERENTE (Ana)"
RESPONSE_GERENTE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H 'Content-Type: application/json' \
  -d '{"username":"ana.gerente@email.com","password":"admin123"}')

echo "$RESPONSE_GERENTE" | jq .
TOKEN_GERENTE=$(echo "$RESPONSE_GERENTE" | jq -r '.token')


# 2. TESTES DE ACESSO AO SALDO
separator
echo "==> [3] CLIENTE tentando listar todos os saldos (Deve filtrar apenas o dele)"
curl -s -H "Authorization: Bearer $TOKEN_BOB" "$BASE_URL/saldos" | jq .

separator
echo "==> [4] GERENTE listando todos os saldos (Deve ver tudo)"
curl -s -H "Authorization: Bearer $TOKEN_GERENTE" "$BASE_URL/saldos" | jq .

separator
echo "==> [5] Tentativa de acesso SEM TOKEN (Deve retornar 401 Unauthorized)"
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" \
  -X GET "$BASE_URL/saldos"


# 3. TESTE DE PROPRIEDADE (SEGURANÇA CRÍTICA)
separator
echo "==> [6] CLIENTE tentando ver saldo de outro ID (Deve retornar 403 Forbidden)"
# Supondo que o ID 99 pertença a outro cliente
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" \
  -H "Authorization: Bearer $TOKEN_BOB" \
  -X GET "$BASE_URL/saldos/99"

separator
echo -e "\n\033[1;32mTestes concluídos!\033[0m"