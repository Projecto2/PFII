/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhCurso;
import entidade.RhEspecialidadeCurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhCursoFacade;
import sessao.RhEspecialidadeCursoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhCursoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhCursoFacade cursoFacade;
    @EJB
    private RhEspecialidadeCursoFacade especialidadeCursoFacade;

    /**
     *
     * Entidades
     */
    private RhCurso curso, cursoRemover;

    /**
     * Creates a new instance of CursoBean
     */
    public RhCursoBean ()
    {
    }

    public RhCurso getCurso ()
    {
        if (this.curso == null)
        {
            this.curso = new RhCurso();
        }

        return curso;
    }

    public void setCurso (RhCurso curso)
    {
        this.curso = curso;
    }

    public RhCurso getCursoRemover ()
    {
        return cursoRemover;
    }

    public void setCursoRemover (RhCurso cursoRemover)
    {
        this.cursoRemover = cursoRemover;
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            cursoFacade.create(curso);
            userTransaction.commit();
            Mensagem.sucessoMsg("Curso guardado com sucesso!");
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

        curso = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (curso.getPkIdCurso() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            cursoFacade.edit(curso);
            userTransaction.commit();
            Mensagem.sucessoMsg("Curso editado com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg("Ocorreu um erro e não foi possível editar o curso!");
                e.printStackTrace();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        curso = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            cursoFacade.remove(cursoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Curso removido com sucesso!");
            cursoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este curso está a ser utilizado, impossível remover !");
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

    public List<RhCurso> findAll ()
    {
        List<RhCurso> cursoList = new ArrayList<>();

        for (RhCurso c : cursoFacade.findAll())
        {
            RhEspecialidadeCurso esp = new RhEspecialidadeCurso();
            esp.setFkIdCurso(c);
            c.setRhEspecialidadeCursoList(especialidadeCursoFacade.findEspecialidadeCurso(esp));
            
            cursoList.add(c);
        }

        return cursoList;
    }

    public String limpar ()
    {
        curso = null;
        return "cursoRh?faces-redirect=true";
    }

}
