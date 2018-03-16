/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AdmsServico;
import entidade.InterEnfermaria;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterEnfermariaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterEnfermariaListarBean implements Serializable
{

    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterEnfermaria enfermaria;

    private int pk_id_enfermaria, fk_id_servico, servicoPesq;

    private List<InterEnfermaria> listaEnfermarias;

    private String enfermariaPesq, codEnfermariaPesq;
    
    /**
     * Creates a new instance of EnfermariaBean
     */
    public InterEnfermariaListarBean()
    {

    }

    public static InterEnfermariaListarBean getInstanciaBean()
    {
        return (InterEnfermariaListarBean) GeradorCodigo.getInstanciaBean("interEnfermariaListarBean");
    }

    public InterEnfermaria getInstancia()
    {
        InterEnfermaria instancia = new InterEnfermaria();
        instancia.setFkIdServico(new AdmsServico());

        return instancia;
    }

    public InterEnfermaria getEnfermaria()
    {
        if (enfermaria == null)
        {
            enfermaria = getInstancia();
        }
        return enfermaria;
    }

    public void setEnfermaria(InterEnfermaria enfermaria)
    {
        this.enfermaria = enfermaria;
    }

    public int getFk_id_servico()
    {
        return fk_id_servico;
    }

    public void setFk_id_servico(int fk_id_servico)
    {
        this.fk_id_servico = fk_id_servico;
    }

    public String getCodEnfermariaPesq()
    {
        return codEnfermariaPesq;
    }

    public void setCodEnfermariaPesq(String codEnfermariaPesq)
    {
        this.codEnfermariaPesq = codEnfermariaPesq;
    }

    public int getServicoPesq()
    {
        return servicoPesq;
    }

    public void setServicoPesq(int servicoPesq)
    {
        this.servicoPesq = servicoPesq;
    }

    public List<InterEnfermaria> getListaEnfermarias()
    {
        return listaEnfermarias;
    }

    public void setListaEnfermarias(List<InterEnfermaria> listaEnfermarias)
    {
        this.listaEnfermarias = listaEnfermarias;
    }

    public int getPk_id_enfermaria()
    {
        return pk_id_enfermaria;
    }

    public void setPk_id_enfermaria(int pk_id_enfermaria)
    {
        this.pk_id_enfermaria = pk_id_enfermaria;
    }

    public String getEnfermariaPesq()
    {
        return enfermariaPesq;
    }

    public void setEnfermariaPesq(String enfermariaPesq)
    {
        this.enfermariaPesq = enfermariaPesq;
    }

    public InterEnfermariaFacade getInterEnfermariaFacade()
    {
        return interEnfermariaFacade;
    }

    public void setInterEnfermariaFacade(InterEnfermariaFacade interEnfermariaFacade)
    {
        this.interEnfermariaFacade = interEnfermariaFacade;
    }

    public void salvar()
    {
        try
        {
            userTransaction.begin();

            if (interEnfermariaFacade.listarEnfermariasPorNome_e_Codigo(enfermaria.getDescricao(), enfermaria.getCodigoEnfermaria()).isEmpty())
            {
                enfermaria.setFkIdServico(new AdmsServico(fk_id_servico));

                interEnfermariaFacade.create(enfermaria);

                userTransaction.commit();

                limparCampos();
                Mensagem.sucessoMsg("Enfermaria cadastrada com sucesso!");
            }
            else
            {
                Mensagem.erroMsg("Já existe uma enfermaria com este nome!");
            }
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
        enfermaria = null;

        if (listaEnfermarias != null)
        {
            listaEnfermarias = interEnfermariaFacade.pesquisarEnfermaria(enfermariaPesq, codEnfermariaPesq, servicoPesq);
        }
    }

    public void eliminar(InterEnfermaria enfermariaRemover)
    {
        interEnfermariaFacade.remove(enfermariaRemover);
    }

    public List<InterEnfermaria> listarTodas()
    {        
        return interEnfermariaFacade.listarTodasEnfermarias();
    }
    
    public List<InterEnfermaria> listarTodasPorTipoServico()
    {   
        String servicoInter = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        if (servicoInter.equals("Tuberculose Adultos"))
            servicoInter = "Tuberculose";
        else if (servicoInter.equals("Tuberculose Crianças"))
             servicoInter = "Pediatria Geral";        
        
        return interEnfermariaFacade.listarEnfermariasPorTipo(servicoInter);
    }

    public List<InterEnfermaria> findAllByServico(String servico)
    {   
        if (servico.equals("Tuberculose Adultos"))
            servico = "Tuberculose";
        
        return interEnfermariaFacade.listarEnfermariasPorTipo(servico);
    }
    
    public List<InterEnfermaria> findBySexo()
    {
        String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        if (tipoServico.equals("Medicina") || tipoServico.equals("Tuberculose"))
        {
            List<InterEnfermaria> lista = interEnfermariaFacade.listarEnfermariasPorTipo(tipoServico);
            List<InterEnfermaria> listaAux = new ArrayList();

            for (int i = 0; i < lista.size(); i++)
            {
                if (InterSolicitacoesInterBean.getInstanciaBean().getServicoSolicitado().getPkIdServicoSolicitado() != null)
                {
                    if (InterSolicitacoesInterBean.getInstanciaBean().getServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getDescricao().equals("Masculino"))
                    {
                        if (lista.get(i).getDescricao().equals("Medicina Homens") || lista.get(i).getDescricao().equals("TB Homens"))
                        {
                            listaAux.add(lista.get(i));
                        }
                    }
                    else
                    {
                        if (lista.get(i).getDescricao().equals("Medicina Mulheres") || lista.get(i).getDescricao().equals("TB Mulheres"))
                        {
                            listaAux.add(lista.get(i));
                        }
                    }
                }
            }

            return listaAux;
        }
        return interEnfermariaFacade.listarEnfermariasPorTipo(tipoServico);
    }

    public void pesquisar()
    {
        listaEnfermarias = interEnfermariaFacade.pesquisarEnfermaria(enfermariaPesq, codEnfermariaPesq, servicoPesq);

        if (listaEnfermarias.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaEnfermarias.size() + " registo(s) retornado(s).");
        }
    }
}
