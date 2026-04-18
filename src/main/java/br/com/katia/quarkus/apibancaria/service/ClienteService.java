package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.model.Cliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ClienteService {

    // Método para listar todos (o Postman vai usar isso logo mais)
    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }

    // Método para salvar um novo cliente
    @Transactional
    public void salvar(Cliente cliente) {
        cliente.persist();
    }

    // Método para buscar por ID
    public Cliente buscarPorId(Long id) {
        return Cliente.findById(id);
    }
}