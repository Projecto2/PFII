/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AdmsPaciente;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.GrlPessoa;
import entidade.InterCamaInternamento;
import entidade.InterEstadoCama;
import entidade.InterInscricaoInternamento;
import entidade.InterNotificacao;
import entidade.InterRegistoInternamento;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.InterCamaInternamentoFacade;
import sessao.InterDoencaInternamentoPacienteFacade;
import sessao.InterEnfermariaFacade;
import sessao.InterEstadoCamaFacade;
import sessao.InterInscricaoInternamentoFacade;
import sessao.InterRegistoInternamentoFacade;
import util.Constantes;
import util.Data;
import util.Mensagem;
import util.RelatorioJasper;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRegistoInternamentoBean implements Serializable
{

    @EJB
    private AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;

    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;

    @EJB
    private InterEstadoCamaFacade interEstadoCamaFacade;

    @EJB
    private InterDoencaInternamentoPacienteFacade interDoencaInternamentoPacienteFacade;

    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;

    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;

    @EJB
    private InterCamaInternamentoFacade interCamaInternamentoFacade;

    @EJB
    private InterInscricaoInternamentoFacade interInscricaoInternamentoFacade;

    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterRegistoInternamento registo, registoPesquisa;

    private int cama, pk_id_registo, paciente, fk_id_sala;

    private List<InterRegistoInternamento> listaRegistos;

    private final Calendar dataCorrente = Calendar.getInstance();

    private InterCamaInternamento camaInstancia;

    private String nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPacientePesq;
    private String numeroInscricaoPesq, numeroPacientePesq, doencaPesq, doencaPrincipal;

    private AdmsServicoEfetuado servicoEfectuado;

    private SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();

    private boolean gravou = false;

    private Date dataRegistoPesq1, dataRegistoPesq2;

    /**
     * Creates a new instance of RegistoInternamentoBean
     */
    public InterRegistoInternamentoBean()
    {

    }

    public static InterRegistoInternamentoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRegistoInternamentoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRegistoInternamentoBean");
    }

    public List<InterRegistoInternamento> getListaRegistos()
    {
        if (listaRegistos == null)
            listaRegistos = new ArrayList();
        return listaRegistos;
    }

    public void setListaRegistos(List<InterRegistoInternamento> listaRegistos)
    {
        this.listaRegistos = listaRegistos;
    }

    public InterRegistoInternamento getInstancia()
    {
        InterRegistoInternamento r = new InterRegistoInternamento();
        r.setFkIdCamaInternamento(new InterCamaInternamento());
        r.setFkIdFuncionarioEnfermeiro(new RhFuncionario());
        r.setFkIdServicoSolicitado(new AdmsServicoSolicitado());
        r.setFkIdInscricaoInternamento(new InterInscricaoInternamento());

        return r;
    }

    public InterRegistoInternamento getRegisto()
    {
        if (registo == null)
        {
            registo = getInstancia();
        }

        if (InterCamaReservadaNovoBean.getInstanciaBean().findCamaReservadaByIdPaciente(InterSolicitacoesInterBean.getInstanciaBean().getServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()) != null)
        {
            registo.getFkIdCamaInternamento().setDescricaoCamaInternamento(InterCamaReservadaNovoBean.getInstanciaBean().findCamaReservadaByIdPaciente(InterSolicitacoesInterBean.getInstanciaBean().getServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()).getFkIdCamaInternamento().getDescricaoCamaInternamento());
        }

        return registo;
    }

    public void setRegisto(InterRegistoInternamento registo)
    {
        this.registo = registo;
    }

    public InterRegistoInternamentoFacade getInterRegistoInternamentoFacade()
    {
        return interRegistoInternamentoFacade;
    }

    public Date getDataRegistoPesq1()
    {
        return dataRegistoPesq1;
    }

    public void setDataRegistoPesq1(Date dataRegistoPesq1)
    {
        this.dataRegistoPesq1 = dataRegistoPesq1;
    }

    public Date getDataRegistoPesq2()
    {
        return dataRegistoPesq2;
    }

    public void setDataRegistoPesq2(Date dataRegistoPesq2)
    {
        this.dataRegistoPesq2 = dataRegistoPesq2;
    }

    public InterRegistoInternamento getRegistoPesquisa()
    {
        if (registoPesquisa == null)
        {
            registoPesquisa = getInstancia();
            registoPesquisa.getFkIdServicoSolicitado().setFkIdSolicitacao(new AdmsSolicitacao());
            registoPesquisa.getFkIdServicoSolicitado().getFkIdSolicitacao().setFkIdPaciente(new AdmsPaciente());
            registoPesquisa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().setFkIdPessoa(new GrlPessoa());
        }

        return registoPesquisa;
    }

    public void setRegistoPesquisa(InterRegistoInternamento registoPesquisa)
    {
        this.registoPesquisa = registoPesquisa;
    }

    public int getCama()
    {
        return cama;
    }

    public void setCama(int cama)
    {
        this.cama = cama;
    }

    public int getPk_id_registo()
    {
        return pk_id_registo;
    }

    public void setPk_id_registo(int pk_id_registo)
    {
        this.pk_id_registo = pk_id_registo;
    }

    public int getPaciente()
    {
        return paciente;
    }

    public String getNumeroInscricaoPesq()
    {
        return numeroInscricaoPesq;
    }

    public void setNumeroInscricaoPesq(String numeroInscricaoPesq)
    {
        this.numeroInscricaoPesq = numeroInscricaoPesq;
    }

    public void setPaciente(int paciente)
    {
        this.paciente = paciente;
    }

    public int getFk_id_sala()
    {
        return fk_id_sala;
    }

    public void setFk_id_sala(int fk_id_sala)
    {
        this.fk_id_sala = fk_id_sala;
    }

    public String getNomePacientePesq()
    {
        return nomePacientePesq;
    }

    public void setNomePacientePesq(String nomePacientePesq)
    {
        this.nomePacientePesq = nomePacientePesq;
    }

    public String getNomeMeioPacientePesq()
    {
        return nomeMeioPacientePesq;
    }

    public void setNomeMeioPacientePesq(String nomeMeioPacientePesq)
    {
        this.nomeMeioPacientePesq = nomeMeioPacientePesq;
    }

    public String getSobreNomePacientePesq()
    {
        return sobreNomePacientePesq;
    }

    public void setSobreNomePacientePesq(String sobreNomePacientePesq)
    {
        this.sobreNomePacientePesq = sobreNomePacientePesq;
    }

    public String getSexoPacientePesq()
    {
        return sexoPacientePesq;
    }

    public void setSexoPacientePesq(String sexoPacientePesq)
    {
        this.sexoPacientePesq = sexoPacientePesq;
    }

    public String getNumeroPacientePesq()
    {
        return numeroPacientePesq;
    }

    public void setNumeroPacientePesq(String numeroPacientePesq)
    {
        this.numeroPacientePesq = numeroPacientePesq;
    }

    public String getDoencaPesq()
    {
        return doencaPesq;
    }

    public void setDoencaPesq(String doencaPesq)
    {
        this.doencaPesq = doencaPesq;
    }

    public String getDoencaPrincipal()
    {
        return doencaPrincipal;
    }

    public void setDoencaPrincipal(String doencaPrincipal)
    {
        this.doencaPrincipal = doencaPrincipal;
    }

    public String getCarregarSolicitacao(Long pk_id)
    {
        return interRegistoInternamentoFacade.carregarSolicitacao(pk_id).getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + interRegistoInternamentoFacade.carregarSolicitacao(pk_id).getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() + " " + interRegistoInternamentoFacade.carregarSolicitacao(pk_id).getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome();
    }

    public String numeroInscricaoInternamento(String numeroProcesso, Long pk_id_paciente)
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        List<InterRegistoInternamento> lista = interRegistoInternamentoFacade.pesquisarRegistoArquivados(pk_id_paciente, tipoServico, numeroProcesso, null, null, null, null, null, 0, 0, null);

        if (lista.isEmpty())
        {
            return interInscricaoInternamentoFacade.getUltimaInscricao(tipoServico) + " / " + interEnfermariaFacade.getEnfermariaFuncionario(tipoServico).getFkIdServico().getCodServico();
        }
        return lista.get(0).getFkIdInscricaoInternamento().getNumeroInscricao();
    }

    public String numeroVezesInternado(String numeroProcesso, Long pk_id_paciente)
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        List<InterRegistoInternamento> lista = interRegistoInternamentoFacade.pesquisarRegistoArquivados(pk_id_paciente, tipoServico, numeroProcesso, null, null, null, null, null, 0, 0, null);

        if (lista.isEmpty())
        {
            return "1";
        }
        return "" + (lista.size() + 1);
    }

    public boolean isGravou()
    {
        return gravou;
    }

    public void setGravou(boolean gravou)
    {
        this.gravou = gravou;
    }

    public void salvar(Long servicoSocilicitado, String numeroProcesso, Long pk_id_paciente)
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

        if (registo.getFkIdCamaInternamento().getDescricaoCamaInternamento() == null)
        {
            Mensagem.erroMsg("A cama é obrigatória. Clica em Escolher Cama");
        }
        else
        {
            try
            {
                userTransaction.begin();

                List<InterRegistoInternamento> lista = interRegistoInternamentoFacade.pesquisarRegisto(pk_id_paciente, tipoServico, numeroProcesso, null, null, null, null, null, 0, 0, 0, null, null, null);
                
                if (lista.isEmpty())
                {
                    InterInscricaoInternamento inscricao = new InterInscricaoInternamento();
                    inscricao.setDataInscricao(dataCorrente.getTime());
                    inscricao.setFkIdFuncionario(new RhFuncionario(segConta.getFkIdFuncionario().getPkIdFuncionario()));
                    inscricao.setNumeroInscricao(numeroInscricaoInternamento(numeroProcesso, pk_id_paciente));

                    interInscricaoInternamentoFacade.create(inscricao);

                    List<InterInscricaoInternamento> listaInscricao = interInscricaoInternamentoFacade.findAll();

                    registo.setFkIdInscricaoInternamento(listaInscricao.get(listaInscricao.size() - 1));
                }
                else
                {
                    registo.getFkIdInscricaoInternamento().setPkIdInscricaoInternamento(lista.get(0).getFkIdInscricaoInternamento().getPkIdInscricaoInternamento());
                }

                registo.setFkIdCamaInternamento(interCamaInternamentoFacade.getCama(tipoServico, registo.getFkIdCamaInternamento().getDescricaoCamaInternamento()));
                registo.getFkIdFuncionarioEnfermeiro().setPkIdFuncionario(segConta.getFkIdFuncionario().getPkIdFuncionario());
                registo.getFkIdServicoSolicitado().setPkIdServicoSolicitado(servicoSocilicitado);
                registo.setDataRegisto(dataCorrente.getTime());
                registo.setDataInternamento(dataCorrente.getTime());
                registo.setAtivo(true);

                interRegistoInternamentoFacade.create(registo);

                camaInstancia = interCamaInternamentoFacade.find(registo.getFkIdCamaInternamento().getPkIdCamaInternamento());

                camaInstancia.setFkIdEstadoCama(new InterEstadoCama(interEstadoCamaFacade.findByDescricao("Ocupada").get(0).getPkIdEstadoCama()));

                interCamaInternamentoFacade.edit(camaInstancia);

                servicoEfectuado = new AdmsServicoEfetuado();
                servicoEfectuado.setFkIdServicoSolicitado(new AdmsServicoSolicitado(servicoSocilicitado));
                servicoEfectuado.setDescricaoTabelaBusca("inter_registo_internamento");

                List<InterRegistoInternamento> listaInternados = interRegistoInternamentoFacade.findAll();

                servicoEfectuado.setCodigoTabelaBusca(BigInteger.valueOf(listaInternados.get(listaInternados.size() - 1).getPkIdRegistoInternamento()));

                servicoEfectuado.setDataEfetuada(dataCorrente.getTime());

                admsServicoEfetuadoFacade.create(servicoEfectuado);

                AdmsServicoSolicitado servico = admsServicoSolicitadoFacade.find(servicoSocilicitado);
                admsAgendamentoFacade.mudarEstadoParaEfetuado(servico);
                
                if (doencaPrincipal != null)
                {
                    InterDoencaInternamentoPacienteBean.getInstanciaBean().salvarDoencaPrincipal(listaInternados.get(listaInternados.size() - 1).getPkIdRegistoInternamento(), doencaPrincipal);
                }
                
                if (InterCidHipoteseDiagnosticoBean.getInstanciaBean().getListaPkIdSubcategoriasDasDoencasSeleccionadas() != null)
                {
                    InterDoencaInternamentoPacienteBean.getInstanciaBean().salvarDoencaCoexistente(listaInternados.get(listaInternados.size() - 1).getPkIdRegistoInternamento());
                }
                
                if (!InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().findBy(tipoServico, 1, null, pk_id_paciente, 0, 0).isEmpty())
                {
                    InterNotificacao notificacao = InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().findBy(tipoServico, 1, null, pk_id_paciente, 0, 0).get(0);

                    if (notificacao != null)
                        InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().remove(notificacao);                
                }
                
                userTransaction.commit();
                Mensagem.sucessoMsg("Internamento registado com sucesso!");

                gravou = true;
            }
            catch (Exception e)
            {
                try
                {
                    userTransaction.rollback();
                    System.out.println(e.getMessage());
                }
                catch (IllegalStateException | SecurityException | SystemException ex)
                {
                    Mensagem.erroMsg("Está solicitação já foi atendida");
                    System.out.println("Rollback: " + ex.toString());
                }
            }
        }
    }

    public void limparCampos()
    {
        registo = null;
        registoPesquisa = null;
        doencaPrincipal = null;
        gravou = false;
        atualizarDados();
    }

    public void atualizarDados()
    {
//        InterSolicitacoesInterBean.getInstanciaBean().pesquisar();
        InterSolicitacoesInterBean.getInstanciaBean().limparPesquisa();
        //InterCamaListarBean.getInstanciaBean().findAllCamasLivres();
    }

    public void pesquisar()
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        listaRegistos = interRegistoInternamentoFacade.pesquisarRegisto(null, tipoServico, numeroPacientePesq, numeroInscricaoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPacientePesq, InterCamaListarBean.getInstanciaBean().getEnfermaria(), InterCamaListarBean.getInstanciaBean().getFk_id_sala(), cama, null, null, null);

        if (listaRegistos.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaRegistos.size() + " registo(s) retornado(s).");
        }
    }
    
    public List<InterRegistoInternamento> findAll()
    {
        List<InterRegistoInternamento> lista;
        
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        lista = interRegistoInternamentoFacade.pesquisarRegisto(null, tipoServico, numeroPacientePesq, numeroInscricaoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPacientePesq, InterCamaListarBean.getInstanciaBean().getEnfermaria(), InterCamaListarBean.getInstanciaBean().getFk_id_sala(), cama, null, dataRegistoPesq1, dataRegistoPesq2);
//
//        if (lista.isEmpty())
//        {
//            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
//        }
//        else
//        {
//            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + lista.size() + " registo(s) retornado(s).");
//        }
        
        return lista;
    }

    public AdmsServicoEfetuado getServicoEfetuado(Long pk_id_registo)
    {
//        System.err.println("codigo id registo " + pk_id_registo);
        AdmsServicoEfetuado servicoEfetuado = admsServicoEfetuadoFacade.findServicoEfetuadoInternamento(pk_id_registo);
//        System.err.println("servico retornado" + servicoEfetuado);
        return servicoEfetuado;
    }

    public String solicitarTipagemSeanguinia(InterRegistoInternamento registoVisualizar)
    {
        InterRequisicaoComponenteSanguineoBean.getInstanciaBean().setRegistoInternamento(registoVisualizar);

        return "/faces/interVisao/interLaboratorio/solicitacoesComponentesSanguineosAprovadas.xhtml?faces-redirect=true";
    }

    public String solicitacaoServico(InterRegistoInternamento registoInter)
    {
        InterControloDiarioBean.getInstanciaBean().setRegistoInternamento(registoInter);

        return "/faces/interVisao/interAdmissoes/servicosSolicitadosListarInter.xhtml?faces-redirect=true";
    }

    public String navegarRegistoInternamentoListar()
    {
        return "/faces/interVisao/interInternamento/internamentoListar/registoInternamentoListarInter.xhtml?faces-redirect=true";
    }

    public String findDoencaPrincipalPacienteByRegistoInternamento(Long pkIdRegistoInternamento)
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        if (!interDoencaInternamentoPacienteFacade.pesquisarRegisto(tipoServico, null, null, null, null, pkIdRegistoInternamento, null, Constantes.DOENCAPRINCIPAL, null).isEmpty())
        {
            return interDoencaInternamentoPacienteFacade.pesquisarRegisto(tipoServico, null, null, null, null, pkIdRegistoInternamento, null, Constantes.DOENCAPRINCIPAL, null).get(0).getFkIdCidSubcategorias().getNome();
        }
        return "";
    }

    public InterInscricaoInternamentoFacade getInterInscricaoInternamentoFacade()
    {
        return interInscricaoInternamentoFacade;
    }

    public void setInterInscricaoInternamentoFacade(InterInscricaoInternamentoFacade interInscricaoInternamentoFacade)
    {
        this.interInscricaoInternamentoFacade = interInscricaoInternamentoFacade;
    }

    public void exportPDF(ActionEvent evt)
    {
        if (registoPesquisa == null)
        {
            Mensagem.erroMsg("Deve seleccionar o registo a imprimir");
        }
        else
        {
            HashMap<String, Object> parametrosMap = new HashMap<>();

            List<InterRegistoInternamento> listaRegistosImprimir = new ArrayList();
            listaRegistosImprimir.add(registoPesquisa);

            Data data = new Data();
            String arrayDataNascimento[] = data.dateToStr(registoPesquisa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getDataNascimento()).split("/");
            int anoNascimento = Integer.parseInt(arrayDataNascimento[arrayDataNascimento.length - 1]);
            parametrosMap.put("idade", (dataCorrente.get(Calendar.YEAR) - anoNascimento));
            parametrosMap.put("dataCorrente", dataCorrente.getTime());
            parametrosMap.put("doencaPrincipal", findDoencaPrincipalPacienteByRegistoInternamento(registoPesquisa.getPkIdRegistoInternamento()));
            RelatorioJasper.exportPDF("inter/justificativoAusenciaServicoInter.jasper", parametrosMap, listaRegistosImprimir);
        }

    }

    public boolean disabledEnfermaria(String servico)
    {
        if (servico.equals("Medicina") || servico.equals("Tuberculose Adultos"))
            return false;
        return true;                
    }
    
    public void listaPacientesInternadosExportPDF(ActionEvent evt)
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        if (findAll().isEmpty())
        {
            Mensagem.erroMsg("A lista está vazia");
        }
        else
        {
            HashMap<String, Object> parametrosMap = new HashMap<>();

            parametrosMap.put("servico", tipoServico);
            RelatorioJasper.exportPDF("inter/listaPacientesInternados.jasper", parametrosMap, findAll());
        }
    }
}
