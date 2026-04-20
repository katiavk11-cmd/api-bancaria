#!/usr/bin/env bash
set -euo pipefail

BASE_URL="http://localhost:8080"

printf '\n==> Criando Cliente Bob\n'
curl -sS -X POST "$BASE_URL/clientes" \
  -H 'Content-Type: application/json' \
  -d '{
    "nome": "Bob Santos",
    "email": "bob.santos@bancada.com.br",
    "senha": "senha123",
    "role": "GERENTE"
  }'

printf '\n\n==> Listando Clientes\n'
curl -sS "$BASE_URL/clientes"