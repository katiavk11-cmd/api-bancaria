package br.com.katia.quarkus.apibancaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("""
    SELECT 
        co.id as id, 
        co.cliente_id as clienteId, 
        co.numero as numero, 
        co.tipo as tipo,
        (SELECT COALESCE(SUM(t.valor), 0) FROM transacao t WHERE t.conta_destino_id = co.id) - 
        (SELECT COALESCE(SUM(t.valor), 0) FROM transacao t WHERE t.conta_origem_id = co.id) as saldo
    FROM conta co
    """)
public class ViewSaldo extends PanacheEntityBase {
    @Id
    public Long id;
    public Long clienteId;
    public String numero;
    public String tipo;
    public Double saldo;
}