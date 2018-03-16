/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegFuncionalidade;
import entidade.SegPerfil;
import entidade.SegPerfilAssociado;
import entidade.SegPerfilAssociadoPK;
import entidade.SegPerfilHasFuncionalidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import sessao.SegFuncionalidadeFacade;
import sessao.SegPerfilAssociadoFacade;
import sessao.SegPerfilFacade;
import sessao.SegPerfilHasFuncionalidadeFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author adalberto
 */
@ManagedBean
@SessionScoped
public class SegPerfilListarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;
    @EJB
    private SegPerfilFacade perfilFacade;
    @EJB
    private SegFuncionalidadeFacade funcionalidadeFacade;

    @EJB
    private SegPerfilHasFuncionalidadeFacade perfilFuncionalidadeFacade;
    @EJB
    private SegPerfilAssociadoFacade perfilNovoFacade;

    private SegPerfil perfilPesquisa, perfilEditar;
    private List<SegPerfil> perfisPesquisados;

    private SegPerfil perfilSelecionado;
    private String descricao;
    private String nome;

    // para picklist
    private boolean resposta = false;
    private boolean resposta1 = false;
    private boolean resposta2 = false;

    //Funcionalidade
    private DualListModel<SegFuncionalidade> recursos;
    private List<SegFuncionalidade> recursosOrigem;
    private List<SegFuncionalidade> recursosDestino;

    //Perfil
    private DualListModel<SegPerfil> perfis;
    private List<SegPerfil> perfisOrigem;
    private List<SegPerfil> perfisDestino;

    private ArrayList<SegFuncionalidade> removedModulos = new ArrayList<SegFuncionalidade>();
    private ArrayList<SegPerfil> removedPerfis = new ArrayList<SegPerfil>();

    /**
     * Creates a new instance of SegPerfilFacade
     */
    public SegPerfilListarBean()
    {
    }

    public static SegPerfilListarBean getInstanciaBean()
    {
        return (SegPerfilListarBean) GeradorCodigo.getInstanciaBean("segPerfilListarBean");
    }

    @PostConstruct
    public void init()
    {
        //Funcionalidade
        recursosOrigem = listaFuncionalidades();
        recursosDestino = new ArrayList<SegFuncionalidade>();
        recursos = new DualListModel<SegFuncionalidade>(recursosOrigem, recursosDestino);

        //Perfil
        perfisOrigem = listaPerfis();
        perfisDestino = new ArrayList<SegPerfil>();
        perfis = new DualListModel<SegPerfil>(perfisOrigem, perfisDestino);
    }

    public void preRenderView()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public void pesquisarPerfils()
    {
        perfisPesquisados = perfilFacade.findPerfil(perfilPesquisa);
        if (perfisPesquisados.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + perfisPesquisados.size() + " registo(s) retornado(s).");
        }
    }

    
    //Criar um perfil com varias funcionalidades ou com Perfis existentes
    public void criarPeril()
    {
        try
        {
            //userTransaction.begin();

            SegPerfil perfil1 = new SegPerfil();
            perfil1.setDescricao(descricao);
            perfilFacade.create(perfil1);

            // perfilFacade.create(perfilEditar);
            //atribuir Modulos ao Perfil
            
            //Criar um perfil com varias funcionalidades
            System.out.println("resposta1: "+resposta1);
            System.out.println("resposta2: "+resposta2);
            if (resposta1 == true)
            {
                recursosDestino = recursos.getTarget();
                for (SegFuncionalidade recurso1 : recursosDestino)
                {

                    SegPerfilHasFuncionalidade recursosPerfil = new SegPerfilHasFuncionalidade();
                    recursosPerfil.setFkIdFuncionalidade(recurso1);
                    recursosPerfil.setFkIdPerfil(perfil1);
//                    SegPerfilHasFuncionalidadePK recursosPerfilPK = new SegPerfilHasFuncionalidadePK(perfil1.getPkIdPerfil(), recurso1.getPkIdFuncionalidade());
//                    recursosPerfil.setSegPerfilHasFuncionalidadePK(recursosPerfilPK);
                    recursosPerfil.setStatus(true);

                    perfilFuncionalidadeFacade.create(recursosPerfil);

                }

            }
            
            //Criar um perfil com Perfis existentes
          
            if (resposta2 == true)
            {
                System.out.println("estou aqui 2");
                perfisDestino = perfis.getTarget();
                for (SegPerfil perfis1 : perfisDestino)
                {
                    SegPerfilAssociado perfilAssociado = new SegPerfilAssociado();
                    perfilAssociado.setSegPerfil(perfis1);
                    perfilAssociado.setSegPerfil1(perfil1);
                    SegPerfilAssociadoPK perfilAssociadoPK = new SegPerfilAssociadoPK(perfil1.getPkIdPerfil(), perfis1.getPkIdPerfil());
                    perfilAssociado.setSegPerfilAssociadoPK(perfilAssociadoPK);
                    //perfilAssociado.setStatus(true);

                    perfilNovoFacade.create(perfilAssociado);

                }
            }
            recursos.getTarget().clear();

            //userTransaction.commit();
            perfisPesquisados = perfilFacade.findPerfil(perfilPesquisa);
            Mensagem.sucessoMsg("Perfil cadastrado com sucesso.");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        setPerfilEditar(null);
    }

    public void editar()
    {
        try
        {
            //userTransaction.begin();
            perfilFacade.edit(perfilEditar);

            //remover as funcionalidades associada ao perfil selecionado
            if (!removedModulos.isEmpty())
            {
                for (SegFuncionalidade recursos : removedModulos)
                {
                    SegPerfilHasFuncionalidade perfilModulos = new SegPerfilHasFuncionalidade();
                    perfilModulos.setFkIdFuncionalidade(recursos);
                    perfilModulos.setFkIdPerfil(new SegPerfil(perfilEditar.getPkIdPerfil()));
//                    perfilModulos.setSegPerfilHasFuncionalidadePK(new SegPerfilHasFuncionalidadePK(perfilEditar.getPkIdPerfil(), recursos.getPkIdFuncionalidade()));

                    //remover as funcionalidades associada ao perfil selecionado
                    perfilFuncionalidadeFacade.remove(perfilModulos);
                    //perfilPermissasFacade.removerPermissoesPorModuloPerfil(perfilEditar.getPksegperf(), recursos.getPksegrecur());

                }
                removedModulos.clear();
            }

            // Adicionar nova Funcionalidade ao perfil selecionado
            ArrayList<SegFuncionalidade> listaRecursos = (ArrayList<SegFuncionalidade>) recursos.getTarget();
            for (SegFuncionalidade recursos : listaRecursos)
            {
                SegPerfilHasFuncionalidade perfilModulos = new SegPerfilHasFuncionalidade();
                perfilModulos.setFkIdFuncionalidade(recursos);
                perfilModulos.setFkIdPerfil(new SegPerfil(perfilEditar.getPkIdPerfil()));
//                perfilModulos.setSegPerfilHasFuncionalidadePK(new SegPerfilHasFuncionalidadePK(perfilEditar.getPkIdPerfil(), recursos.getPkIdFuncionalidade()));
//                perfilModulos.setStatus(true);
                perfilFuncionalidadeFacade.edit(perfilModulos);

            }

            //userTransaction.commit();
            perfisPesquisados = perfilFacade.findPerfil(perfilPesquisa);
            Mensagem.sucessoMsg("Perfil editado com sucesso.");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        setPerfilEditar(null);
    }

    public void eliminar()
    {
        try
        {
            userTransaction.begin();
            perfilFacade.remove(perfilEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Perfil eliminado com sucesso.");
            perfisPesquisados = perfilFacade.findPerfil(perfilPesquisa);
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.warnMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        setPerfilEditar(null);

    }

    public String limparCampos()
    {
        
        perfisPesquisados = null;
        perfilPesquisa = null;
        
        return "perfilListarSeg.xhtml?faces-redirect=true";
        
    }

    public List<SegFuncionalidade> listaFuncionalidades()
    {
        return funcionalidadeFacade.funcionalidadesPai();
    }

    public List<SegPerfil> listaPerfis()
    {
        return perfilFacade.findAll();
    }

    public List<SegFuncionalidade> listaFuncionalidadesByPerfil()
    {

        if (perfilEditar != null)
        {
            //System.out.println("Id do perfil:" + perfilSelecionado.getPksegperf());
            return perfilFuncionalidadeFacade.getFuncionalidadesByPerfil(perfilEditar.getPkIdPerfil());
        }
        return new ArrayList<SegFuncionalidade>();
    }

    /*
     picklit
     */
    public void updatepicklist()
    {
        recursosOrigem = listaFuncionalidades();
        recursosDestino = listaFuncionalidadesByPerfil();
        removerepeatedvalues(recursosOrigem, recursosDestino);
        recursos = new DualListModel<SegFuncionalidade>(recursosOrigem, recursosDestino);
    }

    public void onTransfer(TransferEvent event)
    {
        if (event.isRemove())
        {
            for (Object modulos : event.getItems())
            {
                SegFuncionalidade modulos1 = (SegFuncionalidade) modulos;
                removedModulos.add(modulos1);
            }
        }
    }

    public void removerepeatedvalues(List<SegFuncionalidade> eliminarItens, List<SegFuncionalidade> comparar)
    {
        for (SegFuncionalidade p : comparar)
        {
            int val = existeNaLista(eliminarItens, p);
            if (val != -1)
            {
                eliminarItens.remove(val);
            }
        }
    }

    public int existeNaLista(List<SegFuncionalidade> lista, SegFuncionalidade recursos)
    {
        for (int i = 0; i < lista.size(); i++)
        {
            if (recursos.getNome().equals(lista.get(i).getNome()))
            {
                return i;
            }
        }
        return -1;
    }

    public boolean isResposta1()
    {
        return resposta1;
    }

    public void setResposta1(boolean resposta1)
    {
        this.resposta1 = resposta1;
    }

    public boolean isResposta2()
    {
        return resposta2;
    }

    public void setResposta2(boolean resposta2)
    {
        this.resposta2 = resposta2;
    }

    public boolean isResposta()
    {
        return resposta;
    }

    public void setResposta(boolean resposta)
    {
        this.resposta = resposta;
    }

    public DualListModel<SegFuncionalidade> getRecursos()
    {
        return recursos;
    }

    public void setRecursos(DualListModel<SegFuncionalidade> recursos)
    {
        this.recursos = recursos;
    }

    public List<SegFuncionalidade> getRecursosOrigem()
    {
        return recursosOrigem;
    }

    public void setRecursosOrigem(List<SegFuncionalidade> recursosOrigem)
    {
        this.recursosOrigem = recursosOrigem;
    }

    public List<SegPerfil> getPerfisOrigem()
    {
        return perfisOrigem;
    }

    public void setPerfisOrigem(List<SegPerfil> perfisOrigem)
    {
        this.perfisOrigem = perfisOrigem;
    }

    public List<SegFuncionalidade> getRecursosDestino()
    {
        return recursosDestino;
    }

    public void setRecursosDestino(List<SegFuncionalidade> recursosDestino)
    {
        this.recursosDestino = recursosDestino;
    }

    /* listar todos o recusos ou modulos disponiveis no sistema
     public List<SegFuncionalidade> listarSegfuncionalidade() {
     return funcionalidadeFacade.findAll();
     }*/
    public SegPerfil getPerfilSelecionado()
    {
        return perfilSelecionado;
    }

    public void setPerfilSelecionado(SegPerfil perfilSelecionado)
    {
        this.perfilSelecionado = perfilSelecionado;
    }

    public ArrayList<SegFuncionalidade> getRemovedModulos()
    {
        return removedModulos;
    }

    public void setRemovedModulos(ArrayList<SegFuncionalidade> removedModulos)
    {
        this.removedModulos = removedModulos;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
    
     public DualListModel<SegPerfil> getPerfis()
    {
        return perfis;
    }

    public void setPerfis(DualListModel<SegPerfil> perfis)
    {
        this.perfis = perfis;
    }

    /**
     * @return the perfilPesquisa
     */
    public SegPerfil getPerfilPesquisa()
    {
        if (perfilPesquisa == null)
        {
            perfilPesquisa = SegPerfilBean.getInstancia();
        }
        return perfilPesquisa;
    }

    /**
     * @param perfilPesquisa the perfilPesquisa to set
     */
    public void setPerfilPesquisa(SegPerfil perfilPesquisa)
    {
        this.perfilPesquisa = perfilPesquisa;
    }

    /**
     * @return the perfilEditar
     */
    public SegPerfil getPerfilEditar()
    {
        if (perfilEditar == null)
        {
            perfilEditar = SegPerfilBean.getInstancia();
        }

        return perfilEditar;
    }

    /**
     * @param perfilEditar the perfilEditar to set
     */
    public void setPerfilEditar(SegPerfil perfilEditar)
    {
        this.perfilEditar = perfilEditar;
    }

    /**
     * @return the perfisPesquisados
     */
    public List<SegPerfil> getPerfilsPesquisados()
    {
        if (perfisPesquisados == null)
        {
            perfisPesquisados = new ArrayList<>();
        }
        return perfisPesquisados;
    }

    /**
     * @param perfisPesquisados the perfisPesquisados to set
     */
    public void setPerfilsPesquisados(List<SegPerfil> perfisPesquisados)
    {
        this.perfisPesquisados = perfisPesquisados;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    //Elementos para o Perfil
    public void updatepicklist2()
    {
        perfisOrigem = listaPerfis();
        perfisDestino = listaPerfisByPerfil();
        removerepeatedvalue2(perfisOrigem, perfisDestino);
        perfis = new DualListModel<SegPerfil>(perfisOrigem, perfisDestino);
    }

    public void onTransfer2(TransferEvent event)
    {
        if (event.isRemove())
        {
            for (Object modulos : event.getItems())
            {
                SegPerfil perfilf1 = (SegPerfil) modulos;
                removedPerfis.add(perfilf1);
            }
        }
    }

    public void removerepeatedvalue2(List<SegPerfil> eliminarItens, List<SegPerfil> comparar)
    {
        for (SegPerfil p : comparar)
        {
            int val = existeNaLista2(eliminarItens, p);
            if (val != -1)
            {
                eliminarItens.remove(val);
            }
        }
    }

    public int existeNaLista2(List<SegPerfil> lista, SegPerfil recursos)
    {
        for (int i = 0; i < lista.size(); i++)
        {
            if (recursos.getDescricao().equals(lista.get(i).getDescricao()))
            {
                return i;
            }
        }
        return -1;
    }
    
    public List<SegPerfil> listaPerfisByPerfil()
    {

        if (perfilEditar != null)
        {
            //System.out.println("Id do perfil:" + perfilSelecionado.getPksegperf());
            return perfilNovoFacade.getPerfilByPerfil(perfilEditar.getPkIdPerfil());
        }
        return new ArrayList<SegPerfil>();
    }
    
     public ArrayList<SegPerfil> getRemovedPerfis()
    {
        return removedPerfis;
    }

    public void setRemovedPerfis(ArrayList<SegPerfil> removedPerfis)
    {
        this.removedPerfis = removedPerfis;
    }

    
}
