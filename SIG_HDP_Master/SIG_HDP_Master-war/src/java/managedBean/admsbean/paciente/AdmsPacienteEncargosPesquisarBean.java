/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.paciente;

import entidade.AdmsPaciente;
import entidade.GrlDocumentoIdentificacao;
import entidade.GrlPais;
import entidade.GrlTipoDocumentoIdentificacao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.grlbean.GrlPessoaBean;
import sessao.AdmsPacienteFacade;
import sessao.GrlPaisFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsPacienteEncargosPesquisarBean
{

    @EJB
    private GrlPaisFacade grlPaisFacade;
    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    
    private AdmsPaciente pacientePesquisa;
    
    private boolean pesquisar = false;
    
//    private Integer quantidadeRegistos = 10;
    
    private List<AdmsPaciente> pacientes;
    
//    private String nomeCompleto;
    
    private Date dataNascimentoInicial, dataNascimentoFinal;

    public AdmsPacienteEncargosPesquisarBean()
    {
    }
    
    public static AdmsPacienteEncargosPesquisarBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsPacienteEncargosPesquisarBean admsPacienteEncargosPesquisarBean = 
            (AdmsPacienteEncargosPesquisarBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsPacienteEncargosPesquisarBean");
        
        return admsPacienteEncargosPesquisarBean;
    }
    
    
    public String getDocumentoIdentificacao(AdmsPaciente paciente)
    {
        if(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty())
            return "";
        return ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getDescricao()+": "
            + ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento();
    }
    
    
    public AdmsPaciente getPacientePesquisa()
    {
        if (this.pacientePesquisa == null)
        {
            pacientePesquisa = getInstanciaPaciente();
            pacientePesquisa.getFkIdPessoa().setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
            pacientePesquisa.getFkIdPessoa().getGrlDocumentoIdentificacaoList().add(0, new GrlDocumentoIdentificacao());
            pacientePesquisa.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
        }
        return pacientePesquisa;
    }

    public void setPacientePesquisa(AdmsPaciente pacientePesquisa)
    {
        this.pacientePesquisa = pacientePesquisa;
    }

    
    public AdmsPaciente getInstanciaPaciente()
    {  
       AdmsPaciente pacienteInstancia = new AdmsPaciente(null, "");
       pacienteInstancia.setFkIdPessoa(new GrlPessoaBean().getInstanciaPessoa());
       return pacienteInstancia;
    }
    
    
    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }
    
    public boolean getPesquisar()
    {
        return this.pesquisar;
    }


    public void pesquisarPacientes()
    {
        if(pacientes != null)
            pacientes.clear();
        pacientes = admsPacienteFacade.findPaciente(this.pacientePesquisa, /*nomeCompleto,*/ 100, dataNascimentoInicial, dataNascimentoFinal);
        if(pacientes.isEmpty())
            Mensagem.warnMsg("Nenhum Paciente Encontrado");
        else Mensagem.sucessoMsg("Tabela Atualizada. "+pacientes.size()+" registos!");
    }
    
    public List<GrlPais> getPaises()
    {
        List<GrlPais> paises;
        paises = grlPaisFacade.findAll();
        return paises;
    }
    
    
    public Date getDataNascimentoInicial()
    {
        return dataNascimentoInicial;
    }

    public void setDataNascimentoInicial(Date dataNascimentoInicial)
    {
        this.dataNascimentoInicial = dataNascimentoInicial;
    }
    
    
    public Date getDataNascimentoFinal()
    {
        return dataNascimentoFinal;
    }

    public void setDataNascimentoFinal(Date dataNascimentoFinal)
    {
        this.dataNascimentoFinal = dataNascimentoFinal;
    }
    
    
//    public String novoAtendimento()
//    {
//        return "/admsVisao/solicitacoes/solicitacaoNovaAdms.xhtml?faces-redirect=true";
//    }

    public List<AdmsPaciente> getPacientes()
    {
        return pacientes;
    }

    public void setPacientes(List<AdmsPaciente> pacientes)
    {
        this.pacientes = pacientes;
    }
    
    public void pesquisaAtualizacao()
    {
        if(pacientes != null)
            pacientes.clear();
        pacientes = admsPacienteFacade.findPaciente(this.pacientePesquisa, /*nomeCompleto,*/ 100, dataNascimentoInicial, dataNascimentoFinal);
    }

//    public Integer getQuantidadeRegistos()
//    {
//        return quantidadeRegistos;
//    }
//
//    public void setQuantidadeRegistos(Integer quantidadeRegistos)
//    {
//        this.quantidadeRegistos = quantidadeRegistos;
//    }
    
    
    public void limparResultadosLista()
    {
        if(pacientes != null) pacientes.clear();
    }
  
    
}
