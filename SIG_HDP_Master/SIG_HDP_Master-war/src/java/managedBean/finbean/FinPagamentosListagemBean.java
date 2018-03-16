/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import entidade.AdmsPaciente;
//import entidade.AdmsTipoPagamentoSolicitacao;
import entidade.FinBanco;
import entidade.FinContaBancaria;
import entidade.FinFormaPagamento;
import entidade.FinPagamento;
import entidade.FinPagamentoConvenio;
import entidade.FinPagamentoEncargoDevido;
import entidade.FinPagamentoViaBanco;
import entidade.FinTipoPagamento;
import entidade.GrlConvenio;
import entidade.GrlProjetoConvenio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.UserTransaction;
import sessao.FinContaBancariaFacade;
import sessao.FinFormaPagamentoFacade;
import sessao.FinPagamentoConvenioFacade;
import sessao.FinPagamentoEncargoDevidoFacade;
import sessao.FinPagamentoFacade;
import sessao.FinPagamentoViaBancoFacade;
import sessao.FinTipoPagamentoFacade;
import sessao.GrlProjetoConvenioFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class FinPagamentosListagemBean implements Serializable
{
    @EJB
    private FinPagamentoEncargoDevidoFacade finPagamentoEncargoDevidoFacade;
    @EJB
    private FinPagamentoFacade finPagamentoFacade;
    @EJB
    private FinPagamentoConvenioFacade finPagamentoConvenioFacade;
    @EJB
    private FinPagamentoViaBancoFacade finPagamentoViaBancoFacade;
    @EJB
    private FinTipoPagamentoFacade finTipoPagamentoFacade;
    @EJB
    private GrlProjetoConvenioFacade grlProjetoConvenioFacade;
    @EJB
    private FinContaBancariaFacade finContaBancariaFacade;
    @EJB
    private FinFormaPagamentoFacade finFormaPagamentoFacade;
    
    
    private FinPagamento pagamentoPesquisar; 
    
    private FinPagamentoViaBanco pagamentoViaBanco;
    
    private FinPagamentoConvenio finPagamentoConvenio;
    
    private AdmsPaciente pacienteApresentarDadosAssociadoPagamento;
    
    private Date dataInicial, dataFinal;
    
    private List <FinBanco> bancoLista;
    
    private List <FinContaBancaria> contaBancariaLista;
    
    private List <GrlConvenio> convenioLista;
    
    private List <GrlProjetoConvenio> projetoConvenioLista;
    
    private List <FinPagamento> finPagamentoLista;
    
    private Integer quantidadeRegistos = 10;
    
    private boolean pagamentoBancario = false, pagamentoConvenio = false, convenioProjetos = false, formasDePagamento = true;
    
    
    @Resource
    protected UserTransaction userTransaction;

    /**
     * Creates a new instance of FinPagamentosListagem
     */
    public FinPagamentosListagemBean()
    {
    }
    
    
    public static FinPagamentosListagemBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FinPagamentosListagemBean finPagamentosListagemBean = 
            (FinPagamentosListagemBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "finPagamentosListagemBean");
        
        return finPagamentosListagemBean;
    }
    

    public FinPagamento getPagamento()
    {
        if(pagamentoPesquisar == null)
        {
            pagamentoPesquisar = getInstancia();
        }
        return pagamentoPesquisar;
    }

    public void setPagamento(FinPagamento pagamentoPesquisar)
    {
        this.pagamentoPesquisar = pagamentoPesquisar;
    }
    
    
    public FinPagamento getInstancia()
    {
        FinPagamento pagamentoPesquisarInstancia = new FinPagamento();
        pagamentoPesquisarInstancia.setFkIdFormaPagamento(new FinFormaPagamento());
//        pagamentoPesquisarInstancia.setFkIdTipoPagamentoSolicitacao(new AdmsTipoPagamentoSolicitacao());
//        pagamentoPesquisarInstancia.getFkIdTipoPagamentoSolicitacao().setFkIdTipoPagamento(new FinTipoPagamento());
        pagamentoPesquisarInstancia.setDataPagamento(new Date());
        
        return pagamentoPesquisarInstancia;
    }

    public FinPagamentoViaBanco getPagamentoViaBanco()
    {
        if(pagamentoViaBanco == null)
        {
            pagamentoViaBanco = getInstanciaPagamentoViaBanco();
        }
        return pagamentoViaBanco;
    }

    public void setPagamentoViaBanco(FinPagamentoViaBanco pagamentoViaBanco)
    {
        this.pagamentoViaBanco = pagamentoViaBanco;
    }
    
    
    public FinPagamentoViaBanco getInstanciaPagamentoViaBanco()
    {
        FinPagamentoViaBanco pagamentoViaBancoInstancia = new FinPagamentoViaBanco();
        
        pagamentoViaBancoInstancia.setFkIdContaBancaria(new FinContaBancaria());
        pagamentoViaBancoInstancia.getFkIdContaBancaria().setFkIdBanco(new FinBanco());
        
        return pagamentoViaBancoInstancia;
    }

    
    public FinPagamentoConvenio getFinPagamentoConvenio()
    {
        if(finPagamentoConvenio == null)
        {
            finPagamentoConvenio = getInstanciaPagamentoConvenio();
        }
        return finPagamentoConvenio;
    }

    public void setFinPagamentoConvenio(FinPagamentoConvenio finPagamentoConvenio)
    {
        this.finPagamentoConvenio = finPagamentoConvenio;
    }
    
    public FinPagamentoConvenio getInstanciaPagamentoConvenio()
    {
        FinPagamentoConvenio finPagamentoConvenioInstancia = new FinPagamentoConvenio();
        
        finPagamentoConvenioInstancia.setFkIdConvenio(new GrlConvenio());
        finPagamentoConvenioInstancia.setFkIdProjetoConvenio(new GrlProjetoConvenio());
        
        return finPagamentoConvenioInstancia;
    }

    public Date getDataInicial()
    {
        if(dataInicial == null)
        {
            dataInicial = new Date();
        }
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial)
    {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal()
    {
        if(dataFinal == null)
        {
            dataFinal = new Date();
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        dataFinal = getEndOfDay(dataFinal);
        this.dataFinal = dataFinal;
    }
    
    
    public Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
   
    public void changeFormaDePagamento(ValueChangeEvent e)
    {
        
        if (!(e.getNewValue() == null))
        {
            int cod = Integer.parseInt(e.getNewValue().toString());
            pagamentoPesquisar.getFkIdFormaPagamento().setPkIdFormaPagamento(cod);
            FinFormaPagamento formaDePagamento = finFormaPagamentoFacade.find(pagamentoPesquisar.getFkIdFormaPagamento().getPkIdFormaPagamento());
            if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Bancário"))
            {
                pagamentoBancario = true;
                pagamentoConvenio = false;
                definirDadosConvenioANull();
            }
            else if (formaDePagamento.getDescricaoFormaPagamento().equalsIgnoreCase("Pagamento Por Convênio"))
            {
                pagamentoConvenio = true;
                pagamentoBancario = false;
                definirDadosBancarioANull();
            }
            else
            {
                definirDadosConvenioANull();
                definirDadosBancarioANull();
                definirFormasParaFalse();
            }
        }
        else
        {
            definirFormasParaFalse();
            pagamentoPesquisar.getFkIdFormaPagamento().setPkIdFormaPagamento(null);
            definirDadosConvenioANull();
            definirDadosBancarioANull();
        }
    }

    public void definirFormasParaFalse()
    {
        pagamentoConvenio = false;
        pagamentoBancario = false;
        convenioProjetos = false;
    }
    
    public void definirDadosConvenioANull()
    {
        finPagamentoConvenio.getFkIdConvenio().setPkIdConvenio(null);
        finPagamentoConvenio.getFkIdProjetoConvenio().setPkIdProjetoConvenio(null);
    }
    
    public void definirDadosBancarioANull()
    {
        pagamentoViaBanco.getFkIdContaBancaria().getFkIdBanco().setPkIdBanco(null);
        pagamentoViaBanco.getFkIdContaBancaria().setPkIdContaBancaria(null);
    }
   
   public void changeBanco(ValueChangeEvent e)
   {
        if (e.getNewValue() != null)
        {
            int cod = Integer.parseInt(e.getNewValue().toString());
            pagamentoViaBanco.getFkIdContaBancaria().getFkIdBanco().setPkIdBanco(cod);
            carregarContasBancarias(cod);
        }
        else 
        {
            if(contaBancariaLista == null) getContaBancariaLista();
            else contaBancariaLista.clear();
        }
   }
   
    public void carregarContasBancarias(int banco)
    {
        if(contaBancariaLista == null) getContaBancariaLista();
        contaBancariaLista.clear();
        contaBancariaLista.addAll(finContaBancariaFacade.pesquisaPorIdDoBanco(banco));
    }
   
   public void changeContaBancaria(ValueChangeEvent e)
   {
        if (e.getNewValue() == null)
        {
            pagamentoViaBanco.getFkIdContaBancaria().setPkIdContaBancaria(null);
        }
        else
        {
            pagamentoViaBanco.getFkIdContaBancaria().setPkIdContaBancaria((Integer)e.getNewValue());
        }
   }
   
   public void changeConvenio(ValueChangeEvent e)
   {
        if (e.getNewValue() == null)
        {
            finPagamentoConvenio.getFkIdConvenio().setPkIdConvenio(null);
            if(projetoConvenioLista == null) getProjetoConvenioLista();
            else projetoConvenioLista.clear();
        }
        else
        {
            int cod = Integer.parseInt(e.getNewValue().toString());
            finPagamentoConvenio.getFkIdConvenio().setPkIdConvenio(cod);
            carregarProjetosConvenios(cod);
        }
   }
   
    public void carregarProjetosConvenios(int idConvenio)
    {
        if(projetoConvenioLista == null) getProjetoConvenioLista();
        projetoConvenioLista.clear();
        projetoConvenioLista.addAll(grlProjetoConvenioFacade.findPorProjetosAtivos(idConvenio));
    }
   
   public void changeProjetoConvenio(ValueChangeEvent e)
   {
        if (e.getNewValue() == null)
        {
            finPagamentoConvenio.getFkIdProjetoConvenio().setPkIdProjetoConvenio(null);
        }
        else
        {
            finPagamentoConvenio.getFkIdProjetoConvenio().setPkIdProjetoConvenio((Integer)e.getNewValue());
        }
   }
   
   public void changeTipoPagamento(ValueChangeEvent e)
   {
        if (!(e.getNewValue() == null))
        {
            int cod = Integer.parseInt(e.getNewValue().toString());
//            pagamentoPesquisar.getFkIdTipoPagamentoSolicitacao().setPkIdTipoPagamentoSolicitacao((long)(Integer)e.getNewValue());
            FinTipoPagamento finTipoPagamento = finTipoPagamentoFacade.find(cod);
            if (finTipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP") || finTipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP-FS"))
            {
                pagamentoBancario = false;
                pagamentoConvenio = false;
                formasDePagamento = false;
                definirDadosConvenioANull();
                definirDadosBancarioANull();
                pagamentoPesquisar.getFkIdFormaPagamento().setPkIdFormaPagamento(null);
            }
            else{
                formasDePagamento = true;
            }
        }
        else
        {
            pagamentoBancario = true;
            pagamentoConvenio = true;
            formasDePagamento = true;
//            pagamentoPesquisar.getFkIdTipoPagamentoSolicitacao().setPkIdTipoPagamentoSolicitacao(null);
        }
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
        if(contaBancariaLista == null)
        {
            contaBancariaLista = new ArrayList<>();
        }
        this.contaBancariaLista = contaBancariaLista;
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
    
    public void pesquisar()
    {        
        if (finPagamentoLista == null)
        {
            finPagamentoLista = new ArrayList<>();
        }
        finPagamentoLista = finPagamentoFacade.findPagamento(pagamentoPesquisar, 
            dataInicial, dataFinal, pagamentoViaBanco, finPagamentoConvenio, new AdmsPaciente(), 100);
        
        if(finPagamentoLista.isEmpty())
            Mensagem.warnMsg("Nenhuma Informação Encontrada");
    }
    
    public void atualizarPesquisar()
    {        
        finPagamentoLista = finPagamentoFacade.findPagamento(pagamentoPesquisar, 
            dataInicial, dataFinal, pagamentoViaBanco, finPagamentoConvenio, new AdmsPaciente(), 100);
    }

    public boolean isPagamentoBancario()
    {
        return pagamentoBancario;
    }

    public void setPagamentoBancario(boolean pagamentoBancario)
    {
        this.pagamentoBancario = pagamentoBancario;
    }

    public boolean isPagamentoConvenio()
    {
        return pagamentoConvenio;
    }

    public void setPagamentoConvenio(boolean pagamentoConvenio)
    {
        this.pagamentoConvenio = pagamentoConvenio;
    }

    public boolean isConvenioProjetos()
    {
        return convenioProjetos;
    }

    public void setConvenioProjetos(boolean convenioProjetos)
    {
        this.convenioProjetos = convenioProjetos;
    }    
    
    public List<FinPagamento> getPagamentoLista()
    {
        if(finPagamentoLista == null)
        {
            finPagamentoLista = new ArrayList<>();
        }
        return finPagamentoLista;
    }

    public void setPagamentoLista(List<FinPagamento> finPagamentoLista)
    {
        this.finPagamentoLista = finPagamentoLista;
    }


    public boolean isFormasDePagamento()
    {
        return formasDePagamento;
    }

    public void setFormasDePagamento(boolean formasDePagamento)
    {
        this.formasDePagamento = formasDePagamento;
    }
     
    public String getFormaQueFoiPago(FinPagamento finPagamento)
    {
        String formaPagamento = "";
    
        FinPagamentoConvenio pagamentoConvenioLocal = finPagamentoConvenioFacade.findPagamentoConvenioPorPagamento(finPagamento);
        FinPagamentoViaBanco pagamentoViaBancoLocal = finPagamentoViaBancoFacade.findPagamentoViaBancoPorPagamento(finPagamento);
        
        if(pagamentoConvenioLocal != null)
        {
            if(pagamentoConvenioLocal.getFkIdConvenio() != null)
            {
                formaPagamento = ""+pagamentoConvenioLocal.getFkIdConvenio().getNomeConvenio();
            }
            else{
                formaPagamento = ""+pagamentoConvenioLocal.getFkIdProjetoConvenio().getFkIdConvenio().getNomeConvenio()+" / Projeto > "+
                    pagamentoConvenioLocal.getFkIdProjetoConvenio().getDescricaoProjeto();
            }
        }
        if(pagamentoViaBancoLocal != null)
        {
            formaPagamento = ""+pagamentoViaBancoLocal.getFkIdContaBancaria().getFkIdBanco().getNomeBanco()+" / "+
                pagamentoViaBancoLocal.getFkIdContaBancaria().getNumeroConta();
        }
        
        return formaPagamento;
    }
    
    public Date getDataAtual()
    {
        return new Date();
    }
    
    public Integer getQuantidadeRegistos()
    {
//        if(quantidadeRegistos == null) quantidadeRegistos = new Integer(0);
        return quantidadeRegistos;
    }

    public void setQuantidadeRegistos(Integer quantidadeRegistos)
    {
        this.quantidadeRegistos = quantidadeRegistos;
    }
    
    public void limparResultadosLista()
    {
        if(finPagamentoLista != null) finPagamentoLista.clear();
    }
    
    
    public void carregarEncargosDoPagamentoEDefinirPaciente(FinPagamento pagamentoLocal)
    {
        List<FinPagamentoEncargoDevido> encargosPagamento = finPagamentoEncargoDevidoFacade.findPagamentoEncargoListaByPagamento(pagamentoLocal);
        pagamentoLocal.setFinPagamentoEncargoDevidoList(encargosPagamento); 
        
        pacienteApresentarDadosAssociadoPagamento = encargosPagamento.get(0).getFkIdEncargoDevido().getFkIdSubprocesso().getFkIdPaciente();
    }

    public AdmsPaciente getPacienteApresentarDadosAssociadoPagamento()
    {
        return pacienteApresentarDadosAssociadoPagamento;
    }

    public void setPacienteApresentarDadosAssociadoPagamento(AdmsPaciente pacienteApresentarDadosAssociadoPagamento)
    {
        this.pacienteApresentarDadosAssociadoPagamento = pacienteApresentarDadosAssociadoPagamento;
    }
    
    
    
    
    
}
