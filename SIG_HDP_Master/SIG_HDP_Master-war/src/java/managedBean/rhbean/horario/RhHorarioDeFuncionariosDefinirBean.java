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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
public class RhHorarioDeFuncionariosDefinirBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhHorarioGeralDeTrabalhoFacade horarioGeralDeTrabalhoFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;

    /**
     *
     * Entidades
     */
    private DualListModel<RhFuncionario> funcionarios;

    /**
     * Creates a new instance of RhHorarioDeFuncionariosDefinirBean
     */
    public RhHorarioDeFuncionariosDefinirBean ()
    {
    }

    @PostConstruct
    public void init ()
    {
        List<RhFuncionario> funcionarioSource = findAllFuncionariosSemHorarioGeral();
        List<RhFuncionario> funcionarioTarget = new ArrayList<>();

        funcionarios = new DualListModel<>(funcionarioSource, funcionarioTarget);
    }

    private List<RhFuncionario> findAllFuncionariosSemHorarioGeral ()
    {
        return funcionarioFacade.findAllSemHorarioGeral();
    }

    public RhFuncionarioFacade getFuncionarioFacade ()
    {
        return funcionarioFacade;
    }

    public void setFuncionarioFacade (RhFuncionarioFacade funcionarioFacade)
    {
        this.funcionarioFacade = funcionarioFacade;
    }

    public DualListModel<RhFuncionario> getFuncionarios ()
    {
        return funcionarios;
    }

    public void setFuncionarios (DualListModel<RhFuncionario> funcionarios)
    {
        this.funcionarios = funcionarios;
    }

    public void guardarAlteracoes ()
    {
        final String HORARIO_FIXO = "Horário Fixo";
        if (horarioGeralDeTrabalhoFacade.findAll().isEmpty())
        {
            Mensagem.erroMsg("O horário geral de trabalho ainda não foi definido");
        }
        try
        {
            userTransaction.begin();

            RhHorarioGeralDeTrabalho horario = horarioGeralDeTrabalhoFacade.findAll().get(0);

            int contSD = 0;
            int contWD = 0;
            StringBuilder sucessoDefinir = new StringBuilder();
            StringBuilder warningDefinir = new StringBuilder();

            for (RhFuncionario f : funcionarios.getTarget())
            {
                if (f.getFkIdTipoDeHorarioTrabalho().getDescricao().equalsIgnoreCase(HORARIO_FIXO))
                {
                    sucessoDefinir.append(", ").append(f.getFkIdPessoa().getNome()).append(" ").
                            append(f.getFkIdPessoa().getNomeDoMeio()).append(" ").
                            append(f.getFkIdPessoa().getSobreNome()).
                            append(" - Cartão Nº ").append(f.getNumeroCartao()).append(" ");

                    f.setFkIdHorarioGeralDeTrabalho(horario);
                    funcionarioFacade.edit(f);
                    contSD++;
                }
                else
                {
                    warningDefinir.append(", ").append(f.getFkIdPessoa().getNome()).append(" ").
                            append(f.getFkIdPessoa().getNomeDoMeio()).append(" ").
                            append(f.getFkIdPessoa().getSobreNome()).
                            append(" - Cartão Nº ").append(f.getNumeroCartao()).append(" ");
                    contWD++;
                }
            }

            userTransaction.commit();

            FacesMessage msgSucesso = new FacesMessage(FacesMessage.SEVERITY_INFO, "Horário definido com sucesso para ", sucessoDefinir.toString());
            FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_WARN, "Horário não definido para ", warningDefinir.append(" PORQUE O SEU TIPO/REGIME DE HORÁRIO DE TRABALHO NÃO É FIXO").toString());

            if (contSD > 0)
            {
                FacesContext.getCurrentInstance().addMessage(null, msgSucesso);
            }
            if (contWD > 0)
            {
                FacesContext.getCurrentInstance().addMessage(null, msgErro);
            }

            if(contSD <= 0 && contWD <= 0)
                Mensagem.erroMsg("Nenhum funcionário selecionado !");
            
            funcionarios = new DualListModel<>(findAllFuncionariosSemHorarioGeral(), new ArrayList<RhFuncionario>());
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro ! Não foi possível guardar as alterações");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public void onTransfer (TransferEvent event)
    {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems())
        {
            RhFuncionario f = (RhFuncionario) item;

            builder.append(f.getFkIdPessoa().getNome()).append(" ").
                    append(f.getFkIdPessoa().getNomeDoMeio()).append(" ").
                    append(f.getFkIdPessoa().getSobreNome()).append(" ").append("<br />");
        }

        System.out.println("Itens Transferidos: " + builder.toString());
    }

    public void onSelect (SelectEvent event)
    {
        System.out.println("Item Selecionado: " + event.getObject().toString());
    }

    public void onUnselect (UnselectEvent event)
    {
        System.out.println("Item Unselected" + event.getObject().toString());
    }

    public void onReorder ()
    {
        System.out.println("Lista Reordenada");
    }
}
