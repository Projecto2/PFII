/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterEnfermaria;
import entidade.InterSalaInternamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.InterEnfermariaFacade;
import sessao.InterSalaInternamentoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterSalaListarBean implements Serializable
{

    @EJB
    private InterSalaInternamentoFacade interSalaInternamentoFacade;

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;

    private InterSalaInternamento sala;

    private int enfermaria, pk_id_sala;

    private List<InterSalaInternamento> listaSalas;

    private String nomeEnfermaria, nomeSala;

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

    /**
     * Creates a new instance of SalaBean
     */
    public InterSalaListarBean()
    {

    }

    public static InterSalaListarBean getInstanciaBean()
    {
        return (InterSalaListarBean) GeradorCodigo.getInstanciaBean("interSalaListarBean");
    }

    public String novoNomeSala()
    {
        interSalaInternamentoFacade.getUltimaSala(tipoServico);
        return "Sala " + interSalaInternamentoFacade.getUltimaSala(tipoServico);
    }

    public String novoCodigoSala()
    {
        interSalaInternamentoFacade.getUltimaSala(tipoServico);
        return "S0" + interSalaInternamentoFacade.getUltimaSala(tipoServico);
    }

    public void ultimaSala()
    {
        interSalaInternamentoFacade.getUltimaSala(tipoServico);
        sala.setNomeSala("Sala " + interSalaInternamentoFacade.getUltimaSala(tipoServico));
        sala.setCodigoSalaInternamento("S0" + interSalaInternamentoFacade.getUltimaSala(tipoServico));
    }

    public InterSalaInternamento getInstancia()
    {
        InterSalaInternamento s = new InterSalaInternamento();
        s.setFkIdEnfermaria(new InterEnfermaria());

        return s;
    }

    public InterSalaInternamento getSala()
    {
        if (sala == null)
        {
            sala = getInstancia();
        }
        return sala;
    }

    public void setSala(InterSalaInternamento sala)
    {
        this.sala = sala;
    }

    public List<InterSalaInternamento> getListaSalas()
    {
        return listaSalas;
    }

    public void setListaSalas(List<InterSalaInternamento> listaSalas)
    {
        this.listaSalas = listaSalas;
    }

    public int getEnfermaria()
    {
        return enfermaria;
    }

    public void setEnfermaria(int enfermaria)
    {
        this.enfermaria = enfermaria;
    }

    public int getPk_id_Sala()
    {
        return pk_id_sala;
    }

    public void setPk_id_Sala(int pk_id_sala)
    {
        this.pk_id_sala = pk_id_sala;
    }

    public String getNomeEnfermaria()
    {
        return nomeEnfermaria;
    }

    public void setNomeEnfermaria(String nomeEnfermaria)
    {
        this.nomeEnfermaria = nomeEnfermaria;
    }

    public String getNomeSala()
    {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala)
    {
        this.nomeSala = nomeSala;
    }

    public String getTipoServico()
    {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico)
    {
        this.tipoServico = tipoServico;
    }

    public void salvar()
    {
        try
        {
            userTransaction.begin();

            sala.setNomeSala(novoNomeSala());
            sala.setCodigoSalaInternamento(novoCodigoSala());
            if (!tipoServico.equals("Medicina"))
            {
                sala.setFkIdEnfermaria(new InterEnfermaria(interEnfermariaFacade.listarEnfermariasPorTipo(tipoServico).get(0).getPkIdEnfermaria()));
            }

            interSalaInternamentoFacade.create(sala);

            userTransaction.commit();
            Mensagem.sucessoMsg("Sala cadastrada com sucesso!");

            limparCampos();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                System.out.println(e.toString());

            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
    }

    public void limparCampos()
    {
        sala = null;
        novoNomeSala();
        novoCodigoSala();
    }

    public List<InterSalaInternamento> listarTodos()
    {
        return interSalaInternamentoFacade.listarSalasPorTipoServico(tipoServico);
    }

    public List<InterSalaInternamento> listarTodosDaEnfermaria(int pkIdEnfermaria)
    {
        if (tipoServico == null && pkIdEnfermaria == 0)
            return new ArrayList();
        else if (pkIdEnfermaria != 0 || tipoServico.equals("Tuberculose Adultos") || tipoServico.equals("Medicina"))        
            return interSalaInternamentoFacade.listarSalasPorTipoServico_AllEnfermaria(pkIdEnfermaria);
        else
        {
            if (tipoServico.equals("Tuberculose Crianças"))
                tipoServico = "Pediatria Geral";
            return interSalaInternamentoFacade.listarSalasPorTipoServico(tipoServico);
        }
    }

    public void pesquisar()
    {        
        listaSalas = interSalaInternamentoFacade.findSala(enfermaria, nomeSala);

        if (listaSalas.isEmpty())
        {
            Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaSalas.size() + " registo(s) retornado(s).");
        }
    }

    public List<InterSalaInternamento> listarSalasPorEnfermaria()
    {
        return interSalaInternamentoFacade.listarSalasPorTipoServico_Enfermaria(tipoServico, enfermaria);
    }
}
