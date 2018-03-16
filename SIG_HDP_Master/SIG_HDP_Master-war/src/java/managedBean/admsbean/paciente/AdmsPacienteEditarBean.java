/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.paciente;

import entidade.AdmsPaciente;
import entidade.DiagGrupoSanguineo;
import entidade.GrlComuna;
import entidade.GrlDistrito;
import entidade.GrlDocumentoIdentificacao;
import entidade.GrlEstadoCivil;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlReligiao;
import entidade.GrlSexo;
import entidade.GrlTipoDocumentoIdentificacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.GrlPessoaBean;
import sessao.AdmsPacienteFacade;
import sessao.GrlContactoFacade;
import sessao.GrlDocumentoIdentificacaoFacade;
import sessao.GrlEnderecoFacade;
//import sessao.GrlPaisFacade;
import sessao.GrlPessoaFacade;
import sessao.GrlTipoDocumentoIdentificacaoFacade;
import util.ItensAjaxBean;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsPacienteEditarBean implements Serializable
{
    @EJB
    private GrlDocumentoIdentificacaoFacade grlDocumentoIdentificacaoFacade;
    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;
    @EJB
    private GrlEnderecoFacade grlEnderecoFacade;
    @EJB
    private GrlContactoFacade grlContactoFacade;
    @EJB
    private GrlPessoaFacade grlPessoaFacade;
//    @EJB
//    private GrlPaisFacade grlPaisFacade;
    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    
    FacesContext context = FacesContext.getCurrentInstance();
    ItensAjaxBean itensAjaxBean = (ItensAjaxBean) context.getELContext().getELResolver()
        .getValue(context.getELContext(), null, "itensAjaxBean");
    
    private AdmsPaciente pacienteEditar;
    
    private List<GrlDocumentoIdentificacao> documentosAntigos;
    
    private int tipoDocumento, idDocumento;
    
    private boolean pesquisar = false, edicao = false, pararAtualizacao = true;;
    
    
    private String numeroDocumento;
    
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of AdmsPacienteEditarBean
     */
    public AdmsPacienteEditarBean()
    {
    }
    
    public static AdmsPacienteEditarBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsPacienteEditarBean admsPacienteEditarBean = 
            (AdmsPacienteEditarBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsPacienteEditarBean");
        
        return admsPacienteEditarBean;
    }
    
    public AdmsPaciente getPacienteEditar()
    {
        if (this.pacienteEditar == null)
            pacienteEditar = getInstanciaPaciente();
        return this.pacienteEditar;
    }
    
    
    public void setPacienteEditar(AdmsPaciente pacienteEditar)
    {
        idDocumento = 0;
        edicao = false;
        numeroDocumento = "";
        documentosAntigos = null;
        this.pacienteEditar = null;
        this.pacienteEditar = admsPacienteFacade.find(pacienteEditar.getPkIdPaciente());
        documentosAntigos = new ArrayList<GrlDocumentoIdentificacao>();
        
        if(this.pacienteEditar.getFkIdPessoa().getFkIdGrupoSanguineo() == null)
        {
            this.pacienteEditar.getFkIdPessoa().setFkIdGrupoSanguineo(new DiagGrupoSanguineo());
        }
//        if(this.pacienteEditar.getFkIdPessoa().getFkIdReligiao() == null)
//        {
//            this.pacienteEditar.getFkIdPessoa().setFkIdReligiao(new GrlReligiao());
//        }
        if(this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdComuna() == null)
        {
            this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna());
        }
        if(this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdDistrito() == null)
        {
            this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
        }
        
        itensAjaxBean.setPais(this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais());
        itensAjaxBean.setProvincia(this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia());
        itensAjaxBean.setMunicipio(this.pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio());
    }
    
    public AdmsPaciente getInstanciaPaciente()
    {  
       AdmsPaciente pacienteInstancia = new AdmsPaciente(null, "");
       pacienteInstancia.setFkIdPessoa(new GrlPessoaBean().getInstanciaPessoa());
       return pacienteInstancia;
    }
    
    
    public void edit()
    {
        try
        {
//            if(pacienteEditar.getFkIdPessoa().getFkIdReligiao() == null || pacienteEditar.getFkIdPessoa().getFkIdReligiao().getPkIdReligiao() == null)
            pacienteEditar.getFkIdPessoa().setFkIdReligiao(null);
            if(pacienteEditar.getFkIdPessoa().getFkIdGrupoSanguineo() == null || pacienteEditar.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() == null)
                pacienteEditar.getFkIdPessoa().setFkIdGrupoSanguineo(null);
            
            userTransaction.begin();
                pacienteEditar.getFkIdPessoa().setFkIdSexo(new GrlSexo(pacienteEditar.getFkIdPessoa().getFkIdSexo().getPkIdSexo()));
                pacienteEditar.getFkIdPessoa().setFkIdNacionalidade(new GrlPais(pacienteEditar.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais()));
                pacienteEditar.getFkIdPessoa().setFkIdEstadoCivil(new GrlEstadoCivil(pacienteEditar.getFkIdPessoa().getFkIdEstadoCivil().getPkIdEstadoCivil()));
                
                editarDocumentosIdentificacao(pacienteEditar/*pacienteDocumento*/);
                grlContactoFacade.edit(pacienteEditar.getFkIdPessoa().getFkIdContacto());
                editarEndereco();
                grlPessoaFacade.edit(pacienteEditar.getFkIdPessoa());
                admsPacienteFacade.edit(pacienteEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Paciente editado com sucesso!");
            
            pacienteEditar = admsPacienteFacade.find(pacienteEditar.getPkIdPaciente());

            
//            if(pacienteEditar.getFkIdPessoa().getFkIdReligiao() == null || pacienteEditar.getFkIdPessoa().getFkIdReligiao().getPkIdReligiao() == null)
//                pacienteEditar.getFkIdPessoa().setFkIdReligiao(new GrlReligiao());
            
            if(pacienteEditar.getFkIdPessoa().getFkIdGrupoSanguineo() == null || pacienteEditar.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() == null)
                pacienteEditar.getFkIdPessoa().setFkIdGrupoSanguineo(new DiagGrupoSanguineo());
            
            if(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdComuna() == null)
                pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna());

            if(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdDistrito() == null)
                pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());        
            
            documentosAntigos = new ArrayList<>();
        }
        catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | NotSupportedException | SystemException ex)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(ex.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException excep)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    
    public void editarDocumentosIdentificacao(AdmsPaciente paciente)
    {
        System.out.println("Tamanho "+documentosAntigos.size());
        for (int i = 0; i < paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
        {
            if(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getPkIdDocumento() == null)
            {
                System.out.println("criou um");
                paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).setFkIdPessoa(paciente.getFkIdPessoa());
                grlDocumentoIdentificacaoFacade.create(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i));
            }
            else{
                grlDocumentoIdentificacaoFacade.edit(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i));
                System.out.println("editou um");
            }
        }
        
        System.out.println(""+documentosAntigos.size());
        
        for(int i = 0; i < documentosAntigos.size(); i++)
        {
            if(documentosAntigos.get(i).getPkIdDocumento() != null)
            {
                grlDocumentoIdentificacaoFacade.remove(documentosAntigos.get(i));
                System.out.println("removeu os q restaram");
            }
        }

    }
    
    
     public void removerDocumentoIdentificacao(GrlDocumentoIdentificacao documento)
     {
         if((documento.getPkIdDocumento() != null) && (documento.getPkIdDocumento() == idDocumento))
         {
             Mensagem.erroMsg("O Documento Que Esta a Ser Editado Não Pode Ser Eliminado");
             return;
         }
        for(int i = 0; i < pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
        {
            if(pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getNumeroDocumento().equalsIgnoreCase(documento.getNumeroDocumento()) && 
                pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() == 
                documento.getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao())
            {
                if(!(documentosAntigos == null))
                    documentosAntigos.add(pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i));
                pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().remove(i);
                break;
            }
        }
     }
     
    public void editarEndereco()
    {
//        if(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdComuna().getPkIdComuna() == null)
//            pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(null);
        pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio()));
        
        if(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdComuna().getPkIdComuna() == null)
            pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(null);
        else pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdComuna().getPkIdComuna()));
        
        if(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdDistrito().getPkIdDistrito() == null)
            pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(null);
        else pacienteEditar.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(new GrlDistrito(pacienteEditar.getFkIdPessoa().getFkIdEndereco().getFkIdDistrito().getPkIdDistrito()));
        
        
        grlEnderecoFacade.edit(pacienteEditar.getFkIdPessoa().getFkIdEndereco());
    }
    
     public void definirPacienteEditar(GrlDocumentoIdentificacao documento)
     {
         if(documento.getPkIdDocumento() == null)
         {
             Mensagem.erroMsg("Esta Identificacao Foi Adicionado Agora, Para Editar Basta Remover e Voltar a Adicionar Com os Dados Novos");
             return;
         }
         idDocumento = documento.getPkIdDocumento();
         numeroDocumento = documento.getNumeroDocumento();
         tipoDocumento = documento.getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao();
         edicao = true;
     }
     
     public void editarDocumento(){
         if(validarEdicaoDocumento())
         {
            for (int i = 0; i < pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if(idDocumento == pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getPkIdDocumento())
                {
                    pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).setNumeroDocumento(numeroDocumento);
                    pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().setPkTipoDocumentoIdentificacao(tipoDocumento);
                    idDocumento = 0;
                    numeroDocumento = "";
                    Mensagem.sucessoMsg("Documento Alterado, Tem De Gravar a Edição Para Se Efetuar");
                    edicao = false;
                    break;
                }
            }
         }
     }
     
    public boolean validarEdicaoDocumento()
    {
        if(!numeroDocumento.equals(""))
            for (int i = 0; i < pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if(idDocumento != pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getPkIdDocumento() 
                    && tipoDocumento == pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i)
                        .getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao())
                {
                    Mensagem.erroMsg("Tipo de documento ja adicionado, so pode ser adicionado uma vez");
                    return false;
                }
            }
        else
        {
            Mensagem.erroMsg("O numero do documento nao pode ser um texto em branco");
            return false;
        }
        return true;
    }
    
    public String getNumeroDocumento()
    {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento.toUpperCase();
    }
    

    
    //8788888888888888888888888888888888888888888888888888888888888888888888888888888888  Teste
    public String documentosDeIdentificacaoEditar()
    {
        String documentos;
        if(pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList() == null) return "";
        if(pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty())
        {
            return "Sem Documentos Ainda";
        }
        documentos = "Os Documentos São :";
        for(int i = 0; i < pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
        {
            documentos += "\n   "+pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i)
                .getFkTipoDocumentoIdentificacao().getDescricao()+": "+pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i)
                .getNumeroDocumento();
        }
        return documentos;
    }
    
    public int getTipoDocumento()
    {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento)
    {
        this.tipoDocumento = tipoDocumento;
    }
    
    public void adicionarDocumentoDeIdentificacao()
    {
        GrlTipoDocumentoIdentificacao tipoDocumentoObj = grlTipoDocumentoIdentificacaoFacade.find(tipoDocumento);
        if(validarDocumentoIdentificacao(tipoDocumentoObj))
        {
            GrlDocumentoIdentificacao documentoIdentificacao = new GrlDocumentoIdentificacao();
            documentoIdentificacao.setNumeroDocumento(numeroDocumento);
            documentoIdentificacao.setFkTipoDocumentoIdentificacao(tipoDocumentoObj);
            pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().add(documentoIdentificacao);
            numeroDocumento = "";
        }
    }
    
   private boolean validarDocumentoIdentificacao(GrlTipoDocumentoIdentificacao tipoDocumentoObj)
    {
        if(grlDocumentoIdentificacaoFacade.findDocumentoByNumero(numeroDocumento) != null)
        {
            Mensagem.erroMsg("Já Existe Um Paciente Com Este Número De Identificação, O Número Deve Ser Único!");
            return false; 
        }
        if(!numeroDocumento.equals(""))
        {
            for(int i = 0; i < pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if(pacienteEditar.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() == 
                    tipoDocumentoObj.getPkTipoDocumentoIdentificacao())
                {
                    Mensagem.erroMsg("Tipo de documento ja adicionado, so pode ser adicionado uma vez");
                    return false;
                } 
            }
        }
        else
        {
            Mensagem.erroMsg("O numero do documento nao pode ser um texto em branco");
            return false;
        }
        return true;
    }

    public boolean isEdicao()
    {
        return edicao;
    }

    public void setEdicao(boolean edicao)
    {
        this.edicao = edicao;
    }

    public boolean isPararAtualizacao()
    {
        return pararAtualizacao;
    }

    public void setPararAtualizacao(boolean pararAtualizacao)
    {
        this.pararAtualizacao = pararAtualizacao;
    }
    
    
   
   
    
}
