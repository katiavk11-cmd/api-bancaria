package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.entity.Conta;
import br.com.katia.quarkus.apibancaria.entity.TipoConta;
import br.com.katia.quarkus.apibancaria.entity.Transacao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class TransacaoService {

    // =========================
    // DEPÓSITO
    // =========================
    @Transactional
    public void depositar(Long contaId, BigDecimal valor) {

        Conta conta = Conta.findById(contaId);

        if (conta == null) {
            throw new WebApplicationException("{\"erro\":\"Conta não encontrada\"}", 404);
        }

        if (conta.tipo == TipoConta.ELETRONICA) {
            throw new WebApplicationException(
                    "{\"erro\":\"Conta eletrônica não permite depósito.\"}",
                    422
            );
        }

        conta.saldo = conta.saldo.add(valor);
        conta.persist();

        Transacao t = new Transacao();
        t.tipo = "DEPOSITO";
        t.valor = valor;
        t.dataHora = LocalDateTime.now();
        t.contaDestinoId = contaId;
        t.persist();
    }

    // =========================
    // SAQUE
    // =========================
    @Transactional
    public void sacar(Long contaId, BigDecimal valor) {

        Conta conta = Conta.findById(contaId);

        if (conta == null) {
            throw new WebApplicationException("{\"erro\":\"Conta não encontrada\"}", 404);
        }

        if (conta.tipo == TipoConta.ELETRONICA) {
            throw new WebApplicationException(
                    "{\"erro\":\"Conta eletrônica não permite saque.\"}",
                    422
            );
        }

        if (conta.saldo.compareTo(valor) < 0) {
            throw new WebApplicationException("{\"erro\":\"Saldo insuficiente\"}", 422);
        }

        conta.saldo = conta.saldo.subtract(valor);
        conta.persist();

        Transacao t = new Transacao();
        t.tipo = "SAQUE";
        t.valor = valor;
        t.dataHora = LocalDateTime.now();
        t.contaOrigemId = contaId;
        t.persist();
    }

    // =========================
    // TRANSFERÊNCIA (LIBERADA PARA TODOS)
    // =========================
    @Transactional
    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {

        Conta origem = Conta.findById(origemId);
        Conta destino = Conta.findById(destinoId);

        if (origem == null || destino == null) {
            throw new WebApplicationException("{\"erro\":\"Conta não encontrada\"}", 404);
        }

        if (origem.saldo.compareTo(valor) < 0) {
            throw new WebApplicationException("{\"erro\":\"Saldo insuficiente\"}", 422);
        }

        origem.saldo = origem.saldo.subtract(valor);
        destino.saldo = destino.saldo.add(valor);

        origem.persist();
        destino.persist();

        Transacao t = new Transacao();
        t.tipo = "TRANSFERENCIA";
        t.valor = valor;
        t.dataHora = LocalDateTime.now();
        t.contaOrigemId = origemId;
        t.contaDestinoId = destinoId;
        t.persist();
    }
}