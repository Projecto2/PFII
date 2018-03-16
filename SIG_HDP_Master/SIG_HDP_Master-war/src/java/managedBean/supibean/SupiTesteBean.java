/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlProvincia;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.GrlMunicipioFacade;
import sessao.GrlPaisFacade;
import sessao.GrlProvinciaFacade;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiTesteBean {
    @EJB
    private GrlProvinciaFacade grlProvinciaFacade;
    @EJB
    private GrlMunicipioFacade grlMunicipioFacade;
    @EJB
    private GrlPaisFacade grlPaisFacade;
    
    
    GrlPais pais;   
    GrlProvincia prov;
    GrlMunicipio muni;
    

    /**
     * Creates a new instance of TesteBean
     */
    public SupiTesteBean() {
    }

    public GrlMunicipio getMuni() {
        return muni;
    }

    public void setMuni(GrlMunicipio muni) {
        this.muni = muni;
    }
    
    public void gravar(){
        pais =grlPaisFacade.find(2); 
        prov =grlProvinciaFacade.find(2);
        prov.setFkIdPais(pais);
        muni =grlMunicipioFacade.find(2);
        muni.setFkIdProvincia(prov);
        grlMunicipioFacade.create(muni);
    }
      public List<GrlMunicipio> listar(){
      return grlMunicipioFacade.findAll();
    }
    
}
