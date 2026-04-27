package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.entity.Conta;
import br.com.katia.quarkus.apibancaria.entity.Transacao;
import br.com.katia.quarkus.apibancaria.security.AccountSecurity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.List;

@ApplicationScoped
public class ExtratoService {

    @Inject
    AccountSecurity accountSecurity;

    public List<Transacao> extratoConta(ContainerRequestContext ctx, Long contaId) {

        Conta conta = Conta.findById(contaId);

        accountSecurity.validarAcessoConta(ctx, conta);

        return Transacao.list(
                "contaOrigem = ?1 or contaDestino = ?1",
                conta
        );
    }

    public List<Transacao> extratoGeral(ContainerRequestContext ctx) {

        return Transacao.listAll();
    }
}