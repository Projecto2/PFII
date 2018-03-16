/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.funcionario;

import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.RhCategoriaProfissional;
import entidade.RhEstadoFuncionario;
import entidade.RhFuncionario;
import entidade.RhFuncionarioHasRhSubsidio;
import entidade.RhProfissao;
import entidade.RhSalarioFuncionario;
import entidade.RhSeccaoTrabalho;
import entidade.RhSubsidio;
import entidade.RhTipoDeHorarioTrabalho;
import java.io.IOException;
import java.io.Serializable;
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
import sessao.RhFuncionarioFacade;
import util.ItensAjaxBean;
import util.Mensagem;
import managedBean.rhbean.RhPessoaBean;
import managedBean.rhbean.validacao.RhFuncionarioValidarBean;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhCategoriaProfissionalFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFuncaoFacade;
import sessao.RhFuncionarioHasRhSubsidioFacade;
import sessao.RhNivelAcademicoFacade;
import sessao.RhProfissaoFacade;
import sessao.RhTipoDeHorarioTrabalhoFacade;
import sessao.RhSalarioFuncionarioFacade;
import sessao.RhSubsidioFacade;
import sessao.RhTipoFuncionarioFacade;
import util.Constantes;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFuncionarioEditarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private GrlEspecialidadeFacade especialidadeFacade;
    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    private RhProfissaoFacade profissaoFacade;
    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;
    @EJB
    private RhNivelAcademicoFacade nivelAcademicoFacade;
    @EJB
    private RhTipoFuncionarioFacade tipoFuncionarioFacade;
    @EJB
    private RhTipoDeHorarioTrabalhoFacade tipoDeHorarioTrabalhoFacade;
    @EJB
    private RhFuncaoFacade funcaoFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhSubsidioFacade subsidioFacade;
    @EJB
    private RhFuncionarioHasRhSubsidioFacade funcionarioHasRhSubsidioFacade;
    @EJB
    private RhSalarioFuncionarioFacade salarioFuncionarioFacade;

    /**
     * Entidades
     */
    private RhFuncionario funcionario;
    private RhSalarioFuncionario salarioFuncionario;

    private Part anexoCarregado;

    private List<Object> listaSubsidios;

    /**
     * Creates a new instance of funcionarioBean
     */
    public RhFuncionarioEditarBean()
    {
    }

    public static RhFuncionarioPesquisarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhFuncionarioPesquisarBean) c.getELContext().getELResolver()
            .getValue(c.getELContext(), null, "rhFuncionarioEditarBean");
    }

    public RhFuncionario getFuncionario()
    {
        if (funcionario == null)
        {
            funcionario = RhFuncionarioNovoBean.getInstancia();
        }
        if (funcionario.getFkIdEspecialidade1() == null)
        {
            funcionario.setFkIdEspecialidade1(new GrlEspecialidade());
        }
        if (funcionario.getFkIdEspecialidade2() == null)
        {
            funcionario.setFkIdEspecialidade2(new GrlEspecialidade());
        }

        return funcionario;
    }

    public void setFuncionario(RhFuncionario funcionario)
    {
        this.funcionario = funcionario;
    }

    public RhSalarioFuncionario getSalarioFuncionario()
    {
        if (salarioFuncionario == null)
        {
            salarioFuncionario = RhFuncionarioNovoBean.getInstanciaSalarioFuncionario();
        }
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(RhSalarioFuncionario salarioFuncionario)
    {
        this.salarioFuncionario = salarioFuncionario;
    }

    public Part getAnexoCarregado()
    {
        return anexoCarregado;
    }

    public void setAnexoCarregado(Part anexoCarregado)
    {
        this.anexoCarregado = anexoCarregado;
    }

    public List<Object> getListaSubsidios()
    {
        return listaSubsidios;
    }

    public void setListaSubsidios(List<Object> listaSubsidios)
    {
        this.listaSubsidios = listaSubsidios;
    }

    public String limpar()
    {
        funcionario = null;
        listaSubsidios = null;

        return "funcionarioEditarRh.xhtml?faces-redirect=true";
    }

    public String sair()
    {
        limpar();

        return RhFuncionarioPesquisarBean.getInstanciaBean().limparPesquisa();
    }

    public String prepararEditar(RhFuncionario funcionario)
    {
        limpar();
        if (funcionario.getFkIdProfissao() == null)
        {
            funcionario.setFkIdProfissao(new RhProfissao());
        }
        if (funcionario.getFkIdTipoDeHorarioTrabalho() == null)
        {
            funcionario.setFkIdTipoDeHorarioTrabalho(new RhTipoDeHorarioTrabalho());
        }
        if (funcionario.getFkIdEspecialidade1() == null)
        {
            funcionario.setFkIdEspecialidade1(new GrlEspecialidade());
        }
        if (funcionario.getFkIdEspecialidade2() == null)
        {
            funcionario.setFkIdEspecialidade2(new GrlEspecialidade());
        }
        if (funcionario.getFkIdAnexoGuiaTransferencia() == null)
        {
            funcionario.setFkIdAnexoGuiaTransferencia(new GrlFicheiroAnexado());
        }

        ItensAjaxBean itensAjaxBean = ItensAjaxBean.getInstanciaBean();

        itensAjaxBean.setCentroHospitalar(funcionario.getFkIdCentroHospitalar().getPkIdCentro());

        itensAjaxBean.setDepartamento(funcionario.getFkIdSeccaoTrabalho().getFkIdDepartamento().getPkIdDepartamento());

        //Prepara as especialidades da profissao
        if (funcionario.getFkIdProfissao().getPkIdProfissao() != null)
        {
            itensAjaxBean.setProfissao(funcionario.getFkIdProfissao().getPkIdProfissao());
            itensAjaxBean.actualizarEspecialidades(funcionario.getFkIdProfissao().getPkIdProfissao());
        }

        this.setFuncionario(funcionario);

        List<RhSalarioFuncionario> salarios = salarioFuncionarioFacade.findActualPorIdFuncionario(funcionario.getPkIdFuncionario());
        if (!salarios.isEmpty())
        {
            this.setSalarioFuncionario(salarios.get(0));
        }

        listaSubsidios = new ArrayList<>();

        for (RhFuncionarioHasRhSubsidio fhs : funcionarioHasRhSubsidioFacade.pesquisaPorFuncionario(funcionario.getPkIdFuncionario()))
        {
            listaSubsidios.add(fhs.getFkIdSubsidio().getPkIdSubsidio());
        }

        this.funcionario = funcionario;

        return "funcionarioEditarRh.xhtml?faces-redirect=true";
    }

    public void changeCategoria(ValueChangeEvent e)
    {
        Integer idCategoria = (Integer) e.getNewValue();

        if (idCategoria != null)
        {
            RhCategoriaProfissional categ = categoriaProfissionalFacade.find(idCategoria);
            funcionario.setFkIdCategoria(categ);

            getSalarioFuncionario().setSalario(categ.getSalarioBase());
            if (categ.getDespesasDeRepresentacao() != null)
            {
                getSalarioFuncionario().setSalario(categ.getRemuneracaoTotal());
            }
        }
        else
        {
            getSalarioFuncionario().setSalario(0);
        }
    }

    public void limparEspecialidadesChange(javax.faces.event.AjaxBehaviorEvent e)
    {
        funcionario.getFkIdEspecialidade1().setPkIdEspecialidade(null);
        funcionario.getFkIdEspecialidade2().setPkIdEspecialidade(null);

    }

    public void limparEspecialidade2Change(javax.faces.event.AjaxBehaviorEvent e)
    {
        funcionario.getFkIdEspecialidade2().setPkIdEspecialidade(null);
    }

    public void changeEstadoFuncionario(ValueChangeEvent e)
    {
        Integer idEstado = (Integer) e.getNewValue();

        if (idEstado != null)
        {
            RhEstadoFuncionario estad = estadoFuncionarioFacade.find(idEstado);
            funcionario.setFkIdEstadoFuncionario(estad);
        }
        else
        {
            funcionario.setFkIdEstadoFuncionario(new RhEstadoFuncionario());
        }
        funcionario.setDataDemissao(null);
        funcionario.setDataReforma(null);
    }

    public void changeTipoFuncionario(ValueChangeEvent e)
    {
        if (e.getNewValue() != null)
        {
            getFuncionario().setFkIdTipoFuncionario(tipoFuncionarioFacade.find((Integer) e.getNewValue()));
        }
    }

    public boolean renderizarTransferencia()
    {
        return getFuncionario().getFkIdTipoFuncionario().getDescricao() != null
            && getFuncionario().getFkIdTipoFuncionario().getDescricao().contains("F. Público");
    }

    public String edit()
    {
        try
        {
            RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();

            funcionario.setFkIdPessoa(pessoaBean.getPessoa());

            RhFuncionarioValidarBean validarFuncionario = RhFuncionarioValidarBean.getInstanciaBean();

            if (!validarFuncionario.validarEditar(funcionario))
            {
                return null;
            }

            pessoaBean.gravarPessoa();

            userTransaction.begin();

            funcionario.setFkIdPessoa(pessoaBean.getPessoa());

            actualizarForeignKeys();

            Integer especialidade1 = funcionario.getFkIdEspecialidade1().getPkIdEspecialidade();
            Integer especialidade2 = funcionario.getFkIdEspecialidade2().getPkIdEspecialidade();

            if (especialidade1 == null)
            {
                funcionario.setFkIdEspecialidade1(null);
            }

            if (especialidade2 == null)
            {
                funcionario.setFkIdEspecialidade2(null);
            }

            if (funcionario.getNumeroSegurancaSocial() != null && funcionario.getNumeroSegurancaSocial().trim().isEmpty())
            {
                funcionario.setNumeroSegurancaSocial(null);
            }

            ficheiroAnexadoFacade.edit(funcionario.getFkIdAnexoGuiaTransferencia());
            funcionarioFacade.edit(funcionario);

            if (salarioFuncionario.getPkIdSalarioFuncionario() == null)
            {
                salarioFuncionario.setFkIdFuncionario(funcionario);
                salarioFuncionario.setDataCadastro(Calendar.getInstance().getTime());
                salarioFuncionarioFacade.create(salarioFuncionario);
            }
            else
            {
                salarioFuncionarioFacade.edit(salarioFuncionario);
            }

            /**
             * Removendo os subsídios anteriores do funcionário
             */
            funcionarioHasRhSubsidioFacade.eliminarPorFuncionario(funcionario.getPkIdFuncionario());

            /**
             * Gravando os subsídios no contrato
             */
            for (Object idSubsidio : listaSubsidios)
            {
                RhFuncionarioHasRhSubsidio funcHasSub = new RhFuncionarioHasRhSubsidio();
                funcHasSub.setFkIdFuncionario(funcionario);
                int idSub = Integer.parseInt("" + idSubsidio);
                funcHasSub.setFkIdSubsidio(new RhSubsidio(idSub));

                funcionarioHasRhSubsidioFacade.create(funcHasSub);
            }

            userTransaction.commit();

            Mensagem.sucessoMsg("Funcionário editado com sucesso!");
            funcionario.setPkIdFuncionario(null);
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

    private void actualizarForeignKeys()
    {
        if (funcionario.getFkIdEspecialidade1().getPkIdEspecialidade() != null)
        {
            funcionario.setFkIdEspecialidade1(especialidadeFacade.find(funcionario.getFkIdEspecialidade1().getPkIdEspecialidade()));
        }

        if (funcionario.getFkIdEspecialidade2().getPkIdEspecialidade() != null)
        {
            funcionario.setFkIdEspecialidade2(especialidadeFacade.find(funcionario.getFkIdEspecialidade2().getPkIdEspecialidade()));
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() != null)
        {
            funcionario.setFkIdTipoFuncionario(tipoFuncionarioFacade.find(funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario()));
        }

        if (funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario() != null)
        {
            funcionario.setFkIdEstadoFuncionario(estadoFuncionarioFacade.find(funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario()));
        }

        if (funcionario.getFkIdNivelAcademico().getPkIdNivelAcademico() != null)
        {
            funcionario.setFkIdNivelAcademico(nivelAcademicoFacade.find(funcionario.getFkIdNivelAcademico().getPkIdNivelAcademico()));
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() != null)
        {
            funcionario.setFkIdCentroHospitalar(centroHospitalarFacade.find(funcionario.getFkIdCentroHospitalar().getPkIdCentro()));
        }

        if (funcionario.getFkIdProfissao().getPkIdProfissao() != null)
        {
            funcionario.setFkIdProfissao(profissaoFacade.find(funcionario.getFkIdProfissao().getPkIdProfissao()));
        }

        if (funcionario.getFkIdCategoria().getPkIdCategoriaProfissional() != null)
        {
            funcionario.setFkIdCategoria(categoriaProfissionalFacade.find(funcionario.getFkIdCategoria().getPkIdCategoriaProfissional()));
        }

        if (funcionario.getFkIdFuncao().getPkIdFuncao() != null)
        {
            funcionario.setFkIdFuncao(funcaoFacade.find(funcionario.getFkIdFuncao().getPkIdFuncao()));
        }

        if (funcionario.getFkIdTipoDeHorarioTrabalho().getPkIdTipoDeHorarioTrabalho() != null)
        {
            funcionario.setFkIdTipoDeHorarioTrabalho(tipoDeHorarioTrabalhoFacade.find(funcionario.getFkIdTipoDeHorarioTrabalho().getPkIdTipoDeHorarioTrabalho()));
        }

    }

    public String uploadAnexo()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, util.rh.Defs.DESTINO_ANEXO_FUNCIONARIO, "GUIA_TRANSFER");

            funcionario.getFkIdAnexoGuiaTransferencia().setFicheiro(novoNome);

            anexoCarregado = null;

            System.out.println("Anexo carregado com sucesso !");
            return "funcionarioEditarRh.xhtml?faces-redirect=true";
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
    public void removerAnexo(GrlFicheiroAnexado anexo, boolean apresentarMensagem)
    {
        boolean b = UploadFicheiro.getInstance().apagarFicheiro(new java.io.File(util.rh.Defs.DESTINO_ANEXO_FUNCIONARIO + anexo.getFicheiro()));

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
