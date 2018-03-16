/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.carregamentoExcel;

import entidade.GrlCentroHospitalar;
import entidade.GrlEstadoCivil;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlSexo;
import entidade.RhCategoriaProfissional;
import entidade.RhEstadoFuncionario;
import entidade.RhFuncao;
import entidade.RhFuncionario;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.RhTipoDeHorarioTrabalho;
import entidade.RhTipoFuncionario;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.rhbean.RhPessoaBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import static managedBean.rhbean.funcionario.RhFuncionarioNovoBean.getInstancia;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhCarregarTabelasBean
{

    /**
     * Creates a new instance of RhCarregarTabelasBean
     */
    public RhCarregarTabelasBean()
    {
    }

    public static RhCarregarTabelasBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhCarregarTabelasBean rhCarregarTabelasBean
            = (RhCarregarTabelasBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
                getELContext(), null, "rhCarregarTabelasBean");

        return rhCarregarTabelasBean;
    }

    public void actualizarRecursosHumanosTabelas()
    {
        //Tabelas independentes
        RhEstadoCandidatoExcelBean.getInstanciaBean().carregarEstadoCandidatoTabela();
        RhEstadoContratoExcelBean.getInstanciaBean().carregarEstadoContratoTabela();
        RhEstadoEstagiarioExcelBean.getInstanciaBean().carregarEstadoEstagiarioTabela();
        RhEstadoFeriasExcelBean.getInstanciaBean().carregarEstadoFeriasTabela();
        RhEstadoFuncionarioExcelBean.getInstanciaBean().carregarEstadoFuncionarioTabela();

        RhTipoContratoExcelBean.getInstanciaBean().carregarTipoContratoTabela();
        RhTipoDeHorarioTrabalhoExcelBean.getInstanciaBean().carregarTipoDeHorarioTrabalhoTabela();
        RhTipoEstagioExcelBean.getInstanciaBean().carregarTipoEstagioTabela();
        RhTipoFaltaExcelBean.getInstanciaBean().carregarTipoFaltaTabela();
        RhTipoFuncionarioExcelBean.getInstanciaBean().carregarTipoFuncionarioTabela();

        RhFormaPagamentoExcelBean.getInstanciaBean().carregarFormaPagamentoTabela();
        RhPeriodoAulasExcelBean.getInstanciaBean().carregarPeriodoAulasTabela();
        RhNivelAcademicoExcelBean.getInstanciaBean().carregarNivelAcademicoTabela();

        //Tabelas dependentes
        RhCriterioDeAvaliacaoExcelBean.getInstanciaBean().carregarCriterioDeAvaliacaoTabela();
        RhClassificacaoDoCriterioExcelBean.getInstanciaBean().carregarClassificacaoDoCriterioTabela();
    }


    public void criarFuncionarioRoot()
    {
        actualizarRecursosHumanosTabelas();

        managedBean.grlbean.GrlCentroHospitalarBean.getInstanciaBean().carregarExcel();
        
        managedBean.rhbean.carregamentoExcel.RhCategoriaProfissionalExcelBean.getInstanciaBean().carregarCategoriaProfissionalTabela();
        managedBean.rhbean.carregamentoExcel.RhProfissaoExcelBean.getInstanciaBean().carregarProfissaoTabela();

        managedBean.rhbean.RhSeccaoTrabalhoBean.getInstanciaBean().carregarExcel();
        managedBean.rhbean.carregamentoExcel.RhFuncaoExcelBean.getInstanciaBean().carregarFuncaoTabela();

        RhPessoaBean pessoaBean = RhPessoaBean.getInstanciaBean();
        RhFuncionarioNovoBean funcionarioNovoBean = RhFuncionarioNovoBean.getInstanciaBean();

        pessoaBean.limpar();
        funcionarioNovoBean.limpar();
        funcionarioNovoBean.setListaSubsidios(new ArrayList<>());

        pessoaBean.getPessoa().setNome("Root");
        pessoaBean.getPessoa().setNomeDoMeio("Root");
        pessoaBean.getPessoa().setSobreNome("Root");
        pessoaBean.getPessoa().setNomeMae("Root Mãe");
        pessoaBean.getPessoa().setNomePai("Root Pai");
        pessoaBean.getPessoa().setDataCadastro(new Date());
        pessoaBean.getPessoa().setDataNascimento(new Date());
        pessoaBean.getPessoa().setFkIdEstadoCivil(new GrlEstadoCivil(1));
        pessoaBean.getPessoa().setFkIdNacionalidade(new GrlPais(1));
        pessoaBean.getPessoa().setFkIdReligiao(null);
        pessoaBean.getPessoa().setFkIdSexo(new GrlSexo(1));
        pessoaBean.getPessoa().getFkIdContacto().setTelefone1("1111111");
        pessoaBean.getPessoa().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(1));
        pessoaBean.getPessoa().setFkIdGrupoSanguineo(null);
        
        //Gravar a pessoa root
        pessoaBean.gravarPessoa();
        
        funcionarioNovoBean.getFuncionario().setFkIdPessoa(pessoaBean.getPessoa());
        funcionarioNovoBean.getFuncionario().setDataAdmissao(new Date());
        funcionarioNovoBean.getFuncionario().setDataCadastro(new Date());
        funcionarioNovoBean.getFuncionario().setCodigoFuncionario("F"+pessoaBean.getPessoa().getPkIdPessoa());
        funcionarioNovoBean.getFuncionario().setNumeroBi("0000");
        funcionarioNovoBean.getFuncionario().setNumeroCartao(0);
        funcionarioNovoBean.getFuncionario().setNumeroContribuinte("0000");
        funcionarioNovoBean.getFuncionario().setNumeroSegurancaSocial("0000");
        
        funcionarioNovoBean.getFuncionario().setFkIdProfissao(new RhProfissao(1));
        funcionarioNovoBean.getFuncionario().setFkIdCategoria(new RhCategoriaProfissional(1));
        funcionarioNovoBean.getFuncionario().setFkIdEstadoFuncionario(new RhEstadoFuncionario(1));
        funcionarioNovoBean.getFuncionario().setFkIdFuncao(new RhFuncao(1));
        funcionarioNovoBean.getFuncionario().setFkIdNivelAcademico(new RhNivelAcademico(1));
        
        funcionarioNovoBean.getFuncionario().setFkIdCentroHospitalar(new GrlCentroHospitalar(1));
        funcionarioNovoBean.getFuncionario().setFkIdSeccaoTrabalho(new RhSeccaoTrabalho(1));
        funcionarioNovoBean.getFuncionario().setFkIdTipoDeHorarioTrabalho(new RhTipoDeHorarioTrabalho(1));
        funcionarioNovoBean.getFuncionario().setFkIdTipoFuncionario(new RhTipoFuncionario(1));
        
        //Gravar o funcionario
        funcionarioNovoBean.create();

    }
}
