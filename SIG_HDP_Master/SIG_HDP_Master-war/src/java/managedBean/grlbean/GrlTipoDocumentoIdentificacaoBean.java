/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlTipoDocumentoIdentificacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sessao.GrlTipoDocumentoIdentificacaoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@RequestScoped
public class GrlTipoDocumentoIdentificacaoBean implements Serializable
{

    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;
    
    
    

    /**
     * Creates a new instance of TipoDocumentoIdentificacaoBean
     */
    public GrlTipoDocumentoIdentificacaoBean()
    {
       grlTipoDocumentoIdentificacaoFacade = new GrlTipoDocumentoIdentificacaoFacade();
    }
    
    public List<GrlTipoDocumentoIdentificacao> getTipoDocumentoIdentificacao()
    {
        return grlTipoDocumentoIdentificacaoFacade.findAll();
    }

    
    public int getTotalTipoDocumentoIdentificacao(){
        return grlTipoDocumentoIdentificacaoFacade.count();
    }
    
}
