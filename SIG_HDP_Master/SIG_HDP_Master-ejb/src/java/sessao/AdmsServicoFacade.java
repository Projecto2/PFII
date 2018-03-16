/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsServicoFacade extends AbstractFacade<AdmsServico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsServicoFacade()
    {
        super(AdmsServico.class);
    }
    
    
    public List<AdmsServico> findServico(AdmsServico servico, boolean comPreco)
    {
        String query = constroiQuery(servico, comPreco);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (servico.getNomeServico() != null && !servico.getNomeServico().trim().isEmpty())
            qry.setParameter("nomeServico", servico.getNomeServico()+"%");

        if (servico.getCodServico()!= null && !servico.getCodServico().trim().isEmpty())
            qry.setParameter("codServico", servico.getCodServico()+ "%");

        if (servico.getFkIdGrupoServico().getPkIdGrupoServico() != null)
            qry.setParameter("fkIdGrupoServico", servico.getFkIdGrupoServico().getPkIdGrupoServico());

        if (servico.getFkIdTipoServico().getPkIdTipoServico() != null)
            qry.setParameter("fkIdTipoServico", servico.getFkIdTipoServico().getPkIdTipoServico());

        if (servico.getFkIdArea().getPkIdAreaInterna() != null)
            qry.setParameter("fkIdArea", servico.getFkIdArea().getPkIdAreaInterna());

        if (servico.getFkIdEspecialidade().getPkIdEspecialidade() != null)
            qry.setParameter("fkIdEspecialidade", servico.getFkIdEspecialidade().getPkIdEspecialidade());

        List<AdmsServico> servicos = qry.getResultList();

        return servicos;
    }

    public String constroiQuery(AdmsServico servico, boolean comPreco)
    {
        String query = "SELECT servico FROM AdmsServico as servico WHERE :pesquisar = :pesquisar";

        if (servico.getNomeServico() != null && !servico.getNomeServico().trim().isEmpty())
            query += " AND LOWER(servico.nomeServico) LIKE LOWER(:nomeServico)";

        if (servico.getCodServico()!= null && !servico.getCodServico().trim().isEmpty())
            query += " AND servico.codServico LIKE :codServico";

        if (servico.getFkIdGrupoServico().getPkIdGrupoServico() != null)
            query += " AND servico.fkIdGrupoServico.pkIdGrupoServico = :fkIdGrupoServico";

        if (servico.getFkIdTipoServico().getPkIdTipoServico() != null)
            query += " AND servico.fkIdTipoServico.pkIdTipoServico = :fkIdTipoServico";

        if (servico.getFkIdArea().getPkIdAreaInterna() != null)
            query += " AND servico.fkIdArea.pkIdAreaInterna = :fkIdArea";

        if (servico.getFkIdEspecialidade().getPkIdEspecialidade() != null)
            query += " AND servico.fkIdEspecialidade.pkIdEspecialidade = :fkIdEspecialidade";
        
        if(comPreco)
            query += " AND servico.pkIdServico IN(SELECT categoriaServico.fkIdServico.pkIdServico from AdmsCategoriaServico categoriaServico where categoriaServico.estadoCategoriaServico = true "
                + "AND categoriaServico.pkIdCategoriaServico IN(SELECT precoCategoriaServico.fkIdCategoriaServico.pkIdCategoriaServico FROM FinPrecoCategoriaServico precoCategoriaServico WHERE precoCategoriaServico.estadoPreco = true))";
//FinaValorPreco.fkIdPreco.pkIdPreco
        query += " ORDER BY servico.nomeServico";
        System.out.println(""+query);
        return query;
    } 
    
    
    public boolean isServicoTabelaEmpty()
    {
        List<AdmsServico> listServico = this.findAll();
        return (listServico == null || listServico.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdServico)
    {
        AdmsServico reg = this.find(pkIdServico);
        return reg != null;
    }
    
    public List<AdmsServico> findServicosInternamento()
    {
        Query qrs = em.createQuery("SELECT o FROM AdmsServico o WHERE o.fkIdTipoServico.descricaoTipoServico = :nomeTipo AND o.nomeServico != :nome");
        qrs.setParameter("nomeTipo", "Internamento").setParameter("nome", "Material p/ Transfusão de Sangue");
        
        return qrs.getResultList();
    }
    
}
