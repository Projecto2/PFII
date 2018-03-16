/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.finbean;

import entidade.AdmsPaciente;
import entidade.AdmsSubprocesso;
import entidade.AdmsTipoPagamentoSubprocesso;
import entidade.FinHistoricoCancelamentoEncargoDevidoPago;
import entidade.FinHistoricoPagamentoCancelados;
import entidade.FinPagamento;
import entidade.FinPagamentoConvenio;
import entidade.FinPagamentoEncargoDevido;
import entidade.FinPagamentoViaBanco;
import entidade.FinTipoPagamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.AdmsTipoPagamentoSubprocessoFacade;
import sessao.FinEncargoDevidoFacade;
import sessao.FinEstadoPagamentoPagaNaopagoFacade;
import sessao.FinHistoricoCancelamentoEncargoDevidoPagoFacade;
import sessao.FinHistoricoPagamentoCanceladosFacade;
import sessao.FinPagamentoConvenioFacade;
import sessao.FinPagamentoEncargoDevidoFacade;
import sessao.FinPagamentoFacade;
import sessao.FinPagamentoViaBancoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
//@ViewScoped
public class FinPagamentoEncargoDevidoAbrirBean implements Serializable
{
    @EJB
    private AdmsTipoPagamentoSubprocessoFacade admsTipoPagamentoSubprocessoFacade;
    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    private AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;
    @EJB
    private FinEstadoPagamentoPagaNaopagoFacade finEstadoPagamentoPagaNaopagoFacade;
    @EJB
    private FinEncargoDevidoFacade finEncargoDevidoFacade;
    @EJB
    private FinHistoricoCancelamentoEncargoDevidoPagoFacade finHistoricoCancelamentoEncargoDevidoPagoFacade;
    @EJB
    private FinHistoricoPagamentoCanceladosFacade finHistoricoPagamentoCanceladosFacade;
    @EJB
    private FinPagamentoEncargoDevidoFacade finPagamentoEncargoDevidoFacade;
    @EJB
    private FinPagamentoFacade finPagamentoFacade;
    @EJB
    private FinPagamentoConvenioFacade finPagamentoConvenioFacade;
    @EJB
    private FinPagamentoViaBancoFacade finPagamentoViaBancoFacade;
    
    
    

    private List<FinPagamentoEncargoDevido> pagamentoEncargoDevidoLista;

    private FinPagamento pagamento;

    private String paginaProveniencia = "";
    
    private AdmsPaciente pacienteAssociadoAoPagamento;
    
    @Resource
    protected UserTransaction userTransaction;

    /**
     * Creates a new instance of FinPagamentoServicoSolicitadoAbrirBean
     */
    public FinPagamentoEncargoDevidoAbrirBean()
    {
    }
    
    public static FinPagamentoEncargoDevidoAbrirBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FinPagamentoEncargoDevidoAbrirBean finPagamentoEncargoDevidoAbrirBean = 
            (FinPagamentoEncargoDevidoAbrirBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "finPagamentoEncargoDevidoAbrirBean");
        
        return finPagamentoEncargoDevidoAbrirBean;
    }


    public List<FinPagamentoEncargoDevido> getPagamentoEncargoDevidoLista()
    {
        if (pagamentoEncargoDevidoLista == null)
        {
            pagamentoEncargoDevidoLista = new ArrayList<>();
        }
        return pagamentoEncargoDevidoLista;
    }

    public void setPagamentoEncargoDevidoLista(List<FinPagamentoEncargoDevido> pagamentoEncargoDevidoLista)
    {
        this.pagamentoEncargoDevidoLista = pagamentoEncargoDevidoLista;
    }
    
    
    

    public void carregarPagamentosEncargosDevidos(FinPagamento pagamento, String paginaProveniencia, AdmsPaciente pacienteAssociadoAoPagamento)
    {
        this.pacienteAssociadoAoPagamento = pacienteAssociadoAoPagamento;
         
        this.paginaProveniencia = paginaProveniencia;
        this.pagamento = pagamento;
//        if (pagamentoEncargoDevidoLista == null)
//        {
//            pagamentoEncargoDevidoLista = new ArrayList<>();
//        }
//
////        if(paginaProvencia.equalsIgnoreCase("pagamentosFin"))
////        pagamentoEncargoDevidoLista = finPagamentoEncargoDevidoFacade.findPagamentoEncargoListaByPagamento(pagamento);
//        pagamentoEncargoDevidoLista = pagamento.getFinPagamentoEncargoDevidoList();
        
//        if(pagamentoEncargoDevidoLista.isEmpty())
//            Mensagem.warnMsg("Nenhuma Informação encontrada");
    }

    public FinPagamento getPagamento()
    {
        return pagamento;
    }

    public void setPagamento(FinPagamento pagamento)
    {
        this.pagamento = pagamento;
    }

    public String getFormaQueFoiPago(FinPagamento finPagamento)
    {
        if (finPagamento == null)
        {
            return "";
        }
        String formaPagamento = "";

        FinPagamentoConvenio pagamentoConvenioLocal = finPagamentoConvenioFacade.findPagamentoConvenioPorPagamento(finPagamento);
        FinPagamentoViaBanco pagamentoViaBancoLocal = finPagamentoViaBancoFacade.findPagamentoViaBancoPorPagamento(finPagamento);

        if (pagamentoConvenioLocal != null)
        {
            if (pagamentoConvenioLocal.getFkIdConvenio() != null)
            {
                formaPagamento = "" + pagamentoConvenioLocal.getFkIdConvenio().getNomeConvenio();
            }
            else
            {
                formaPagamento = "" + pagamentoConvenioLocal.getFkIdProjetoConvenio().getFkIdConvenio().getNomeConvenio() + " / Projeto > "
                    + pagamentoConvenioLocal.getFkIdProjetoConvenio().getDescricaoProjeto();
            }
        }
        if (pagamentoViaBancoLocal != null)
        {
            formaPagamento = "" + pagamentoViaBancoLocal.getFkIdContaBancaria().getFkIdBanco().getNomeBanco() + " / "
                + pagamentoViaBancoLocal.getFkIdContaBancaria().getNumeroConta();
        }

        return formaPagamento;
    }
    
    
    public void cancelarPagamento()
    {
        FinHistoricoPagamentoCancelados pagamentoCancelado = new FinHistoricoPagamentoCancelados();
        pagamentoCancelado.setFinHistoricoCancelamentoEncargoDevidoPagoList(new ArrayList<FinHistoricoCancelamentoEncargoDevidoPago>());
//        List<FinHistoricoCancelamentoEncargoDevidoPago> encargosCancelados;
        
        adicionarPagamentoCancelado(pagamento, pagamentoCancelado);
        criarHistoricoDeCancelamentoDeEncargoPago(pagamento.getFinPagamentoEncargoDevidoList(), pagamentoCancelado);
                
        try
        {
            userTransaction.begin();
                atualizarEncargos(pagamento.getFinPagamentoEncargoDevidoList());
//                if (!jaFoiEfetuado(finPagamentoServicoSolicitado))
//                {
//                    boolean jafoiCancelado = jaFoiCancelado(finPagamentoServicoSolicitado);
//                    if (!jafoiCancelado)
//                    {
//                        finPagamentoServicoSolicitado.setFkIdEstadoPagamento(finEstadoPagamentoFacade.getEstadoPagamentoCancelado());
//                        finPagamentoEncargoDevidoFacade.edit(finPagamentoServicoSolicitado);
//                        finPagamentoEncargoDevido.getFkIdPagamento()
//                        .setValorPago(finPagamentoServicoSolicitado.getFkIdPagamento().getValorPago() - finPagamentoServicoSolicitado.getValorPago());
//     (finEstadoPagamentoFacade.getEstadoPagamentoCancelado());
                gravarHistoricoPagamentoCancelado(pagamentoCancelado);
//                gravarHistoricoEncargoPago(pagamentoCancelado);
            
//                        eliminarPagamentoEncargo(pagamento.getFinPagamentoEncargoDevidoList());
                        
//                        finHistoricoPagamentoCanceladosFacade.create(new FinHistoricoPagamentoCancelados);
                        
                        finPagamentoFacade.remove(pagamento);
//                        finPagamentoServicoSolicitado.getFkIdServicoSolicitado().setFkIdEstadoPagamento(admsEstadoPagamentoFacade.findPagamentoPendente());
//                        admsServicoSolicitadoFacade.edit(finPagamentoServicoSolicitado.getFkIdServicoSolicitado());
                        
//                        pagamento = finPagamentoServicoSolicitado.getFkIdPagamento();
//                    }
//                    else
//                    {
//                        Mensagem.warnMsg("Pagamento Já Cancelado Anteriormente, Atualize A Página!");
//                    }
//                }
//                else
//                {
//                    Mensagem.erroMsg("O Pagamento Não Pode Ser Cancelado, Pois Já Existem Serviços Efetuados Com Este Pagamento!");
//                }
            userTransaction.commit();
            Mensagem.sucessoMsg("Pagamento Cancelado Com Sucesso!");
//            pagamentoEncargoDevidoLista.clear();
//            carregarPagamentosEncargosDevidos(pagamento, paginaProveniencia);
//            atualizarDadosPaginaProveniencia();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        atualizarPesquisa();
    }
    
    
    public void atualizarPesquisa()
    {
        if(paginaProveniencia.equalsIgnoreCase("pagamentosFin"))
        {
            FinPagamentosListagemBean.getInstanciaBean().atualizarPesquisar();
        }
    }
    
    
    public void eliminarPagamentoEncargo(List<FinPagamentoEncargoDevido> listaPagamentoEncargo)
    {
        for(int i = 0; i < listaPagamentoEncargo.size(); i++)
        {
            finPagamentoEncargoDevidoFacade.remove(listaPagamentoEncargo.get(i));
        }
    }
    
    
    public void criarHistoricoDeCancelamentoDeEncargoPago(List<FinPagamentoEncargoDevido> listaPagamentoEncargo, FinHistoricoPagamentoCancelados pagamentoCancelado)
    {
//         List<FinHistoricoCancelamentoEncargoDevidoPago> listaPagamentoEncargoCancelado = new ArrayList<>();
        for(int i = 0; i < listaPagamentoEncargo.size(); i++)
        {
            FinHistoricoCancelamentoEncargoDevidoPago encargoPago = new  FinHistoricoCancelamentoEncargoDevidoPago();
            encargoPago.setDataEncargoDevido(listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getData());
            encargoPago.setDescricaoEncargoDevido(listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getDescricaoEncargoDevido());
            encargoPago.setFkIdPagamentoCancelado(pagamentoCancelado);
            encargoPago.setQuantidade(listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getQuantidade());
            encargoPago.setValor(listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getValor());
            pagamentoCancelado.getFinHistoricoCancelamentoEncargoDevidoPagoList().add(encargoPago);
        }
//        return listaPagamentoEncargoCancelado;
    }
    
    
    public void atualizarEncargos(List<FinPagamentoEncargoDevido> listaPagamentoEncargo)
    {
        for(int i = 0; i < listaPagamentoEncargo.size(); i++)
        {
            listaPagamentoEncargo.get(i).getFkIdEncargoDevido().setFkIdEstadoPagamentoPagoNaopago(finEstadoPagamentoPagaNaopagoFacade.findEstadoPagamentoNaoPago());
            
            finEncargoDevidoFacade.edit(listaPagamentoEncargo.get(i).getFkIdEncargoDevido());
            if(listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getFkIdServicoSolicitado() != null)
            {
                listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getFkIdServicoSolicitado().setFkIdEstadoPagamento(admsEstadoPagamentoFacade.findPagamentoPendente());
                admsServicoSolicitadoFacade.edit(listaPagamentoEncargo.get(i).getFkIdEncargoDevido().getFkIdServicoSolicitado());
            } 
        }  
    }
    
    
    public void adicionarPagamentoCancelado(FinPagamento pagamentoPorCancelar, FinHistoricoPagamentoCancelados pagamentoCancelado)
    {
//        pagamentoCancelado.setFkIdFormaPagamento(null);
        pagamentoCancelado.setFkIdFuncionarioRegistouPagamento(pagamentoPorCancelar.getFkIdRecepcionista());
        pagamentoCancelado.setFkIdFuncionarioCancelou(SegLoginBean.getInstanciaBean().getContaUtilizador().getFkIdFuncionario());
//        pagamentoCancelado.setFkIdSubprocesso(null);
        pagamentoCancelado.setDataCancelamento(new Date());
        pagamentoCancelado.setDataRegistoPagamento(pagamentoPorCancelar.getDataPagamento());
        pagamentoCancelado.setValorPago(pagamentoPorCancelar.getValorPago());
    }
    
    public void gravarHistoricoPagamentoCancelado(FinHistoricoPagamentoCancelados pagamentoCancelado)
    {
        finHistoricoPagamentoCanceladosFacade.create(pagamentoCancelado);
        for(FinHistoricoCancelamentoEncargoDevidoPago encargoPago:pagamentoCancelado.getFinHistoricoCancelamentoEncargoDevidoPagoList())
        {
            encargoPago.setFkIdPagamentoCancelado(pagamentoCancelado);
            finHistoricoCancelamentoEncargoDevidoPagoFacade.create(encargoPago);
        }
        
        
    }
    
//    public void gravarHistoricoEncargoPago(FinHistoricoPagamentoCancelados pagamentoCancelado)
//    {
//        
//        for(int i = 0; i < listaEncargosPagos.size(); i++)
//        {
//            System.out.println("lista de encargos por gravar "+listaEncargosPagos.get(0).getDescricaoEncargoDevido());
//            finHistoricoCancelamentoEncargoDevidoPagoFacade.create(listaEncargosPagos.get(i));
//        }
//            
//    }
    
    
    public String getTipoPagamentoBySubprocesso(AdmsSubprocesso subProcessoLocal)
    {
        FinTipoPagamento tipoPagamento = admsTipoPagamentoSubprocessoFacade.findTipoPagamentoBySubprocesso(subProcessoLocal);
        if(tipoPagamento == null)
            return "";
        return tipoPagamento.getDescricaoTipoPagamento();
    }

    public AdmsPaciente getPacienteAssociadoAoPagamento()
    {
        if(pacienteAssociadoAoPagamento == null)
        {
            pacienteAssociadoAoPagamento = new AdmsPaciente();
        }
        return pacienteAssociadoAoPagamento;
    }

    public void setPacienteAssociadoAoPagamento(AdmsPaciente pacienteAssociadoAoPagamento)
    {
        this.pacienteAssociadoAoPagamento = pacienteAssociadoAoPagamento;
    }
    
    
}
