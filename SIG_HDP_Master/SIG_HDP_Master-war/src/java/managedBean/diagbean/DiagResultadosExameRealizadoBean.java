/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.AdmsServico;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.DiagAmostra;
import entidade.DiagCategoriaExame;
import entidade.DiagColecta;
import entidade.DiagExame;
import entidade.DiagExameRealizado;
import entidade.DiagTipoAmostra;
import entidade.GrlCentroHospitalar;
import entidade.GrlPessoa;
import entidade.RhFuncionario;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.AdmsServicoEfetuadoBean;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.DiagAmostraFacade;
import sessao.DiagCategoriaExameFacade;
import sessao.DiagColectaFacade;
import sessao.DiagExameFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.DiagTipoAmostraFacade;
import sessao.GrlCentroHospitalarFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;
import util.RelatorioJasper;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@ViewScoped
public class DiagResultadosExameRealizadoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsClassificacaoServicoSolicitadoFacade admsClassificacaoServicoSolicitadoFacade;

    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;

    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;

    @EJB
    private DiagExameFacade diagExameFacade;
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private DiagColectaFacade diagColectaFacade;
    @EJB
    private DiagTipoAmostraFacade diagTipoAmostraFacade;
    @EJB
    private DiagAmostraFacade diagAmostraFacade;
    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;

    private DiagColecta diagColecta;
    private DiagAmostra diagAmostra;
    private DiagTipoAmostra diagTipoAmostra;
    private DiagExame diagExame;
    private DiagExameRealizado diagExameRealizado, diagExameRealizadoPesquisar, diagExameRealizadoVisualizar;
    private AdmsServicoSolicitado admsServicoSolicitado, admsServicoSolicitadoVisualizar;
    private RhFuncionario rhFuncionarioExame, rhFuncionarioColecta;

    private AdmsServicoEfetuado admsServicoEfetuado;

    private List<DiagExame> listaExames;

    private Date dataInicioPesquisa, dataFimPesquisa;

    private boolean pesquisar;

    List<DiagExameRealizado> itens;

    private int numeroRegistos = 10;

    public static DiagResultadosExameRealizadoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagResultadosExameRealizadoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagResultadosExameRealizadoBean");
    }

    public static DiagExameRealizado getInstancia()
    {
        AdmsServicoSolicitado admsServicoSolicitado = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
        admsServicoSolicitado.setFkIdClassificacaoServicoSolicitado(new AdmsClassificacaoServicoSolicitado());
        admsServicoSolicitado.getFkIdClassificacaoServicoSolicitado().setPkIdClassificacaoServicoSolicitado(Defs.CLASSIFICACAO_EXAME_DEFAULT_ID);
        admsServicoSolicitado.setFkIdEstadoPagamento(new AdmsEstadoPagamento());
        admsServicoSolicitado.setFkIdRecepcionista(RhFuncionarioNovoBean.getInstancia());
        admsServicoSolicitado.setFkIdServico(new AdmsServico());

        AdmsPaciente admsPaciente = new AdmsPaciente();
        admsPaciente.setFkIdPessoa(new GrlPessoa());

        AdmsSolicitacao admsSolicitacao = new AdmsSolicitacao();
        admsSolicitacao.setFkIdPaciente(admsPaciente);

        admsServicoSolicitado.setFkIdSolicitacao(admsSolicitacao);

        DiagExameRealizado diagExameRealizado = new DiagExameRealizado();
        diagExameRealizado.setFkIdServicoSolicitado(admsServicoSolicitado);

        DiagExame exameAux = DiagExameBean.getInstancia();
        exameAux.setPkIdExame(Defs.EXAME_LABORATORIO_DEFAULT_ID);
        diagExameRealizado.setFkIdExame(exameAux);
        diagExameRealizado.setFkIdAmostra(DiagAmostraBean.getInstancia());
        diagExameRealizado.getFkIdAmostra().getFkIdTipoAmostra().setPkIdTipoAmostra(Defs.TIPO_AMOSTRA_DEFAULT_ID);
        diagExameRealizado.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return diagExameRealizado;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }

    public List<DiagExameRealizado> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagExameRealizado> itens)
    {
        this.itens = itens;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public Date getDataInicioPesquisa()
    {
        return dataInicioPesquisa;
    }

    public void setDataInicioPesquisa(Date dataInicioPesquisa)
    {
        this.dataInicioPesquisa = dataInicioPesquisa;
    }

    public Date getDataFimPesquisa()
    {
        return dataFimPesquisa;
    }

    public void setDataFimPesquisa(Date dataFimPesquisa)
    {
        this.dataFimPesquisa = dataFimPesquisa;
    }

    public DiagExameRealizado getDiagExameRealizadoPesquisar()
    {
        if (diagExameRealizadoPesquisar == null)
        {
            diagExameRealizadoPesquisar = getInstancia();
        }
        return diagExameRealizadoPesquisar;
    }

    public void setDiagExameRealizadoPesquisar(DiagExameRealizado exameRealizado)
    {
        this.diagExameRealizadoPesquisar = exameRealizado;
    }

    public List<DiagExame> getListaExames()
    {
        return listaExames;
    }

    public void setListaExames(List<DiagExame> listaExames)
    {
        this.listaExames = listaExames;
    }

    public DiagAmostra getDiagAmostra()
    {
        if (diagAmostra == null)
        {
            diagAmostra = DiagAmostraBean.getInstancia();
        }
        return diagAmostra;
    }

    public void setDiagAmostra(DiagAmostra diagAmostra)
    {
        this.diagAmostra = diagAmostra;
    }

    public DiagExameRealizado getDiagExameRealizado()
    {
        if (diagExameRealizado == null)
        {
            diagExameRealizado = DiagExameRealizadoBean.getInstancia();
        }
        return diagExameRealizado;
    }

    public void setDiagExameRealizado(DiagExameRealizado diagExameRealizado)
    {
        this.diagExameRealizado = diagExameRealizado;
    }

    public DiagExameRealizado getDiagExameRealizadoVisualizar()
    {
        return diagExameRealizadoVisualizar;
    }

    public void setDiagExameRealizadoVisualizar(DiagExameRealizado diagExameRealizadoVisualizar)
    {
        this.diagExameRealizadoVisualizar = diagExameRealizadoVisualizar;
    }

    public void selecionarExameRealizadoVisualizar(DiagExameRealizado exameRealizadoVisualizar)
    {
        this.diagExameRealizadoVisualizar = exameRealizadoVisualizar;
    }

    public AdmsServicoSolicitado getAdmsServicoSolicitado()
    {
        if (admsServicoSolicitado == null)
        {
            admsServicoSolicitado = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
        }
        return admsServicoSolicitado;
    }

    public void setAdmsServicoSolicitado(AdmsServicoSolicitado admsServicoSolicitado)
    {
        this.admsServicoSolicitado = admsServicoSolicitado;
    }

    public AdmsServicoSolicitado getAdmsServicoSolicitadoVisualizar()
    {
        if (admsServicoSolicitadoVisualizar == null)
        {
            admsServicoSolicitadoVisualizar = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
        }
        return admsServicoSolicitadoVisualizar;
    }

    public void setAdmsServicoSolicitadoVisualizar(AdmsServicoSolicitado admsServicoSolicitadoVisualizar)
    {
        this.admsServicoSolicitadoVisualizar = admsServicoSolicitadoVisualizar;
    }

    public DiagColecta getDiagColecta()
    {
        if (diagColecta == null)
        {
            diagColecta = DiagColectaBean.getInstancia();
        }
        return diagColecta;
    }

    public void setDiagColecta(DiagColecta diagColecta)
    {
        this.diagColecta = diagColecta;
    }

    public DiagTipoAmostra getDiagTipoAmostra()
    {
        if (diagTipoAmostra == null)
        {
            diagTipoAmostra = DiagTipoAmostraBean.getInstancia();
        }
        return diagTipoAmostra;
    }

    public void setDiagTipoAmostra(DiagTipoAmostra diagTipoAmostra)
    {
        this.diagTipoAmostra = diagTipoAmostra;
    }

    public RhFuncionario getRhFuncionarioExame()
    {
        if (rhFuncionarioExame == null)
        {
            rhFuncionarioExame = RhFuncionarioNovoBean.getInstancia();
        }
        return rhFuncionarioExame;
    }

    public void setRhFuncionarioExame(RhFuncionario rhFuncionarioExame)
    {
        this.rhFuncionarioExame = rhFuncionarioExame;
    }

    public RhFuncionario getRhFuncionarioColecta()
    {
        if (rhFuncionarioColecta == null)
        {
            rhFuncionarioColecta = RhFuncionarioNovoBean.getInstancia();
        }
        return rhFuncionarioColecta;
    }

    public void setRhFuncionarioColecta(RhFuncionario rhFuncionarioColecta)
    {
        this.rhFuncionarioColecta = rhFuncionarioColecta;
    }

    public DiagExame getDiagExame()
    {
        if (diagExame == null)
        {
            diagExame = DiagExameBean.getInstancia();
        }
        return diagExame;
    }

    public void setDiagExame(DiagExame diagExame)
    {
        this.diagExame = diagExame;
    }

    public List<DiagTipoAmostra> findAllTiposAmostra()
    {
        return diagTipoAmostraFacade.findAll();
    }

    public List<RhFuncionario> findAllFuncionarios()
    {
        return rhFuncionarioFacade.findAll();
    }

    public List<GrlCentroHospitalar> findAllCentrosHospitalares()
    {
        return grlCentroHospitalarFacade.findAll();
    }

    public List<DiagCategoriaExame> findAllCategoriaExames()
    {
        return diagCategoriaExameFacade.findAll();
    }

    public List<DiagExame> findAllExamesRadiografia()
    {
        return diagExameFacade.findExamesRadiografia();
    }

    public List<DiagExame> findAllExamesEcografia()
    {
        return diagExameFacade.findExamesEcografia();
    }

    public List<AdmsClassificacaoServicoSolicitado> findAllClassificacaoServicoSolicitado()
    {
        return diagExameFacade.findAllClassificacaoServicoSolicitado();
    }

    public void carregarListaExamesPorCategoria()
    {
        listaExames = diagExameFacade.findExamesByCategoria(diagExameRealizadoPesquisar.getFkIdExame().getFkIdCategoriaExame());
    }

    public String selecionarExameRealizar(AdmsServicoSolicitado servicoSolicitadoAux)
    {
        this.admsServicoSolicitado = servicoSolicitadoAux;

        return "realizarExame.xhtml?faces-redirect=true";
    }

    public Date getMomentoActual()
    {
        return new Date();
    }

    public String create()
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagColecta = new DiagColecta();
            diagColecta.setDataColecta(new Date(data.getTimeInMillis()));
            diagColecta.setFkIdFuncionario(rhFuncionarioColecta);

            diagColectaFacade.create(diagColecta);

            diagAmostra = new DiagAmostra();
            diagAmostra.setFkIdColecta(diagColecta);
            diagAmostra.setFkIdTipoAmostra(diagTipoAmostra);
            diagAmostraFacade.create(diagAmostra);

            diagExame = new DiagExame();
            diagExame = diagExameFacade.findExamePorNomeServicoSolicitado(admsServicoSolicitado);

            diagExameRealizado.setFkIdExame(diagExame);
            diagExameRealizado.setData(new Date(data.getTimeInMillis()));
            diagExameRealizado.setFkIdAmostra(diagAmostra);
            diagExameRealizado.setFkIdFuncionario(rhFuncionarioExame);
            diagExameRealizado.setFkIdServicoSolicitado(admsServicoSolicitado);

            diagExameRealizadoFacade.create(diagExameRealizado);

            userTransaction.commit();

            admsServicoEfetuado = new AdmsServicoEfetuado();
            admsServicoEfetuado.setCodigoTabelaBusca(BigInteger.valueOf(diagExameRealizado.getPkIdExameRealizado().intValue()));
            admsServicoEfetuado.setDescricaoTabelaBusca("Exame");
            admsServicoEfetuado.setDataEfetuada(new Date());
            admsServicoEfetuado.setFkIdServicoSolicitado(admsServicoSolicitado);

            admsServicoEfetuadoFacade.create(admsServicoEfetuado);

            Mensagem.sucessoMsg("Exame realizado com sucesso!");

            diagColecta = DiagColectaBean.getInstancia();
            diagAmostra = DiagAmostraBean.getInstancia();
            rhFuncionarioColecta = RhFuncionarioNovoBean.getInstancia();
            rhFuncionarioExame = RhFuncionarioNovoBean.getInstancia();
            diagExame = DiagExameBean.getInstancia();
            diagExameRealizado = DiagExameRealizadoBean.getInstancia();
            admsServicoSolicitado = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
            admsServicoEfetuado = AdmsServicoEfetuadoBean.getInstancia();

        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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

        return limpar();
    }

    public void pesquisarResultadosExamesRealizados(String tipoExame)
    {
        itens = new ArrayList<>();
        itens = diagExameRealizadoFacade.findPesquisaResultadosExames(diagExameRealizadoPesquisar, dataInicioPesquisa, dataFimPesquisa, tipoExame, numeroRegistos);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarResultadosExamesRealizadosEcografia(String tipoExame)
    {
        itens = new ArrayList<>();
        itens = diagExameRealizadoFacade.findPesquisaResultadosExamesEcografia(diagExameRealizadoPesquisar, dataInicioPesquisa, dataFimPesquisa, tipoExame);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarResultadosExamesRealizadosRadiografia(String tipoExame)
    {
        itens = new ArrayList<>();
        itens = diagExameRealizadoFacade.findPesquisaResultadosExamesRadiografia(diagExameRealizadoPesquisar, dataInicioPesquisa, dataFimPesquisa, tipoExame);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagExameRealizadoPesquisar = null;
        itens = new ArrayList<>();

        dataInicioPesquisa = dataFimPesquisa = null;

        return "resultadosExames.xhtml?faces-redirect=true";
    }

    public String limpar()
    {
        diagExameRealizado = null;

        return "pedidosExames.xhtml?faces-redirect=true";
    }

    public void imprimirReport()
    {
        try
        {
            //        //Parametros
//        HashMap<String, Object> parametros = new HashMap();
//        parametros.put("exame", diagExameRealizadoVisualizar.getFkIdExame().getDescricaoExame());
//        parametros.put("paciente", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome());
//        parametros.put("numProcesso", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente());
//        parametros.put("subProcesso", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso());
//        parametros.put("resultado", diagExameRealizadoVisualizar.getResultados());
//
//        RelatorioJasper.exportPDFSemLista("diag/resultadoExame.jasper", parametros);

//        // set input and output stream
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        FileInputStream fis;
//        BufferedInputStream bufferedInputStream;
//
//        try
//        {
//            // get report
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            String path = externalContext.getRealPath("WEB-INF/relatorios/diag/resultadoExame.jasper");
//            fis = new FileInputStream(path);
//            bufferedInputStream = new BufferedInputStream(fis);
//
//            // fill parameters
//            Map<String, Object> map = new HashMap<>();            
//            map.put("exame", diagExameRealizadoVisualizar.getFkIdExame().getDescricaoExame());
//            map.put("paciente", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome());
//            map.put("numProcesso", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente());
//            map.put("subProcesso", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso());
//            map.put("resultado", diagExameRealizadoVisualizar.getResultados());
//
//            // export to pdf           
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
//            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
//
//            // close it
//            fis.close();
//            bufferedInputStream.close();
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }        
//
            
            Map<String, Object> map = new HashMap<>();
            map.put("exame", diagExameRealizadoVisualizar.getFkIdExame().getDescricaoExame());
            map.put("paciente", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome());
            map.put("numProcesso", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente());
            map.put("subProcesso", diagExameRealizadoVisualizar.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().toString());
            map.put("resultado", diagExameRealizadoVisualizar.getResultados());
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String path = externalContext.getRealPath("WEB-INF/relatorios/diag/resultadoExame.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, map, new JREmptyDataSource());
            
            HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, httpServletResponse.getOutputStream());
            
            FacesContext.getCurrentInstance().responseComplete();
            
            System.out.println("gerou report meezy");
        }
        catch (JRException ex)
        {
            Logger.getLogger(DiagResultadosExameRealizadoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DiagResultadosExameRealizadoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
