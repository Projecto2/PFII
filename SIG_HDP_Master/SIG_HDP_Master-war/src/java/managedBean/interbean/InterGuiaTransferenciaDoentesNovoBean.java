/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterCamaInternamento;
import entidade.InterEstadoCama;
import entidade.InterGuiaTransferenciaDoentes;
import entidade.InterRegistoInternamento;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterEnfermariaFacade;
import sessao.InterGuiaTransferenciaDoentesFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;
import util.RelatorioJasper;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterGuiaTransferenciaDoentesNovoBean implements Serializable
{

    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;

    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;

    @EJB
    private InterGuiaTransferenciaDoentesFacade interGuiaTransferenciaDoentesFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterRegistoInternamento registoInternamento;

    private InterGuiaTransferenciaDoentes guiaTransferenciaDoentes;

    private final Calendar dataCorrente = Calendar.getInstance();

    private List<InterGuiaTransferenciaDoentes> listaGuias;

    private boolean gravou;

    /**
     * Creates a new instance of InterGuiaTransferenciaDoentesNovoBean
     */
    public InterGuiaTransferenciaDoentesNovoBean()
    {

    }

    public static InterGuiaTransferenciaDoentesNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterGuiaTransferenciaDoentesNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interGuiaTransferenciaDoentesNovoBean");
    }

    public InterGuiaTransferenciaDoentes getInstancia()
    {
        InterGuiaTransferenciaDoentes gt = new InterGuiaTransferenciaDoentes();
        gt.setFkIdRegistoInternamento(new InterRegistoInternamento());
        gt.setFkIdFuncionarioAssistente(new RhFuncionario());
        gt.setFkIdFuncionarioChefeEquipe(new RhFuncionario());

        return gt;
    }

    public InterGuiaTransferenciaDoentes getGuiaTransferenciaDoentes()
    {
        if (guiaTransferenciaDoentes == null)
        {
            guiaTransferenciaDoentes = getInstancia();
            guiaTransferenciaDoentes.setDataHora(dataCorrente.getTime());
        }
        else
        {
            guiaTransferenciaDoentes.setDataHora(dataCorrente.getTime());
        }
        return guiaTransferenciaDoentes;
    }

    public void setGuiaTransferenciaDoentes(InterGuiaTransferenciaDoentes guiaTransferenciaDoentes)
    {
        this.guiaTransferenciaDoentes = guiaTransferenciaDoentes;
    }

    public List<InterGuiaTransferenciaDoentes> getListaGuias()
    {
        return listaGuias;
    }

    public void setListaGuias(List<InterGuiaTransferenciaDoentes> listaGuias)
    {
        this.listaGuias = listaGuias;
    }

    public String numeroGuia()
    {
        return (interGuiaTransferenciaDoentesFacade.findAll().size() + 1) + " / " + interEnfermariaFacade.getEnfermariaFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getFkIdSeccaoTrabalho().getDescricao()).getFkIdServico().getCodServico();
    }

    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }

    public boolean isGravou()
    {
        return gravou;
    }

    public void setGravou(boolean gravou)
    {
        this.gravou = gravou;
    }

    public void salvar()
    {
        try
        {
            userTransaction.begin();

            guiaTransferenciaDoentes.setNumeroGuia(numeroGuia());
            guiaTransferenciaDoentes.getFkIdRegistoInternamento().setPkIdRegistoInternamento(registoInternamento.getPkIdRegistoInternamento());
            //guiaTransferenciaDoentes.setData(dataCorrente.getTime());

            interGuiaTransferenciaDoentesFacade.create(guiaTransferenciaDoentes);

            InterRegistoSaidaNovoBean.getInstanciaBean().getInterRegistoSaida().setData(dataCorrente.getTime());

            InterRegistoSaidaNovoBean.getInstanciaBean().getInterRegistoSaidaFacade().create(InterRegistoSaidaNovoBean.getInstanciaBean().getInterRegistoSaida());

            registoInternamento.setAtivo(false);
            InterRegistoSaidaNovoBean.getInstanciaBean().getInterRegistoInternamentoFacade().edit(registoInternamento);

            InterCamaInternamento camaInstancia = InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().find(registoInternamento.getFkIdCamaInternamento().getPkIdCamaInternamento());
            camaInstancia.setFkIdEstadoCama(new InterEstadoCama(InterEstadoCamaListarBean.getInstanciaBean().getInterEstadoCamaFacade().findByDescricao("Livre Não Pronta").get(0).getPkIdEstadoCama()));

            InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().edit(camaInstancia);

            Mensagem.sucessoMsg("Saída gravada com sucesso.");
            Mensagem.sucessoMsg("A cama " + camaInstancia.getDescricaoCamaInternamento() + " foi atualizada para Livre");

            userTransaction.commit();

            InterRegistoSaidaNovoBean.getInstanciaBean().setInterRegistoSaida(null);

            Mensagem.sucessoMsg("Guia médica de transferência gravada com sucesso.");

            List<InterGuiaTransferenciaDoentes> lista = interGuiaTransferenciaDoentesFacade.findAll();

            listaGuias = new ArrayList();

            listaGuias.add(lista.get(lista.size() - 1));

            gravou = true;

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
    }

    public void limparCampos()
    {
        guiaTransferenciaDoentes = null;
    }

    public List<RhFuncionario> findAll()
    {
        List<RhFuncionario> listaMedicos = rhFuncionarioFacade.listaDosMedicos();
        List<RhFuncionario> listaMedicosSessao = rhFuncionarioFacade.findFuncionariosPorSeccao(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho());
        List<RhFuncionario> lista = new ArrayList();
        boolean flag;

        for (int i = 0; i < listaMedicosSessao.size(); i++)
        {
            flag = false;

            for (int j = 0; j < listaMedicos.size() && !flag; j++)
            {
                if (listaMedicosSessao.get(i).getPkIdFuncionario() == listaMedicos.get(j).getPkIdFuncionario())
                {
                    lista.add(listaMedicosSessao.get(i));
                    flag = true;
                }
            }
        }

        return lista;
    }

    public void exportPDF()
    {
        limparCampos();

        HashMap<String, Object> parametrosMap = new HashMap<>();
        parametrosMap.put("diagnosticoInicial", InterRegistoInternamentoBean.getInstanciaBean().findDoencaPrincipalPacienteByRegistoInternamento(registoInternamento.getPkIdRegistoInternamento()));
        parametrosMap.put("examesComplementares", "");
        parametrosMap.put("dataCorrente", dataCorrente.getTime());
        parametrosMap.put("dataNascimento", registoInternamento.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getDataNascimento());
        RelatorioJasper.exportPDF("inter/guiaTransferenciaMedica.jasper", parametrosMap, listaGuias);
    }

    public String voltar()
    {
        InterRegistoInternamentoBean.getInstanciaBean().pesquisar();

        return "/faces/interVisao/interInternamento/internamentoListar/registoInternamentoListarInter.xhtml?faces-redirect=true";
    }
}
