/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import entidade.RhCandidato;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.RhCandidatoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhCandidatoValidarBean implements Serializable
{

    @EJB
    private RhCandidatoFacade candidatoFacade;

    /**
     * Creates a new instance of rhCandidatoValidarBean
     */
    public RhCandidatoValidarBean ()
    {
    }

    public static RhCandidatoValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhCandidatoValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhCandidatoValidarBean");
    }
    
    public boolean validarNovo (RhCandidato candidato) throws Exception
    {
        if (!candidatoFacade.findPorIdPessoa(candidato.getFkIdPessoa().getPkIdPessoa()).isEmpty())
        {
            Mensagem.erroMsg("Esta Pessoa já foi cadastrada como candidato");
            return false;
        }

        if (!candidatoFacade.findPorNumeroBI(candidato.getNumeroBi()).isEmpty())
        {
            Mensagem.erroMsg("Já existe um candidato com este número de Bilhete de Identidade");
            return false;
        }

        if (candidato.getFkIdCategoriaPretendida().getPkIdCategoriaProfissional() == null)
        {
            Mensagem.erroMsg("Indique a cargo/categoria pretendida");
            return false;
        }

        Integer especialidade1 = candidato.getFkIdEspecialidade1().getPkIdEspecialidade();
        Integer especialidade2 = candidato.getFkIdEspecialidade2().getPkIdEspecialidade();

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

        return true;
    }

    public boolean validarEditar (RhCandidato candidato) throws Exception
    {
        if (candidato == null)
        {
            Mensagem.erroMsg("Candidato não instanciado");
            return false;
        }

        if (candidato.getPkIdCandidato() == null)
        {
            Mensagem.erroMsg("Candidato PK -> NULL");
            return false;
        }

        List<RhCandidato> funcPorPessoas = candidatoFacade.findPorIdPessoa(candidato.getFkIdPessoa().getPkIdPessoa());
        List<RhCandidato> funcPorNumBi = candidatoFacade.findPorNumeroBI(candidato.getNumeroBi());

        if (!funcPorPessoas.isEmpty())
        {
            if (funcPorPessoas.get(0).getPkIdCandidato() != candidato.getPkIdCandidato())
            {
                Mensagem.erroMsg("Esta Pessoa já foi cadastrada como candidato");
                return false;
            }
        }

        if (!funcPorNumBi.isEmpty())
        {
            if (funcPorNumBi.get(0).getPkIdCandidato() != candidato.getPkIdCandidato())
            {
                Mensagem.erroMsg("Já existe um candidato com este número de Bilhete de Identidade");
                return false;
            }
        }

        Integer especialidade1 = candidato.getFkIdEspecialidade1().getPkIdEspecialidade();
        Integer especialidade2 = candidato.getFkIdEspecialidade2().getPkIdEspecialidade();

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

        if (candidato.getDataAdmissao() != null && candidato.getDataAdmissao().after(Calendar.getInstance().getTime()))
        {
            Mensagem.erroMsg("A data de admissão não pode ser superior a data de hoje");
            return false;
        }

        return true;
    }

}
