/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.receitas;

import entidade.AdmsPaciente;
import entidade.AmbConsulta;
import entidade.AmbEstadoPagamento;
import entidade.AmbPrescricaoMedica;
import entidade.AmbPrescricaoMedicaHasProduto;
import entidade.FarmCategoriaMedicamento;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmProduto;
import entidade.FarmTipoProduto;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.GrlCentroHospitalar;
import entidade.InterHoraMedicacao;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.consultas.AmbConsultaCriarBean;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAdmsPaciente;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsulta;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaGrlCentroHospitalar;
import managedBean.ambbean.ce.consultas.AmbConsultaListarBean;
import managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoListarBean;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaRhFuncionario;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaSeLoginBean;
import managedBean.farmbean.movimentos.FarmPedidoNovoBean;
import managedBean.interbean.InterMedicacaoNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.AmbPrescricaoMedicaFacade;
import sessao.AmbPrescricaoMedicaHasProdutoFacade;
import sessao.FarmProdutoFacade;
import sessao.GrlCentroHospitalarFacade;
import sessao.InterHoraMedicacaoFacade;
import sessao.RhFuncionarioFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbPrescricaoMedicaCriarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
        
    @EJB
    private AmbPrescricaoMedicaFacade ambPrescricaoMedicaFacade;
    @EJB
    private AmbPrescricaoMedicaHasProdutoFacade ambPrescricaoMedicaHasProdutoFacade;
    @EJB
    private FarmProdutoFacade farmProdutoFacade;
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;    
    @EJB
    private InterHoraMedicacaoFacade interHoraMedicacaoFacade; 
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
    private AdmsPaciente admsPacienteConsulta, admsPacienteReconsulta;
    private AmbConsulta ambConsultaAux;
    
    private AmbPrescricaoMedica ambPrescricaoMedica
                              , ambPrescricaoMedicaAux;
    
    private AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProduto
                                        , ambPrescricaoMedicaHasProdutoAux;
    
    private Date dataInicio
               , dataFim;
    
    private FarmProduto farmProduto;
    private GrlCentroHospitalar grlCentroHospitalar;
    private InterHoraMedicacao interHoraMedicacao; 
    
    private boolean salvarReceitaDiagnostico = false
                  , salvarReceitaConsulta    = false;
    
    private int [] listaPrescricoes;
    
    private int codigoHoraMedicacao 
              , fkIdViaAdministracao
              , fkIdFormaFarmaceutica
              , quantidade;
    
    
    /**
     * Creates a new instance of AmbPrescricaoMedicaCriarBean
     */
    public AmbPrescricaoMedicaCriarBean()
    {
        ++quantidade;
    }
    
    public static AmbConsultaListarBean getInstanciaAmbConsultaListarBean()
    {
        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
    }
    
    public static AmbPrescricaoMedicaCriarBean getInstanciaBean()
    {
        return (AmbPrescricaoMedicaCriarBean) GeradorCodigo.getInstanciaBean("ambPrescricaoMedicaCriarBean");
    }
    
    public static AmbPrescricaoMedicaListarBean getInstanciaAmbPrescricaoMedicaListarBean()
    {
        return (AmbPrescricaoMedicaListarBean) GeradorCodigo.getInstanciaBean("ambPrescricaoMedicaListarBean");
    }
    
    public AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    }      
    
//    public static InterMedicacaoNovoBean getInstanciaInterMedicacaoNovoBean()
//    {
//        return (InterMedicacaoNovoBean) GeradorCodigo.getInstanciaBean("interMedicacaoNovoBean");
//    }

//    public static FarmPedidoNovoBean getInstanciaFarmPedidoNovoBean()
//    {
//        return (FarmPedidoNovoBean) GeradorCodigo.getInstanciaBean("farmPedidoNovoBean");
//    }
    
//    public static SegLoginBean getInstanciaSeLoginBean()
//    {
//        return (SegLoginBean) GeradorCodigo.getInstanciaBean("segLoginBean");
//    }
    
    public static AmbPrescricaoMedica getInstanciaAmbPrescricaoMedica()
    {
        AmbPrescricaoMedica ambPrescricaoMedica = new AmbPrescricaoMedica();

        ambPrescricaoMedica.setFkIdConsulta(getInstanciaAmbConsulta());

        return ambPrescricaoMedica;
    }    
        
    public static AmbPrescricaoMedicaHasProduto getInstanciaAmbPrescricaoMedicaHasProduto()
    {
        AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProduto = new AmbPrescricaoMedicaHasProduto();
        
        ambPrescricaoMedicaHasProduto.setFkIdEstadoPagamento(new AmbEstadoPagamento());
        ambPrescricaoMedicaHasProduto.setFkIdFarmProduto(new FarmProduto());
        ambPrescricaoMedicaHasProduto.setFkIdHoraMedicacao(new InterHoraMedicacao());
        ambPrescricaoMedicaHasProduto.setFkIdPrescricaoMedica(new AmbPrescricaoMedica());
        
        return ambPrescricaoMedicaHasProduto;
    }

    public static FarmProduto getInstanciaFarmProduto()
    {
        FarmProduto farmProduto = new FarmProduto();
        
        farmProduto.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
        farmProduto.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica());
        farmProduto.setFkIdFuncionarioCadastrou(getInstanciaRhFuncionario());
        farmProduto.setFkIdTipoProduto(new FarmTipoProduto());
        farmProduto.setFkIdUnidadeMedida(new FarmUnidadeMedida());
        farmProduto.setFkIdViaAdministracao(new FarmViaAdministracao());
        
        return farmProduto;
    }

    public AdmsPaciente getAdmsPacienteConsulta()
    {
        if (admsPacienteConsulta == null)
        {
            admsPacienteConsulta = AmbConsultaCriarBean.getInstanciaAdmsPaciente();
        }
        return admsPacienteConsulta;
    }

    public void setAdmsPacienteConsulta(AdmsPaciente admsPacienteConsulta)
    {
        this.admsPacienteConsulta = admsPacienteConsulta;
    }     

    public AdmsPaciente getAdmsPacienteReconsulta()
    {
        if (admsPacienteReconsulta == null)
        {
            admsPacienteReconsulta = AmbConsultaCriarBean.getInstanciaAdmsPaciente();
        }
        return admsPacienteReconsulta;
    }

    public void setAdmsPacienteReconsulta(AdmsPaciente admsPacienteReconsulta)
    {
        this.admsPacienteReconsulta = admsPacienteReconsulta;
    }
    
    public AmbConsulta getAmbConsultaAux()
    {
        if (this.ambConsultaAux == null)
        {
            this.ambConsultaAux = getInstanciaAmbConsulta();
        }
        return ambConsultaAux;
    }

    public void setAmbConsultaAux(AmbConsulta ambConsultaAux)
    {
        this.ambConsultaAux = ambConsultaAux;
    }
    
    public AmbPrescricaoMedica getAmbPrescricaoMedica()
    {
        if (ambPrescricaoMedica == null)
        {
            ambPrescricaoMedica = getInstanciaAmbPrescricaoMedica();
        }
        return ambPrescricaoMedica;
    }

    public void setAmbPrescricaoMedica(AmbPrescricaoMedica ambPrescricaoMedica)
    {
        this.ambPrescricaoMedica = ambPrescricaoMedica;
    }

    public AmbPrescricaoMedica getAmbPrescricaoMedicaAux()
    {
        if (ambPrescricaoMedicaAux == null)
        {
            ambPrescricaoMedicaAux = getInstanciaAmbPrescricaoMedica();
        }
        return ambPrescricaoMedicaAux;
    }

    public void setAmbPrescricaoMedicaAux(AmbPrescricaoMedica ambPrescricaoMedicaAux)
    {
        this.ambPrescricaoMedicaAux = ambPrescricaoMedicaAux;
    }
    
    public AmbPrescricaoMedicaHasProduto getAmbPrescricaoMedicaHasProduto()
    {
        if (ambPrescricaoMedicaHasProduto == null)
        {
            ambPrescricaoMedicaHasProduto = getInstanciaAmbPrescricaoMedicaHasProduto();
        }
        return ambPrescricaoMedicaHasProduto;
    }

    public void setAmbPrescricaoMedicaHasProduto(AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProduto)
    {
        this.ambPrescricaoMedicaHasProduto = ambPrescricaoMedicaHasProduto;
    }

    public AmbPrescricaoMedicaHasProduto getAmbPrescricaoMedicaHasProdutoAux()
    {
        if (ambPrescricaoMedicaHasProdutoAux == null)
        {
            ambPrescricaoMedicaHasProdutoAux = getInstanciaAmbPrescricaoMedicaHasProduto();
        }
        return ambPrescricaoMedicaHasProdutoAux;
    }

    public void setAmbPrescricaoMedicaHasProdutoAux(AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProdutoAux)
    {
        this.ambPrescricaoMedicaHasProdutoAux = ambPrescricaoMedicaHasProdutoAux;
    }

    public FarmProduto getFarmProduto()
    {
        if (farmProduto == null)
        {
            farmProduto = getInstanciaFarmProduto();
        }
        return farmProduto;
    }

    public void setFarmProduto(FarmProduto farmProduto)
    {
        this.farmProduto = farmProduto;
    }

    public FarmProdutoFacade getFarmProdutoFacade()
    {
        return farmProdutoFacade;
    }

    public void setFarmProdutoFacade(FarmProdutoFacade farmProdutoFacade)
    {
        this.farmProdutoFacade = farmProdutoFacade;
    }

    public GrlCentroHospitalar getGrlCentroHospitalar()
    {
        if (grlCentroHospitalar == null)
        {
            grlCentroHospitalar = getInstanciaGrlCentroHospitalar();
        }
        return grlCentroHospitalar;
    }

    public void setGrlCentroHospitalar(GrlCentroHospitalar grlCentroHospitalar)
    {
        this.grlCentroHospitalar = grlCentroHospitalar;
    }     
    
    public InterHoraMedicacao getInterHoraMedicacao()
    {
        if (interHoraMedicacao == null)
        {
            interHoraMedicacao = new InterHoraMedicacao();
        }
        return interHoraMedicacao;
    }

    public void setInterHoraMedicacao(InterHoraMedicacao interHoraMedicacao)
    {
        this.interHoraMedicacao = interHoraMedicacao;
    }
    
    public boolean isSalvarReceitaDiagnostico()
    {
        return salvarReceitaDiagnostico;
    }

    public void setSalvarReceitaDiagnostico(boolean salvarReceitaDiagnostico)
    {
        this.salvarReceitaDiagnostico = salvarReceitaDiagnostico;
    }    
    
    public boolean isSalvarReceitaConsulta()
    {
        return salvarReceitaConsulta;
    }

    public void setSalvarReceitaConsulta(boolean salvarReceitaConsulta)
    {
        this.salvarReceitaConsulta = salvarReceitaConsulta;
    }
    
    public int[] getListaPrescricoes()
    {
        return listaPrescricoes;
    }

    public void setListaPrescricoes(int[] listaPrescricoes)
    {
        this.listaPrescricoes = listaPrescricoes;
    }

    public int getFkIdViaAdministracao()
    {
        return fkIdViaAdministracao;
    }

    public void setFkIdViaAdministracao(int fkIdViaAdministracao)
    {
        this.fkIdViaAdministracao = fkIdViaAdministracao;
    }

    public int getFkIdFormaFarmaceutica()
    {
        return fkIdFormaFarmaceutica;
    }

    public void setFkIdFormaFarmaceutica(int fkIdFormaFarmaceutica)
    {
        this.fkIdFormaFarmaceutica = fkIdFormaFarmaceutica;
    }
    
    public int getCodigoHoraMedicacao()
    {
        return codigoHoraMedicacao;
    }

    public void setCodigoHoraMedicacao(int codigoHoraMedicacao)
    {
        this.codigoHoraMedicacao = codigoHoraMedicacao;
    }
    
    public int getQuantidade()
    {
        return quantidade;
    }

    public void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }
    
//    public SegConta segContaObterSegLoginBean()
//    {
//        SegConta segConta = new SegConta();
//        segConta = getInstanciaSeLoginBean().obterContaDaCorrenteSessao();
//
//        return segConta;
//    }
    
//    public RhFuncionario obterFuncionario()
//    {
//        return segContaObterSegLoginBean().getFkIdFuncionario();
//    }
    
    public void criarReceitaConsulta()
    {
//        if (ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getPkIdProduto() == null)
//        {
//            Mensagem.warnMsg("O medicamento é obrigatório!");
//        }
//        else if (ambPrescricaoMedicaHasProduto.getFkIdHoraMedicacao().getPkIdHoraMedicacao() == null)
//        {
//            Mensagem.warnMsg("A hora é obrigatória!");
//        }
//        else if (getInstanciaAmbPrescricaoMedicaListarBean().getQuantidade() == 0)
//        {
//            Mensagem.warnMsg("A quantidade é obrigatória!");
//        }
//        else
//        {
            try
            {
            
                this.userTransaction.begin();
            
                ambPrescricaoMedica  = getInstanciaAmbPrescricaoMedica();
                ambPrescricaoMedicaHasProduto = getInstanciaAmbPrescricaoMedicaHasProduto();
                interHoraMedicacao = new InterHoraMedicacao();
            
                if (!salvarReceitaConsulta)
                {
                    grlCentroHospitalar = grlCentroHospitalarFacade.find(getInstanciaAmbPrescricaoMedicaListarBean().getCodigoCentroHospitalar());
System.err.print("Teste 0.1  : ambPrescricaoMedicaBean: " + grlCentroHospitalar.getFkIdInstituicao().getDescricao());
                    ambPrescricaoMedica.setFkIdCentro(grlCentroHospitalar);
                    
                    ambPrescricaoMedica.setDataHoraPrescricao(new Date());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 1   : " + ambPrescricaoMedica.getDataHoraPrescricao());

                    if (AmbConsultaListarBean.getInstanciaBean().getACAux() != null) 
                    {   
                        ambPrescricaoMedica.setFkIdConsulta(AmbConsultaListarBean.getInstanciaBean().getACAux());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 2   : " + ambPrescricaoMedica.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()); 
                    }
                    
                    ambPrescricaoMedica.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 3   : " + ambPrescricaoMedica.getFkIdFuncionario().getFkIdPessoa().getNome());                

                    ambPrescricaoMedicaFacade.create(ambPrescricaoMedica);
                
                    salvarReceitaConsulta = true;
                }
                        
                List<AmbPrescricaoMedica> listaPrescricao = ambPrescricaoMedicaFacade.findAll();
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 4   : " + listaPrescricao.toString() + " Tamanho: " + listaPrescricao.size());                
                
               ambPrescricaoMedicaHasProduto.setFkIdEstadoPagamento(null);
//                
               interHoraMedicacao = interHoraMedicacaoFacade.find(getInstanciaAmbPrescricaoMedicaListarBean().getCodigoHoraMedicacao());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 5   : " + interHoraMedicacao.getDescricao()); 
               farmProduto = farmProdutoFacade.find(getInstanciaAmbPrescricaoMedicaListarBean().getCodigoPrescricaoProduto());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 6   : " + farmProduto.getDescricao() + " (" + farmProduto.getDosagem() + "" + farmProduto.getFkIdUnidadeMedida().getAbreviatura() + ")" + " - " + farmProduto.getFkIdFormaFarmaceutica().getDescricao()); 
               ambPrescricaoMedicaHasProduto.setFkIdFarmProduto(farmProduto);
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 7   : " + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getDescricao() + " (" + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getDosagem() + "" + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getFkIdUnidadeMedida().getAbreviatura() + ")" + " - " + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getFkIdFormaFarmaceutica().getDescricao()); 
               ambPrescricaoMedicaHasProduto.setFkIdHoraMedicacao(interHoraMedicacao);
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 8   : " + ambPrescricaoMedicaHasProduto.getFkIdHoraMedicacao().getDescricao());                
               ambPrescricaoMedicaHasProduto.getFkIdPrescricaoMedica().setPkIdPrescricaoMedica(listaPrescricao.get(listaPrescricao.size() - 1).getPkIdPrescricaoMedica());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 9   : " + ambPrescricaoMedicaHasProduto.getFkIdPrescricaoMedica().getPkIdPrescricaoMedica() + "\nTamanho: " + (listaPrescricao.size() - 1));                
               ambPrescricaoMedicaHasProduto.setQuantidade(getInstanciaAmbPrescricaoMedicaListarBean().getQuantidade());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 10  : " + ambPrescricaoMedicaHasProduto.getQuantidade());
               ambPrescricaoMedicaHasProduto.setObservacoes(getInstanciaAmbPrescricaoMedicaListarBean().getObservacoes());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 11  : " + ambPrescricaoMedicaHasProduto.getObservacoes());

               ambPrescricaoMedicaHasProdutoFacade.create(ambPrescricaoMedicaHasProduto); 
                
             this.userTransaction.commit();

            
               Mensagem.sucessoMsg("Medicamento adicionado à Receita!");
            
               limparCampos();
                
            } catch (Exception e)
            {
                try
                {
                    e.printStackTrace();
                    Mensagem.erroMsg(e.toString());
                    this.userTransaction.rollback();
                } catch (ExceptionInInitializerError | IllegalStateException | SecurityException | SystemException ex)
                {
                    e.printStackTrace();
                    Mensagem.erroMsg("Rollback: " + ex.toString());
                }
            }
//      }
    }
    
    public void criarReceitaDiagnostico()
    {
//        if (ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getPkIdProduto() == null)
//        {
//            Mensagem.warnMsg("O medicamento é obrigatório!");
//        }
//        else if (ambPrescricaoMedicaHasProduto.getFkIdHoraMedicacao().getPkIdHoraMedicacao() == null)
//        {
//            Mensagem.warnMsg("A hora é obrigatória!");
//        }
//        else if (quantidade == 0)
//        {
//            Mensagem.warnMsg("A quantidade é obrigatória!");
//        }
//        else
//        {
            try
            {
            
                this.userTransaction.begin();
            
                ambPrescricaoMedica  = getInstanciaAmbPrescricaoMedica();
                ambPrescricaoMedicaHasProduto = getInstanciaAmbPrescricaoMedicaHasProduto();
                interHoraMedicacao = new InterHoraMedicacao();
            
                if (!salvarReceitaDiagnostico)
                {
                    grlCentroHospitalar = grlCentroHospitalarFacade.find(getInstanciaAmbPrescricaoMedicaListarBean().getCodigoCentroHospitalar());
System.err.print("Teste 0.1  : ambPrescricaoMedicaBean: " + grlCentroHospitalar.getFkIdInstituicao().getDescricao());
                    ambPrescricaoMedica.setFkIdCentro(grlCentroHospitalar);                    
                    
                    ambPrescricaoMedica.setDataHoraPrescricao(new Date());
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 1   : " + ambPrescricaoMedica.getDataHoraPrescricao());

                    if (AmbDiagnosticoListarBean.getInstanciaBean().getAmbDiagnostico() != null) 
                    {   
                        ambPrescricaoMedica.setFkIdConsulta(AmbDiagnosticoListarBean.getInstanciaBean().getAmbDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta());
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 2   : " + ambPrescricaoMedica.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()); 
                    }
                    
                    ambPrescricaoMedica.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 3   : " + ambPrescricaoMedica.getFkIdFuncionario().getFkIdPessoa().getNome());                

                    ambPrescricaoMedicaFacade.create(ambPrescricaoMedica);
                
                    salvarReceitaDiagnostico = true;
                }
                        
                List<AmbPrescricaoMedica> listaPrescricao = ambPrescricaoMedicaFacade.findAll();
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 4   : " + listaPrescricao.toString() + " Tamanho: " + listaPrescricao.size());                
                
               ambPrescricaoMedicaHasProduto.setFkIdEstadoPagamento(null);
//                
               interHoraMedicacao = interHoraMedicacaoFacade.find(getInstanciaAmbPrescricaoMedicaListarBean().getCodigoHoraMedicacao());
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 5   : " + interHoraMedicacao.getDescricao()); 
               farmProduto = farmProdutoFacade.find(getInstanciaAmbPrescricaoMedicaListarBean().getCodigoPrescricaoProduto());
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 6   : " + farmProduto.getDescricao() + " (" + farmProduto.getDosagem() + "" + farmProduto.getFkIdUnidadeMedida().getAbreviatura() + ")" + " - " + farmProduto.getFkIdFormaFarmaceutica().getDescricao()); 
               ambPrescricaoMedicaHasProduto.setFkIdFarmProduto(farmProduto);
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 7   : " + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getDescricao() + " (" + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getDosagem() + "" + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getFkIdUnidadeMedida().getAbreviatura() + ")" + " - " + ambPrescricaoMedicaHasProduto.getFkIdFarmProduto().getFkIdFormaFarmaceutica().getDescricao()); 
               ambPrescricaoMedicaHasProduto.setFkIdHoraMedicacao(interHoraMedicacao);
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 8   : " + ambPrescricaoMedicaHasProduto.getFkIdHoraMedicacao().getDescricao());                
               ambPrescricaoMedicaHasProduto.getFkIdPrescricaoMedica().setPkIdPrescricaoMedica(listaPrescricao.get(listaPrescricao.size() - 1).getPkIdPrescricaoMedica());
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 9   : " + ambPrescricaoMedicaHasProduto.getFkIdPrescricaoMedica().getPkIdPrescricaoMedica() + "\nTamanho: " + (listaPrescricao.size() - 1));                
               ambPrescricaoMedicaHasProduto.setQuantidade(getInstanciaAmbPrescricaoMedicaListarBean().getQuantidade());
System.err.print("ambPrescricaoMedicaBean.criarReceitaDiagnostico(): Teste 10  : " + ambPrescricaoMedicaHasProduto.getQuantidade());
               ambPrescricaoMedicaHasProduto.setObservacoes(getInstanciaAmbPrescricaoMedicaListarBean().getObservacoes());
System.err.print("ambPrescricaoMedicaBean.criarReceitaConsulta(): Teste 11  : " + ambPrescricaoMedicaHasProduto.getObservacoes());

               ambPrescricaoMedicaHasProdutoFacade.create(ambPrescricaoMedicaHasProduto); 
                
             this.userTransaction.commit();

            
               Mensagem.sucessoMsg("Medicamento adicionado à Receita!");
            
               limparCampos();
                
            } catch (Exception e)
            {
                try
                {
                    e.printStackTrace();
                    Mensagem.erroMsg(e.toString());
                    this.userTransaction.rollback();
                } catch (ExceptionInInitializerError | IllegalStateException | SecurityException | SystemException ex)
                {
                    e.printStackTrace();
                    Mensagem.erroMsg("Rollback: " + ex.toString());
                }
            }
//      }
    }    
    
    public void editarReceita()
    {
        try
        {
            this.userTransaction.begin();
            
            if (ambPrescricaoMedicaAux.getPkIdPrescricaoMedica() == null && ambPrescricaoMedicaHasProdutoAux.getPkIdPrescricaoMedicaProduto() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            
//            supiSupervisorHasEscalaHasConsultorio = supiSupervisorHasEscalaHasConsultorioFacade.find(codigoMedico);
//System.out.print("ambConsultorioAtendimentoBeaneditarRegisto(): Teste 0: " + ambConsultorio.getNome());
//            ambConsultorioAtendimentoAux.setFkIdSupiSupervisorEscalaConsultorio(supiSupervisorHasEscalaHasConsultorio);
//System.out.print("ambConsultorioAtendimentoBeaneditarRegisto(): Teste 1: " + ambConsultorioAtendimentoAux.getFkIdSupiSupervisorEscalaConsultorio().getFkIdConsultorio().getNome());
//            ambConsultorioAtendimentoAux.setFkIdMedicoHasEscala(supiMedicoHasEscala);
//            ambConsultorioAtendimentoAux.setFkIdFuncionario(rhFuncionarioFacade.find(obterFuncionario().getPkIdFuncionario()));
//System.out.print("ambConsultorioAtendimentoBeaneditarRegisto(): Teste 2: " + ambConsultorioAtendimentoAux.getFkIdFuncionario().getFkIdPessoa().getNome()); 

            this.ambPrescricaoMedicaHasProdutoFacade.edit(ambPrescricaoMedicaHasProdutoAux);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Dados editados com sucesso!");
            
            ambPrescricaoMedicaHasProdutoAux = getInstanciaAmbPrescricaoMedicaHasProduto();
            
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                this.userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (ExceptionInInitializerError | IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    ///Métodos para listagens e pesquisas
    
    public String lerMedicamentosPrescricao(long idPrescricao) 
    {
        String resultado ="";

        for (int i = 0; i < devolveMedicamentosPrescricao().size(); i++)
            if (devolveMedicamentosPrescricao().get(i).getFkIdPrescricaoMedica().getPkIdPrescricaoMedica() == idPrescricao)
               resultado += devolveMedicamentosPrescricao().get(i).getFkIdFarmProduto().getDescricao() + ". ";
        
        return resultado;
    }
    
    public List<AmbPrescricaoMedicaHasProduto> devolveMedicamentosPrescricao() 
    {
        List<AmbPrescricaoMedicaHasProduto> resultado = new ArrayList();
        
        for (AmbPrescricaoMedicaHasProduto apmp : ambPrescricaoMedicaHasProdutoFacade.findAll()) 
        {
             for (AmbPrescricaoMedica apm : ambPrescricaoMedicaFacade.findAll()) 
             {
                  if (apm.getPkIdPrescricaoMedica().equals(apmp.getFkIdPrescricaoMedica().getPkIdPrescricaoMedica())) 
                  {
                      for (FarmProduto fp : farmProdutoFacade.findAll()) 
                      {
                          if (fp.getPkIdProduto().equals(apmp.getFkIdFarmProduto().getPkIdProduto())) 
                          {
                              apmp.setFkIdFarmProduto(fp);
                              apmp.setFkIdPrescricaoMedica(apm);
                              resultado.add(apmp);
                          }
                       }
                   }
              }
        }
        return resultado;
    }
    
//    public List<AmbPrescricaoMedicaHasProduto> pesquisarPrescricoes()
//    {
//        return ambPrescricaoMedicaHasProdutoFacade.findPrescricao(ambPrescricaoMedicaHasProduto, dataInicio, dataFim);
//    }
    
    ///
    
//    public AmbPrescricaoMedica seleccionarDetalhesConsulta(AmbConsulta ac)
//    {
//        acAux = ac;
//
//System.out.print("1:AmbConsultaBean.seleccionarDetalhesConsulta()\nId Consulta   : " + acAux.getPkIdConsulta());
//System.out.print("2:AmbConsultaBean.seleccionarDetalhesConsulta()\nNome          : " + acAux.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
//System.out.print("3:AmbConsultaBean.seleccionarDetalhesConsulta()\nData          : " + acAux.getDataHoraConsulta());
//        return acAux;
//    } 
    
    public void seleccionar(AmbConsulta ac)
    {
        ambConsultaAux = ac;
    }
    
    public AmbPrescricaoMedicaHasProduto seleccionarDetalhes(AmbPrescricaoMedicaHasProduto apmhp)
    {
        ambPrescricaoMedicaHasProdutoAux = apmhp;

System.out.print("1:\nId AmbPrescricaoMedicaHasProduto   : " + apmhp.getPkIdPrescricaoMedicaProduto());
        return ambPrescricaoMedicaHasProdutoAux;
    }
    
    public String dataSistema()
    {
        return DataUtils.dataTimeAgoraFull();
    }
       
//    public String fecharConsulta()
//    {
//        return "/ambVisao/ambConsultas/consultaClinicaGeralAmb.xhtml?faces-redirect=true";
//    }
//    
//    public String fecharDiagnostico()
//    {
//        return "/ambVisao/ambDiagnosticos/diagnosticosAmbCe.xhtml?faces-redirect=true";
//    }
    
    public void limparCampos()
    {
        ambPrescricaoMedica = null;
        ambPrescricaoMedicaHasProduto = null;
//        ambFarmProdutoListarBean.categoriaNivel1.pkIdCategoriaMedicamento
        AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel1().setPkIdCategoriaMedicamento(null);
        AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel2().setPkIdCategoriaMedicamento(null);
        AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel3().setPkIdCategoriaMedicamento(null);
        AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel4().setPkIdCategoriaMedicamento(null);
        getInstanciaAmbPrescricaoMedicaListarBean().setQuantidade(1);
//        unidadeMedida = null;
    }
    
    ///Relacionado a Farmácia

    
    ///
    
    public List<AmbPrescricaoMedicaHasProduto> retornaProduto(int produto)
    {
        ambPrescricaoMedicaHasProduto = new AmbPrescricaoMedicaHasProduto();

        List<AmbPrescricaoMedicaHasProduto> resultado = new ArrayList<>();

        for (FarmProduto produtos : farmProdutoFacade.findAll())
        {
            if (produtos.getPkIdProduto() == produto)
            {
                ambPrescricaoMedicaHasProduto.setFkIdFarmProduto(produtos);
                resultado.add(ambPrescricaoMedicaHasProduto);
            }
        }
        return resultado;
    }
    
    public List<AmbPrescricaoMedicaHasProduto> devolverProdutos()
    {
        List<AmbPrescricaoMedicaHasProduto> resultadoPrescricoes = new ArrayList();
        
        int cp = 0;
        
        if (getInstanciaAmbPrescricaoMedicaListarBean().lerTodosMedicamentosPorViaAdmissao() != null)
        {
            for (int i = 0; i < getInstanciaAmbPrescricaoMedicaListarBean().lerTodosMedicamentosPorViaAdmissao().size(); i++)
            {
                for (AmbPrescricaoMedicaHasProduto ambPHP : retornaProduto(getInstanciaAmbPrescricaoMedicaListarBean().lerTodosMedicamentosPorViaAdmissao().get(i).getPkIdProduto()))
                {
                    if (ambPHP.getFkIdFarmProduto().getPkIdProduto() == cp)
                    {
                        ambPrescricaoMedicaHasProduto = ambPHP;
                    }
                    resultadoPrescricoes.add(ambPHP);
                }

                if (i < getInstanciaAmbPrescricaoMedicaListarBean().lerTodosMedicamentosPorViaAdmissao().size() - 1)
                {
                    resultadoPrescricoes.get(i).getFkIdFarmProduto().getDescricao();
                }
            }
        }
        return resultadoPrescricoes;
    }
    
    ///Referente à Farmácia
    ///
}