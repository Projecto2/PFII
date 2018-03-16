/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiFormador;
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
public class SupiFormadorFacade extends AbstractFacade<SupiFormador> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiFormadorFacade() {
        super(SupiFormador.class);
    }
    
    public List<SupiFormador> findFormador (SupiFormador formador)
    {
        String query = constroiQuery(formador);

        TypedQuery<SupiFormador> t = em.createQuery(query, SupiFormador.class);
        
         if (formador.getFkIdAreaFormacao().getPkIdAreaFormacao() != null)
        {
            t.setParameter("areaFormacao", formador.getFkIdAreaFormacao().getPkIdAreaFormacao());
        }
        
         if (formador.getFkIdTipoFormador().getDescricao() != null && !formador.getFkIdTipoFormador().getDescricao().trim().isEmpty())
        {
            t.setParameter("descricao", formador.getFkIdTipoFormador().getPkIdTipoFormador());
        }

        if (formador.getFkIdFuncionario().getFkIdEstadoFuncionario().getPkIdEstadoFuncionario() != null)
        {
            t.setParameter("estadoFuncionario", formador.getFkIdFuncionario().getFkIdEstadoFuncionario().getPkIdEstadoFuncionario());
        }

        if (formador.getFkIdTipoFormador().getPkIdTipoFormador() != null)
        {
            t.setParameter("tipoFormador", formador.getFkIdTipoFormador().getPkIdTipoFormador());
        }
        
        if (formador.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !formador.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            t.setParameter("nome", formador.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !formador.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            t.setParameter("nomeDoMeio", formador.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !formador.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            t.setParameter("sobreNome", formador.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            t.setParameter("sexo", formador.getFkIdFuncionario().getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        List<SupiFormador> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (SupiFormador formador)
    {
        String query = "SELECT f FROM SupiFormador f WHERE f.pkIdFormador = f.pkIdFormador";
        
        if (formador.getFkIdAreaFormacao().getPkIdAreaFormacao() != null)
        {
            query += " AND f.fkIdAreaFormacao.pkIdAreaFormacao = :areaFormacao";
        }
        
        if (formador.getFkIdTipoFormador().getPkIdTipoFormador() != null)
        {
            query += " AND f.fkIdTipoFormador.pkIdTipoFormador = :tipoFormador";
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !formador.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND f.fkIdFuncionario.fkIdPessoa.nome LIKE :nome";
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !formador.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND f.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !formador.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND f.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (formador.getFkIdFuncionario().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND f.fkIdFuncionario.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        query += " ORDER BY f.fkIdFuncionario.fkIdPessoa.nome, f.fkIdFuncionario.fkIdPessoa.nomeDoMeio, f.fkIdFuncionario.fkIdPessoa.sobreNome";

        return query;
    }
    
}
