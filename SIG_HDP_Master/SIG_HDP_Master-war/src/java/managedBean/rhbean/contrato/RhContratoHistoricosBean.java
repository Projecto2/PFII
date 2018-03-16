/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.contrato;

import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.RhFuncionarioFacade;
import sessao.RhContratoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhContratoHistoricosBean implements Serializable
{

    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhContratoFacade contratoFacade;

    /**
     * Entidades
     */
    private RhFuncionario funcionarioSelecionado;

    /**
     * Creates a new instance of funcionarioBean
     */
    public RhContratoHistoricosBean ()
    {
    }

    public RhFuncionario getFuncionarioSelecionado ()
    {
        if (funcionarioSelecionado == null)
        {
            funcionarioSelecionado = RhFuncionarioNovoBean.getInstancia();
        }
        return funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado (RhFuncionario funcionarioSelecionado)
    {
        this.funcionarioSelecionado = funcionarioSelecionado;
    }

    public List<RhFuncionario> getFuncionariosContratadosList ()
    {
        List<RhFuncionario> funcionarios = funcionarioFacade.findFuncionariosComContrato();

        for (RhFuncionario f : funcionarios)
        {
            f.setRhContratoList(contratoFacade.pesquisaPorFuncionario(f.getPkIdFuncionario()));
        }

        return funcionarios;
    }

}
