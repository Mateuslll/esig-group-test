package org.esiggroup.esiggrouptest.service;

import org.esiggroup.esiggrouptest.model.Pessoa;
import org.esiggroup.esiggrouptest.repository.PessoaRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Stateless
public class PessoaService {
    @Inject
    private PessoaRepository pessoaRepository;

    @PersistenceContext
    private transient EntityManager em;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Pessoa> findAll() {
        return pessoaRepository.findAllOrderedById();
    }

    public Pessoa findById(int id) {
        return pessoaRepository.findById(id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void create(Pessoa pessoa) {
        pessoaRepository.persist(pessoa);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(Pessoa pessoa) {
        pessoaRepository.update(pessoa);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void delete(int id) {
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa != null) {
            pessoaRepository.delete(pessoa.getPessoaId());
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Pessoa> getPaginatedPessoas(List<Pessoa> pessoas, int currentPage, int rowsPerPage) {
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, pessoas.size());

        if (start >= 0 && start < pessoas.size()) {
            return pessoas.subList(start, end);
        } else {
            return Collections.emptyList();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void recalcularSalarios() {
        pessoaRepository.recalcularSalarios();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int getTotalPages(List<Pessoa> pessoas, int rowsPerPage) {
        return (int) Math.ceil((double) pessoas.size() / rowsPerPage);
    }
}
