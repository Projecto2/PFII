/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.finbean;

//import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.FinBanco;
import entidade.FinContaBancaria;
import entidade.FinEncargoDevido;
//import entidade.FinEstadoEncargoDevido;
import entidade.FinEstadoPagamentoPagaNaopago;
import entidade.FinFormaPagamento;
import entidade.FinPagamento;
import entidade.FinPagamentoConvenio;
import entidade.FinPagamentoEncargoDevido;
//import entidade.FinPagamentoServicoSolicitado;
import entidade.FinPagamentoViaBanco;
import entidade.FinTipoPagamento;
import entidade.GrlConvenio;
import entidade.GrlProjetoConvenio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.finbean.encargo.FinEncargoDevidoPacienteBean;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.FinBancoFacade;
import sessao.FinContaBancariaFacade;
import sessao.FinEncargoDevidoFacade;
import sessao.FinEstadoPagamentoPagaNaopagoFacade;
import sessao.FinFormaPagamentoFacade;
import sessao.FinPagamentoConvenioFacade;
import sessao.FinPagamentoEncargoDevidoFacade;
import sessao.FinPagamentoFacade;
import sessao.FinPagamentoViaBancoFacade;
import sessao.FinTipoPagamentoFacade;
import sessao.GrlConvenioFacade;
import sessao.GrlProjetoConvenioFacade;
//import util.Constantes;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
//@ViewScoped
public class FinPagamentoBean implements Serializable
{
    @EJB
    private FinEncargoDevidoFacade finEncargoDevidoFacade;
    @EJB
    private FinEstadoPagamentoPagaNaopagoFacade finEstadoPagamentoPagaNaopagoFacade;
    @EJB
    private FinPagamentoEncargoDevidoFacade finPagamentoEncargoDevidoFacade;
    @EJB
    private GrlConvenioFacade grlConvenioFacade;
    @EJB
    private FinContaBancariaFacade finContaBancariaFacade;
    @EJB
    private FinBancoFacade finBancoFacade;
    @EJB
    private FinPagamentoConvenioFacade finPagamentoConvenioFacade;
    @EJB
    private GrlProjetoConvenioFacade grlProjetoConvenioFacade;
    @EJB
    private FinPagamentoViaBancoFacade finPagamentoViaBancoFacade;
    @EJB
    private FinFormaPagamentoFacade finFormaPagamentoFacade;
    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    private AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;
//    @EJB
//    private FinPagamentoServicoSolicitadoFacade finPagamentoServicoSolicitadoFacade;
    @EJB
    private FinTipoPagamentoFacade finTipoPagamentoFacade;
    @EJB
    private FinPagamentoFacade finPagamentoFacade;
    
    
    
    
    FacesContext context = FacesContext.getCurrentInstance();

    private FinPagamento pagamento;

    private FinPagamentoViaBanco pagamentoViaBanco;

    private FinPagamentoConvenio pagamentoPorConvenio;

    private FinFormaPagamento formaDePagamento;

//    private AdmsSubprocesso solicitacao;

    private ArrayList<FinEncargoDevido> listaDeEncargosDevidosASerPagos;

    private List<GrlProjetoConvenio> listaProjetos;

//    private List<AdmsServicoSolicitado> listaNaoPagos;
    
//    private List<FinTipoPagamento> tipoPagamentoLista;
    
    private List<FinFormaPagamento> formaPagamentoLista;
    
    private List<FinBanco> bancoLista;
    
    private List<FinContaBancaria> contaBancariaLista;
    
    private List<GrlConvenio> convenioLista;
    
    private List<GrlProjetoConvenio> projetoConvenioLista;

    private String cor = "red", encargosPagos = "";

    private double valorAPagar = 0, precoTotal = 0;
    
    private AdmsPaciente paciente;

    private boolean pagamentoBancario = false, 
        pagamentoConvenio = false, convenioProjetos = false, 
        /*recarregar = false,*/ todoServicosJaForamPagos,
        pararAtualizacao = false;

    private FinTipoPagamento tipoPagamento;
    
    protected HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    protected HttpSession sessao = request.getSession();

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of FinPagamentoBean
     */
    public FinPagamentoBean()
    {
        listaDeEncargosDevidosASerPagos = new ArrayList<>();
    }
    
    public static FinPagamentoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FinPagamentoBean finPagamentoBean = 
            (FinPagamentoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "finPagamentoBean");
        
        return finPagamentoBean;
    }

    public FinPagamento getPagamento()
    {
        if (pagamento == null)
        {
            pagamento = getInstancia();

            pagamentoViaBanco = new FinPagamentoViaBanco();
            pagamentoViaBanco.setFkIdContaBancaria(new FinContaBancaria());
            pagamentoViaBanco.getFkIdContaBancaria().setFkIdBanco(new FinBanco());

            pagamentoPorConvenio = new FinPagamentoConvenio();
            pagamentoPorConvenio.setFkIdConvenio(new GrlConvenio());
            pagamentoPorConvenio.setFkIdProjetoConvenio(new GrlProjetoConvenio());
            pagamentoPorConvenio.setFkIdPagamento(null);
        }
        return pagamento;
    }

    public void setPagamento(FinPagamento pagamento)
    {
        this.pagamento = pagamento;
    }

    public FinPagamento getInstancia()
    {
        FinPagamento pagamentoInstancia = new FinPagamento();
        pagamentoInstancia.setFkIdFormaPagamento(new FinFormaPagamento(0));
        pagamentoInstancia.setFkIdRecepcionista(null);
        
        return pagamentoInstancia;
    }

    public FinPagamentoViaBanco getPagamentoViaBanco()
    {
        if (pagamentoViaBanco == null)
        {
            pagamentoViaBanco = new FinPagamentoViaBanco();
            pagamentoViaBanco.setFkIdContaBancaria(new FinContaBancaria());
            pagamentoViaBanco.setFkIdPagamento(new FinPagamento());
        }
        return pagamentoViaBanco;
    }

    public void setPagamentoViaBanco(FinPagamentoViaBanco pagamentoViaBanco)
    {
        this.pagamentoViaBanco = pagamentoViaBanco;
    }

    public FinPagamentoConvenio getPagamentoConvenio()
    {
        if (pagamentoPorConvenio == null)
        {
            pagamentoPorConvenio = new FinPagamentoConvenio();
            pagamentoPorConvenio.setFkIdConvenio(new GrlConvenio());
            pagamentoPorConvenio.setFkIdProjetoConvenio(new GrlProjetoConvenio());
            pagamentoPorConvenio.setFkIdPagamento(null);
        }
        return pagamentoPorConvenio;
    }

    public void setPagamentoConvenio(FinPagamentoConvenio pagamentoPorConvenio)
    {
        this.pagamentoPorConvenio = pagamentoPorConvenio;
    }

//    public AdmsSolicitacao getSolicitacao()
//    {
//        return solicitacao;
//    }

//    public void setSolicitacao(AdmsSolicitacao solicitacao)
//    {
//        listaVeioDoServicoSolicitado = false;
//        this.solicitacao = solicitacao;
//    }

    public ArrayList<FinEncargoDevido> getListaDeEncargosDevidosASerPagos()
    {
        if (listaDeEncargosDevidosASerPagos == null)
        {
            listaDeEncargosDevidosASerPagos = new ArrayList<>();
        }
        return listaDeEncargosDevidosASerPagos;
    }

    public List<GrlProjetoConvenio> getListaProjetosConvenio()
    {
        if (listaProjetos == null)
        {
            listaProjetos = new ArrayList<>();
        }
        return listaProjetos;
    }

    public void setListaDeEncargosDevidosASerPagos(ArrayList<FinEncargoDevido> listaDeEncargosDevidosASerPagos)
    {
        this.listaDeEncargosDevidosASerPagos = listaDeEncargosDevidosASerPagos;
    }

    public void removerEncargoDevidoAserPago(FinEncargoDevido encargoDevido)
    {
        for (int i = 0; i < listaDeEncargosDevidosASerPagos.size(); i++)
        {
            if(encargoDevido.getPkIdEncargoDevido()== listaDeEncargosDevidosASerPagos.get(i).getPkIdEncargoDevido())
            {
                precoTotal = precoTotal - listaDeEncargosDevidosASerPagos.get(i).getFkIdPrecoCategoriaServico().getValor();
                listaDeEncargosDevidosASerPagos.remove(i);
                return;
            }
        }
//        listaDeEncargosDevidosASerPagos.add(servicoSolicitado);
//        precoTotal = precoTotal + servicoSolicitado.getFkIdPrecoCategoriaServico().getValor();
    }

//    public String getDescricaoAdicionarRemover(int codServicoSolicitado)
//    {
//        for (int i = 0; i < listaDeEncargosDevidosASerPagos.size(); i++)
//        {
//            if (codServicoSolicitado == listaDeEncargosDevidosASerPagos.get(i).getPkIdEncargoDevido())
//            {
//                cor = "green";
//                return "Remover";
//            }
//        }
//        cor = "red";
//        return "Adicionar";
//    }

//    public String getCor()
//    {
//        return cor;
//    }
//
//    public void setCor(String cor)
//    {
//        this.cor = cor;
//    }
//
//    public double getPrecoTotal()
//    {
//        return precoTotal;
//    }
//
//    public void setPrecoTotal(double precoTotal)
//    {
//        this.precoTotal = precoTotal;
//    }

    public double getValorApagar()
    {
//        if(tipoPagamento == null) return 0;
        valorAPagar = 0;
        if(listaDeEncargosDevidosASerPagos == null || listaDeEncargosDevidosASerPagos.isEmpty())
            return valorAPagar;
//        if(tipoPagamento == null)
//        {
//            tipoPagamento = new FinTipoPagamento();
//            return valorAPagar;
//        }
//        if (!(tipoPagamento.getPkIdTipoPagamento() == null))
//        {
//            tipoPagamento = finTipoPagamentoFacade.find(tipoPagamento.getPkIdTipoPagamento());
//            valorAPagar = precoTotal * tipoPagamento.getFatorDeMultiplicacao();
//            return valorAPagar;
//        }
        for(int i = 0; i < listaDeEncargosDevidosASerPagos.size(); i++)
            valorAPagar += listaDeEncargosDevidosASerPagos.get(i).getValor();
        return valorAPagar;
    }

    public void setValorApagar(double precoTotal)
    {
        //this.precoTotal = precoTotal;
    }

//    public FinTipoPagamento getTipoPagamento()
//    {
//        return tipoPagamento;
//    }
//
//    public void setTipoPagamento(FinTipoPagamento tipoPagamento)
//    {
//        this.tipoPagamento = tipoPagamento;
//    }

//    public void getUltimoTipoDePagamento()
//    {
//        ultimoTipoDePagamento = admsTipoPagamentoSolicitacaoFacade.findUltimoTipoPagamento(solicitacao.getPkIdSolicitacao());
//
//        if (!(ultimoTipoDePagamento == null))
//        {
//            tipoPagamento = ultimoTipoDePagamento.getFkIdTipoPagamento();
//        }
//        else
//        {
//            tipoPagamento = new FinTipoPagamento(null);
//        }
//    }

    public void create() throws SystemException
    {
        if (validarPagamento())
        {
            try
            {
                userTransaction.begin();
                    verificarPagamentosDoServico();
                    if(!todoServicosJaForamPagos)
                    {
                        pagamento.setDataPagamento(new Date());
                        definirFuncionarioEfetuouPagamento();
                        if (valorAPagar <= 0)
                        {
                            pagamento.setFkIdFormaPagamento(null);
                        }
//                        definirNovoTipoDePagamento();
                        pagamento.setValorPago(valorAPagar);
                        finPagamentoFacade.create(pagamento);
                        if (!(pagamento.getFkIdFormaPagamento() == null))
                        {
                            definirFormaDePagamento();
                        }
                        gravarPagamentoDeCadaEncargoDevido();
                    }
//                    removerPagos();
                userTransaction.commit();
                if(!todoServicosJaForamPagos) Mensagem.sucessoMsg("Pagamento efetuado com sucesso!");
                if(!encargosPagos.equals(""))
                {
                    Mensagem.warnMsg(encargosPagos);
                }
                if (valorAPagar <= 0)
                {
                    pagamento.setFkIdFormaPagamento(new FinFormaPagamento(0));
                }
                limparGravacao();
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
        }
    }
    
    public void definirFuncionarioEfetuouPagamento()
    {
        pagamento.setFkIdRecepcionista(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
    }
    
    public void gravarPagamento() throws SystemException
    {
        create();
        atualizarPesquisaEncargos();
    }

//    public void definirNovoTipoDePagamento()
//    {
//        if (ultimoTipoDePagamento != null && ultimoTipoDePagamento.getFkIdTipoPagamento().getPkIdTipoPagamento() == tipoPagamento.getPkIdTipoPagamento())
//        {
//            novoTipoDePagamento = ultimoTipoDePagamento;
//        }
//        else
//        {
//            novoTipoDePagamento = new AdmsTipoPagamentoSolicitacao();
//            novoTipoDePagamento.setDataCadastrada(new Date());
//            novoTipoDePagamento.setFkIdSolicitacao(solicitacao);
//            novoTipoDePagamento.setFkIdTipoPagamento(tipoPagamento);
//            admsTipoPagamentoSolicitacaoFacade.create(novoTipoDePagamento);
//        }
//    }
    
    public void atualizarPesquisaEncargos()
    {
        FinEncargoDevidoPacienteBean.getInstanciaBean().atualizarPesquisa();
    }
    

    public void definirFormaDePagamento()
    {
        if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Cash"))
        {
            return;
        }
        else if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Bancário"))
        {
            pagamentoViaBanco.setFkIdPagamento(pagamento);
            finPagamentoViaBancoFacade.create(pagamentoViaBanco);
        }
        else if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Por Convênio"))
        {
            pagamentoPorConvenio.setFkIdPagamento(pagamento);
            if (convenioProjetos)
            {
                pagamentoPorConvenio.setFkIdConvenio(null);
            }
            else
            {
                pagamentoPorConvenio.setFkIdProjetoConvenio(null);
            }
            finPagamentoConvenioFacade.create(pagamentoPorConvenio);
        }
    }

    public void gravarPagamentoDeCadaEncargoDevido()
    {
        FinEstadoPagamentoPagaNaopago estadoPago = finEstadoPagamentoPagaNaopagoFacade.findEstadoPagamentoPago();
System.out.println("tamanho da lista ja na gravacao "+listaDeEncargosDevidosASerPagos.size());
        for (int i = 0; i < listaDeEncargosDevidosASerPagos.size(); i++)
        {
            listaDeEncargosDevidosASerPagos.get(i).setFkIdEstadoPagamentoPagoNaopago(estadoPago);
            finEncargoDevidoFacade.edit(listaDeEncargosDevidosASerPagos.get(i));
            
            FinPagamentoEncargoDevido pagamentoEncargoDevido = new FinPagamentoEncargoDevido();
            pagamentoEncargoDevido.setFkIdEncargoDevido(listaDeEncargosDevidosASerPagos.get(i));
            pagamentoEncargoDevido.setFkIdPagamento(pagamento);
            finPagamentoEncargoDevidoFacade.create(pagamentoEncargoDevido);
            
            if(listaDeEncargosDevidosASerPagos.get(i) != null && listaDeEncargosDevidosASerPagos.get(i).getPkIdEncargoDevido() != null)
            {
                listaDeEncargosDevidosASerPagos.get(i).getFkIdServicoSolicitado().setFkIdEstadoPagamento(admsEstadoPagamentoFacade.findEstadoPagamentoPago());
                admsServicoSolicitadoFacade.edit(listaDeEncargosDevidosASerPagos.get(i).getFkIdServicoSolicitado());
            }
            
        }
    }

    public boolean validarPagamento()
    {
        if (listaDeEncargosDevidosASerPagos.isEmpty())
        {
            Mensagem.erroMsg("Nenhum Serviço Selecionado Para Ser Pago");
            return false;
        }
//        if (tipoPagamento.getPkIdTipoPagamento() == null)
//        {
//            Mensagem.erroMsg("Indique Pelo Menos Um Tipo de Pagamento");
//            return false;
//        }
        if (valorAPagar > 0 && pagamento.getFkIdFormaPagamento().getPkIdFormaPagamento() == 0)
        {
            System.out.println("" + pagamento.getFkIdFormaPagamento().getPkIdFormaPagamento());

            Mensagem.erroMsg("Indique Pelo Menos Uma Forma de Pagamento");
            return false;
        }
        if (pagamento.getFkIdFormaPagamento().getPkIdFormaPagamento() != 0)
        {
            formaDePagamento = finFormaPagamentoFacade.find(pagamento.getFkIdFormaPagamento().getPkIdFormaPagamento());
            if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Cash"))
            {
            }
            else if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Bancário"))
            {
                if (pagamentoViaBanco.getFkIdContaBancaria().getPkIdContaBancaria() == null)
                {
                    Mensagem.erroMsg("Indique Pelo Menos Uma Conta Bancária");
                    return false;
                }
            }
            else if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Por Convênio"))
            {
                if (pagamentoPorConvenio.getFkIdConvenio().getPkIdConvenio() == null)
                {
                    Mensagem.erroMsg("Indique Pelo Menos Um Convénio");
                    return false;
                }
                if (convenioProjetos)
                {
                    if (projetoConvenioLista.isEmpty())
                    {
                        Mensagem.erroMsg("Indique Pelo Menos Um Projeto de Um Convénio");
                        return false;
                    }
                }
            }

        }
        return true;
    }

//    public List<AdmsServicoSolicitado> findServicosSolicitadosNaoPagos()
//    {
//        if(!listaVeioDoServicoSolicitado)
//            listaNaoPagos = admsServicoSolicitadoFacade.findServicosSolicitadosNaoPagos(solicitacao.getPkIdSolicitacao());
//        return listaNaoPagos;
//    }

    public void verificarPagamentosDoServico()
    {
        todoServicosJaForamPagos = true;
        encargosPagos = "";
        List<Integer> listaRemover = new ArrayList<>();
        for(int i = 0; i < listaDeEncargosDevidosASerPagos.size(); i++)
        {
            FinPagamentoEncargoDevido pagamentoEncargoDevido = finPagamentoEncargoDevidoFacade.findPagamentoEncargoByEncargo(listaDeEncargosDevidosASerPagos.get(i));
            if(pagamentoEncargoDevido == null)
            {
                todoServicosJaForamPagos = false;
            }
            else
            {
//                if (pagamentoEncargoDevido.getFkIdEstadoPagamento().getDescricaoEstadoPagamento().equalsIgnoreCase(Constantes.CANCELADOFINANCAS))
//                {
//                    todoServicosJaForamPagos = false;
//                }
//                else{
                    encargosPagos += "\n O Encargo: "+pagamentoEncargoDevido.getFkIdEncargoDevido().getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getFkIdServico().getNomeServico()+" Já Foi Pago";
                    listaRemover.add(i);
//                }
            }
        }
        for(int i = 0; i < listaRemover.size(); i++)
        {


// 8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888


            valorAPagar = valorAPagar -  listaDeEncargosDevidosASerPagos.get(listaRemover.get(i).intValue() - i).getValor();
// 88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
            
            listaDeEncargosDevidosASerPagos.remove(listaRemover.get(i).intValue() - i);
        }
    }
    
    
    //8888888888888888888888888888888888Funcao de teste, deve ser analisada para todos os casos
//    public void removerPagos()
//    {
////        ArrayList<Integer> lista = new ArrayList<>();
//        for (int i = 0; i < listaDeEncargosDevidosASerPagos.size(); i++)
//        {
//            for (int j = 0; j < listaNaoPagos.size(); j++)
//            {
//                if (listaDeEncargosDevidosASerPagos.get(i).getPkIdServicoSolicitado() == listaNaoPagos.get(j).getPkIdServicoSolicitado())
//                {
//                    //lista.add(j);
//                    listaNaoPagos.remove(j);
//                    break;
//                }
//            }
//        }
//        listaDeEncargosDevidosASerPagos = new ArrayList<>();
//    }

    public void definirTipoDePagamento()
    {
        tipoPagamento = finTipoPagamentoFacade.find(tipoPagamento.getPkIdTipoPagamento());
    }

    public boolean pagamentoBancario()
    {
        return pagamentoBancario;
    }

    public boolean pagamentoConvenio()
    {
        return pagamentoConvenio;
    }

    public void changeFormaDePagamento(ValueChangeEvent e)
    {
        int cod = Integer.parseInt(e.getNewValue().toString());
        if (cod != 0)
        {
            pagamento.getFkIdFormaPagamento().setPkIdFormaPagamento(cod);
            FinFormaPagamento formaDePagamentoLocal = finFormaPagamentoFacade.find(pagamento.getFkIdFormaPagamento().getPkIdFormaPagamento());
            if (formaDePagamentoLocal.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Bancário"))
            {
                pagamentoBancario = true;
                pagamentoConvenio = false;
            }
            else if (formaDePagamentoLocal.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Por Convênio"))
            {
                pagamentoConvenio = true;
                pagamentoBancario = false;
            }
            else
            {
                definirFormasParaFalse();
            }
        }
        else
        {
            definirFormasParaFalse();
        }
    }

    public void definirFormasParaFalse()
    {
        pagamentoConvenio = false;
        pagamentoBancario = false;
        convenioProjetos = false;
    }

    public void conveniosProjetos()
    {
        convenioProjetos = !convenioProjetos;
    }

    public boolean isConveniosProjetos()
    {
        return convenioProjetos;
    }

    public void changeTipoPagamento(ValueChangeEvent e)
    {
        if (e.getNewValue() == null)
        {
            tipoPagamento.setPkIdTipoPagamento(null);
        }
        else
        {
            tipoPagamento.setPkIdTipoPagamento(Integer.parseInt(e.getNewValue().toString()));
        }
    }

    public void changeConvenio(ValueChangeEvent e)
    {
        if (e.getNewValue() == null)
        {
            pagamentoPorConvenio.getFkIdConvenio().setPkIdConvenio(null);
        }
        else
        {
            int cod = Integer.parseInt(e.getNewValue().toString());
            pagamentoPorConvenio.getFkIdConvenio().setPkIdConvenio(cod);
            carregarProjetosConvenios(cod);
        }
    }
    
    
    public void changeBanco(ValueChangeEvent e)
    {
        if (e.getNewValue() != null)
        {
            int cod = Integer.parseInt(e.getNewValue().toString());
            carregarContasBancarias(cod);
        }
    }
    

    public void limparGravacao()
    {
        valorAPagar = 0;
        precoTotal = 0;
        pagamentoConvenio = false;
        pagamentoBancario = false;
        convenioProjetos = false;

        pagamento = null;
        listaDeEncargosDevidosASerPagos = null;
        getPagamento();
    }

    public Integer getTipoDePagamento()
    {
        return tipoPagamento.getPkIdTipoPagamento();
    }

    public void setTipoDePagamento(Integer tipoDePagamento)
    {
        //this.tipoDePagamento = tipoDePagamento;
    }
    
    public FinTipoPagamentoFacade getFacadeTipoPagamento()
    {
        return finTipoPagamentoFacade;
    }
    
    public void inicializaFormaDePagamento()
    {
        pagamento.setFkIdFormaPagamento(null);
        pagamento.setFkIdFormaPagamento(new FinFormaPagamento(0));
    }

//    public List<FinTipoPagamento> getTipoPagamentoLista()
//    {
//        if(tipoPagamentoLista == null)
//        {
//            tipoPagamentoLista = new ArrayList<>();
//        }
//        return tipoPagamentoLista;
//    }
//
//    public void setTipoPagamentoLista(List<FinTipoPagamento> tipoPagamentoLista)
//    {
//        this.tipoPagamentoLista = tipoPagamentoLista;
//    }
//    
//    
//    public void carregarTiposDePagamento()
//    {
//        if(tipoPagamentoLista == null) getTipoPagamentoLista();
//        tipoPagamentoLista.clear();
//        tipoPagamentoLista.addAll(finTipoPagamentoFacade.findAll());
//    }
    
    public void limparDados()
    {
//        tipoPagamentoLista.clear();
        formaPagamentoLista.clear();
        bancoLista.clear();
        contaBancariaLista.clear();
        convenioLista.clear();
        projetoConvenioLista.clear();
//        recarregar = true;
    }

    public List<FinFormaPagamento> getFormaPagamentoLista()
    {
        if(formaPagamentoLista == null)
        {
            formaPagamentoLista = new ArrayList<>();
        }
        return formaPagamentoLista;
    }

    public void setFormaPagamentoLista(List<FinFormaPagamento> formaPagamentoLista)
    {
        this.formaPagamentoLista = formaPagamentoLista;
    }
    
    
    public void carregarFormasDePagamento()
    {
        if(formaPagamentoLista == null) getFormaPagamentoLista();
        formaPagamentoLista.clear();
        formaPagamentoLista.addAll(finFormaPagamentoFacade.findAll());
    }

    public List<FinBanco> getBancoLista()
    {
        if(bancoLista == null)
        {
            bancoLista = new ArrayList<>();
        }
        return bancoLista;
    }

    public void setBancoLista(List<FinBanco> bancoLista)
    {
        this.bancoLista = bancoLista;
    }
    
    public void carregarBancos()
    {
        if(bancoLista == null) getBancoLista();
        bancoLista.clear();
        bancoLista.addAll(finBancoFacade.findAll());
        if(!bancoLista.isEmpty())
        {
            carregarContasBancarias(bancoLista.get(0).getPkIdBanco());
        }
    }

    public List<FinContaBancaria> getContaBancariaLista()
    {
        if(contaBancariaLista == null)
        {
            contaBancariaLista = new ArrayList<>();
        }
        return contaBancariaLista;
    }

    public void setContaBancariaLista(List<FinContaBancaria> contaBancariaLista)
    {
        this.contaBancariaLista = contaBancariaLista;
    }
    
    public void carregarContasBancarias(int banco)
    {
        if(contaBancariaLista == null) getContaBancariaLista();
        contaBancariaLista.clear();
        contaBancariaLista.addAll(finContaBancariaFacade.pesquisaPorIdDoBanco(banco));
    }

    public List<GrlConvenio> getConvenioLista()
    {
        if(convenioLista == null)
        {
            convenioLista = new ArrayList<>();
        }
        return convenioLista;
    }

    public void setConvenioLista(List<GrlConvenio> convenioLista)
    {
        this.convenioLista = convenioLista;
    }
    
    public void carregarConvenios()
    {
        if(convenioLista == null) getConvenioLista();
        convenioLista.clear();
        convenioLista.addAll(grlConvenioFacade.findPorConveniosAtivos());
        if(!convenioLista.isEmpty())
        {
            carregarProjetosConvenios(convenioLista.get(0).getPkIdConvenio());
        }
        
    }

    public List<GrlProjetoConvenio> getProjetoConvenioLista()
    {
        if(projetoConvenioLista == null)
        {
            projetoConvenioLista = new ArrayList<>();
        }
        return projetoConvenioLista;
    }

    public void setProjetoConvenioLista(List<GrlProjetoConvenio> projetoConvenioLista)
    {
        this.projetoConvenioLista = projetoConvenioLista;
    }
    
    public void carregarProjetosConvenios(int idConvenio)
    {
        if(projetoConvenioLista == null) getProjetoConvenioLista();
        projetoConvenioLista.clear();
        projetoConvenioLista.addAll(grlProjetoConvenioFacade.findPorProjetosAtivos(idConvenio));
    }
    
    
//    public boolean isRecarregar()
//    {
//        return recarregar;
//    }
//
//    public void setRecarregar(boolean recarregar)
//    {
//        this.recarregar = recarregar;
//    }
    
   public void carregarDadosPagamento()
    {
//        carregarTiposDePagamento();
        carregarFormasDePagamento();
        carregarBancos();
        carregarConvenios();
//        getUltimoTipoDePagamento();
System.out.println("parou pagamento");
        pararAtualizacao = true;
    }
   
   
//   public void definirPagamentoDeUmServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
//   {
//       if(listaNaoPagos == null) listaNaoPagos = new ArrayList<>();
//       if(solicitacao == null) solicitacao = new AdmsSolicitacao();
//       listaNaoPagos.clear();
//       listaDeEncargosDevidosASerPagos.clear();
//       listaVeioDoServicoSolicitado = true;
//       listaNaoPagos.add(servicoSolicitado);
//       solicitacao = servicoSolicitado.getFkIdSolicitacao();
//   }
   
    public boolean isPararAtualizacao()
    {
        return pararAtualizacao;
    }

    public void setPararAtualizacao(boolean pararAtualizacao)
    {
System.out.println("Parou");
        this.pararAtualizacao = pararAtualizacao;
    }

    public AdmsPaciente getPaciente()
    {
        return paciente;
    }

    public void setPaciente(AdmsPaciente paciente)
    {
        this.paciente = paciente;
    }
    
    
    
}
