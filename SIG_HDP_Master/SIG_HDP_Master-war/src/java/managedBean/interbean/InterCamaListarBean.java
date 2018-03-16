/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterCamaInternamento;
import entidade.InterSalaInternamento;
import entidade.InterEstadoCama;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.InterCamaInternamentoFacade;
import sessao.InterEnfermariaFacade;
import sessao.InterEstadoCamaFacade;
import sessao.InterSalaInternamentoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterCamaListarBean implements Serializable
{

    @EJB
    private InterEstadoCamaFacade interEstadoCamaFacade;

    @EJB
    private InterSalaInternamentoFacade interSalaInternamentoFacade;

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;

    @EJB
    private InterCamaInternamentoFacade interCamaInternamentoFacade;

    private InterCamaInternamento cama;

    private int fk_id_sala, status, enfermaria, pk_id_cama;

    private List<InterCamaInternamento> listaCamas;

    private String nomeSala, nomeCama;

    private InterSalaInternamento sala;

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();


    /**
     * Creates a new instance of CamaBean
     */
    public InterCamaListarBean()
    {

    }

    public static InterCamaListarBean getInstanciaBean()
    {
        return (InterCamaListarBean) GeradorCodigo.getInstanciaBean("interCamaListarBean");
    }

    public InterCamaInternamento getInstancia()
    {
        InterCamaInternamento c = new InterCamaInternamento();
        c.setFkIdSalaInternamento(new InterSalaInternamento());
        c.setFkIdEstadoCama(new InterEstadoCama());

        return c;
    }

    public InterCamaInternamento getCama()
    {
        if (cama == null)
        {
            cama = getInstancia();
        }
        return cama;
    }

    public void setCama(InterCamaInternamento cama)
    {
        this.cama = cama;
    }

    public int getFk_id_sala()
    {
        return fk_id_sala;
    }

    public void setFk_id_sala(int fk_id_sala)
    {
        this.fk_id_sala = fk_id_sala;
    }

    public String getTipoServico()
    {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico)
    {
        this.tipoServico = tipoServico;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public List<InterCamaInternamento> getListaCamas()
    {
        return listaCamas;
    }

    public void setListaCamas(List<InterCamaInternamento> listaCamas)
    {
        this.listaCamas = listaCamas;
    }

    public int getEnfermaria()
    {
        return enfermaria;
    }

    public void setEnfermaria(int enfermaria)
    {
        this.enfermaria = enfermaria;
    }

    public int getPk_id_cama()
    {
        return pk_id_cama;
    }

    public void setPk_id_cama(int pk_id_cama)
    {
        this.pk_id_cama = pk_id_cama;
    }

    public String getNomeCama()
    {
        return nomeCama;
    }

    public void setNomeCama(String nomeCama)
    {
        this.nomeCama = nomeCama;
    }

    public String getNomeQuarto()
    {
        return nomeSala;
    }

    public void setNomeQuarto(String nomeQuarto)
    {
        this.nomeSala = nomeQuarto;
    }

    public InterCamaInternamentoFacade getInterCamaInternamentoFacade()
    {
        return interCamaInternamentoFacade;
    }

    public void setInterCamaInternamentoFacade(InterCamaInternamentoFacade interCamaInternamentoFacade)
    {
        this.interCamaInternamentoFacade = interCamaInternamentoFacade;
    }

    public void alterarEstadoCama(int pkIdCama)
    {
        try
        {
            userTransaction.begin();
           
            cama = interCamaInternamentoFacade.find(pkIdCama);            
            cama.setFkIdEstadoCama(new InterEstadoCama(status));
            interCamaInternamentoFacade.edit(cama);

            userTransaction.commit();
            Mensagem.sucessoMsg("O estado da cama foi alterado com sucesso");
            
            status = 0;
            
            pesquisar();
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
        cama = new InterCamaInternamento();
        novoNomeCama();
        novoCodigoCama();
        nomeSala = null;
    }

    public List<InterCamaInternamento> listarTodas()
    {
        return interCamaInternamentoFacade.listarCamasPorTipoServico(tipoServico);
    }
    
    public List<InterCamaInternamento> findAllLivres()
    {
        return interCamaInternamentoFacade.listarCamasPorTipoServicoLivres(tipoServico);
    }
    
    public List<InterCamaInternamento> findAllLivresParaSolicitacoes(String nomeServico)
    {
        return interCamaInternamentoFacade.listarCamasPorTipoServicoLivres(nomeServico);
    }


//    public Integer getCamaLivre()
//    {
//        return interEstadoFacade.getStatusByNomeCama();
//    }

    public void pesquisar()
    {
        listaCamas = interCamaInternamentoFacade.findCama(nomeCama, enfermaria, fk_id_sala, status);

        if (listaCamas.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaCamas.size() + " registo(s) retornado(s).");
        }

    }

    public void pesquisarCamaLivre()
    {
        if (enfermaria == 0)
        {
            enfermaria = InterEnfermariaListarBean.getInstanciaBean().findBySexo().get(0).getPkIdEnfermaria();
        }

        listaCamas = interCamaInternamentoFacade.findCamaLivre(tipoServico, nomeCama, enfermaria, fk_id_sala);

        if (listaCamas.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
    }

//    public List<InterCamaInternamento> findAllCamasLivres()
//    {
//        return interCamaInternamentoFacade.findCama(tipoServico, nomeCama, enfermaria, fk_id_sala, getCamaLivre());
//    }

    public List<InterCamaInternamento> listarTodasDaSala()
    {
        return interCamaInternamentoFacade.listarCamasPorTipoServico_AllSala(tipoServico, fk_id_sala);
    }

    public List<InterCamaInternamento> listarTodasDaSalaLivre()
    {
        return interCamaInternamentoFacade.listarCamasPorTipoServico_Livre_AllSala(tipoServico, fk_id_sala);
    }

    private int getIdEnfermaria()
    {
        return interEnfermariaFacade.listarEnfermariasPorTipo(tipoServico).get(0).getPkIdEnfermaria();
    }

    public List<InterSalaInternamento> listarTodosDaEnfermariaComEspaco()
    {
        return interSalaInternamentoFacade.listarSalasPorTipoServico_AllEnfermaria(getIdEnfermaria());
    }

    public String novoNomeCama()
    {
        if (nomeSala == null)
        {
            return "";
        }
        else
        {

            String quartoArray[] = nomeSala.split(" ");

            fk_id_sala = Integer.parseInt(quartoArray[quartoArray.length - 1]);

            int idSala = interSalaInternamentoFacade.pesquisarPorNomeSala(nomeSala, enfermaria).getPkIdSalaInternamento();

            return "Cama " + fk_id_sala + "" + interCamaInternamentoFacade.totalCamas(idSala);
        }
    }

    public String novoCodigoCama()
    {

        if (nomeSala == null)
        {
            return "";
        }
        else
        {

            String quartoArray[] = nomeSala.split(" ");

            fk_id_sala = Integer.parseInt(quartoArray[quartoArray.length - 1]);

            int idSala = interSalaInternamentoFacade.pesquisarPorNomeSala(nomeSala, enfermaria).getPkIdSalaInternamento();

            return "C" + fk_id_sala + "" + interCamaInternamentoFacade.totalCamas(idSala);
        }
    }
}
