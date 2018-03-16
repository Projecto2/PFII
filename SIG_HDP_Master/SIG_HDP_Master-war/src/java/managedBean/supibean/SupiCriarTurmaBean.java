/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.RhEstagiario;
import entidade.RhFuncionario;
import entidade.RhSeccaoTrabalho;
import entidade.SupiCriacaoTurma;
import entidade.SupiTurmaEstagiario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.RhEstagiarioFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhSeccaoTrabalhoFacade;
import sessao.SupiCriacaoTurmaFacade;
import sessao.SupiTurmaEstagiarioFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiCriarTurmaBean implements Serializable
{

    @EJB
    private RhSeccaoTrabalhoFacade rhSeccaoTrabalhoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private RhEstagiarioFacade rhEstagiarioFacade;

    @EJB
    private SupiTurmaEstagiarioFacade supiTurmaEstagiarioFacade;
    @EJB
    private SupiCriacaoTurmaFacade supiCriacaoTurmaFacade;
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of SupiCriarTurmaBean
     */
    private SupiCriacaoTurma supiCriacaoTurma, pesquisarCriacaoTurma;
    private List<SupiCriacaoTurma> listaSupiCriacaoTurma;
    private List<SupiTurmaEstagiario> listaSupiTurmaEstagiario;

    private RhEstagiario rhEstagiario;
    private RhFuncionario rhFuncionario;
    private RhSeccaoTrabalho rhSeccaoTrabalho;
    private int listaDeEstagiarios[];

    public SupiCriarTurmaBean()
    {
    }

    public static SupiCriarTurmaBean getInstanciaBean()
    {
        return (SupiCriarTurmaBean) GeradorCodigo.getInstanciaBean("supiCriarTurmaBean");
    }

    public static SupiCriacaoTurma getInstancia()
    {

        SupiCriacaoTurma item = new SupiCriacaoTurma();
        SupiTurmaEstagiario turma = new SupiTurmaEstagiario();

        item.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());
        item.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());

        return item;
    }

    public SupiCriacaoTurma getSupiCriacaoTurma()
    {
        if (supiCriacaoTurma == null)
        {
            supiCriacaoTurma = SupiCriarTurmaBean.getInstancia();
        }

        return supiCriacaoTurma;
    }

    public void setSupiCriacaoTurma(SupiCriacaoTurma supiCriacaoTurma)
    {
        this.supiCriacaoTurma = supiCriacaoTurma;
    }

    public int[] getListaDeEstagiarios()
    {
        return listaDeEstagiarios;
    }

    public void setListaDeEstagiarios(int[] listaDeEstagiarios)
    {
        this.listaDeEstagiarios = listaDeEstagiarios;
    }

    public List<SupiTurmaEstagiario> getListaSupiTurmaEstagiario()
    {
        return listaSupiTurmaEstagiario;
    }

    public void setListaSupiTurmaEstagiario(List<SupiTurmaEstagiario> listaSupiTurmaEstagiario)
    {
        this.listaSupiTurmaEstagiario = listaSupiTurmaEstagiario;
    }

    public List<SupiCriacaoTurma> getListaSupiCriacaoTurma()
    {
        return listaSupiCriacaoTurma;
    }

    public void setListaSupiCriacaoTurma(List<SupiCriacaoTurma> listaSupiCriacaoTurma)
    {
        this.listaSupiCriacaoTurma = listaSupiCriacaoTurma;
    }

    public void verEstagiariosTurma(int idTurma)
    {
        listaSupiTurmaEstagiario = supiTurmaEstagiarioFacade.findEstagiariosPorTurma(idTurma);

    }

    public SupiCriacaoTurma getPesquisarCriacaoTurma()
    {
        if (pesquisarCriacaoTurma == null)
        {
            pesquisarCriacaoTurma = SupiCriarTurmaBean.getInstancia();
        }
        return pesquisarCriacaoTurma;
    }

    public void setPesquisarCriacaoTurma(SupiCriacaoTurma pesquisarCriacaoTurma)
    {
        this.pesquisarCriacaoTurma = pesquisarCriacaoTurma;
    }

    //Para Meter na Página turmaNovaSupi.xhtml, no botão eliminar: actionListener="#{supiCriterioBean.setCriterioPesquisa(cargo)}"
                                            //    onclick="PF('dialogEliminar').show()" update="tabela"
    
    /* public void pesquisarCriacao()
     {
     //listaSupiCriacaoTurma = supiCriacaoTurmaFacade.findCriacaoTurma(pesquisarCriacaoTurma);

     if (listaSupiCriacaoTurma.isEmpty())
     {
     Mensagem.erroMsg("Nenhuma turma encontrada para esta pesquisa");
     }
     }*/
//    public void limparPesquisas()
//    {
//        setPesquisarCriacaoTurma(SupiCriarTurmaBean.getInstancia());
//        listaSupiCriacaoTurma = new ArrayList<>();
//    }
//    public void eliminar()
//    {
//        try
//        {
//            supiCriacaoTurmaFacade.remove(pesquisarCriacaoTurma);
//            pesquisarCriacaoTurma = SupiCriarTurmaBean.getInstancia();
//            pesquisarCriacao();
//            Mensagem.sucessoMsg("A Turma foi eliminada com sucesso!");
//        } catch (Exception ex)
//        {
//            Mensagem.warnMsg("A Turma não pode ser eliminada, porque está a ser utilizada.");
//        }
//
//    }
    public void limpar()
    {
        listaDeEstagiarios = null;

    }

    public List<SupiCriacaoTurma> findallTurma()
    {
        return supiCriacaoTurmaFacade.findAll();
    }

    public List<RhEstagiario> findall()
    {
        return rhEstagiarioFacade.findAll();
    }

    public List<RhFuncionario> findallFuncionario()
    {
        return rhFuncionarioFacade.findAll();
    }

    public List<RhSeccaoTrabalho> findallSeccao()
    {
        return rhSeccaoTrabalhoFacade.findAll();
    }

    public void salvaTurma()
    {
        try
        {
            userTransaction.begin();

            supiCriacaoTurmaFacade.create(supiCriacaoTurma);

            for (int idEstagiario : listaDeEstagiarios)
            {
                SupiTurmaEstagiario supiTurmaEstagiario = new SupiTurmaEstagiario();
                supiTurmaEstagiario.setFkIdCriacaoTurma(supiCriacaoTurma);
                supiTurmaEstagiario.setFkIdEstagiario(new RhEstagiario(idEstagiario));

                supiTurmaEstagiarioFacade.create(supiTurmaEstagiario);
            }

            userTransaction.commit();

            Mensagem.sucessoMsg("Turma Gravada com Sucesso!!!");
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro!");
                userTransaction.rollback();
            } catch (SystemException | IllegalStateException | SecurityException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

    }

}
