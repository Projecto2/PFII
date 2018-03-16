/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.horario;

import entidade.RhFuncionario;
import entidade.RhHorarioGeralDeTrabalho;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import sessao.RhFuncionarioFacade;
import sessao.RhHorarioGeralDeTrabalhoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhHorarioDeFuncionariosRemoverBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhFuncionarioFacade funcionarioFacade;

    /**
     *
     * Entidades
     */
    private String nomeCompleto;
    private RhFuncionario funcionarioHorarioRemover;

    /**
     * Creates a new instance of RhHorarioDeFuncionariosDefinirBean
     */
    public RhHorarioDeFuncionariosRemoverBean ()
    {
    }

    public String getNomeCompleto ()
    {
        return nomeCompleto;
    }

    public void setNomeCompleto (String nomeCompleto)
    {
        this.nomeCompleto = nomeCompleto;
    }

    public void setFuncionarioHorarioRemover (RhFuncionario funcionarioHorarioRemover)
    {
        this.funcionarioHorarioRemover = funcionarioHorarioRemover;
    }
    
    public List<RhFuncionario> getFuncionariosComHorarioGeralList ()
    {
        List<RhFuncionario> funcionarios = new ArrayList<>();
        
        for (RhFuncionario f: funcionarioFacade.findContainsNome(nomeCompleto))
        {
            if(f.getFkIdHorarioGeralDeTrabalho() != null)
                funcionarios.add(f);
        }
        
        return funcionarios;
    }

    public void removerHorarioFuncionario ()
    {
        try
        {
            userTransaction.begin();

            funcionarioHorarioRemover.setFkIdHorarioGeralDeTrabalho(null);

            funcionarioFacade.edit(funcionarioHorarioRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("O horário de trabalho do funcionário " 
                                + funcionarioHorarioRemover.getFkIdPessoa().getNome() + " " 
                                + funcionarioHorarioRemover.getFkIdPessoa().getNomeDoMeio() + " " 
                                + funcionarioHorarioRemover.getFkIdPessoa().getSobreNome() + " removido com sucesso !");
            
            funcionarioHorarioRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro ! Não foi possível remover o horário de trabalho");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

}
