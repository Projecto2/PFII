/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.SupiSupervisorHasEscala;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author helga
 */
@Stateless
public class SupiSupervisorHasEscalaFacade extends AbstractFacade<SupiSupervisorHasEscala> {

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiSupervisorHasEscalaFacade() {
        super(SupiSupervisorHasEscala.class);
    }

    public List<SupiSupervisorHasEscala> findMedicosDaEscala(Integer idEscala, Date dia) {
                
        TypedQuery<SupiSupervisorHasEscala> query = em.createQuery("SELECT supHas FROM SupiSupervisorHasEscala supHas "
                + "WHERE supHas.fkIdEscala.pkIdEscala = :idEscala AND supHas.data = :dia ORDER BY supHas.data", SupiSupervisorHasEscala.class)
                .setParameter("idEscala", idEscala).setParameter("dia", dia);

        return query.getResultList();
    }

    public List<SupiSupervisorHasEscala> findDiaTurno(Integer idEscala, Integer idFuncionario) {
        TypedQuery<SupiSupervisorHasEscala> query = em.createQuery("SELECT supHas FROM SupiSupervisorHasEscala supHas "
                + "WHERE supHas.fkIdEscala.pkIdEscala = :idEscala AND supHas.fkIdFuncionario.pkIdFuncionario = :idFuncionario ORDER BY supHas.data", SupiSupervisorHasEscala.class)
                .setParameter("idEscala", idEscala).setParameter("idFuncionario", idFuncionario);

        return query.getResultList();
    }

    public List<SupiSupervisorHasEscala> findEscalaMensal(SupiSupervisorHasEscala supiSupervisorHasEscalaPesquisa) {

        System.out.println("findEscalaMensal");
        String query = constroiQuery(supiSupervisorHasEscalaPesquisa);
        
        System.out.println("query: "+query);
        Query t = em.createQuery(query, SupiSupervisorHasEscala.class);

        if(supiSupervisorHasEscalaPesquisa.getFkIdEscala().getPkIdEscala() !=  null){
            t.setParameter("escala", supiSupervisorHasEscalaPesquisa.getFkIdEscala());
        }
        
        if (supiSupervisorHasEscalaPesquisa.getFkIdFuncionario() != null) {
            if (supiSupervisorHasEscalaPesquisa.getFkIdFuncionario().getPkIdFuncionario() != null) {
                t.setParameter("funcionario", supiSupervisorHasEscalaPesquisa.getFkIdFuncionario().getPkIdFuncionario());
            }
        }

        if (supiSupervisorHasEscalaPesquisa.getFkIdTurno() != null) {
            if (supiSupervisorHasEscalaPesquisa.getFkIdTurno().getPkIdTurno() != null) {
                t.setParameter("turno", supiSupervisorHasEscalaPesquisa.getFkIdTurno().getPkIdTurno());
            }
        }

        List<SupiSupervisorHasEscala> resultado = t.getResultList();

        System.out.println("resultado: "+resultado);
        return resultado;
    }
    

    public String constroiQuery(SupiSupervisorHasEscala supiSupervisorHasEscalaPesquisa) {
        String query = "SELECT e FROM SupiSupervisorHasEscala e WHERE e.fkIdEscala = :escala";

//        if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getFkIdTipoEscala() != null) {
//            if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getFkIdTipoEscala().getPkIdTipoEscala() != null) {
//                query += " AND e.fkIdEscala.fkIdTipoEscala.pkIdTipoEscala = :tipoEscala";
//            }
//        }
//
//        if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getFkIdSeccao() != null) {
//            if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getFkIdSeccao().getPkIdSeccao() != null) {
//                query += " AND e.fkIdEscala.fkIdSeccao.pkIdSeccao = :seccao";
//            }
//        }
//
//        if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getAno() != 0) {
//            query += " AND e.fkIdEscala.ano = :ano";
//        }
//        if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getMes() != 0) {
//            query += " AND e.fkIdEscala.mes = :mes";
//        }
//
        if (supiSupervisorHasEscalaPesquisa.getFkIdFuncionario() != null) {
            if (supiSupervisorHasEscalaPesquisa.getFkIdFuncionario().getPkIdFuncionario() != null) {
                query += " AND e.fkIdFuncionario.pkIdFuncionario = :funcionario";
            }
        }

        if (supiSupervisorHasEscalaPesquisa.getFkIdTurno() != null) {
            if (supiSupervisorHasEscalaPesquisa.getFkIdTurno().getPkIdTurno() != null) {
                query += " AND e.fkIdTurno.pkIdTurno = :turno";
            }
        }
        //retorna todos os funcionarios registado no determinado turno e ano/mes
//        if (supiSupervisorHasEscalaPesquisa.getFkIdEscala().getFkIdSeccao().getPkIdSeccao() != null && supiSupervisorHasEscalaPesquisa.getFkIdEscala().getAno() != 0 && supiSupervisorHasEscalaPesquisa.getFkIdEscala().getMes() != 0 && supiSupervisorHasEscalaPesquisa.getFkIdTurno() != null) {
//            query += " AND e.fkIdEscala.fkIdSeccao.pkIdSeccao = :seccao AND e.fkIdTurno.pkIdTurno = :turno AND e.fkIdEscala.ano = :ano AND e.fkIdEscala.mes = :mes";
//        }

        query += " ORDER BY e.data";

        return query;
    }

}
