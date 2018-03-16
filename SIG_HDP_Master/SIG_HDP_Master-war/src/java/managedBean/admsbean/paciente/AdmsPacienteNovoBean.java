/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.paciente;

import entidade.AdmsPaciente;
import entidade.GrlDocumentoIdentificacao;
import entidade.GrlPais;
import entidade.GrlPessoa;
import entidade.GrlTipoDocumentoIdentificacao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoNovaBean;
import managedBean.grlbean.GrlPessoaBean;
import sessao.AdmsPacienteFacade;
import sessao.GrlContactoFacade;
import sessao.GrlDocumentoIdentificacaoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlPaisFacade;
import sessao.GrlPessoaFacade;
import sessao.GrlTipoDocumentoIdentificacaoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
//@SessionScoped
@ViewScoped
public class AdmsPacienteNovoBean implements Serializable
{
//    @EJB
//    private GrlDocumentoIdentificacaoFacade grlDocumentoIdentificacaoFacade1;
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
    @EJB
    private GrlPaisFacade grlPaisFacade;
    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    
    
    @Resource
    private UserTransaction userTransaction;
    
    private AdmsPaciente admsPaciente, admsUltimoPacienteGravado;
    
    private int tipoDocumento, idDocumento;

    private String numeroDocumento;
    
    private boolean irParaSolicitacao = false, pararAtualizacao = true;

    public AdmsPacienteNovoBean()
    {
    }
    
    public static AdmsPacienteNovoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsPacienteNovoBean admsPacienteNovoBean = 
            (AdmsPacienteNovoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsPacienteNovoBean");
        
        return admsPacienteNovoBean;
    }
    
    public void setAdmsPaciente(AdmsPaciente admsPaciente)
    {
        this.admsPaciente = admsPaciente;
    }
    
    public AdmsPaciente getAdmsPaciente()
    {
        if (this.admsPaciente == null)
            admsPaciente = getInstanciaPaciente();
        return this.admsPaciente;
    } 

    public AdmsPaciente getAdmsUltimoPacienteGravado()
    {
        if (this.admsUltimoPacienteGravado == null)
            admsUltimoPacienteGravado = getInstanciaPaciente();
        return admsUltimoPacienteGravado;
    }

    public void setAdmsUltimoPacienteGravado(AdmsPaciente admsUltimoPacienteGravado)
    {
        this.admsUltimoPacienteGravado = admsUltimoPacienteGravado;
    }
    
    
    
    public String getDocumentoIdentificacao(AdmsPaciente paciente)
    {
        if(paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty())
            return "";
        return ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getDescricao()+": "
            + ""+paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento();
    }

    
    public AdmsPaciente getInstanciaPaciente()
    {  
       AdmsPaciente pacienteInstancia = new AdmsPaciente(null, "");
       pacienteInstancia.setFkIdPessoa(new GrlPessoaBean().getInstanciaPessoa());
       return pacienteInstancia;
    }

    public int getTipoDocumento()
    {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento)
    {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento()
    {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento.toUpperCase();
    }
    
    public List<GrlPais> getPaises()
    {
        List<GrlPais> paises;
        paises = grlPaisFacade.findAll();
        return paises;
    }
    

    /**
     * Gravar Paciente        ****************************
     * @param pessoa
     */
    public void create(GrlPessoa pessoa)
    {
        try
        {
            userTransaction.begin();
            getAdmsPaciente().setNumeroPaciente("P"+pessoa.getPkIdPessoa());
            getAdmsPaciente().setFkIdPessoa(pessoa);
            admsPacienteFacade.create(admsPaciente);
            userTransaction.commit();
            Mensagem.sucessoMsg("Cadastro Como Paciente Efetuado Com Sucesso! O seu Número de Processo é: "+admsPaciente.getNumeroPaciente());
            limpar();
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
    
    
    
    public void create()
    {
         try
         {
                userTransaction.begin();
                    admsPaciente.getFkIdPessoa().setFkIdFoto(null);
                    grlContactoFacade.create(admsPaciente.getFkIdPessoa().getFkIdContacto());
                    gravarEndereco();
    //                if (admsPaciente.getFkIdPessoa().getFkIdReligiao().getPkIdReligiao() == null)
    //                {
                    admsPaciente.getFkIdPessoa().setFkIdReligiao(null);
    //                }
                    if (admsPaciente.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() == null)
                    {
                         admsPaciente.getFkIdPessoa().setFkIdGrupoSanguineo(null);
                    }
                    grlPessoaFacade.create(admsPaciente.getFkIdPessoa());
                    adicionarDocumentosIdentificacao();
                userTransaction.commit();

                userTransaction.begin();
                    admsPaciente.setNumeroPaciente("P"+admsPaciente.getFkIdPessoa().getPkIdPessoa());
                    admsPacienteFacade.create(admsPaciente);
    //                //88888888888888888888888888888888888888
    //                admsPaciente.setNumeroPaciente("P"+admsPaciente.getFkIdPessoa().getPkIdPessoa());
    //                admsPacienteFacade.edit(admsPaciente);
                userTransaction.commit();
                

              Mensagem.sucessoMsg("Paciente Gravado Com Sucesso!");
              Mensagem.sucessoMsg("O Seu Número é: "+admsPaciente.getFkIdPessoa().getPkIdPessoa()+" "
                  + "e o Seu Número de Processo é: "+admsPaciente.getNumeroPaciente());
              
              transferirDadosDoPacienteParaUltimoPacienteGravado();
              admsPaciente = null;
              getAdmsPaciente();

              //pessoa = null;
              numeroDocumento = "";
         }
         catch (Exception e)
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

//         return "pacienteNovoAdms.xhtml";
//         return "";
    }
    
    
    public void gravarEndereco()
    {
        if(admsPaciente.getFkIdPessoa().getFkIdEndereco().getFkIdDistrito().getPkIdDistrito() == null)
            admsPaciente.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(null);
        if(admsPaciente.getFkIdPessoa().getFkIdEndereco().getFkIdComuna().getPkIdComuna() == null)
            admsPaciente.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(null);
        grlEnderecoFacade.create(admsPaciente.getFkIdPessoa().getFkIdEndereco());
    }
    
    
    public void adicionarDocumentosIdentificacao() throws Exception
    {
        for(GrlDocumentoIdentificacao doc:admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList())
        {
            doc.setFkIdPessoa(admsPaciente.getFkIdPessoa());
            grlDocumentoIdentificacaoFacade.create(doc);
        }
    }
    
    public void limpar()
    {
        admsPaciente = null;
    }
    
    public void adicionarDocumentoDeIdentificacao()
    {
        GrlTipoDocumentoIdentificacao tipoDocumentoObj = grlTipoDocumentoIdentificacaoFacade.find(tipoDocumento);
        if(validarDocumentoIdentificacao(tipoDocumentoObj))
        {
            GrlDocumentoIdentificacao documentoIdentificacao = new GrlDocumentoIdentificacao();
            documentoIdentificacao.setNumeroDocumento(numeroDocumento);
            documentoIdentificacao.setFkTipoDocumentoIdentificacao(tipoDocumentoObj);
            admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().add(documentoIdentificacao);
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
            for(int i = 0; i < admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if(admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() == 
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
    
    
     public void removerDocumentoIdentificacao(GrlDocumentoIdentificacao documento)
     {
         if((documento.getPkIdDocumento() != null) && (documento.getPkIdDocumento() == idDocumento))
         {
             Mensagem.erroMsg("O Documento que esta a ser editado nao pode ser eliminado");
             return;
         }
        for(int i = 0; i < admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
        {
            if(admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getNumeroDocumento().equalsIgnoreCase(documento.getNumeroDocumento()) && 
                admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() == 
                documento.getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao())
            {
                admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().remove(i);
                break;
            }
        }
     }
     
     
    public boolean validarEdicaoDocumento()
    {
        if(!numeroDocumento.equals(""))
            for (int i = 0; i < admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if(idDocumento != admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i).getPkIdDocumento() 
                    && tipoDocumento == admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i)
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
    
    
    public String documentosDeIdentificacao()
    {
        String documentos;
        if(admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty())
        {
            return "Sem Documentos Ainda";
        }
        documentos = "Os Documentos São :";
        for(int i = 0; i < admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
        {
            documentos += "\n   "+admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i)
                .getFkTipoDocumentoIdentificacao().getDescricao()+": "+admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(i)
                .getNumeroDocumento();
        }
        return documentos;
    }
    
    
     public String voltar()
     {
//          return "/admsVisao/pessoa/pessoaPesquisarGrl.xhtml?faces-redirect=true";
          return "/admsVisao/paciente/pacienteAdms.xhtml?faces-redirect=true";
     }
    
    public Date getDataAtual()
    {
        return new Date();
    }
    
    
    public void transferirDadosDoPacienteParaUltimoPacienteGravado()
    {
        if(admsUltimoPacienteGravado == null) getAdmsUltimoPacienteGravado();
        admsUltimoPacienteGravado.setPkIdPaciente(admsPaciente.getPkIdPaciente());
        admsUltimoPacienteGravado.setNumeroPaciente(admsPaciente.getNumeroPaciente());
        admsUltimoPacienteGravado.setFkIdPessoa(admsPaciente.getFkIdPessoa());
        irParaSolicitacao = true;
    }
    
    
    //8888888888888888888888888888888888888888888 temporario
    public String getNumero()
    {
        if(admsUltimoPacienteGravado == null)
        {
            getAdmsUltimoPacienteGravado();
        }
        return admsUltimoPacienteGravado.getNumeroPaciente();
    }

    public boolean isIrParaSolicitacao()
    {
        return irParaSolicitacao;
    }

    public void setIrParaSolicitacao(boolean irParaSolicitacao)
    {
        this.irParaSolicitacao = irParaSolicitacao;
    }
    
    
    public String irParaNovaSolicitacao()
    {
        AdmsSolicitacaoNovaBean.getInstanciaBean().definirPacienteSolicitacao(admsPacienteFacade.find(admsUltimoPacienteGravado.getPkIdPaciente()));
//        AdmsSolicitacaoNovaBean.getInstanciaBean().definirPacienteSolicitacao(admsUltimoPacienteGravado);
        return "/admsVisao/solicitacoes/solicitacaoNovaAdms.xhtml?faces-redirect=true";
    }

    public boolean isPararAtualizacao()
    {
        return pararAtualizacao;
    }

    public void setPararAtualizacao(boolean pararAtualizacao)
    {
        System.out.println("chamou");
        this.pararAtualizacao = pararAtualizacao;
    }
    
    
    
}
