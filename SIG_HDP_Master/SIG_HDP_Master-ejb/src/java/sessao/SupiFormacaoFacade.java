/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiFormacao;
import entidade.SupiFormacaoFormando;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author helga
 */
@Stateless
public class SupiFormacaoFacade extends AbstractFacade<SupiFormacao> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiFormacaoFacade() {
        super(SupiFormacao.class);
    }
    
     public List<SupiFormacao> listarFormacoesFormadorInterno () {
      
        
        Query query;
        query = em.createQuery("SELECT f FROM SupiFormacao f where f.fkIdFormadorAux.fkIdFuncionario.fkIdPessoa.nome != ''");
   
       

        if (query.getResultList().isEmpty()) 
            return null;
        else 
            return query.getResultList();
}
    
    public List<SupiFormacao> listarFormacoesFormadorExternoFisico () {
      
        
        Query query;
        query = em.createQuery("SELECT f FROM SupiFormacao f where f.fkIdFormadorAux.fkIdPessoa.nome != '' ");
   
       

        if (query.getResultList().isEmpty()) 
            return null;
        else 
            return query.getResultList();
}
    
    
    
     public List<SupiFormacao> listarFormacoesFormadorExternoJuridico () {
       
        Query query;
        query = em.createQuery("SELECT f FROM SupiFormacao f where f.fkIdFormadorAux.fkIdInstituicaoProveniencia.descricao != ''");
   
        if (query.getResultList().isEmpty()) 
            return null;
        else 
            return query.getResultList();
}
     
     public List<SupiFormacaoFormando> listarFormandos (int id) {
       
        Query query;
        query = em.createQuery("SELECT f FROM SupiFormacaoFormando f where f.fkIdFormacao.pkIdFormacao = :id");
        query.setParameter("id",id);
        if (query.getResultList().isEmpty()) 
            return null;
        else 
            return query.getResultList();
}
    
}
