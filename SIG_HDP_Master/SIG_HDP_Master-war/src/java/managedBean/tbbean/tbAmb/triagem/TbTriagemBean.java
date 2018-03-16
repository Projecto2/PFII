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
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import managedBean.ambbean.ce.consultas.AmbConsultaCriarBean;
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
public class TbTriagemBean implements Serializable
{
    @EJB
    private TbTriagemFacade tbTriagemFacade;

    private List<TbTriagem> listaTriagem;
    
    private TbTriagem tbTriagem;
    
    /**
     * Creates a new instance of TbTriagemBean
     */
    public TbTriagemBean()
    {
    }
    
    public static TbTriagemBean getInstanciaBean()
    {
        return (TbTriagemBean) GeradorCodigo.getInstanciaBean("tbTriagemBean");
    }
    
    public static TbTriagem getInstancia()
    {
        TbTriagem tbTriagem = new TbTriagem();
        tbTriagem.setFkConsulta(AmbConsultaCriarBean.getInstanciaAmbConsulta());
        tbTriagem.setFkEnfermeiro(new RhFuncionario());
        tbTriagem.setFkObservacao(new TbObservacao());   
        tbTriagem.setFkEstado(new AmbEstado());
        return tbTriagem;
    }
    
    public void pesquisarTriagem()
    {
        listaTriagem = tbTriagemFacade.findTriagem(new TbTriagem());
        if (listaTriagem.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaTriagem.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbTriagem> getListaTriagem()
    {
        return tbTriagemFacade.findAll();
    }
    
    public String getPaciente(AmbConsulta c)
    {
        GrlPessoa p = c.getFkIdConsultorioAtendimento().getFkIdTriagem().
            getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().
            getFkIdPaciente().getFkIdPessoa();
        return MetodosGerais.concatenarNome(p);
    }
    
    public String getPacienteHistorico(AmbConsulta c)
    {
        GrlPessoa p = c.getFkIdConsultorioAtendimento().getFkIdTriagem().
            getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().
            getFkIdPaciente().getFkIdPessoa();
        return MetodosGerais.concatenarNome(p);
    }
    
    public String getEnfermeiro(RhFuncionario f)
    {
        return MetodosGerais.concatenarNome(f.getFkIdPessoa());
    }
    
    public TbTriagem findTriagem(int pk_triagem)
    {
        TbTriagem t = null;
        try
        {
            t = tbTriagemFacade.find(pk_triagem);
            Mensagem.sucessoMsg(TbMensagens.SUCESSO);
        }
        catch (Exception e)
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
            System.err.println("Roolback: " + e.toString());
        }
        return t;
    }
    
    public TbTriagem getTbTriagem()
    {
        return tbTriagem;
    }

    public void setTbTriagem(TbTriagem tbTriagem)
    {
        this.tbTriagem = tbTriagem;
    }
    
}
