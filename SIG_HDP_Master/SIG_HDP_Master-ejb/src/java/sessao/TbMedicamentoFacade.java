/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbMedicamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author adelino
 */
@Stateless
public class TbMedicamentoFacade extends AbstractFacade<TbMedicamento>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbMedicamentoFacade()
    {
        super(TbMedicamento.class);
    }

    public boolean isMedicamentoTabelaEmpty()
    {
        List<TbMedicamento> listTbMedicamento = this.findAll();
        return (listTbMedicamento == null || listTbMedicamento.isEmpty());
    }

    public boolean existeRegisto(int pkMedicamento)
    {
        TbMedicamento reg = this.find(pkMedicamento);
        return reg != null;
    }

    public List<TbMedicamento> findMedicamento(TbMedicamento medicamento)
    {
        String query = constroiQuery(medicamento);

        TypedQuery<TbMedicamento> t = em.createQuery(query, TbMedicamento.class);

        if (medicamento.getDescricao() != null && !(medicamento.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", medicamento.getDescricao() + "%");
        }

        List<TbMedicamento> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbMedicamento medicamento)
    {
        String query = "SELECT a FROM TbMedicamento a WHERE a.pkMedicamento = a.pkMedicamento";

        if (medicamento.getDescricao() != null && !(medicamento.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
