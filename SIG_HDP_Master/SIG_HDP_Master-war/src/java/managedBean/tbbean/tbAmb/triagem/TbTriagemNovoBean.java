/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.tbAmb.triagem;

import entidade.AmbConsulta;
import entidade.AmbEstado;
import entidade.GrlPessoa;
import entidade.RhFuncionario;
import entidade.TbObservacao;
import entidade.TbTriagem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.consultas.AmbConsultaCriarBean;
import sessao.AmbConsultaFacade;
import sessao.TbObservacaoFacade;
import sessao.TbTriagemFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.MetodosGerais;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTriagemNovoBean implements Serializable
{

    @EJB
    private TbTriagemFacade tbTriagemFacade;

    @EJB
    private TbObservacaoFacade tbObservacaoFacade;

    @EJB
    private AmbConsultaFacade ambConsultaFacade;

    @Resource
    private UserTransaction userTransaction;

    private TbTriagem tbTriagem;

    private TbObservacao tbObservacao;

    /**
     * Creates a new instance of TbTriagemNovoBean
     */
    public TbTriagemNovoBean()
    {
    }

    public static TbTriagemNovoBean getInstanciaBean()
    {
        return (TbTriagemNovoBean) GeradorCodigo.getInstanciaBean(
            "tbTriagemNovoBean");
    }

    public static TbTriagem getInstancia()
    {
        TbTriagem tbTriagem = new TbTriagem();
        tbTriagem.setFkConsulta(AmbConsultaCriarBean.getInstanciaAmbConsulta());
        tbTriagem.setFkEnfermeiro(new RhFuncionario());
        tbTriagem.setFkEstado(new AmbEstado());
        tbTriagem.setFkObservacao(new TbObservacao());
        return tbTriagem;
    }

    public List<AmbConsulta> getPacientes()
    {
        return ambConsultaFacade.findAll();
    }
    
    public String getPaciente(AmbConsulta c)
    {
        GrlPessoa p = c.getFkIdConsultorioAtendimento().getFkIdTriagem().
            getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().
            getFkIdPaciente().getFkIdPessoa();
        return MetodosGerais.concatenarNome(p);
    }

    public TbObservacao getTbObservacao()
    {
        if (tbObservacao == null)
        {
            tbObservacao = new TbObservacao();
        }
        return tbObservacao;
    }

    public TbTriagem getTbTriagem()
    {
        if (tbTriagem == null)
        {
            tbTriagem = new TbTriagem();
            tbTriagem.setFkConsulta(new AmbConsulta());
            tbTriagem.setFkEnfermeiro(new RhFuncionario());
            tbTriagem.setFkEstado(new AmbEstado());
//            tbTriagem.setFkObservacao(new TbObservacao());
        }
        return tbTriagem;
    }

    public void createTriagem()
    {
        try
        {
            this.userTransaction.begin();
            tbObservacaoFacade.create(tbObservacao);

            tbTriagem.setFkObservacao(tbObservacao);
            tbTriagem.setFkEnfermeiro(new RhFuncionario(1));
            tbTriagem.setFkEstado(new AmbEstado(2));
            tbTriagem.setDataHoraTriagem(new Date());
            this.tbTriagemFacade.create(tbTriagem);
            this.userTransaction.commit();
            Mensagem.sucessoMsg(TbMensagens.OK);
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.err.println("Roolback: " + ex.toString());
            }
        }
    }

}
