package org.esiggroup.esiggrouptest.bean;


import org.esiggroup.esiggrouptest.model.Pessoa;
import org.esiggroup.esiggrouptest.service.PessoaService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PessoaService pessoaService;
    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas;
    private int currentPage = 1;
    private int rowsPerPage = 10;
    private List<Pessoa> paginatedPessoas;


    @PostConstruct
    public void init() {
        pessoas = pessoaService.findAll();
        updatePaginatedPessoas();
    }

    public void recalcularSalarios() {
        pessoaService.recalcularSalarios();
        pessoas = pessoaService.findAll();
        currentPage = 1;
        updatePaginatedPessoas();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salários recalculados com sucesso!"));
    }
    public void goToFirstPage() {
        currentPage = 1;
        updatePaginatedPessoas();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Voltou para a primeira página"));
    }

    public void savePessoa() {
        if (pessoa != null) {
            pessoaService.create(pessoa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pessoa criada com sucesso!"));
        } else {
            pessoaService.update(pessoa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pessoa atualizada com sucesso!"));
        }
        pessoas = pessoaService.findAll();
        pessoa = new Pessoa();
    }

    public void deletePessoa() {
        if (pessoa != null) {
            pessoaService.delete(pessoa.getPessoaId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pessoa deletada com sucesso!"));
            pessoas = pessoaService.findAll();
            pessoa = new Pessoa();
        }
    }

    public void editPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getPaginatedPessoas() {
        return paginatedPessoas;
    }

    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            System.out.println("Indo para a página: " + currentPage);
            updatePaginatedPessoas();
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            System.out.println("Voltando para a página: " + currentPage);
            updatePaginatedPessoas();
        }
    }

    public void updatePaginatedPessoas() {
        paginatedPessoas = pessoaService.getPaginatedPessoas(pessoas, currentPage, rowsPerPage);
    }

    public int getTotalPages() {
        return pessoaService.getTotalPages(pessoas, rowsPerPage);
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
