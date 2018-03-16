/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.paciente;

import entidade.AdmsPaciente;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.grlbean.GrlPessoaBean;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsPacienteVisualizarBean implements Serializable
{

    /**
     * Creates a new instance of AdmsPacienteVisualizarBean
     */
    private AdmsPaciente admsPaciente;
    
    public AdmsPacienteVisualizarBean()
    {
    }
    
    public static AdmsPacienteVisualizarBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsPacienteVisualizarBean admsPacienteVisualizarBean = 
            (AdmsPacienteVisualizarBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsPacienteVisualizarBean");
        
        return admsPacienteVisualizarBean;
    }

    public AdmsPaciente getAdmsPaciente()
    {
//        if(admsPaciente == null)
//        {
//            admsPaciente = new AdmsPaciente();
//            admsPaciente.setFkIdPessoa(getPessoaBean().getInstanciaPessoa());
//        }
        return admsPaciente;
    }

    public void setAdmsPaciente(AdmsPaciente admsPaciente)
    {
        this.admsPaciente = null;
//System.out.println("paciente "+admsPaciente);
        this.admsPaciente = admsPaciente;
    }
    
//     private GrlPessoaBean getPessoaBean()
//     {
//          return (GrlPessoaBean) FacesContext.getCurrentInstance()
//                  .getELContext().getELResolver()
//                  .getValue(FacesContext.getCurrentInstance().getELContext(), null, "pessoaBean");
//     }
     
     
     
     public String getDocumentosIdentificacao()
     {
         if(admsPaciente == null) return "";
         if(admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().isEmpty()) return "*** SEM DOCUMENTOS ***";
         String documentos = ""+admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList()
             .get(0).getFkTipoDocumentoIdentificacao().getDescricao()+": "+admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList()
             .get(0).getNumeroDocumento();
         for(int i = 1; i < admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().size(); i++)
         {
             documentos += " // "+admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList()
             .get(i).getFkTipoDocumentoIdentificacao().getDescricao()+": "+admsPaciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList()
             .get(i).getNumeroDocumento();
         }
         return documentos;
     }


    
}
