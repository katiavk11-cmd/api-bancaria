package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.dto.ContaResponse;
import br.com.katia.quarkus.apibancaria.entity.Cliente;
import br.com.katia.quarkus.apibancaria.entity.Conta;
import br.com.katia.quarkus.apibancaria.entity.TipoConta;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ContaService {

    @Transactional
    public ContaResponse criarConta(String numero, Long clienteId, TipoConta tipo, BigDecimal saldoInicial) {

        if (numero == null || clienteId == null) {
            throw new WebApplicationException("numero e clienteId obrigatórios", 400);
        }

        Conta existente = Conta.find("numero", numero).firstResult();
        if (existente != null) {
            throw new WebApplicationException("Já existe conta com esse número", 409);
        }

        Cliente cliente = Cliente.findById(clienteId);
        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado", 404);
        }

        Conta conta = new Conta();
        conta.numero = numero;
        conta.tipo = tipo;
        conta.cliente = cliente;
        conta.saldo = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;

        conta.persist();

        return toResponse(conta);
    }

    public List<ContaResponse> listarContas() {
        return Conta.listAll()
                .stream()
                .map(c -> toResponse((Conta) c))
                .toList();
    }

    public ContaResponse buscarPorId(Long id) {

        Conta conta = Conta.findById(id);

        if (conta == null) {
            throw new WebApplicationException("Conta não encontrada", 404);
        }

        return toResponse(conta);
    }

    private ContaResponse toResponse(Conta c) {
        return new ContaResponse(
                c.id,
                c.numero,
                c.tipo.name(),
                c.saldo,
                c.cliente.id
        );
    }
}