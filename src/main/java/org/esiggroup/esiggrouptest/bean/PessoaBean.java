package org.esiggroup.esiggrouptest.bean;


import org.esiggroup.esiggrouptest.model.PessoaSalarioConsolidado;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ManagedBean()
@Named
@RequestScoped
public class PessoaBean {

    @PersistenceContext
    private EntityManager em;

    private List<PessoaSalarioConsolidado> pessoas;

    // Método para listar todas as pessoas com salários consolidados
    public List<PessoaSalarioConsolidado> getPessoas() {
        if (pessoas == null) {
            pessoas = em.createQuery("SELECT p FROM PessoaSalarioConsolidado p", PessoaSalarioConsolidado.class)
                    .getResultList();
        }
        return pessoas;
    }

    // Método para recalcular salários
    @Transactional
    public void recalcularSalarios() {
        em.createQuery("DELETE FROM PessoaSalarioConsolidado").executeUpdate();

        em.createNativeQuery(
                "INSERT INTO pessoa_salario_consolidado (pessoa_id, nome_pessoa, nome_cargo, salario) " +
                        "SELECT p.id AS pessoa_id, p.nome AS nome_pessoa, c.nome AS nome_cargo, " +
                        "SUM(CASE WHEN v.tipo = 'CREDITO' THEN v.valor WHEN v.tipo = 'DEBITO' THEN -v.valor ELSE 0 END) AS salario " +
                        "FROM pessoa p JOIN cargo_vencimentos cv ON p.cargo_id = cv.cargo_id " +
                        "JOIN cargo c ON cv.cargo_id = c.id JOIN vencimentos v ON cv.vencimento_id = v.id " +
                        "GROUP BY p.id, p.nome, c.nome"
        ).executeUpdate();
    }
}
