/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhCurso;
import entidade.RhEspecialidadeCurso;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
import sessao.RhEspecialidadeCursoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhEspecialidadeCursoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhEspecialidadeCursoFacade especialidadeCursoFacade;

    /**
     *
     * Entidades
     */
    private RhEspecialidadeCurso especialidadeCurso, especialidadeCursoEditar, especialidadeCursoRemover;

    /**
     * Creates a new instance of EspecialidadeCursoBean
     */
    public RhEspecialidadeCursoBean ()
    {
    }

    public RhEspecialidadeCurso getInstanciaEspecialidadeCurso ()
    {
        RhEspecialidadeCurso cat = new RhEspecialidadeCurso();
        cat.setFkIdCurso(new RhCurso());

        return cat;
    }

    public RhEspecialidadeCurso getEspecialidadeCurso ()
    {
        if (this.especialidadeCurso == null)
        {
            especialidadeCurso = getInstanciaEspecialidadeCurso();
        }

        return especialidadeCurso;
    }

    public void setEspecialidadeCurso (RhEspecialidadeCurso especialidadeCurso)
    {
        this.especialidadeCurso = especialidadeCurso;
    }

    public RhEspecialidadeCurso getEspecialidadeCursoEditar ()
    {
        if (especialidadeCursoEditar == null)
        {
            especialidadeCursoEditar = getInstanciaEspecialidadeCurso();
        }
        return especialidadeCursoEditar;
    }

    public void setEspecialidadeCursoEditar (RhEspecialidadeCurso especialidadeCursoEditar)
    {
        this.especialidadeCursoEditar = especialidadeCursoEditar;
    }

    public RhEspecialidadeCurso getEspecialidadeCursoRemover ()
    {
        return especialidadeCursoRemover;
    }

    public void setEspecialidadeCursoRemover (RhEspecialidadeCurso especialidadeCursoRemover)
    {
        this.especialidadeCursoRemover = especialidadeCursoRemover;
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            especialidadeCursoFacade.create(especialidadeCurso);
            userTransaction.commit();
            Mensagem.sucessoMsg("Especialidade de curso guardada com sucesso!");
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

        especialidadeCurso = null;

        return null;
    }
    
    public String createPorCurso (RhCurso curso)
    {
        try
        {
            userTransaction.begin();
            
            especialidadeCurso.setFkIdCurso(curso);
            especialidadeCursoFacade.create(especialidadeCurso);
            userTransaction.commit();
            
            curso.setRhEspecialidadeCursoList(especialidadeCursoFacade.pesquisaPorCurso(curso.getPkIdCurso()));
            especialidadeCurso = null;
            
            Mensagem.sucessoMsg("Especialidade de curso guardada com sucesso!");
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

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (especialidadeCurso.getPkIdEspecialidadeCurso() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            int curs = especialidadeCursoEditar.getFkIdCurso().getPkIdCurso();
            especialidadeCursoEditar.setFkIdCurso(new RhCurso(curs));
            
            especialidadeCursoFacade.edit(especialidadeCursoEditar);
            userTransaction.commit();

            Mensagem.sucessoMsg("Especialidade de curso editada com sucesso! ");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.getMessage());
                
                RequestContext dialogCurso = RequestContext.getCurrentInstance();
                dialogCurso.execute("PF('dialogCurso').hide()");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        especialidadeCurso = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            especialidadeCursoFacade.remove(especialidadeCursoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Especialidade de curso removida com sucesso!");
            especialidadeCursoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Esta especialidade está a ser utilizada, impossível remover !");
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public String removeAndLoad (RhEspecialidadeCurso espCurso, RhCurso curso)
    {
        try
        {
            userTransaction.begin();

            especialidadeCursoFacade.remove(espCurso);

            userTransaction.commit();
            
            curso.setRhEspecialidadeCursoList(especialidadeCursoFacade.pesquisaPorCurso(curso.getPkIdCurso()));

            Mensagem.sucessoMsg("Especialidade de curso removida com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Esta especialidade está a ser utilizada, impossível remover !");
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public String limpar ()
    {
        especialidadeCurso = null;
        return "especialidadeCursoRh?faces-redirect=true";
    }
}
