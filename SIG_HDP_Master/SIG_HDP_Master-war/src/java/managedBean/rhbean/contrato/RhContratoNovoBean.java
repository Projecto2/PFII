/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.contrato;

import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.RhCandidato;
import entidade.RhCategoriaProfissional;
import entidade.RhContrato;
import entidade.RhEstadoContrato;
import entidade.RhFormaPagamento;
import entidade.RhSubsidio;
import entidade.RhFuncionarioHasRhSubsidio;
import entidade.RhSalarioFuncionario;
import entidade.RhTipoContrato;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.candidato.RhCandidatoNovoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.rhbean.validacao.RhContratoValidarBean;
import managedBean.rhbean.validacao.RhFuncionarioValidarBean;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhCandidatoFacade;
import sessao.RhCategoriaProfissionalFacade;
import sessao.RhContratoFacade;
import sessao.RhEstadoCandidatoFacade;
import sessao.RhEstadoContratoFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhSubsidioFacade;
import sessao.RhFuncionarioHasRhSubsidioFacade;
import sessao.RhSalarioFuncionarioFacade;
import sessao.RhTipoContratoFacade;
import sessao.RhTipoFuncionarioFacade;
import util.Constantes;
import util.rh.Defs;
import util.ItensAjaxBean;
import util.Mensagem;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhContratoNovoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;
    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private RhSubsidioFacade subsidioFacade;
    @EJB
    private RhFuncionarioHasRhSubsidioFacade funcionarioHasRhSubsidioFacade;
    @EJB
    private RhTipoContratoFacade tipoContratoFacade;
    @EJB
    private RhEstadoContratoFacade estadoContratoFacade;
    @EJB
    private RhTipoFuncionarioFacade tipoFuncionarioFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhEstadoCandidatoFacade estadoCandidatoFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;
    @EJB
    private RhCandidatoFacade candidatoFacade;
    @EJB
    private RhSalarioFuncionarioFacade salarioFuncionarioFacade;
    @EJB
    private RhContratoFacade contratoFacade;

    /**
     * Entidades
     */
    private RhContrato contrato;

    private List<Object> listaSubsidios;

    private boolean disabledDuracaoMesesDataFim;

    private RhCandidato candidatoPesquisa;
    private List<RhCandidato> candidatosPesquisados;

    private Part anexoCarregado;
    private boolean anexosFlag;

    /**
     * Creates a new instance of rhContratoNovoBean
     */
    public RhContratoNovoBean ()
    {
    }

    public String limpar ()
    {
        limparPesquisaCandidatos();
        listaSubsidios = null;
        contrato = null;
        anexosFlag = false;
        return "contratoNovoRh.xhtml?faces-redirect=true";
    }

    public static RhContrato getInstanciaContrato ()
    {
        RhContrato contr = new RhContrato();

        contr.setFkIdCandidato(RhCandidatoNovoBean.getInstancia());
        contr.setFkIdFormaPagamento(new RhFormaPagamento());
        contr.setFkIdEstadoContrato(new RhEstadoContrato());
        contr.setFkIdTipoContrato(new RhTipoContrato());
        contr.setFkIdAnexoContrato(new GrlFicheiroAnexado());
        contr.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());
        try
        {
            contr.setSalarioBase(ItensAjaxBean.getInstanciaBean().getCategoriaProfissionalList().get(0).getSalarioBase());
        }
        catch (Exception e)
        {
        }

        return contr;
    }

    public RhContrato getContrato ()
    {
        if (contrato == null)
        {
            contrato = getInstanciaContrato();
        }
        return contrato;
    }

    public void setContrato (RhContrato contrato)
    {
        this.contrato = contrato;
    }

    public List<Object> getListaSubsidios ()
    {
        return listaSubsidios;
    }

    public void setListaSubsidios (List<Object> listaSubsidios)
    {
        this.listaSubsidios = listaSubsidios;
    }

    public boolean isDisabledDuracaoMesesDataFim ()
    {
        return disabledDuracaoMesesDataFim;
    }

    public RhCandidato getCandidatoPesquisa ()
    {
        if (candidatoPesquisa == null)
        {
            candidatoPesquisa = RhCandidatoNovoBean.getInstancia();
        }
        return candidatoPesquisa;
    }

    public void setCandidatoPesquisa (RhCandidato candidatoPesquisa)
    {
        this.candidatoPesquisa = candidatoPesquisa;
    }

    public List<RhCandidato> getCandidatosPesquisados ()
    {
        return candidatosPesquisados;
    }

    public Part getAnexoCarregado ()
    {
        return anexoCarregado;
    }

    public void setAnexoCarregado (Part anexoCarregado)
    {
        this.anexoCarregado = anexoCarregado;
    }

    public boolean isAnexosFlag ()
    {
        return anexosFlag;
    }

    public void activarAnexos ()
    {
        anexosFlag = true;
    }
    
    public void limparPesquisaCandidatos ()
    {
        candidatosPesquisados = null;
        candidatoPesquisa = null;
    }

    public void pesquisarCandidatos ()
    {
        candidatoPesquisa.setFkIdEstadoCandidato(estadoCandidatoFacade.pesquisaPorDescricao(Defs.RH_APROVADO).get(0));

        candidatosPesquisados = candidatoFacade.findCandidato(candidatoPesquisa);

        if (candidatosPesquisados.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + candidatosPesquisados.size() + ")");
        }
    }

    public void changeCategoria (ValueChangeEvent e)
    {
        Integer idCategoria = (Integer) e.getNewValue();

        if (idCategoria != null)
        {
            RhCategoriaProfissional categ = categoriaProfissionalFacade.find(idCategoria);
            getContrato().getFkIdFuncionario().setFkIdCategoria(categ);

            getContrato().setSalarioBase(categ.getSalarioBase());
            if (categ.getDespesasDeRepresentacao() != null)
            {
                getContrato().setSalarioBase(categ.getRemuneracaoTotal());
            }
        }
        else
        {
            getContrato().setSalarioBase(0);
        }
    }
    
    public Integer getDuracaoMesesCalculada ()
    {
        int duracaoMeses = 0;
        try
        {
            String[] dataIni = new SimpleDateFormat("dd-MM-yyyy").format(contrato.getDataInicio()).split("-");
            String[] dataFim = new SimpleDateFormat("dd-MM-yyyy").format(contrato.getDataTermino()).split("-");

            int diferDias = Integer.parseInt(dataFim[0]) - Integer.parseInt(dataIni[0]);
            int diferMes = Integer.parseInt(dataFim[1]) - Integer.parseInt(dataIni[1]);
            int diferAno = Integer.parseInt(dataFim[2]) - Integer.parseInt(dataIni[2]);

            //duração em meses = total de meses do ano * diferença de anos + diferença de meses
            duracaoMeses += 12 * diferAno + diferMes;

            //Se a diferença de dias for negativa, reduz-se um mês
            if (diferDias < 0)
            {
                duracaoMeses -= 1;
            }
        }
        catch (Exception e)
        {
            System.out.println("Método: getDuracaoMesesCalculada() -> " + e);
        }

        return duracaoMeses;
    }

    public List<RhCandidato> findCandidatosAprovados ()
    {
        return candidatoFacade.findCandidatosAprovados();
    }

    /**
     * Recebe o valor da combobox tipo de contrato e habilita/desabilita a data
     * de fim de contrato de acordo o valor da combobox
     *
     * @param eve
     */
    public void changeTipoContrato (ValueChangeEvent eve)
    {
        Integer idTipoContrato = (Integer) eve.getNewValue();

        if (idTipoContrato != null)
        {
            RhTipoContrato tipCont = tipoContratoFacade.find(idTipoContrato);

            //É o equivalente à: if(descricao == "Determinado") renderDuracao = true
            disabledDuracaoMesesDataFim = tipCont.getDescricao().equalsIgnoreCase("Indeterminado");

            if (disabledDuracaoMesesDataFim)
            {
                contrato.setDataTermino(null);
                contrato.setDuracaoMeses(0);
            }
        }

    }

    public String create ()
    {
        try
        {
            transferirDadosDeCandidatoParaFuncionario();
            contrato.setDuracaoMeses(getDuracaoMesesCalculada());
            contrato.getFkIdFuncionario().setFkIdEspecialidade1(new GrlEspecialidade());
            contrato.getFkIdFuncionario().setFkIdEspecialidade2(new GrlEspecialidade());

            RhFuncionarioValidarBean funcionarioValidar = RhFuncionarioValidarBean.getInstanciaBean();
            RhContratoValidarBean contratoValidar = RhContratoValidarBean.getInstanciaBean();

            //Realiza as validações
            if (!contratoValidar.validarNovo(contrato) || !funcionarioValidar.validarNovo(contrato.getFkIdFuncionario()))
            {
                return null;
            }

            userTransaction.begin();

            contrato.setDataCadastro(Calendar.getInstance().getTime());

            GrlCentroHospitalar centro = centroHospitalarFacade.find(contrato.getFkIdFuncionario().getFkIdCentroHospitalar().getPkIdCentro());
            if (!centro.getFkIdInstituicao().getCodigoInstituicao().equalsIgnoreCase(Constantes.HDP))
            {
                contrato.getFkIdFuncionario().setFkIdSeccaoTrabalho(null);
            }

            contrato.setFkIdEstadoContrato(estadoContratoFacade.pesquisaPorDescricao(Defs.RH_ACTIVO).get(0));

            testarConsistenciaSubsidios();

            //Alterando o estado do candidato e colocando a data de admissão
            contrato.getFkIdCandidato().setFkIdEstadoCandidato(estadoCandidatoFacade.pesquisaPorDescricao(Defs.RH_CONTRATADO).get(0));
            contrato.getFkIdCandidato().setDataAdmissao(Calendar.getInstance().getTime());
            candidatoFacade.edit(contrato.getFkIdCandidato());

            //Transfere todos os dados do candidato para gravar como funcionário
            transferirDadosDeCandidatoParaFuncionario();

            if (contrato.getFkIdFuncionario().getNumeroSegurancaSocial() != null && contrato.getFkIdFuncionario().getNumeroSegurancaSocial().trim().isEmpty())
            {
                contrato.getFkIdFuncionario().setNumeroSegurancaSocial(null);
            }

            funcionarioFacade.create(contrato.getFkIdFuncionario());

            //Gravando o salário do funcionário
            RhSalarioFuncionario salario = new RhSalarioFuncionario();
            salario.setDataCadastro(Calendar.getInstance().getTime());
            salario.setSalario(contrato.getSalarioBase());
            salario.setFkIdFuncionario(contrato.getFkIdFuncionario());
            salarioFuncionarioFacade.create(salario);
            
            ficheiroAnexadoFacade.create(contrato.getFkIdAnexoContrato());
            contratoFacade.create(contrato);

            /**
             * Gravando os subsídios do funcionário
             */
            for (Object idSubsidio : listaSubsidios)
            {
                RhFuncionarioHasRhSubsidio funcHasSub = new RhFuncionarioHasRhSubsidio();
                funcHasSub.setFkIdFuncionario(contrato.getFkIdFuncionario());
                int idSub = Integer.parseInt("" + idSubsidio);
                funcHasSub.setFkIdSubsidio(new RhSubsidio(idSub));

                funcionarioHasRhSubsidioFacade.create(funcHasSub);
            }

            userTransaction.commit();

            Mensagem.sucessoMsg("Contrato guardado com sucesso!");
            Mensagem.sucessoMsg("O candidato é agora um funcionário!");
            limpar();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public boolean transferirDadosDeCandidatoParaFuncionario ()
    {
        try
        {
            RhCandidato cand = contrato.getFkIdCandidato();

            contrato.getFkIdFuncionario().setFkIdPessoa(cand.getFkIdPessoa());
            contrato.getFkIdFuncionario().setFkIdAnexoGuiaTransferencia(null);
            contrato.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(Defs.RH_INACTIVO).get(0));
            contrato.getFkIdFuncionario().setNumeroBi(cand.getNumeroBi());
            contrato.getFkIdFuncionario().setFkIdProfissao(cand.getFkIdProfissao());
            contrato.getFkIdFuncionario().setFkIdEspecialidade1(cand.getFkIdEspecialidade1());
            contrato.getFkIdFuncionario().setFkIdEspecialidade2(cand.getFkIdEspecialidade2());
            contrato.getFkIdFuncionario().setFkIdNivelAcademico(cand.getFkIdNivelAcademico());
            contrato.getFkIdFuncionario().setFkIdTipoFuncionario(tipoFuncionarioFacade.pesquisaPorDescricao(Defs.RH_FUNCIONARIO_CONTRATADO).get(0));

            contrato.getFkIdFuncionario().setDataAdmissao(Calendar.getInstance().getTime());
            contrato.getFkIdFuncionario().setDataCadastro(contrato.getFkIdFuncionario().getDataAdmissao());

        }
        catch (Exception e)
        {
            Mensagem.erroMsg("Ao transferir candidato para funcionário: " + e.toString());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String sair ()
    {
        limpar();

        return "contratoRenovarPesquisarRh.xhtml?faces-redirect=true";
    }

    public String prepararContrato (RhCandidato cand)
    {
        limpar();
        getContrato().setFkIdCandidato(cand);
        getContrato().setSalarioBase(cand.getFkIdCategoriaPretendida().getSalarioBase());

        return "novoContratoRh.xhtml?faces-redirect=true";
    }

    private void testarConsistenciaSubsidios () throws Exception
    {
        String resultado = "";

        if (listaSubsidios == null)
        {
            listaSubsidios = new ArrayList<>();
        }

        for (Object s : listaSubsidios)
        {
            System.out.println("Subsidio: " + s);
        }

        for (RhSubsidio sub : subsidioFacade.findAll())
        {
            if (sub.getObrigatorio() && !listaSubsidios.contains("" + sub.getPkIdSubsidio()))
            {
                resultado += sub.getDescricaoSubsidio() + ", ";
            }
        }

        if (!resultado.trim().isEmpty())
        {
            throw new Exception("Os seguintes subsídios são obrigatórios, selecione-os: " + resultado);
        }
    }

    public void selecionarCandidato (RhCandidato candidato)
    {
        contrato.setFkIdCandidato(candidato);
    }

    public String uploadAnexo ()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, Defs.DESTINO_ANEXO_CONTRATO, "CONTRATO");

            removerAnexo(contrato.getFkIdAnexoContrato(), false);
            contrato.getFkIdAnexoContrato().setFicheiro(novoNome);

            System.out.println("Anexo carregado com sucesso !");
            return "contratoNovoRh.xhtml?faces-redirect=true";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Mensagem.erroMsg(e.getMessage());
        }
        return null;
    }

    /**
     * Remove um determinado anexo(ficheiro) que foi carregado no servidor
     *
     * @param anexo Entidade que contém o nome do ficheiro
     * @param apresentarMensagem flag booleana que indica se o resultado da
     * operação será apresentado
     */
    public void removerAnexo (GrlFicheiroAnexado anexo, boolean apresentarMensagem)
    {
        boolean b = UploadFicheiro.getInstance().apagarFicheiro(new java.io.File(Defs.DESTINO_ANEXO_CONTRATO + anexo.getFicheiro()));

        anexo.setFicheiro(null);

        if (apresentarMensagem)
        {
            if (b)
            {
                Mensagem.sucessoMsg("Anexo removido");
            }
            else
            {
                Mensagem.erroMsg("Não foi possível remover o anexo");
            }
        }
    }

}
