/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean.cnt;

import entidade.InterAntropometria;
import entidade.InterDietaTerapeutica;
import entidade.InterRegistoInternamento;
import entidade.InterTabelaVigilancia;
import entidade.InterTratamentoEspecifico;
import entidade.InterTratamentoMalnutricao;
import entidade.InterTratamentoSistematico;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.interbean.InterControloDiarioBean;
import managedBean.segbean.SegLoginBean;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import sessao.InterAntropometriaFacade;
import sessao.InterDietaTerapeuticaFacade;
import sessao.InterTabelaVigilanciaFacade;
import sessao.InterTratamentoEspecificoFacade;
import sessao.InterTratamentoMalnutricaoFacade;
import sessao.InterTratamentoSistematicoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTratamentoMalnutricaoNovoBean implements Serializable
{

    @EJB
    private InterTratamentoMalnutricaoFacade interTratamentoMalnutricaoFacade;
    @EJB
    private InterTratamentoEspecificoFacade interTratamentoEspecificoFacade;
    @EJB
    private InterTratamentoSistematicoFacade interTratamentoSistematicoFacade;
    @EJB
    private InterTabelaVigilanciaFacade interTabelaVigilanciaFacade;
    @EJB
    private InterDietaTerapeuticaFacade interDietaTerapeuticaFacade;
    @EJB
    private InterAntropometriaFacade interAntropometriaFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterAntropometria antropometria;

    private InterDietaTerapeutica dietaTerapeutica;

    private InterTabelaVigilancia tabelaVigilancia;

    private InterTratamentoSistematico tratamentoSistematico;

    private InterTratamentoEspecifico tratamentoEspecifico;

    private InterTratamentoMalnutricao interTratamentoMalnutricao;

    private final Calendar dataCorrente = Calendar.getInstance();

//    private boolean gravou = false;

    /**
     * Creates a new instance of InterTratamentoMalnutricaoNovoBean
     */
    public InterTratamentoMalnutricaoNovoBean()
    {
    }

    public static InterTratamentoMalnutricaoNovoBean getInstanciaBean()
    {
        return (InterTratamentoMalnutricaoNovoBean) GeradorCodigo.getInstanciaBean("interTratamentoMalnutricaoNovoBean");
    }

    public InterTratamentoMalnutricao getInstancia()
    {
        InterTratamentoMalnutricao tratamento = new InterTratamentoMalnutricao();
        tratamento.setFkIdRegistoInternamento(new InterRegistoInternamento());
        tratamento.setFkIdFuncionario(new RhFuncionario());

        return tratamento;
    }

    public InterAntropometria getAntropometria()
    {
        if (antropometria == null)
        {
            antropometria = InterAntropometriaNovoBean.getInstanciaBean().getInstancia();
        }
        return antropometria;
    }

    public void setAntropometria(InterAntropometria antropometria)
    {
        this.antropometria = antropometria;
    }

    public InterDietaTerapeutica getDietaTerapeutica()
    {
        if (dietaTerapeutica == null)
        {
            dietaTerapeutica = InterDietaTerapeuticaNovoBean.getInstanciaBean().getInstancia();
        }
        return dietaTerapeutica;
    }

    public void setDietaTerapeutica(InterDietaTerapeutica dietaTerapeutica)
    {
        this.dietaTerapeutica = dietaTerapeutica;
    }

    public InterTabelaVigilancia getTabelaVigilancia()
    {
        if (tabelaVigilancia == null)
        {
            tabelaVigilancia = InterTabelaVigilanciaNovoBean.getInstanciaBean().getInstancia();
        }
        return tabelaVigilancia;
    }

    public void setTabelaVigilancia(InterTabelaVigilancia tabelaVigilancia)
    {
        this.tabelaVigilancia = tabelaVigilancia;
    }

    public InterTratamentoSistematico getTratamentoSistematico()
    {
        if (tratamentoSistematico == null)
        {
            tratamentoSistematico = InterTratamentoSistematicoNovoBean.getInstanciaBean().getInstancia();
        }
        return tratamentoSistematico;
    }

    public void setTratamentoSistematico(InterTratamentoSistematico tratamentoSistematico)
    {
        this.tratamentoSistematico = tratamentoSistematico;
    }

    public InterTratamentoEspecifico getTratamentoEspecifico()
    {
        if (tratamentoEspecifico == null)
        {
            tratamentoEspecifico = InterTratamentoEspecificoNovoBean.getInstanciaBean().getInstancia();
        }
        return tratamentoEspecifico;
    }

    public void setTratamentoEspecifico(InterTratamentoEspecifico tratamentoEspecifico)
    {
        this.tratamentoEspecifico = tratamentoEspecifico;
    }

    public InterTratamentoMalnutricao getInterTratamentoMalnutricao()
    {
        if (interTratamentoMalnutricao == null)
        {
            interTratamentoMalnutricao = getInstancia();
        }
        return interTratamentoMalnutricao;
    }

    public void setInterTratamentoMalnutricao(InterTratamentoMalnutricao interTratamentoMalnutricao)
    {
        this.interTratamentoMalnutricao = interTratamentoMalnutricao;
    }

//    public boolean isGravou()
//    {
//        return gravou;
//    }
//
//    public void setGravou(boolean gravou)
//    {
//        this.gravou = gravou;
//    }

    public void salvar()
    {
        try
        {
            userTransaction.begin();

            getInterTratamentoMalnutricao().getFkIdRegistoInternamento().setPkIdRegistoInternamento(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento());
            getInterTratamentoMalnutricao().getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());
            getInterTratamentoMalnutricao().setData(dataCorrente.getTime());

            interTratamentoMalnutricaoFacade.create(getInterTratamentoMalnutricao());

            Long pkIdTratamentoMalnutricao = interTratamentoMalnutricaoFacade.findAll().get(interTratamentoMalnutricaoFacade.findAll().size() - 1).getPkIdTratamentoMalnutricao();

            antropometria.getFkIdTratamentoMalnutricao().setPkIdTratamentoMalnutricao(pkIdTratamentoMalnutricao);
            interAntropometriaFacade.create(antropometria);

            dietaTerapeutica.getFkIdTratamentoMalnutricao().setPkIdTratamentoMalnutricao(pkIdTratamentoMalnutricao);
            interDietaTerapeuticaFacade.create(dietaTerapeutica);

            tabelaVigilancia.getFkIdTratamentoMalnutricao().setPkIdTratamentoMalnutricao(pkIdTratamentoMalnutricao);
            interTabelaVigilanciaFacade.create(tabelaVigilancia);

            tratamentoSistematico.getFkIdTratamentoMalnutricao().setPkIdTratamentoMalnutricao(pkIdTratamentoMalnutricao);
            interTratamentoSistematicoFacade.create(tratamentoSistematico);

            tratamentoEspecifico.getFkIdTratamentoMalnutricao().setPkIdTratamentoMalnutricao(pkIdTratamentoMalnutricao);
            interTratamentoEspecificoFacade.create(tratamentoEspecifico);

            userTransaction.commit();
            Mensagem.sucessoMsg("Ficha de tratamento de malnutrição gravada com sucesso!");

//            gravou = true;

            limparCampos();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
    }

    public void limparCampos()
    {
        antropometria = null;
        dietaTerapeutica = null;
        tabelaVigilancia = null;
        tratamentoSistematico = null;
        tratamentoEspecifico = null;
        interTratamentoMalnutricao = null;
//        gravou = false;
    }
}
