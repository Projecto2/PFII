/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhAvaliacaoDeDesempenho;
import entidade.RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhAvaliacaoDeDesempenhoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhAvaliacaoDeDesempenhoValidarBean implements Serializable
{

    @EJB
    private RhAvaliacaoDeDesempenhoFacade avaliacaoDeDesempenhoFacade;

    /**
     * Creates a new instance of rhAvaliacaoDeDesempenhoValidarBean
     */
    public RhAvaliacaoDeDesempenhoValidarBean ()
    {
    }

    public static RhAvaliacaoDeDesempenhoValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhAvaliacaoDeDesempenhoValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhAvaliacaoDeDesempenhoValidarBean");
    }

    public boolean validarNova (RhAvaliacaoDeDesempenho avaliacaoDeDesempenho) throws Exception
    {
        Integer idFuncionario = avaliacaoDeDesempenho.getFkIdFuncionario().getPkIdFuncionario();
        Integer ano = avaliacaoDeDesempenho.getAno();

        if (idFuncionario == null)
        {
            Mensagem.erroMsg("Indique o funcionário para realizar a avaliação de desempenho");
            return false;
        }

        if (!avaliacaoDeDesempenhoFacade.pesquisarAvaliacao(idFuncionario, ano).isEmpty())
        {
            Mensagem.erroMsg("A avaliação de desempenho deste funcionário para o ano " + ano + " já foi realizada anteriormente");
            return false;
        }

        boolean temAlgumCriterioAvaliado = false;
        for (int i = 0; i < avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList().size(); i++)
        {
            RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificNoCriterio;
            classificNoCriterio = avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList().get(i);

            if (classificNoCriterio.getDescricaoClassificacao() != null)
            {
                temAlgumCriterioAvaliado = true;
                break;
            }
        }

        if (!temAlgumCriterioAvaliado)
        {
            Mensagem.erroMsg("Nenhum critério foi avaliado, impossível guardar a avaliação do funcionário");
            return false;
        }
        
        return true;
    }

}
