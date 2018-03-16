/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.GrlFicheiroAnexado;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.UserTransaction;
import sessao.GrlFicheiroAnexadoFacade;
import util.Constantes;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFicheiroAnexadoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;

    /**
     *
     * Entidades
     */
    private GrlFicheiroAnexado anexoPessoa, anexoFuncionario, anexoCandidato, anexoEstagiario, anexoContrato;

    /**
     * Creates a new instance of FicheiroAnexadoBean
     */
    public RhFicheiroAnexadoBean ()
    {
    }

    public GrlFicheiroAnexado getAnexoPessoa ()
    {
        return anexoPessoa;
    }

    public void setAnexoPessoa (GrlFicheiroAnexado anexoPessoa)
    {
        this.anexoPessoa = anexoPessoa;
    }

    public GrlFicheiroAnexado getAnexoFuncionario ()
    {
        return anexoFuncionario;
    }

    public void setAnexoFuncionario (GrlFicheiroAnexado anexoFuncionario)
    {
        this.anexoFuncionario = anexoFuncionario;
    }

    public GrlFicheiroAnexado getAnexoCandidato ()
    {
        return anexoCandidato;
    }

    public void setAnexoCandidato (GrlFicheiroAnexado anexoCandidato)
    {
        this.anexoCandidato = anexoCandidato;
    }

    public GrlFicheiroAnexado getAnexoEstagiario ()
    {
        return anexoEstagiario;
    }

    public void setAnexoEstagiario (GrlFicheiroAnexado anexoEstagiario)
    {
        this.anexoEstagiario = anexoEstagiario;
    }

    public GrlFicheiroAnexado getAnexoContrato ()
    {
        return anexoContrato;
    }

    public void setAnexoContrato (GrlFicheiroAnexado anexoContrato)
    {
        this.anexoContrato = anexoContrato;
    }
    
    public String previsualizarAnexoPessoa ()
    {
        if (anexoPessoa == null)
        {
            return null;
        }
        else if (anexoPessoa.getFicheiro() != null && !anexoPessoa.getFicheiro().trim().isEmpty())
        {
            return (Constantes.PASTA_FOTO + anexoPessoa.getFicheiro()).trim();
//            return ("./../../../"+Constantes.PASTA_FOTO + anexoPessoa.getFicheiro()).trim();
        }

        return null;
    }

    public String previsualizarAnexoFuncionario ()
    {
        if (anexoFuncionario == null)
        {
            return null;
        }
        else if (anexoFuncionario.getFicheiro() != null && !anexoFuncionario.getFicheiro().trim().isEmpty())
        {
            return (util.rh.Defs.PASTA_ANEXO_FUNCIONARIO + anexoFuncionario.getFicheiro()).trim();
//            return ("./../../../"+Constantes.PASTA_ANEXO_FUNCIONARIO + anexoFuncionario.getFicheiro()).trim();
        }

        return null;
    }

    public String previsualizarAnexoCandidato ()
    {
        if (anexoCandidato == null)
        {
            return null;
        }
        else if (anexoCandidato.getFicheiro() != null && !anexoCandidato.getFicheiro().trim().isEmpty())
        {
            return (util.rh.Defs.PASTA_ANEXO_CANDIDATO + anexoCandidato.getFicheiro()).trim();
//            return ("./../../../"+Constantes.PASTA_ANEXO_CANDIDATO + anexoCandidato.getFicheiro()).trim();
        }

        return null;
    }

    public String previsualizarAnexoEstagiario ()
    {
        if (anexoEstagiario == null)
        {
            return null;
        }
        else if (anexoEstagiario.getFicheiro() != null && !anexoEstagiario.getFicheiro().trim().isEmpty())
        {
            return (util.rh.Defs.PASTA_ANEXO_ESTAGIARIO + anexoEstagiario.getFicheiro()).trim();
//            return ("./../../../"+Constantes.PASTA_ANEXO_ESTAGIARIO + anexoEstagiario.getFicheiro()).trim();
        }

        return null;
    }

    public String previsualizarAnexoContrato ()
    {
        if (anexoContrato == null)
        {
            return null;
        }
        else if (anexoContrato.getFicheiro() != null && !anexoContrato.getFicheiro().trim().isEmpty())
        {
            return (util.rh.Defs.PASTA_ANEXO_CONTRATO + anexoContrato.getFicheiro()).trim();
//            return ("./../../"+Constantes.PASTA_ANEXO_CONTRATO + anexoContrato.getFicheiro()).trim();
        }

        return null;
    }

}
