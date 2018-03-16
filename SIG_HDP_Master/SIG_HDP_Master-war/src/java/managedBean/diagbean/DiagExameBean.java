/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagCategoriaExame;
import entidade.DiagExame;
import entidade.DiagSubcategoriaExame;
import entidade.DiagTipoResultadoExame;
import entidade.FarmUnidadeMedida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.farmbean.tabelasAuxiliares.FarmUnidadeDeMedidaBean;
import sessao.DiagExameFacade;
import sessao.DiagCategoriaExameFacade;
import sessao.DiagSubcategoriaExameFacade;
import sessao.DiagTipoResultadoExameFacade;
import sessao.FarmUnidadeMedidaFacade;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagExameBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagTipoResultadoExameFacade diagTipoResultadoExameFacade;
    @EJB
    private FarmUnidadeMedidaFacade farmUnidadeMedidaFacade;
    @EJB
    private DiagSubcategoriaExameFacade diagSubcategoriaExameFacade;
    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;
    @EJB
    private DiagExameFacade diagExameFacade;

    private DiagExame diagExame, diagExameEditar, diagExameRemover, diagExamePesquisar;
    private DiagCategoriaExame diagCategoriaExame, diagCategoriaExameEditar;
    private DiagSubcategoriaExame diagSubcategoriaExame, diagSubcategoriaExameEditar;
    private DiagTipoResultadoExame diagTipoResultadoExame, diagTipoResultadoExameEditar;
    private FarmUnidadeMedida farmUnidadeMedida, farmUnidadeMedidaEditar;

    private boolean pesquisar;

    private List<DiagSubcategoriaExame> listaSubcategoriasPorCategoriaPesquisar, listaSubcategoriasPorCategoriaRegistar, listaSubcategoriasPorCategoriaEditar;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    List<DiagExame> itens;
    
    public static DiagExameBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagExameBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagExameBean");
    }

    public static DiagExame getInstancia()
    {
        DiagExame diagExame = new DiagExame();
        diagExame.setFkIdCategoriaExame(DiagCategoriaExameBean.getInstancia());
        diagExame.getFkIdCategoriaExame().setPkIdCategoria(Defs.CATEGORIA_EXAME_LABORATORIO_DEFAULT_ID);
        diagExame.setFkIdSubcategoriaExame(DiagSubcategoriaExameBean.getInstancia());
        diagExame.setFkIdTipoResultado(DiagTipoResultadoExameBean.getInstancia());
        diagExame.setFkIdUnidadeMedida(FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade());
        diagExame.getFkIdUnidadeMedida().setPkIdUnidadeMedida(Defs.UNIDADE_EXAME_DEFAULT_ID);

        return diagExame;
    }

    public List<DiagExame> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagExame> itens)
    {
        this.itens = itens;
    }

    public DiagExame getDiagExame()
    {
        if (diagExame == null)
        {
            diagExame = getInstancia();
        }
        return diagExame;
    }

    public void setDiagExame(DiagExame diagExame)
    {
        this.diagExame = diagExame;
    }

    public DiagCategoriaExame getDiagCategoriaExame()
    {
        if (diagCategoriaExame == null)
        {
            diagCategoriaExame = DiagCategoriaExameBean.getInstancia();
        }
        return diagCategoriaExame;
    }

    public void setDiagCategoriaExame(DiagCategoriaExame tipoExame)
    {
        this.diagCategoriaExame = tipoExame;
    }

    public DiagSubcategoriaExame getDiagSubcategoriaExame()
    {
        if (diagSubcategoriaExame == null)
        {
            diagSubcategoriaExame = DiagSubcategoriaExameBean.getInstancia();
        }
        return diagSubcategoriaExame;
    }

    public void setDiagSubcategoriaExame(DiagSubcategoriaExame diagSubcategoriaExame)
    {
        this.diagSubcategoriaExame = diagSubcategoriaExame;
    }

    public DiagExame getDiagExamePesquisar()
    {
        if (diagExamePesquisar == null)
        {
            diagExamePesquisar = getInstancia();
        }
        return diagExamePesquisar;
    }

    public void setDiagExamePesquisar(DiagExame diagExamePesquisar)
    {
        this.diagExamePesquisar = diagExamePesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public FarmUnidadeMedida getFarmUnidadeMedida()
    {
        if (farmUnidadeMedida == null)
        {
            farmUnidadeMedida = FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade();
        }
        return farmUnidadeMedida;
    }

    public void setFarmUnidadeMedida(FarmUnidadeMedida farmUnidadeMedida)
    {
        this.farmUnidadeMedida = farmUnidadeMedida;
    }

    public DiagExame getDiagExameEditar()
    {
        if (diagExameEditar == null)
        {
            diagExameEditar = getInstancia();
        }
        return diagExameEditar;
    }

    public void setDiagExameEditar(DiagExame diagExameEditar)
    {
        this.diagExameEditar = diagExameEditar;
    }

    public DiagExame getDiagExameRemover()
    {
        if (diagExameRemover == null)
        {
            diagExameRemover = getInstancia();
        }
        return diagExameRemover;
    }

    public void setDiagExameRemover(DiagExame diagExameRemover)
    {
        this.diagExameRemover = diagExameRemover;
    }

    public DiagCategoriaExame getDiagCategoriaExameEditar()
    {
        if (diagCategoriaExameEditar == null)
        {
            diagCategoriaExameEditar = DiagCategoriaExameBean.getInstancia();
        }
        return diagCategoriaExameEditar;
    }

    public void setDiagCategoriaExameEditar(DiagCategoriaExame diagCategoriaExameEditar)
    {
        this.diagCategoriaExameEditar = diagCategoriaExameEditar;
    }

    public DiagSubcategoriaExame getDiagSubcategoriaExameEditar()
    {
        if (diagSubcategoriaExameEditar == null)
        {
            diagSubcategoriaExameEditar = DiagSubcategoriaExameBean.getInstancia();
        }
        return diagSubcategoriaExameEditar;
    }

    public void setDiagSubcategoriaExameEditar(DiagSubcategoriaExame diagSubcategoriaExameEditar)
    {
        this.diagSubcategoriaExameEditar = diagSubcategoriaExameEditar;
    }

    public DiagTipoResultadoExame getDiagTipoResultadoExame()
    {
        if (diagTipoResultadoExame == null)
        {
            diagTipoResultadoExame = DiagTipoResultadoExameBean.getInstancia();
        }
        return diagTipoResultadoExame;
    }

    public void setDiagTipoResultadoExame(DiagTipoResultadoExame diagTipoResultadoExame)
    {
        this.diagTipoResultadoExame = diagTipoResultadoExame;
    }

    public DiagTipoResultadoExame getDiagTipoResultadoExameEditar()
    {
        if (diagTipoResultadoExameEditar == null)
        {
            diagTipoResultadoExameEditar = DiagTipoResultadoExameBean.getInstancia();
        }
        return diagTipoResultadoExameEditar;
    }

    public void setDiagTipoResultadoExameEditar(DiagTipoResultadoExame diagTipoResultadoExameEditar)
    {
        this.diagTipoResultadoExameEditar = diagTipoResultadoExameEditar;
    }

    public FarmUnidadeMedida getFarmUnidadeMedidaEditar()
    {
        if (farmUnidadeMedidaEditar == null)
        {
            farmUnidadeMedidaEditar = FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade();
        }
        return farmUnidadeMedidaEditar;
    }

    public void setFarmUnidadeMedidaEditar(FarmUnidadeMedida farmUnidadeMedidaEditar)
    {
        this.farmUnidadeMedidaEditar = farmUnidadeMedidaEditar;
    }

    public boolean isTemMensagemPendente()
    {
        return temMensagemPendente;
    }

    public void setTemMensagemPendente(boolean temMensagemPendente)
    {
        this.temMensagemPendente = temMensagemPendente;
    }

    public String getMensagemPendente()
    {
        return mensagemPendente;
    }

    public void setMensagemPendente(String mensagemPendente)
    {
        this.mensagemPendente = mensagemPendente;
    }

    public String getTipoMensagemPendente()
    {
        return tipoMensagemPendente;
    }

    public void setTipoMensagemPendente(String tipoMensagemPendente)
    {
        this.tipoMensagemPendente = tipoMensagemPendente;
    }

    public List<DiagSubcategoriaExame> getListaSubcategoriasPorCategoriaPesquisar()
    {
        return listaSubcategoriasPorCategoriaPesquisar;
    }

    public void setListaSubcategoriasPorCategoriaPesquisar(List<DiagSubcategoriaExame> listaSubcategoriasPorCategoriaPesquisar)
    {
        this.listaSubcategoriasPorCategoriaPesquisar = listaSubcategoriasPorCategoriaPesquisar;
    }

    public List<DiagSubcategoriaExame> getListaSubcategoriasPorCategoriaRegistar()
    {
        return listaSubcategoriasPorCategoriaRegistar;
    }

    public void setListaSubcategoriasPorCategoriaRegistar(List<DiagSubcategoriaExame> listaSubcategoriasPorCategoriaRegistar)
    {
        this.listaSubcategoriasPorCategoriaRegistar = listaSubcategoriasPorCategoriaRegistar;
    }

    public List<DiagSubcategoriaExame> getListaSubcategoriasPorCategoriaEditar()
    {
        return listaSubcategoriasPorCategoriaEditar;
    }

    public void setListaSubcategoriasPorCategoriaEditar(List<DiagSubcategoriaExame> listaSubcategoriasPorCategoriaEditar)
    {
        this.listaSubcategoriasPorCategoriaEditar = listaSubcategoriasPorCategoriaEditar;
    }

    public List<FarmUnidadeMedida> findAllUnidadeMedidas()
    {
        return diagExameFacade.findAllUnidades();
    }

    public List<DiagCategoriaExame> findAllCategoriaExamesLaboratorio()
    {
        return diagExameFacade.findCategoriasLaboratorio();
    }

    public List<DiagSubcategoriaExame> findAllSubcategoriaExames()
    {
        return diagSubcategoriaExameFacade.findAll();
    }

    public List<DiagTipoResultadoExame> findAllTipoResultadoExames()
    {
        return diagTipoResultadoExameFacade.findAll();
    }

    public List<DiagExame> findAll()
    {
        return diagExameFacade.findAll();
    }

    public void carregarListaSubcategoriasPorCategoriaPesquisar()
    {
        listaSubcategoriasPorCategoriaPesquisar = diagExameFacade.findSubcategoriasByCategoria(diagExamePesquisar.getFkIdCategoriaExame());
    }

    public void carregarListaSubcategoriasPorCategoriaRegistar()
    {
        listaSubcategoriasPorCategoriaRegistar = diagExameFacade.findSubcategoriasByCategoria(diagCategoriaExame);
    }

    public void carregarListaSubcategoriasPorCategoriaEditar()
    {
        listaSubcategoriasPorCategoriaEditar = diagExameFacade.findSubcategoriasByCategoria(diagCategoriaExameEditar);
    }

    public void getMensagem()
    {
        if (tipoMensagemPendente == "Sucesso")
        {
            Mensagem.sucessoMsg(mensagemPendente);

            temMensagemPendente = false;
        } else
        {
            Mensagem.erroMsg(mensagemPendente);

            temMensagemPendente = false;
        }
    }

    public String create()
    {
        try
        {
            userTransaction.begin();

            if (diagSubcategoriaExame.getPkIdSubcategoriaExame() == null)
            {
                diagExame.setFkIdSubcategoriaExame(null);
            } else
            {
                diagExame.setFkIdSubcategoriaExame(diagSubcategoriaExame);
            }

            if (farmUnidadeMedida.getPkIdUnidadeMedida() == null)
            {
                diagExame.setFkIdUnidadeMedida(null);
            } else
            {
                diagExame.setFkIdUnidadeMedida(farmUnidadeMedida);
            }

            diagExame.setFkIdCategoriaExame(diagCategoriaExame);

            diagExame.setFkIdTipoResultado(diagTipoResultadoExame);

            diagExameFacade.create(diagExame);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Exame salvo com sucesso!";

            diagExame = DiagExameBean.getInstancia();
            diagCategoriaExame = DiagCategoriaExameBean.getInstancia();
            diagSubcategoriaExame = DiagSubcategoriaExameBean.getInstancia();
            farmUnidadeMedida = FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade();
            diagTipoResultadoExame = DiagTipoResultadoExameBean.getInstancia();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return "exames.xhtml?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagExameEditar.getPkIdExame() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            if (diagSubcategoriaExameEditar.getPkIdSubcategoriaExame() != null)
            {
                diagExameEditar.setFkIdSubcategoriaExame(diagSubcategoriaExameEditar);
            } else
            {
                diagExameEditar.setFkIdSubcategoriaExame(null);
            }

            if (farmUnidadeMedidaEditar.getPkIdUnidadeMedida() != null)
            {
                diagExameEditar.setFkIdUnidadeMedida(farmUnidadeMedidaEditar);
            } else
            {
                diagExameEditar.setFkIdUnidadeMedida(null);
            }

            diagExameEditar.setFkIdCategoriaExame(diagCategoriaExameEditar);

            diagExameEditar.setFkIdTipoResultado(diagTipoResultadoExameEditar);

            diagExameFacade.edit(diagExameEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Exame editado com sucesso!");

            diagExameEditar = DiagExameBean.getInstancia();
            diagCategoriaExameEditar = DiagCategoriaExameBean.getInstancia();
            diagSubcategoriaExameEditar = DiagSubcategoriaExameBean.getInstancia();
            farmUnidadeMedidaEditar = FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade();
            diagTipoResultadoExameEditar = DiagTipoResultadoExameBean.getInstancia();
        } catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }
    }

    public void remove()
    {
        try
        {
            userTransaction.begin();

            if (diagExameRemover.getPkIdExame() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagExameFacade.remove(diagExameRemover);
            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Exame removido com sucesso!";
        } catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();

            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }
    }

    public String limpar()
    {

        diagExame = DiagExameBean.getInstancia();
        diagCategoriaExame = DiagCategoriaExameBean.getInstancia();
        diagSubcategoriaExame = DiagSubcategoriaExameBean.getInstancia();
        farmUnidadeMedida = FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade();
        diagTipoResultadoExame = DiagTipoResultadoExameBean.getInstancia();

        return "exames.xhtml?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagExamePesquisar = DiagExameBean.getInstancia();
        itens = new ArrayList<>();

        return "exames.xhtml?faces-redirect=true";
    }

    public List<DiagExame> pesquisarExames()
    {
        itens = diagExameFacade.findPesquisa(diagExamePesquisar, 1);
        
        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }      

        return null;
    }
}
