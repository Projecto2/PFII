/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.contrato;

import entidade.GrlCentroHospitalar;
import entidade.GrlFicheiroAnexado;
import entidade.RhCategoriaProfissional;
import entidade.RhContrato;
import entidade.RhSubsidio;
import entidade.RhFuncionarioHasRhSubsidio;
import entidade.RhSalarioFuncionario;
import entidade.RhSeccaoTrabalho;
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
import managedBean.rhbean.validacao.RhContratoValidarBean;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhCategoriaProfissionalFacade;
import sessao.RhContratoFacade;
import sessao.RhEstadoContratoFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhSubsidioFacade;
import sessao.RhFuncionarioHasRhSubsidioFacade;
import sessao.RhSalarioFuncionarioFacade;
import sessao.RhTipoContratoFacade;
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
public class RhContratoRenovarBean implements Serializable
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
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;
    @EJB
    private RhSalarioFuncionarioFacade salarioFuncionarioFacade;

    @EJB
    private RhContratoFacade contratoFacade;

    private RhContrato contrato, contratoAnterior;

    private List<Object> listaSubsidios;

    private boolean disabledDuracaoMesesDataFim;

    private Part anexoCarregado;
    private boolean anexosFlag;

    /**
     * Creates a new instance of rhContratoRenovarBean
     */
    public RhContratoRenovarBean ()
    {
    }

    public String limpar ()
    {
        listaSubsidios = null;
        contrato = contratoAnterior = null;
        anexosFlag = false;

        return "contratoRenovarRh.xhtml?faces-redirect=true";
    }

    public RhContrato getContrato ()
    {
        if (contrato == null)
        {
            contrato = RhContratoNovoBean.getInstanciaContrato();
            contrato.setFkIdCandidato(null);
        }
        return contrato;
    }

    public void setContrato (RhContrato contrato)
    {
        this.contrato = contrato;
    }

    public RhContrato getContratoAnterior ()
    {
        if (contratoAnterior == null)
        {
            contratoAnterior = RhContratoNovoBean.getInstanciaContrato();
        }
        return contratoAnterior;
    }

    public void setContratoAnterior (RhContrato contratoAnterior)
    {
        this.contratoAnterior = contratoAnterior;
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

    public String createRenovar ()
    {
        try
        {
            contrato.setDuracaoMeses(getDuracaoMesesCalculada());
            RhContratoValidarBean contratoValidar = obterContratoValidarBean();

            //Realiza as validações
            if (!contratoValidar.validarRenovar(contrato, contratoAnterior))
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

            funcionarioFacade.edit(contrato.getFkIdFuncionario());

            //Gravando o salário do funcionário
            RhSalarioFuncionario salario = new RhSalarioFuncionario();
            salario.setDataCadastro(Calendar.getInstance().getTime());
            salario.setSalario(contrato.getSalarioBase());
            salario.setFkIdFuncionario(contrato.getFkIdFuncionario());
            salarioFuncionarioFacade.create(salario);

            ficheiroAnexadoFacade.create(contrato.getFkIdAnexoContrato());
            contratoFacade.create(contrato);

            /**
             * Removendo os subsídios anteriores do funcionário
             */
            funcionarioHasRhSubsidioFacade.eliminarPorFuncionario(contrato.getFkIdFuncionario().getPkIdFuncionario());

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

            Mensagem.sucessoMsg("Contrato renovado com sucesso!");
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

    public String sair ()
    {
        limpar();

        return "contratoRenovarPesquisarRh.xhtml?faces-redirect=true";
    }

    public String prepararRenovacaoContrato (RhContrato contratoAnt)
    {
        limpar();

        setContratoAnterior(contratoAnt);
        ItensAjaxBean itensAjaxBean = obterItensAjaxBean();

        if (contratoAnt.getFkIdFuncionario().getFkIdSeccaoTrabalho() == null)
        {
            contratoAnt.getFkIdFuncionario().setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        }
        else
        {
            Integer idDep = contratoAnt.getFkIdFuncionario().getFkIdSeccaoTrabalho().getFkIdDepartamento().getPkIdDepartamento();
            itensAjaxBean.setDepartamento(idDep);
        }

        getContrato().setFkIdFuncionario(contratoAnt.getFkIdFuncionario());
        getContrato().setSalarioBase(contratoAnt.getFkIdFuncionario().getFkIdCategoria().getSalarioBase());

        itensAjaxBean.setCentroHospitalar(contratoAnt.getFkIdFuncionario().getFkIdCentroHospitalar().getPkIdCentro());

        return "contratoRenovarRh.xhtml?faces-redirect=true";
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

    public void uploadAnexo ()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, Defs.DESTINO_ANEXO_CONTRATO, "CONTRATO");

            removerAnexo(contrato.getFkIdAnexoContrato(), false);
            contrato.getFkIdAnexoContrato().setFicheiro(novoNome);

            anexoCarregado = null;

            System.out.println("Anexo carregado com sucesso !");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Mensagem.erroMsg(e.getMessage());
        }
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

    private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }

    private RhContratoValidarBean obterContratoValidarBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhContratoValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhContratoValidarBean");
    }

}
