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
import entidade.RhFuncao;
import entidade.RhFuncionario;
import entidade.RhFuncionarioHasRhSubsidio;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import entidade.RhTipoDeHorarioTrabalho;
import entidade.RhSalarioFuncionario;
import entidade.RhSeccaoTrabalho;
import entidade.RhSubsidio;
import entidade.RhTipoFuncionario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
import util.Mensagem;
import managedBean.rhbean.RhPessoaBean;
import managedBean.rhbean.validacao.RhFuncionarioValidarBean;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.RhCategoriaProfissionalFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFuncionarioHasRhSubsidioFacade;
import sessao.RhSalarioFuncionarioFacade;
import sessao.RhSubsidioFacade;
import sessao.RhTipoFuncionarioFacade;
import util.Constantes;
import util.ItensAjaxBean;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFuncionarioNovoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;
    @EJB
    private RhFuncionarioHasRhSubsidioFacade funcionarioHasRhSubsidioFacade;
    @EJB
    private RhTipoFuncionarioFacade tipoFuncionarioFacade;
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
    public RhFuncionarioNovoBean()
    {
    }

    public static RhFuncionarioNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhFuncionarioNovoBean) c.getELContext().getELResolver()
            .getValue(c.getELContext(), null, "rhFuncionarioNovoBean");
    }

    public static RhFuncionario getInstancia()
    {
        RhFuncionario funcionario = new RhFuncionario();
        funcionario.setFkIdPessoa(RhPessoaBean.getInstancia());
        funcionario.setDataAdmissao(new Date());
        funcionario.setFkIdAnexoGuiaTransferencia(new GrlFicheiroAnexado());
        funcionario.setFkIdEstadoFuncionario(new RhEstadoFuncionario());
        funcionario.setFkIdFuncao(new RhFuncao());
        funcionario.setFkIdTipoFuncionario(new RhTipoFuncionario());
        funcionario.setFkIdNivelAcademico(new RhNivelAcademico(4));
        funcionario.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        funcionario.setFkIdProfissao(new RhProfissao());
        funcionario.setFkIdCategoria(new RhCategoriaProfissional());
        funcionario.setFkIdEspecialidade1(new GrlEspecialidade());
        funcionario.setFkIdEspecialidade2(new GrlEspecialidade());
        funcionario.setFkIdTipoDeHorarioTrabalho(new RhTipoDeHorarioTrabalho());
        funcionario.setFkIdCentroHospitalar(new GrlCentroHospitalar());
        
        try
        {
            funcionario.setFkIdCentroHospitalar(getInstanciaBean().centroHospitalarFacade.findCentroHospitalarDivina());
            ItensAjaxBean.getInstanciaBean().setCentroHospitalar(funcionario.getFkIdCentroHospitalar().getPkIdCentro());
        }
        catch (Exception e)
        {
        }

        return funcionario;
    }

    public static RhSalarioFuncionario getInstanciaSalarioFuncionario()
    {
        RhSalarioFuncionario salarioFuncionario = new RhSalarioFuncionario();
        salarioFuncionario.setFkIdFuncionario(getInstancia());

        try
        {
            salarioFuncionario.setSalario(ItensAjaxBean.getInstanciaBean().getCategoriaProfissionalList().get(0).getSalarioBase());
        }
        catch (Exception e)
        {
        }

        return salarioFuncionario;
    }

    public RhFuncionario getFuncionario()
    {
        if (funcionario == null)
        {
            funcionario = getInstancia();
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
            salarioFuncionario = getInstanciaSalarioFuncionario();
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
        salarioFuncionario = null;

        return "/faces/rhVisao/rhIngresso/rhFuncionario/funcionarioNovoRh.xhtml?faces-redirect=true";
    }

    public String sair()
    {
        limpar();

        return RhFuncionarioPesquisarBean.getInstanciaBean().limparPesquisa();
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

    public void changeTipoFuncionario (ValueChangeEvent e)
    {
        if (e.getNewValue() != null)
        {
            getFuncionario().setFkIdTipoFuncionario(tipoFuncionarioFacade.find((Integer) e.getNewValue()));
        }
    }

    public boolean renderizarTransferencia ()
    {
        return getFuncionario().getFkIdTipoFuncionario().getDescricao() != null
               && getFuncionario().getFkIdTipoFuncionario().getDescricao().contains("F. Público");
    }
    
    
    public String create()
    {
        try
        {
            RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();

            funcionario.setFkIdPessoa(pessoaBean.getPessoa());

            RhFuncionarioValidarBean funcionarioValidar = RhFuncionarioValidarBean.getInstanciaBean();

            //Realiza as validações
            if (!funcionarioValidar.validarNovo(funcionario))
            {
                return null;
            }

            pessoaBean.gravarPessoa();

            userTransaction.begin();

            funcionario.setCodigoFuncionario("F" + funcionario.getFkIdPessoa().getPkIdPessoa());

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
            
            funcionario.setDataCadastro(Calendar.getInstance().getTime());

            ficheiroAnexadoFacade.create(funcionario.getFkIdAnexoGuiaTransferencia());
            funcionarioFacade.create(funcionario);

            if (salarioFuncionario != null)
            {
                salarioFuncionario.setFkIdFuncionario(funcionario);
                salarioFuncionario.setDataCadastro(Calendar.getInstance().getTime());
                salarioFuncionarioFacade.create(salarioFuncionario);
            }
            /**
             * Gravando os subsídios do funcionário
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

            Mensagem.sucessoMsg("Funcionário guardado com sucesso!");
            pessoaBean.setPessoa(null);
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

    public String uploadAnexo()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();

            String novoNome = upload.gravar(anexoCarregado, util.rh.Defs.DESTINO_ANEXO_FUNCIONARIO, "GUIA_TRANSFER");

            removerAnexo(funcionario.getFkIdAnexoGuiaTransferencia(), false);
            funcionario.getFkIdAnexoGuiaTransferencia().setFicheiro(novoNome);

            anexoCarregado = null;

            System.out.println("Anexo carregado com sucesso !");
//            return "funcionarioNovoRh.xhtml?faces-redirect=true";
            return null;
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

    public List<RhFuncionario> getTodosMedicos()
    {
        return funcionarioFacade.listaDosMedicos();
    }

}
