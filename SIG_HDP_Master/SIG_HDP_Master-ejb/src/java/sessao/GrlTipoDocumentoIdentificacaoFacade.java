/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlTipoDocumentoIdentificacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlTipoDocumentoIdentificacaoFacade extends AbstractFacade<GrlTipoDocumentoIdentificacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlTipoDocumentoIdentificacaoFacade ()
    {
        super(GrlTipoDocumentoIdentificacao.class);
    }
    
    
    public boolean isTipoDocumentoIdentificacaoTabelaEmpty()
    {
        List<GrlTipoDocumentoIdentificacao> listTipoDocumentoIdentificacao = this.findAll();
        return (listTipoDocumentoIdentificacao == null || listTipoDocumentoIdentificacao.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdTipoDocumentoIdentificacao)
    {
        GrlTipoDocumentoIdentificacao reg = this.find(pkIdTipoDocumentoIdentificacao);
        return reg != null;
    }
    
}
