/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsEstadoAgendamento;
import entidade.GrlDocumentoIdentificacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlDocumentoIdentificacaoFacade extends AbstractFacade<GrlDocumentoIdentificacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlDocumentoIdentificacaoFacade ()
    {
        super(GrlDocumentoIdentificacao.class);
    }
    
    public List<GrlDocumentoIdentificacao> pesquisaPorPessoa (Integer idPessoa)
    {
        TypedQuery<GrlDocumentoIdentificacao> t;
        t = em.createQuery("SELECT d FROM GrlDocumentoIdentificacao d WHERE d.fkIdPessoa.pkIdPessoa = :idPessoa", GrlDocumentoIdentificacao.class).setParameter("idPessoa", idPessoa);

        List<GrlDocumentoIdentificacao> resultado = t.getResultList();

        return resultado;
    }
    
    public GrlDocumentoIdentificacao findDocumentoByNumero(String numeroDocumento)
    {
        Query query = em.createQuery("SELECT d FROM GrlDocumentoIdentificacao d WHERE d.numeroDocumento = :numeroDocumento");

        query.setParameter("numeroDocumento", numeroDocumento);
        
        List<GrlDocumentoIdentificacao> documentos = query.getResultList();
        if(documentos.isEmpty()) return null;
        return documentos.get(0);
    }
    

}
