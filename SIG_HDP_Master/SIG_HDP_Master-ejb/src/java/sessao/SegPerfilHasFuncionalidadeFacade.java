/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.SegFuncionalidade;
import entidade.SegPerfil;
import entidade.SegPerfilHasFuncionalidade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegPerfilHasFuncionalidadeFacade extends AbstractFacade<SegPerfilHasFuncionalidade>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;
    private Connection con;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SegPerfilHasFuncionalidadeFacade()
    {
        super(SegPerfilHasFuncionalidade.class);
    }

    /*
     * verificar funcionalidades ou recursos 
     * permitido de um determinado
     * perfil atribuido no determinado funcionario
     */
    public SegFuncionalidade existeFuncionalidadePerfil(Integer idPerfil, String recurso)
    {
        System.out.println("contaUtilizador Perfil: " + idPerfil);
        Query query;
        query = em.createQuery("SELECT rp.fkIdFuncionalidade FROM SegPerfilHasFuncionalidade rp WHERE rp.fkIdPerfil.pkIdPerfil = :id AND rp.fkIdFuncionalidade.nome = :funcionalidade");

        query.setParameter("id", idPerfil)
            .setParameter("funcionalidade", recurso);

        List<SegFuncionalidade> results = (List<SegFuncionalidade>) query.getResultList();
        System.out.println("contaUtilizador Acesso: " + results);
        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return results.get(0);
        }

    }

    /*permissao as funcionalidades dos submodulos consoante o perfil*/
    public boolean temAcessoPermissaoUtilizador(Integer idPerfil, String permissao)
    {
        Query query = em.createQuery("SELECT p.fkIdFuncionalidade FROM SegPerfilHasFuncionalidade p WHERE p.fkIdPerfil.pkIdPerfil = :idPerfil AND p.fkIdFuncionalidade.nome = :permissao");

        query.setParameter("idPerfil", idPerfil)
            .setParameter("permissao", permissao);

        List<SegPerfilHasFuncionalidade> results = (List<SegPerfilHasFuncionalidade>) query.getResultList();
        System.out.println("query: " + results);
        if (results.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @SuppressWarnings("Convert2Diamond")
    public List<SegFuncionalidade> getFuncionalidadesByPerfil(Integer idperfil)
    {
        Query query;
        query = em.createQuery("SELECT rp.fkIdFuncionalidade FROM SegPerfilHasFuncionalidade rp WHERE rp.fkIdPerfil.pkIdPerfil = :idperfil ");

        query.setParameter("idperfil", idperfil);

        List<SegFuncionalidade> results = (List<SegFuncionalidade>) query.getResultList();

        //System.out.println("query: " + results);
        if (results.isEmpty())
        {
            return new ArrayList<SegFuncionalidade>();
        }
        else
        {
            return results;
        }

    }

    @SuppressWarnings("Convert2Diamond")
    public List<SegFuncionalidade> getPermissoesByRecursosPerfil(Integer idPerfil, Integer idRecursos)
    {
        Query query;
        query = em.createQuery("SELECT p.fkIdFuncionalidade FROM SegPerfilHasFuncionalidade p WHERE p.fkIdPerfil.pkIdPerfil = :idPerfil And p.fkIdFuncionalidade.pkIdFuncionalidade = :idRecursos");

        query.setParameter("idPerfil", idPerfil)
            .setParameter("idRecursos", idRecursos);

        List<SegFuncionalidade> results = (List<SegFuncionalidade>) query.getResultList();

        if (results.isEmpty())
        {
            return new ArrayList<SegFuncionalidade>();
        }
        else
        {
            return results;
        }

    }

    public boolean buscaPorMenuUsuario1(SegFuncionalidade funcionalidade, SegPerfil perfil)
    {
        Query query = em.createQuery("SELECT p.fkIdFuncionalidade FROM SegPerfilHasFuncionalidade p WHERE p.fkIdPerfil.pkIdPerfil = :idPerfil AND p.fkIdFuncionalidade.nome = :permissao ORDER BY nome");

        query.setParameter("idPerfil", perfil)
            .setParameter("permissao", funcionalidade);

        List<SegPerfilHasFuncionalidade> results = (List<SegPerfilHasFuncionalidade>) query.getResultList();
        System.out.println("query: " + results);
        if (results.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public SegPerfilHasFuncionalidade buscaPorMenuUsuario(SegFuncionalidade funcionalidade, SegPerfil perfil) throws SQLException
    {

        SegPerfilHasFuncionalidade permissao = new SegPerfilHasFuncionalidade();
        String query = "select * from SegPerfilHasFuncionalidade where fkIdPerfil=? and fkIdFuncionalidade=?";
        con = ConnectionFactory();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, perfil.getPkIdPerfil());
        ps.setInt(2, funcionalidade.getPkIdFuncionalidade());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
        {
            //permissao.setId(rs.getInt(COL_ID));
            permissao.setFkIdFuncionalidade(funcionalidade);
            permissao.setFkIdPerfil(perfil);
        }
        con.close();
        return permissao;
    }

    private Connection ConnectionFactory()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //buscar os todas as permissoes por  perfil selecionado
    public List<SegPerfilHasFuncionalidade> buscaPorPerfil(Integer idperfil)
    {
        Query query;
        query = em.createQuery("SELECT rp FROM SegPerfilHasFuncionalidade rp WHERE rp.fkIdPerfil.pkIdPerfil = :idperfil ");

        query.setParameter("idperfil", idperfil);

        List<SegPerfilHasFuncionalidade> results = (List<SegPerfilHasFuncionalidade>) query.getResultList();

        //System.out.println("query: " + results);
        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return results;
        }

    }
    
    public SegPerfilHasFuncionalidade buscaPorFuncionalidadePerfil(Integer idfuncionalidade, Integer idperfil)
    {
        Query query;
        query = em.createQuery("SELECT rp FROM SegPerfilHasFuncionalidade rp WHERE rp.fkIdFuncionalidade.pkIdFuncionalidade = :idfuncionalidade AND rp.fkIdPerfil.pkIdPerfil = :idperfil");

        query.setParameter("idfuncionalidade", idfuncionalidade);
        query.setParameter("idperfil", idperfil);

        List<SegPerfilHasFuncionalidade> results = (List<SegPerfilHasFuncionalidade>) query.getResultList();

        //System.out.println("query: " + results);
        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return results.get(0);
        }

    }


}
