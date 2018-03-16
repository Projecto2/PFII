/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhEstagiario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhEstagiarioFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhEstagiarioValidarBean implements Serializable
{

    @EJB
    private RhEstagiarioFacade estagiarioFacade;

    /**
     * Creates a new instance of rhEstagiarioValidarBean
     */
    public RhEstagiarioValidarBean ()
    {
    }

    public static RhEstagiarioValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhEstagiarioValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhEstagiarioValidarBean");
    }
    
    public boolean validarNovo (RhEstagiario estagiario) throws Exception
    {
        if (!estagiarioFacade.findPorIdPessoa(estagiario.getFkIdPessoa().getPkIdPessoa()).isEmpty())
        {
            Mensagem.erroMsg("Esta Pessoa já foi cadastrada como estagiario");
            return false;
        }

        if (!estagiarioFacade.findPorNumeroBI(estagiario.getNumeroBi()).isEmpty())
        {
            Mensagem.erroMsg("Já existe um estagiario com este número de Bilhete de Identidade");
            return false;
        }

        if (estagiario.getFkIdTipoEstagio().getPkIdTipoEstagio() == null)
        {
            Mensagem.erroMsg("Indique o tipo de estágio");
            return false;
        }

        if (estagiario.getDataInicioEstagio() != null && estagiario.getDataFimEstagio() != null)
        {
            if (estagiario.getDataInicioEstagio().before(estagiario.getDataAdmissao()))
            {
                Mensagem.erroMsg("A data de ínicio de estágio não pode ser inferior a data de admissão");
                return false;
            }
            if (estagiario.getDataInicioEstagio().after(estagiario.getDataFimEstagio()))
            {
                Mensagem.erroMsg("A data de ínicio de estágio não pode ser superior a data de fim de estágio");
                return false;
            }
        }

        return true;
    }

    public boolean validarEditar (RhEstagiario estagiario) throws Exception
    {
        if (estagiario == null)
        {
            Mensagem.erroMsg("Estagiário não instanciado");
            return false;
        }

        if (estagiario.getPkIdEstagiario() == null)
        {
            Mensagem.erroMsg("Estagiário PK -> NULL");
            return false;
        }

        List<RhEstagiario> funcPorPessoas = estagiarioFacade.findPorIdPessoa(estagiario.getFkIdPessoa().getPkIdPessoa());
        List<RhEstagiario> funcPorNumBi = estagiarioFacade.findPorNumeroBI(estagiario.getNumeroBi());

        if (!funcPorPessoas.isEmpty())
        {
            if (funcPorPessoas.get(0).getPkIdEstagiario() != estagiario.getPkIdEstagiario())
            {
                Mensagem.erroMsg("Esta Pessoa já foi cadastrada como estagiario");
                return false;
            }
        }

        if (!funcPorNumBi.isEmpty())
        {
            if (funcPorNumBi.get(0).getPkIdEstagiario() != estagiario.getPkIdEstagiario())
            {
                Mensagem.erroMsg("Já existe um estagiario com este número de Bilhete de Identidade");
                return false;
            }
        }

        if (estagiario.getDataAdmissao() != null && estagiario.getDataAdmissao().after(Calendar.getInstance().getTime()))
        {
            Mensagem.erroMsg("A data de admissão não pode ser superior a data de hoje");
            return false;
        }

        if (estagiario.getFkIdTipoEstagio().getPkIdTipoEstagio() == null)
        {
            Mensagem.erroMsg("Indique o tipo de estágio");
            return false;
        }

        if (estagiario.getDataInicioEstagio() != null && estagiario.getDataFimEstagio() != null)
        {
            if (estagiario.getDataInicioEstagio().before(estagiario.getDataAdmissao()))
            {
                Mensagem.erroMsg("A data de ínicio de estágio não pode ser inferior a data de admissão");
                return false;
            }
            if (estagiario.getDataInicioEstagio().after(estagiario.getDataFimEstagio()))
            {
                Mensagem.erroMsg("A data de ínicio de estagio não pode ser superior a data de fim de estágio");
                return false;
            }
        }

        return true;
    }

}
