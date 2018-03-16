/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegFuncionalidade;
import entidade.SegPerfil;
import entidade.SegPerfilHasFuncionalidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import sessao.SegFuncionalidadeFacade;
import sessao.SegPerfilFacade;
import sessao.SegPerfilHasFuncionalidadeFacade;

/**
 *
 * @author adalberto
 */
@ManagedBean
@SessionScoped
public class segPermissoesListarBean implements Serializable
{

    @EJB
    private SegPerfilHasFuncionalidadeFacade perfilPermissoesFacade;
    @EJB
    private SegPerfilFacade perfilFacade;
    // @EJB
    // private SegPerfilHasModuloFacade moduloPerfilFacade;
    @EJB
    private SegFuncionalidadeFacade permissaoFacade;

    private SegPerfilHasFuncionalidade perfilPermissaoPesquisa, permissaoEditar, localNovo;
    private List<SegPerfilHasFuncionalidade> PerfilPermissaoPesquisados;
    private ArrayList<SegFuncionalidade> retirarPermissas = new ArrayList<SegFuncionalidade>();

    private DualListModel<SegFuncionalidade> permissas;
    private List<SegFuncionalidade> modulosPermissasOrigem;
    private List<SegFuncionalidade> modulosPermissasDestino;

    private Integer idPerfil;
    private Integer idModuloPerfil;

    /**
     * Creates a new instance of segPermissoesListarBean
     */
    public segPermissoesListarBean()
    {
    }

    @PostConstruct
    public void init()
    {
        modulosPermissasOrigem = listaPermissosByModulos();
        modulosPermissasDestino = listaPermissasByRecursosPerfil();
        permissas = new DualListModel<SegFuncionalidade>(modulosPermissasOrigem, modulosPermissasDestino);
    }

    public Integer getIdModuloPerfil()
    {
        return idModuloPerfil;
    }

    public void setIdModuloPerfil(Integer idModuloPerfil)
    {
        this.idModuloPerfil = idModuloPerfil;
    }

    public Integer getIdPerfil()
    {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil)
    {
        this.idPerfil = idPerfil;
    }

    public List<SegPerfil> getListarPerfis()
    {
        return perfilFacade.findAll();
    }

    public ArrayList<SegFuncionalidade> getRetirarPermissas()
    {
        return retirarPermissas;
    }

    public void setRetirarPermissas(ArrayList<SegFuncionalidade> retirarPermissas)
    {
        this.retirarPermissas = retirarPermissas;
    }

    public DualListModel<SegFuncionalidade> getPermissas()
    {
        return permissas;
    }

    public void setPermissas(DualListModel<SegFuncionalidade> permissas)
    {
        this.permissas = permissas;
    }

    /*atribuir permissoes a um determinado perfil*/
    public void atribuirPermissoes()
    {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!retirarPermissas.isEmpty())
        {
            for (SegFuncionalidade perm : retirarPermissas)
            {

                SegPerfilHasFuncionalidade perfilPermissoes = new SegPerfilHasFuncionalidade();
                perfilPermissoes.setFkIdPerfil(new SegPerfil(idPerfil));
                System.out.println("Perfil 1N." + perfilPermissoes.getFkIdPerfil().getPkIdPerfil());

                perfilPermissoes.setFkIdFuncionalidade(new SegFuncionalidade(perm.getPkIdFuncionalidade()));

//                SegPerfilHasFuncionalidadePK perfilPermissoesPK = new SegPerfilHasFuncionalidadePK(idPerfil, perm.getPkIdFuncionalidade());
//                perfilPermissoes.setSegPerfilHasFuncionalidadePK(perfilPermissoesPK);
                perfilPermissoes.setStatus(true);

                perfilPermissoesFacade.remove(perfilPermissoes);

            }
            retirarPermissas.clear();
        }

        modulosPermissasDestino = permissas.getTarget();
        for (SegFuncionalidade perm : modulosPermissasDestino)
        {
            SegPerfilHasFuncionalidade perfilPermissoes = new SegPerfilHasFuncionalidade();
            perfilPermissoes.setFkIdPerfil(new SegPerfil(idPerfil));

            System.out.println("Perfil 2N." + perfilPermissoes.getFkIdPerfil().getPkIdPerfil());

            perfilPermissoes.setFkIdFuncionalidade(new SegFuncionalidade(perm.getPkIdFuncionalidade()));
//            SegPerfilHasFuncionalidadePK perfilPermissoesPK = new SegPerfilHasFuncionalidadePK(idPerfil, perm.getPkIdFuncionalidade());
//            perfilPermissoes.setSegPerfilHasFuncionalidadePK(perfilPermissoesPK);

            perfilPermissoesFacade.edit(perfilPermissoes);
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Permissões atribuidas com sucesso!", ""));
    }

    /*listar os recurso por perfil*/
    public List<SegFuncionalidade> getListarModulosByPerfil()
    {

        //System.out.println("Perfil: " + idPerfil);
        if (idPerfil != null)
        {
            return perfilPermissoesFacade.getFuncionalidadesByPerfil(idPerfil);
        }
        return new ArrayList<SegFuncionalidade>();
    }

    public SegPerfilHasFuncionalidade getInstanciaPerfilPermissao()
    {
        SegPerfilHasFuncionalidade item = new SegPerfilHasFuncionalidade();
        item.setFkIdPerfil(new SegPerfil());
        item.setFkIdFuncionalidade(new SegFuncionalidade());
        return item;
    }

    public List<SegFuncionalidade> listaPermissoesPerfil()
    {
        if (idModuloPerfil != null && idModuloPerfil != 0 && idPerfil != null)
        {
            return perfilPermissoesFacade.getPermissoesByRecursosPerfil(idPerfil, idModuloPerfil);
        }
        else
        {
            return new ArrayList<SegFuncionalidade>();
        }
    }

    public List<SegFuncionalidade> listaPermissasByRecursosPerfil()
    {
        if (idModuloPerfil != null && idPerfil != null)
        {
            return perfilPermissoesFacade.getPermissoesByRecursosPerfil(idPerfil, idModuloPerfil);
        }
        return new ArrayList<SegFuncionalidade>();
    }

    public SegPerfilHasFuncionalidade getPerfilPermissaoPesquisa()
    {
        if (perfilPermissaoPesquisa == null)
        {
            perfilPermissaoPesquisa = getInstanciaPerfilPermissao();
        }
        return perfilPermissaoPesquisa;
    }

    /*listar as permissas ou funcionalidades dos recursos*/
    public List<SegFuncionalidade> listaPermissosByModulos()
    {
        if (idModuloPerfil != null)
        {
            return permissaoFacade.getPermissasByRecursos(idModuloPerfil);
        }
        return new ArrayList<SegFuncionalidade>();
    }

    /*manipular a picklit*/
    public void updatepicklist()
    {
        modulosPermissasOrigem = listaPermissosByModulos();
        modulosPermissasDestino = listaPermissasByRecursosPerfil();
        removerepeatedvalues(modulosPermissasOrigem, modulosPermissasDestino);
        permissas = new DualListModel<SegFuncionalidade>(modulosPermissasOrigem, modulosPermissasDestino);
    }

    public void removerepeatedvalues(List<SegFuncionalidade> eliminarexis, List<SegFuncionalidade> comparar)
    {
        for (SegFuncionalidade p : comparar)
        {
            int valor = existeNaLista(eliminarexis, p);
            if (valor != -1)
            {
                eliminarexis.remove(valor);
            }
        }
    }

    public int existeNaLista(List<SegFuncionalidade> lista, SegFuncionalidade perm)
    {
        for (int i = 0; i < lista.size(); i++)
        {
            if (perm.getNome().equals(lista.get(i).getNome()))
            {
                return i;
            }
        }
        return -1;
    }

    public void onTransfer(TransferEvent event)
    {
        if (event.isRemove())
        {
            for (Object obj : event.getItems())
            {
                SegFuncionalidade obj1 = (SegFuncionalidade) obj;
                retirarPermissas.add(obj1);
            }

        }
    }

    public void actualizar()
    {
        //idModuloPerfil = null;
        //idPerfil = null;
        //return "permisoesListarSeg.xhtml?faces-redirect=true";
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
  

    }

}
