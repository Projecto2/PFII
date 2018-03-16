/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhFuncionarioFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhFuncionarioValidarBean implements Serializable
{

    @EJB
    private RhFuncionarioFacade funcionarioFacade;

    /**
     * Creates a new instance of rhFuncionarioValidarBean
     */
    public RhFuncionarioValidarBean ()
    {
    }

    public static RhFuncionarioValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhFuncionarioValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhFuncionarioValidarBean");
    }

    public boolean validarNovo (RhFuncionario funcionario) throws Exception
    {
        if (funcionario == null)
        {
            Mensagem.erroMsg("Funcionário não instanciado");
            return false;
        }

        if (funcionario.getNumeroCartao() == null || (funcionario.getNumeroCartao() < 1 && !funcionarioFacade.findAll().isEmpty()))
        {
            Mensagem.erroMsg("Número do cartão inválido");
            return false;
        }

        if (!funcionarioFacade.findPorIdPessoa(funcionario.getFkIdPessoa().getPkIdPessoa()).isEmpty())
        {
            Mensagem.erroMsg("Esta Pessoa já foi cadastrada como funcionário");
            return false;
        }

        if (!funcionarioFacade.findPorNumeroBI(funcionario.getNumeroBi()).isEmpty())
        {
            Mensagem.erroMsg("Já existe um funcionário com este número de Bilhete de Identidade");
            return false;
        }

        if (!funcionarioFacade.findPorNumeroCartaoIdentidade(funcionario.getNumeroCartao()).isEmpty())
        {
            Mensagem.erroMsg("Já existe um funcionário com este número de cartão de identidade");
            return false;
        }

        if (funcionario.getNumeroSegurancaSocial() != null)
        {
            if (!funcionarioFacade.findPorNumeroSegurancaSocial(funcionario.getNumeroSegurancaSocial()).isEmpty())
            {
                Mensagem.erroMsg("Já existe um funcionário com este número de segurança social");
                return false;
            }
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() == null)
        {
            Mensagem.erroMsg("Indique o centro hospitalar");
            return false;
        }

        Integer especialidade1 = funcionario.getFkIdEspecialidade1().getPkIdEspecialidade();
        Integer especialidade2 = funcionario.getFkIdEspecialidade2().getPkIdEspecialidade();

        //Se uma das especialidades não estiver nula
        //Verifica se são iguais
        if (especialidade1 != null || especialidade2 != null)
        {
            if (especialidade1 == especialidade2)
            {
                Mensagem.erroMsg("As especialidades não devem ser iguais");
                return false;
            }
        }

        if (especialidade1 == null && especialidade2 != null)
        {
            Mensagem.erroMsg("A Especialidade1 deve ser selecionada se possuir apenas uma especialidade e não a Especialidade2");
            return false;
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() == null)
        {
            Mensagem.erroMsg("Indique o tipo de funcionário");
            return false;
        }

        if (funcionario.getDataDemissao() != null && funcionario.getDataDemissao().before(funcionario.getDataAdmissao()))
        {
            Mensagem.erroMsg("A data de demissão não pode ser inferior a data de admissão");
            return false;
        }

        if (funcionario.getDataReforma() != null && funcionario.getDataReforma().before(funcionario.getDataAdmissao()))
        {
            Mensagem.erroMsg("A data de reforma não pode ser inferior a data de admissão");
            return false;
        }

        return true;
    }

    public boolean validarEditar (RhFuncionario funcionario) throws Exception
    {
        if (funcionario == null)
        {
            Mensagem.erroMsg("Funcionário não instanciado");
            return false;
        }

        if (funcionario.getPkIdFuncionario() == null)
        {
            Mensagem.erroMsg("Funcionário PK -> NULL");
            return false;
        }

        if (funcionario.getNumeroCartao() == null || funcionario.getNumeroCartao() < 1)
        {
            Mensagem.erroMsg("Número do cartão inválido");
            return false;
        }

        List<RhFuncionario> funcPorPessoas = funcionarioFacade.findPorIdPessoa(funcionario.getFkIdPessoa().getPkIdPessoa());
        List<RhFuncionario> funcPorNumBi = funcionarioFacade.findPorNumeroBI(funcionario.getNumeroBi());
        List<RhFuncionario> funcPorNumCartao = funcionarioFacade.findPorNumeroCartaoIdentidade(funcionario.getNumeroCartao());

        if (!funcPorPessoas.isEmpty())
        {
            if (funcPorPessoas.get(0).getPkIdFuncionario() != funcionario.getPkIdFuncionario())
            {
                Mensagem.erroMsg("Esta Pessoa já foi cadastrada como funcionário");
                return false;
            }
        }

        if (!funcPorNumBi.isEmpty())
        {
            if (funcPorNumBi.get(0).getPkIdFuncionario() != funcionario.getPkIdFuncionario())
            {
                Mensagem.erroMsg("Já existe um funcionário com este número de Bilhete de Identidade");
                return false;
            }
        }

        if (!funcPorNumCartao.isEmpty())
        {
            if (funcPorNumCartao.get(0).getPkIdFuncionario() != funcionario.getPkIdFuncionario())
            {
                Mensagem.erroMsg("Já existe um funcionário com este número de cartão de identidade");
                return false;
            }
        }

        if (!funcPorNumCartao.isEmpty())
        {
            if (funcPorNumCartao.get(0).getPkIdFuncionario() != funcionario.getPkIdFuncionario())
            {
                Mensagem.erroMsg("Já existe um funcionário com este número de cartão de identidade");
                return false;
            }
        }

        if (funcionario.getNumeroSegurancaSocial() != null)
        {
            List<RhFuncionario> funcPorSegSocial = funcionarioFacade.findPorNumeroSegurancaSocial(funcionario.getNumeroSegurancaSocial());

            if (!funcPorSegSocial.isEmpty())
            {
                if (funcPorSegSocial.get(0).getPkIdFuncionario() != funcionario.getPkIdFuncionario())
                {
                    Mensagem.erroMsg("Já existe um funcionário com este número de segurança social");
                    return false;
                }
            }
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() == null)
        {
            Mensagem.erroMsg("Indique o centro hospitalar");
            return false;
        }

        Integer especialidade1 = funcionario.getFkIdEspecialidade1().getPkIdEspecialidade();
        Integer especialidade2 = funcionario.getFkIdEspecialidade2().getPkIdEspecialidade();

        //Se uma das especialidades não estiver nula
        //Verifica se são iguais
        if (especialidade1 != null || especialidade2 != null)
        {
            if (especialidade1 == especialidade2)
            {
                Mensagem.erroMsg("As especialidades não devem ser iguais");
                return false;
            }
        }

        if (especialidade1 == null && especialidade2 != null)
        {
            Mensagem.erroMsg("A Especialidade1 deve ser selecionada se possuir apenas uma especialidade e não a Especialidade2");
            return false;
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() == null)
        {
            Mensagem.erroMsg("Indique o tipo de funcionário");
            return false;
        }

        if (funcionario.getDataDemissao() != null && funcionario.getDataDemissao().before(funcionario.getDataAdmissao()))
        {
            Mensagem.erroMsg("A data de demissão não pode ser inferior a data de admissão");
            return false;
        }

        if (funcionario.getDataReforma() != null && funcionario.getDataReforma().before(funcionario.getDataAdmissao()))
        {
            Mensagem.erroMsg("A data de reforma não pode ser inferior a data de admissão");
            return false;
        }

        return true;
    }

}
