/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterEnfermaria;
import entidade.InterRegistoInternamento;
import entidade.InterTipoAlta;
import entidade.InterTituloAlta;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
//import managedBean.segbean.SegRenderizarInternamentoBean;
import sessao.InterEnfermariaFacade;
import sessao.InterRegistoInternamentoFacade;
import sessao.InterTipoAltaFacade;
import sessao.InterTituloAltaFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTituloAltaBean implements Serializable
{

    @EJB
    private InterTipoAltaFacade interTipoAltaFacade;

    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;

    @EJB
    private InterTituloAltaFacade interTituloAltaFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterTituloAlta tituloAlta;

    private InterRegistoInternamento registoInternamento;

    private final Calendar dataCorrente = Calendar.getInstance();

    private List<InterTituloAlta> listaTituloAlta;

    private String numeroTituloPesq, tipoAltaPesq;

    private Date dataAltaPesq1, dataAltaPesq2;

    private SegConta segConta;

    /**
     * Creates a new instance of InterTituloAltaBean
     */
    public InterTituloAltaBean()
    {
    }

    public static InterTituloAltaBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterTituloAltaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interTituloAltaBean");
    }
    
    public InterTituloAlta getInstancia()
    {
        InterTituloAlta t = new InterTituloAlta();
        t.setFkIdRegistoInternamento(new InterRegistoInternamento());
        t.setFkIdFuncionario(new RhFuncionario());
        t.setFkIdTipoAlta(new InterTipoAlta());

        return t;
    }

    public InterTituloAlta getTituloAlta()
    {
        if (tituloAlta == null)
            tituloAlta = getInstancia();
        return tituloAlta;
    }

    public void setTituloAlta(InterTituloAlta tituloAlta)
    {
        this.tituloAlta = tituloAlta;
    }

    public List<InterTituloAlta> getListaTituloAlta()
    {
        return listaTituloAlta;
    }

    public String getNumeroTituloPesq()
    {
        return numeroTituloPesq;
    }

    public void setNumeroTituloPesq(String numeroTituloPesq)
    {
        this.numeroTituloPesq = numeroTituloPesq;
    }

    public Date getDataAltaPesq1()
    {
        return dataAltaPesq1;
    }

    public void setDataAltaPesq1(Date dataAltaPesq1)
    {
        this.dataAltaPesq1 = dataAltaPesq1;
    }

    public Date getDataAltaPesq2()
    {
        return dataAltaPesq2;
    }

    public void setDataAltaPesq2(Date dataAltaPesq2)
    {
        this.dataAltaPesq2 = dataAltaPesq2;
    }

    public String getTipoAltaPesq()
    {
        return tipoAltaPesq;
    }

    public void setTipoAltaPesq(String tipoAltaPesq)
    {
        this.tipoAltaPesq = tipoAltaPesq;
    }

    public void setListaTituloAlta(List<InterTituloAlta> listaTituloAlta)
    {
        this.listaTituloAlta = listaTituloAlta;
    }

    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }

    public String numeroTituloaAlta()
    {
        segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        List<InterTituloAlta> lista = interTituloAltaFacade.pesquisarPorTipoServico(tipoServico);
        List<InterEnfermaria> listaEnf = interEnfermariaFacade.listarEnfermariasPorTipo(tipoServico);

        if (lista.isEmpty())
        {
            return 1 + " / " + listaEnf.get(0).getFkIdServico().getCodServico();
        }
        return (lista.size() + 1) + " / " + listaEnf.get(0).getFkIdServico().getCodServico();
    }

    public String salvar()
    {
        String tipoS = "";

//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoPediatria())
//        {
//            tipoS = "Internamento Pediatrico";
//        }
//
//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoCNT())
//        {
//            tipoS = "Centro Nutricional Terapêutico";
//        }
//
//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoMedicina())
//        {
            tipoS = "Internamento Medicina";
//        }

        try
        {
            userTransaction.begin();

            tituloAlta.setNumeroTituloAlta(numeroTituloaAlta());
            tituloAlta.getFkIdRegistoInternamento().setPkIdRegistoInternamento(registoInternamento.getPkIdRegistoInternamento());
            tituloAlta.getFkIdFuncionario().setPkIdFuncionario(segConta.getFkIdFuncionario().getPkIdFuncionario());
            tituloAlta.setDataAlta(dataCorrente.getTime());
            tituloAlta.getFkIdTipoAlta().setPkIdTipoAlta(1);
            
            interTituloAltaFacade.create(tituloAlta);

//            registoInternamento.setAtivo(false);
//            interRegistoInternamentoFacade.edit(registoInternamento);

            userTransaction.commit();
            Mensagem.sucessoMsg("Título de Alta registado com sucesso!");

//            if (interTipoAltaFacade.find(tituloAlta.getFkIdTipoAlta().getPkIdTipoAlta()).getDescricao().equals("Transferido"))
//            {
//                InterGuiaTransferenciaDoentesNovoBean.getInstanciaBean().setRegistoInternamento(registoInternamento);
//                tituloAlta = null;
//                return "guiaTransferenciaDoentesNovoInter.xhtml?faces-redirect=true";
//            }

            limparCampos();

//            InterRegistoInternamentoBean.getInstanciaBean().pesquisar();

            return "registoInternamentoListarInter.xhtml?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                System.out.println(e.toString());

            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }

        return null;
    }

    public void limparCampos()
    {
        String tipoS = "";

//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoPediatria())
//        {
//            tipoS = "Internamento Pediatrico";
//        }
//
//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoCNT())
//        {
//            tipoS = "Centro Nutricional Terapêutico";
//        }
//
//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoMedicina())
//        {
            tipoS = "Internamento Medicina";
//        }

        tituloAlta = null;
        numeroTituloaAlta();
    }

    public void pesquisar()
    {
        String tipoS = "";

//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoPediatria())
//        {
//            tipoS = "Internamento Pediatrico";
//        }
//
//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoCNT())
//        {
//            tipoS = "Centro Nutricional Terapêutico";
//        }
//
//        if (SegRenderizarInternamentoBean.getInstanciaBean().renderizarInternamentoMedicina())
//        {
            tipoS = "Internamento Medicina";
//        }

        listaTituloAlta = interTituloAltaFacade.pesquisarTituloAlta(tipoS, numeroTituloPesq, tipoAltaPesq, dataAltaPesq1, dataAltaPesq2, registoInternamento.getFkIdInscricaoInternamento().getNumeroInscricao());

        if (listaTituloAlta.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaTituloAlta.size() + " registo(s) retornado(s).");
        }

    }
}
