/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.consultas;

import entidade.AdmsServicoEfetuado;
import entidade.AmbConsulta;
import entidade.AmbConsultaHasColoracao;
import entidade.AmbConsultaHasCor;
import entidade.AmbConsultaHasEspessura;
import entidade.AmbConsultaHasImpressoesGerais;
import entidade.AmbConsultaHasTextura;
import entidade.AmbConsultaHasTurgorPele;
import entidade.AmbConsultaHasTurgorTecido;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.AmbDiagnosticoHipotese;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsulta;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbDiagnosticoHipotese;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbDiagnosticoHipoteseHasDoenca;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbConsultaHasColoracaoFacade;
import sessao.AmbConsultaHasCorFacade;
import sessao.AmbConsultaHasEspessuraFacade;
import sessao.AmbConsultaHasImpressoesGeraisFacade;
import sessao.AmbConsultaHasTexturaFacade;
import sessao.AmbConsultaHasTurgorPeleFacade;
import sessao.AmbConsultaHasTurgorTecidoFacade;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.AmbDiagnosticoHipoteseHasDoencaFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultaEliminarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;     
    @EJB
    private AmbConsultaFacade ambConsultaFacade;    
    @EJB
    private AmbConsultaHasColoracaoFacade ambConsultaHasColoracaoFacade;
    @EJB
    private AmbConsultaHasCorFacade ambConsultaHasCorFacade;    
    @EJB
    private AmbConsultaHasEspessuraFacade ambConsultaHasEspessuraFacade;   
    @EJB
    private AmbConsultaHasImpressoesGeraisFacade ambConsultaHasImpressoesGeraisFacade;    
    @EJB
    private AmbConsultaHasTexturaFacade ambConsultaHasTexturaFacade;    
    @EJB
    private AmbConsultaHasTurgorPeleFacade ambConsultaHasTurgorPeleFacade;    
    @EJB
    private AmbConsultaHasTurgorTecidoFacade ambConsultaHasTurgorTecidoFacade;
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;    
    @EJB
    private AmbDiagnosticoHipoteseHasDoencaFacade ambDiagnosticoHipoteseHasDoencaFacade;  
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;    
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;      
    
    private AmbConsulta ambConsultaEliminar; 
    private AmbDiagnosticoHipotese ambDiagnosticoHipoteseEliminar;
    
    /**
     * Creates a new instance of AmbConsultaEliminarBean
     */
    public AmbConsultaEliminarBean()
    {
    }
    
    public static AmbConsultaEliminarBean getInstanciaBean()
    {
        return (AmbConsultaEliminarBean) GeradorCodigo.getInstanciaBean("ambConsultaEliminarBean");
    }

    /******************Início dos métodos setters e getters********************/
    
    public AmbConsulta getAmbConsultaEliminar()
    {
        if (ambConsultaEliminar == null)
        {
            ambConsultaEliminar = getInstanciaAmbConsulta();
        }
        return ambConsultaEliminar;
    }

    public void setAmbConsultaEliminar(AmbConsulta ambConsultaEliminar)
    {
        this.ambConsultaEliminar = ambConsultaEliminar;
    }

    public AmbDiagnosticoHipotese getAmbDiagnosticoHipoteseEliminar()
    {
        if (ambDiagnosticoHipoteseEliminar == null)
        {
            ambDiagnosticoHipoteseEliminar = getInstanciaAmbDiagnosticoHipotese();
        }
        return ambDiagnosticoHipoteseEliminar;
    }

    public void setAmbDiagnosticoHipoteseEliminar(AmbDiagnosticoHipotese ambDiagnosticoHipoteseEliminar)
    {
        this.ambDiagnosticoHipoteseEliminar = ambDiagnosticoHipoteseEliminar;
    }    

    /*********************Fim dos métodos setters e getters********************/ 
        
//    public void eliminarConsulta(AmbDiagnosticoHipotese ambDH) throws SystemException 
//    {
//        try
//        {
//            this.userTransaction.begin();
//
//            ambConsultaEliminar = ambDH.getFkIdConsulta();
//            
//            for (AmbDiagnosticoHipoteseHasDoenca adhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
//                if (adhd.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese().equals(ambDH.getPkIdDiagnosticoHipotese()))
//                    ambDiagnosticoHipoteseHasDoencaFacade.remove(adhd);
//
//            for (AmbConsultaHasColoracao achc : ambConsultaHasColoracaoFacade.findAll())
//                 if (achc.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasColoracaoFacade.remove(achc);
//            
//            for (AmbConsultaHasCor acc : ambConsultaHasCorFacade.findAll())
//                 if (acc.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasCorFacade.remove(acc);
//            
//            for (AmbConsultaHasEspessura ache : ambConsultaHasEspessuraFacade.findAll())
//                 if (ache.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasEspessuraFacade.remove(ache);
//            
//            for (AmbConsultaHasImpressoesGerais achig : ambConsultaHasImpressoesGeraisFacade.findAll())
//                 if (achig.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasImpressoesGeraisFacade.remove(achig);
//            
//            for (AmbConsultaHasTextura acht : ambConsultaHasTexturaFacade.findAll())
//                 if (acht.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasTexturaFacade.remove(acht);
//            
//            for (AmbConsultaHasTurgorPele achtp : ambConsultaHasTurgorPeleFacade.findAll())
//                 if (achtp.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasTurgorPeleFacade.remove(achtp);
//            
//            for (AmbConsultaHasTurgorTecido achtt : ambConsultaHasTurgorTecidoFacade.findAll())
//                 if (achtt.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
//                     ambConsultaHasTurgorTecidoFacade.remove(achtt);
//            
//            ambDiagnosticoHipoteseFacade.remove(ambDH);
//            ambConsultaFacade.remove(ambConsultaEliminar);
//            
//            this.userTransaction.commit();
//            
//            Mensagem.sucessoMsg("Consulta eliminada com sucesso!");
//            
//        } catch (Exception e)
//        {
//            try
//            {
//                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
//                this.userTransaction.rollback();
//            } catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//    }
    
    public void eliminarConsulta(AmbDiagnosticoHipotese ambDH) throws SystemException 
    {
        try
        {
            this.userTransaction.begin();

            ambConsultaEliminar = ambDH.getFkIdConsulta();
            
            for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
                if (adhhd.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese().equals(ambDH.getPkIdDiagnosticoHipotese()))
                    ambDiagnosticoHipoteseHasDoencaFacade.remove(adhhd);

            for (AmbConsultaHasColoracao achc : ambConsultaHasColoracaoFacade.findAll())
                 if (achc.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasColoracaoFacade.remove(achc);
            
            for (AmbConsultaHasCor acc : ambConsultaHasCorFacade.findAll())
                 if (acc.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasCorFacade.remove(acc);
            
            for (AmbConsultaHasEspessura ache : ambConsultaHasEspessuraFacade.findAll())
                 if (ache.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasEspessuraFacade.remove(ache);
            
            for (AmbConsultaHasImpressoesGerais achig : ambConsultaHasImpressoesGeraisFacade.findAll())
                 if (achig.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasImpressoesGeraisFacade.remove(achig);
            
            for (AmbConsultaHasTextura acht : ambConsultaHasTexturaFacade.findAll())
                 if (acht.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasTexturaFacade.remove(acht);
            
            for (AmbConsultaHasTurgorPele achtp : ambConsultaHasTurgorPeleFacade.findAll())
                 if (achtp.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasTurgorPeleFacade.remove(achtp);
            
            for (AmbConsultaHasTurgorTecido achtt : ambConsultaHasTurgorTecidoFacade.findAll())
                 if (achtt.getFkIdConsulta().getPkIdConsulta().equals(ambConsultaEliminar.getPkIdConsulta()))
                     ambConsultaHasTurgorTecidoFacade.remove(achtt);
            
            for (AdmsServicoEfetuado ase : admsServicoEfetuadoFacade.findAll())
                 if (ase.getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(ambConsultaEliminar.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado()))
                     admsServicoEfetuadoFacade.remove(ase);            
            
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
            {
                 if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado()
                     .equals(ambConsultaEliminar.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado()))
                 {
                     if (adhd.getFkIdDiagnostico().getFkIdExameRealizado() == null)
                     {
                         ambDiagnosticoHasDoencaFacade.remove(adhd); 
                         ambDiagnosticoFacade.remove(adhd.getFkIdDiagnostico());
                     }
                 }
            }
            
            ambDiagnosticoHipoteseFacade.remove(ambDH);
            ambConsultaFacade.remove(ambConsultaEliminar);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Consulta eliminada com sucesso!");
            
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Impossível remover!");
//                Mensagem.erroMsg(e.toString());
                this.userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    public void removerDoencaConsulta(AmbDiagnosticoHipoteseHasDoenca adhhd) throws SystemException 
    {
        try
        {
            this.userTransaction.begin();
            
            ambDiagnosticoHipoteseHasDoencaFacade.remove(adhhd);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Doença removida com sucesso!");
            
        } catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg("Impossível remover!");
                e.printStackTrace();                
//                Mensagem.erroMsg(e.toString());
                this.userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }    
}
