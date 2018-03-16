/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.SegUpdate;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegUpdateFacade extends AbstractFacade<SegUpdate>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SegUpdateFacade()
    {
        super(SegUpdate.class);
    }

    public SegUpdate obterRegistoUpdates()
    {
        List<SegUpdate> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    public void escreverDataActualizacaoFuncionalidadeTabela()
    {
        SegUpdate reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new SegUpdate();
            reg.setFuncionalidade(new Date());
            this.create(reg);
        }
        else
        {
            reg.setFuncionalidade(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoFuncionalidadeTabela()
    {
        SegUpdate reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new SegUpdate();
            reg.setTipoFuncionalidade(new Date());
            this.create(reg);
        }
        else
        {
            reg.setFuncionalidade(new Date());
            this.edit(reg);
        }
    }

    public Date dataFuncionalidade()
    {
        SegUpdate reg = obterRegistoUpdates();
        return reg == null ? null : reg.getFuncionalidade();
    }

}
