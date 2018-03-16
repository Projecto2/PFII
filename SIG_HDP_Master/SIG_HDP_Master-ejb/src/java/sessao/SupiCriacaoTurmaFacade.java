/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiCriacaoTurma;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author helga
 */
@Stateless
public class SupiCriacaoTurmaFacade extends AbstractFacade<SupiCriacaoTurma>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SupiCriacaoTurmaFacade()
    {
        super(SupiCriacaoTurma.class);
    }

//    public List<SupiCriacaoTurma> findCriacaoTurma(SupiCriacaoTurma criacaoTurma)
//    {
//         String query = constroiQuery(criacaoTurma);
//
//          TypedQuery<SupiCriacaoTurma> t = em.createQuery(query, SupiCriacaoTurma.class);
//          if (criacaoTurma.getCodigoTurma() != null && !criacaoTurma.getCodigoTurma().trim().isEmpty())
//        {
//            t.setParameter("codigoTurma", criacaoTurma.getCodigoTurma() + "%");
//        }
//
//          if (criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNome() != null && criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
//          {
//               t.setParameter("nome", "%"+criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNome()+"%");
//          }
//          
//          if (criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
//          {
//               t.setParameter("nomeDoMeio", "%"+criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio()+"%");
//          }
//
//          if (criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
//          {
//               t.setParameter("sobreNome", "%"+criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getSobreNome()+"%");
//          }
//
//          if (criacaoTurma.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() != null )
//          {
//               t.setParameter("seccaoTrabalho",+criacaoTurma.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho());
//          }
//       
//
//          List<SupiCriacaoTurma> resultado = t.getResultList();
//
//          return resultado;
//    }
//    
//     
//     
//      public String constroiQuery (SupiCriacaoTurma criacaoTurma)
//     {
//         String query = "SELECT e FROM SupiCriacaoTurma e WHERE e.pkIdCriacaoTurma = e.pkIdCriacaoTurma";
//         
//         if (criacaoTurma.getCodigoTurma() != null && !criacaoTurma.getCodigoTurma().trim().isEmpty())
//        {
//            query += " AND e.codigoTurma LIKE :codigoTurma";
//        }
//         
//         if (criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
//          {
//               query += " AND e.fkIdFuncionario.fkIdPessoa.nome LIKE :nome ORDER BY e.nome";
//          }
//         if (criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio()!= null && !criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
//          {
//               query += " AND e.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio ORDER BY e.nomeDoMeio";
//          }
//         if (criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getSobreNome()!= null && !criacaoTurma.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
//          {
//               query += " AND e.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome ORDER BY e.sobreNome";
//          }
//         if (criacaoTurma.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() != null )
//          {
//               query += " AND e.fkIdSeccaoTrabalho.pkIdSeccaoTrabalho = :seccaoTrabalho";
//          }
//         
//          query += " ORDER BY e.fkIdFuncionario.fkIdPessoa.nome, e.fkIdFuncionario.fkIdPessoa.nomeDoMeio, e.fkIdFuncionario.fkIdPessoa.sobreNome";
//         
//         return query;
//     }
    
    
    
}
