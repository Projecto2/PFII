/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.FarmLoteProduto;
import entidade.FarmProduto;
import entidade.InterHoraMedicacao;
import entidade.InterMedicacao;
import entidade.InterMedicacaoHasFarmProduto;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.InterMedicacaoHasFarmProdutoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterMedicacaoEditarBean implements Serializable
{
    @EJB
    private InterMedicacaoHasFarmProdutoFacade interMedicacaoHasFarmProdutoFacade;
        
    @Resource
    private UserTransaction userTransaction;
          
    private InterMedicacaoHasFarmProduto medicacaoProdutoOral;
    private InterMedicacaoHasFarmProduto medicacaoProdutoEndovenoso;
    private InterMedicacaoHasFarmProduto medicacaoProdutoIM;
    private InterMedicacaoHasFarmProduto medicacaoProdutoInalacao;
    
    /**
     * Creates a new instance of InterMedicacaoEditarBean
     */
    public InterMedicacaoEditarBean()
    {
    }
    
    public static InterMedicacaoEditarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterMedicacaoEditarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interMedicacaoEditarBean");
    }
    
    public InterMedicacaoHasFarmProduto getInstancia ()
    {
         InterMedicacaoHasFarmProduto mdP = new InterMedicacaoHasFarmProduto();
         mdP.setFkIdProduto(new FarmProduto());
         mdP.setFkIdMedicacao(new InterMedicacao());
         mdP.setFkIdHoraMedicacao(new InterHoraMedicacao());
            
         return mdP;
    }

    public InterMedicacaoHasFarmProduto getMedicacaoProdutoOral()
    {
        if (medicacaoProdutoOral == null)
            medicacaoProdutoOral = getInstancia();
        return medicacaoProdutoOral;
    }

    public void setMedicacaoProdutoOral(InterMedicacaoHasFarmProduto medicacaoProdutoOral)
    {
        this.medicacaoProdutoOral = medicacaoProdutoOral;
    }

    public InterMedicacaoHasFarmProduto getMedicacaoProdutoEndovenoso()
    {
        if (medicacaoProdutoEndovenoso == null)
            medicacaoProdutoEndovenoso = getInstancia();
        return medicacaoProdutoEndovenoso;
    }

    public void setMedicacaoProdutoEndovenoso(InterMedicacaoHasFarmProduto medicacaoProdutoEndovenoso)
    {
        this.medicacaoProdutoEndovenoso = medicacaoProdutoEndovenoso;
    }

    public InterMedicacaoHasFarmProduto getMedicacaoProdutoIM()
    {
        if (medicacaoProdutoIM == null)
            medicacaoProdutoIM = getInstancia();
        return medicacaoProdutoIM;
    }

    public void setMedicacaoProdutoIM(InterMedicacaoHasFarmProduto medicacaoProdutoIM)
    {
        this.medicacaoProdutoIM = medicacaoProdutoIM;
    }

    public InterMedicacaoHasFarmProduto getMedicacaoProdutoInalacao()
    {
        return medicacaoProdutoInalacao;
    }

    public void setMedicacaoProdutoInalacao(InterMedicacaoHasFarmProduto medicacaoProdutoInalacao)
    {
        this.medicacaoProdutoInalacao = medicacaoProdutoInalacao;
    }
    
    public void editarMedicamento(int opcao)
    {
        try
        {
            userTransaction.begin();
            if (opcao == 1)
                interMedicacaoHasFarmProdutoFacade.edit(medicacaoProdutoOral);
            else if (opcao == 2)
                interMedicacaoHasFarmProdutoFacade.edit(medicacaoProdutoEndovenoso);
            else if (opcao == 3)
                interMedicacaoHasFarmProdutoFacade.edit(medicacaoProdutoIM);
            
            userTransaction.commit();
            Mensagem.sucessoMsg("A medicação foi editada com sucesso.");
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
}
