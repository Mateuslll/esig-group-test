package org.esiggroup.esiggrouptest.repository;

import org.esiggroup.esiggrouptest.model.Pessoa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class PessoaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Pessoa pessoa) {
        em.persist(pessoa);
    }

    @Transactional
    public Pessoa findById(int id) {
        return em.find(Pessoa.class, id);
    }

    @Transactional
    public void update(Pessoa pessoa) {
        em.merge(pessoa);
    }

    @Transactional
    public void delete(int id) {
        Pessoa pessoa = findById(id);
        if (pessoa != null) {
            em.remove(pessoa);
        }
    }
    @Transactional
    public List<Pessoa> findAllOrderedById() {
        String jpql = "SELECT p FROM Pessoa p ORDER BY p.pessoaId ASC";
        TypedQuery<Pessoa> query = em.createQuery(jpql, Pessoa.class);
        return query.getResultList();
    }
    @Transactional
    public void recalcularSalarios() {
        em.createQuery("DELETE FROM Pessoa").executeUpdate();

        em.createNativeQuery(
                "INSERT INTO pessoa_salario_consolidado (pessoa_id, nome_pessoa, nome_cargo, salario) " +
                        "SELECT p.id AS pessoa_id, p.nome AS nome_pessoa, c.nome AS nome_cargo, " +
                        "SUM(CASE WHEN v.tipo = 'CREDITO' THEN v.valor WHEN v.tipo = 'DEBITO' THEN -v.valor ELSE 0 END) AS salario " +
                        "FROM pessoa p " +
                        "JOIN cargo_vencimentos cv ON p.cargo_id = cv.cargo_id " +
                        "JOIN cargo c ON cv.cargo_id = c.id " +
                        "JOIN vencimentos v ON cv.vencimento_id = v.id " +
                        "GROUP BY p.id, p.nome, c.nome"
        ).executeUpdate();
    }
}
