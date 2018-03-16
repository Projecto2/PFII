/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsPaciente;
import entidade.DiagExameCandidato;
import entidade.DiagResultadoExameCandidato;
import entidade.DiagTesteCompatibilidade;
import entidade.DiagTipagemDoente;
import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlEndereco;
import entidade.GrlEstadoCivil;
import entidade.GrlFicheiroAnexado;
import entidade.GrlPais;
import entidade.GrlPessoa;
import entidade.GrlReligiao;
import entidade.GrlSexo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.admsbean.paciente.AdmsPacienteNovoBean;
import sessao.DiagTesteCompatibilidadeFacade;
import sessao.DiagTipagemDoenteFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTesteCompatibilidadeBean implements Serializable
{

    @EJB
    private DiagTipagemDoenteFacade diagTipagemDoenteFacade;

    @EJB
    private DiagTesteCompatibilidadeFacade diagTesteCompatibilidadeFacade;

    private DiagTesteCompatibilidade diagTesteCompatibilidadeVisulaizar;

    private AdmsPaciente admsPacienteVisualizarTesteCompatibiladade, admsPacientePesquisarTesteCompatibiladade;

    private DiagTipagemDoente diagTipagemDoenteVisualizar;

    private List<DiagTesteCompatibilidade> listaTestesCompatibilidade;

    private int totalTestesCompatibilidade = 0;

    private boolean pesquisar;

    private String paginaAnterior;

    public static DiagTesteCompatibilidadeBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTesteCompatibilidadeBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTesteCompatibilidadeBean");
    }

    public static DiagTesteCompatibilidade getInstancia()
    {
        DiagTesteCompatibilidade diagTesteCompatibilidade = new DiagTesteCompatibilidade();
        diagTesteCompatibilidade.setProvaSoroFisiologico(new DiagResultadoExameCandidato());
        diagTesteCompatibilidade.setTesteCoombs(new DiagResultadoExameCandidato());
        diagTesteCompatibilidade.setFkIdBolsaSangue(DiagBolsaSangueBean.getInstancia());
        diagTesteCompatibilidade.setFkIdPaciente(AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente());
        diagTesteCompatibilidade.setFkIdResultadoTesteCompatibilidade(DiagResultadoTesteCompatibilidadeBean.getInstancia());
        diagTesteCompatibilidade.setFkIdRequisicaoComponente(DiagRequisicaoComponenteSanguineoBean.getInstancia());
        //diagTesteCompatibilidade.sFkId da Solicitacao de Componente Sanguineo

        return diagTesteCompatibilidade;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public AdmsPaciente getAdmsPacientePesquisarTesteCompatibiladade()
    {
        if (admsPacientePesquisarTesteCompatibiladade == null)
        {
            admsPacientePesquisarTesteCompatibiladade = AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente();
        }
        return admsPacientePesquisarTesteCompatibiladade;
    }

    public void setAdmsPacientePesquisarTesteCompatibiladade(AdmsPaciente admsPacientePesquisarTesteCompatibiladade)
    {
        this.admsPacientePesquisarTesteCompatibiladade = admsPacientePesquisarTesteCompatibiladade;
    }

    public AdmsPaciente getAdmsPacienteVisualizarTesteCompatibiladade()
    {
        if (admsPacienteVisualizarTesteCompatibiladade == null)
        {
            admsPacienteVisualizarTesteCompatibiladade = AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente();
        }
        return admsPacienteVisualizarTesteCompatibiladade;
    }

    public void setAdmsPacienteVisualizarTesteCompatibiladade(AdmsPaciente admsPacienteVisualizarTesteCompatibiladade)
    {
        this.admsPacienteVisualizarTesteCompatibiladade = admsPacienteVisualizarTesteCompatibiladade;
    }

    public DiagTesteCompatibilidade getDiagTesteCompatibilidadeVisulaizar()
    {
        if (diagTesteCompatibilidadeVisulaizar == null)
        {
            diagTesteCompatibilidadeVisulaizar = getInstancia();
        }
        return diagTesteCompatibilidadeVisulaizar;
    }

    public void setDiagTesteCompatibilidadeVisulaizar(DiagTesteCompatibilidade diagTesteCompatibilidadeVisulaizar)
    {
        this.diagTesteCompatibilidadeVisulaizar = diagTesteCompatibilidadeVisulaizar;
    }

    public DiagTipagemDoente getDiagTipagemDoenteVisualizar()
    {
        if (diagTipagemDoenteVisualizar == null)
        {
            diagTipagemDoenteVisualizar = DiagTipagemDoenteBean.getInstancia();
        }
        return diagTipagemDoenteVisualizar;
    }

    public void setDiagTipagemDoenteVisualizar(DiagTipagemDoente diagTipagemDoenteVisualizar)
    {
        this.diagTipagemDoenteVisualizar = diagTipagemDoenteVisualizar;
    }

    public List<DiagTesteCompatibilidade> getListaTestesCompatibilidade()
    {
        if (listaTestesCompatibilidade == null)
        {
            listaTestesCompatibilidade = new ArrayList<>();
        }
        return listaTestesCompatibilidade;
    }

    public AdmsPaciente getInstanciaPaciente()
    {
        GrlFicheiroAnexado foto = new GrlFicheiroAnexado();
        foto.setFicheiro(Constantes.FOTO_DEFAULT);

        AdmsPaciente pacient = new AdmsPaciente();

        pacient.setFkIdPessoa(new GrlPessoa());
        pacient.getFkIdPessoa().setFkIdFoto(foto);
        pacient.getFkIdPessoa().setFkIdEstadoCivil(new GrlEstadoCivil());
        pacient.getFkIdPessoa().setFkIdEndereco(new GrlEndereco());
        pacient.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna());
//        pacient.getFkIdPessoa().setFkIdDocumentoIdentificacao(new GrlDocumentoIdentificacao());
//        pacient.getFkIdPessoa().getFkIdDocumentoIdentificacao().setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
//      cand.getFkIdPessoa().setFkIdConjugePessoa(new GrlConjugePessoa());
        pacient.getFkIdPessoa().setFkIdContacto(new GrlContacto());
        pacient.getFkIdPessoa().setFkIdSexo(new GrlSexo());
        pacient.getFkIdPessoa().setFkIdReligiao(new GrlReligiao());
        pacient.getFkIdPessoa().setFkIdNacionalidade(new GrlPais());

        return pacient;
    }

    public List<DiagTesteCompatibilidade> findTesteCompatibilidadeByPaciente(AdmsPaciente paciente)
    {
        return diagTesteCompatibilidadeFacade.findTesteCompatibilidadeByPaciente(paciente);
    }

    public List<DiagTesteCompatibilidade> findDistinctTesteCompatibilidadeByPaciente(AdmsPaciente paciente)
    {
        return diagTesteCompatibilidadeFacade.findDistinctTesteCompatibilidadeByPaciente(paciente);
    }

    public void setListaTestesCompatibilidade(List<DiagTesteCompatibilidade> listaTestesCompatibilidade)
    {
        this.listaTestesCompatibilidade = listaTestesCompatibilidade;
    }

    public String voltarParaPesquisa()
    {
        admsPacienteVisualizarTesteCompatibiladade = null;

        if (paginaAnterior.equals("solicitacoesComponentesSanguineos"))
        {
            diagTipagemDoenteVisualizar = null;

            return paginaAnterior + ".xhtml?faces-redirect=true";
        }

        admsPacientePesquisarTesteCompatibiladade = null;

        return paginaAnterior + ".xhtml?faces-redirect=true";
    }

    public void selecionarTesteCompatibilidadeVisualizar(DiagTesteCompatibilidade testeCompatibilidadeAux)
    {
        diagTesteCompatibilidadeVisulaizar = testeCompatibilidadeAux;
    }

    public List<AdmsPaciente> pesquisarTestesCompatibilidade()
    {
        if (pesquisar)
        {
            List<AdmsPaciente> pp;
            pp = diagTesteCompatibilidadeFacade.findPacienteComTesteCompatibilidade(admsPacientePesquisarTesteCompatibiladade);
            if (pp.isEmpty() || pp == null)
            {
                Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return pp;
        }
        return null;
    }

    public String limparPesquisa()
    {
        pesquisar = false;

        admsPacientePesquisarTesteCompatibiladade = null;

        return "pesquisarTestesCompatibilidade.xhtml?faces-redirect=true";
    }

}
