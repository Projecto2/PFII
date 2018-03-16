/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidSubcategorias;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidPerfisFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;
import util.amb.AmbCidHipoteseDiagnosticoAbstract;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidHipoteseDiagnosticoBean extends AmbCidHipoteseDiagnosticoAbstract
                                                implements Serializable
{
    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;
    
    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;

    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;

    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;

    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;

    // lista dos codigos CID (SubCategorias das doencas seleccionadas)


    /**
     * Creates a new instance of AmbCidHipoteseDiagnosticoBean
     */
    public AmbCidHipoteseDiagnosticoBean()
    {
    }

    public static AmbCidHipoteseDiagnosticoBean getInstanciaBean()
    {
        return (AmbCidHipoteseDiagnosticoBean) GeradorCodigo.getInstanciaBean("ambCidHipoteseDiagnosticoBean");
    }

    @Override
    public void init()
    {
        super.init();
        
    }
    
    // retorna a lista dos nomes das doencas
    @Override
    public List<String> getListaNomeDoencasSeleccionadas()
    {
        return super.getListaNomeDoencasSeleccionadas();
    }

    // retorna a lista das doencas
    @Override
    public List<AmbCidSubcategorias> getListaAmbCidSubcategoriasDasDoencasSeleccionadas()
    {
        return super.getListaAmbCidSubcategoriasDasDoencasSeleccionadas();
    }

    // retorna a lista dos codigos (AmbCidSubcategorias.pkIdSubcategorias) das doencas
    @Override
    public List<String> getListaPkIdSubcategoriasDasDoencasSeleccionadas()
    {
        return super.getListaPkIdSubcategoriasDasDoencasSeleccionadas();
    }

 
    // metodos get e set

    @Override
    public AmbCidSubcategoriasFacade getAmbCidSubcategoriasFacade()
    {
        return ambCidSubcategoriasFacade;
    }

    @Override
    public AmbCidCategoriasFacade getAmbCidCategoriasFacade()
    {
        return ambCidCategoriasFacade;
    }

    @Override
    public AmbCidAgrupamentosFacade getAmbCidAgrupamentosFacade()
    {
        return ambCidAgrupamentosFacade;
    }

    @Override
    public AmbCidCapitulosFacade getAmbCidCapitulosFacade()
    {
        return ambCidCapitulosFacade;
    }

    @Override
    public RhProfissaoFacade getRhProfissaoFacade()
    {
        return rhProfissaoFacade;
    }

    @Override
    public GrlEspecialidadeFacade getGrlEspecialidadeFacade()
    {
        return grlEspecialidadeFacade;
    }

    @Override
    public AmbCidPerfisFacade getAmbCidPerfisFacade()
    {
        return ambCidPerfisFacade;
    }

    @Override
    public AmbCidConfiguracoesFacade getAmbCidConfiguracoesFacade()
    {
        return ambCidConfiguracoesFacade;
    }

    @Override
    public AmbCidDoencasPrioridadesFacade getAmbCidDoencasPrioridadesFacade()
    {
        return ambCidDoencasPrioridadesFacade;
    }

    public List<String> getListaDoencasDiagnosticadas()
    {
        return getListaDoencasDiagnosticadas();
    }

    public void setListaDoencasDiagnosticadas(List<String> listaDoencasDiagnosticadas)
    {
        this.setListaDoencasDiagnosticadas(listaDoencasDiagnosticadas);
    }

}
