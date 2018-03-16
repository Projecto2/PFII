/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhContrato;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhContratoFacade extends AbstractFacade<RhContrato>
{

     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhContratoFacade ()
     {
          super(RhContrato.class);
     }

    /**
     *
     * Procura todos os contratos activos com data de início 
     * antes de uma determinada data, cujo funcionário ligado ao contrato
     * ainda se encontra inactivo
     * 
     * @param data
     * @return
     */
    public List<RhContrato> procurarActivosComInicioAntesDe (Date data)
    {
        TypedQuery<RhContrato> query;
        query = em.createQuery("SELECT c FROM RhContrato c WHERE c.dataInicio <= :data AND c.fkIdEstadoContrato.descricao = 'Activo' "
                + "AND c.fkIdFuncionario.fkIdEstadoFuncionario.descricao = 'Inactivo'", RhContrato.class).setParameter("data", data);

        return query.getResultList();
    }
    
    
    /**
     *
     * Procura todos os contratos activos cuja data de término 
     * se encontra num intervalo de datas
     * 
     * @param data1
     * @param data2
     * @return
     */
    public List<RhContrato> procurarActivosComTerminoEntre (Date data1, Date data2)
    {
        TypedQuery<RhContrato> query;
        query = em.createQuery("SELECT c FROM RhContrato c WHERE c.dataTermino BETWEEN :data1 AND :data2 "
                + "AND c.fkIdEstadoContrato.descricao = 'Activo' ", RhContrato.class).setParameter("data1", data1).setParameter("data2", data2);

        return query.getResultList();
    }
    
    /**
     *
     * Procura todos os contratos activos com data de término antes de uma
     * determinada data
     *
     * @param data
     * @return
     */
    public List<RhContrato> procurarActivosComTerminoAntesDe (Date data)
    {
        TypedQuery<RhContrato> query;
        query = em.createQuery("SELECT c FROM RhContrato c WHERE c.dataTermino <= :data "
                               + "AND c.fkIdEstadoContrato.descricao = 'Activo' ", RhContrato.class).setParameter("data", data);

        return query.getResultList();
    }

    /**
     * Pesquisa todos os contratos de um determinado funcionário
     * 
     * @param idFuncionario
     * @return
     */
    public List<RhContrato> pesquisaPorFuncionario (Integer idFuncionario)
     {
          TypedQuery<RhContrato> t = em.createQuery("SELECT c FROM RhContrato c WHERE c.fkIdFuncionario.pkIdFuncionario = :idFuncionario", RhContrato.class)
                  .setParameter("idFuncionario", idFuncionario);

          List<RhContrato> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Pesquisa contratos por intervalos de data
     * 
     * @param dataCadastroInf
     * @param dataCadastroSup
     * @param dataInicioInf
     * @param dataInicioSup
     * @param dataTerminoInf
     * @param dataTerminoSup
     * @return
     */
    public List<RhContrato> findContratoPorIntervaloDeDatas (Date dataCadastroInf, Date dataCadastroSup, Date dataInicioInf, Date dataInicioSup, Date dataTerminoInf, Date dataTerminoSup)
     {
          String query = constroiQueryIntervaloDeDatas(dataCadastroInf, dataCadastroSup, dataInicioInf, dataInicioSup, dataTerminoInf, dataTerminoSup);

          TypedQuery<RhContrato> t = em.createQuery(query, RhContrato.class);

          if (dataCadastroInf != null)
          {
               t.setParameter("dataCadastroInf", dataCadastroInf);
          }

          if (dataCadastroSup != null)
          {
               t.setParameter("dataCadastroSup", dataCadastroSup);
          }

          if (dataInicioInf != null)
          {
               t.setParameter("dataInicioInf", dataInicioInf);
          }

          if (dataInicioSup != null)
          {
               t.setParameter("dataInicioSup", dataInicioSup);
          }

          if (dataTerminoInf != null)
          {
               t.setParameter("dataTerminoInf", dataTerminoInf);
          }

          if (dataTerminoSup != null)
          {
               t.setParameter("dataTerminoSup", dataTerminoSup);
          }

          List<RhContrato> resultado = t.getResultList();

          return resultado;
     }

     private String constroiQueryIntervaloDeDatas (Date dataCadastroInf, Date dataCadastroSup, Date dataInicioInf, Date dataInicioSup, Date dataTerminoInf, Date dataTerminoSup)
     {
          String query = "SELECT c FROM RhContrato c WHERE c.pkIdContrato = c.pkIdContrato";

          if (dataCadastroInf != null)
          {
               query += " AND c.dataCadastro >= :dataCadastroInf";
          }
          if (dataCadastroSup != null)
          {
               query += " AND c.dataCadastro <= :dataCadastroSup";
          }

          if (dataInicioInf != null)
          {
               query += " AND c.dataInicio >= :dataInicioInf";
          }
          if (dataInicioSup != null)
          {
               query += " AND c.dataInicio <= :dataInicioSup";
          }

          if (dataTerminoInf != null)
          {
               query += " AND c.dataTermino >= :dataTerminoInf";
          }
          if (dataTerminoSup != null)
          {
               query += " AND c.dataTermino <= :dataTerminoSup";
          }

          query += " ORDER BY c.fkIdFuncionario.fkIdPessoa.nome, c.fkIdFuncionario.fkIdPessoa.nomeDoMeio, c.fkIdFuncionario.fkIdPessoa.sobreNome, c.dataCadastro";

          return query;
     }
     
    /**
     * Pesquisa todos os contratos de acordo com todos os campos que 
     * tiverem dados no objecto contrato, ou seja, procura de acordo os critérios
     * introduzidos
     *
     * @param contrato
     * @return
     */
     public List<RhContrato> findContrato (RhContrato contrato)
     {
          String query = constroiQuery(contrato);

          TypedQuery<RhContrato> t = em.createQuery(query, RhContrato.class);

          if (contrato.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !contrato.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
          {
               t.setParameter("nome", contrato.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
          }

          if (contrato.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !contrato.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          {
               t.setParameter("nomeDoMeio", contrato.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + "%");
          }

          if (contrato.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !contrato.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
          {
               t.setParameter("sobreNome", contrato.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
          }

          if (contrato.getFkIdTipoContrato().getPkIdTipoContrato() != null)
          {
               t.setParameter("tipoContrato", contrato.getFkIdTipoContrato().getPkIdTipoContrato());
          }

          if (contrato.getFkIdEstadoContrato().getPkIdEstadoContrato() != null)
          {
               t.setParameter("estadoContrato", contrato.getFkIdEstadoContrato().getPkIdEstadoContrato());
          }

          List<RhContrato> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Constrói uma query JPQL com todos os campos que 
     * tiverem dados no objecto contrato
     * 
     * @param contrato
     * @return
     */
     public String constroiQuery (RhContrato contrato)
     {
          String query = "SELECT c FROM RhContrato c WHERE c.pkIdContrato = c.pkIdContrato";

          if (contrato.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !contrato.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
          {
               query += " AND c.fkIdFuncionario.fkIdPessoa.nome LIKE :nome";
          }

          if (contrato.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !contrato.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          {
               query += " AND c.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
          }

          if (contrato.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !contrato.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
          {
               query += " AND c.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
          }

          if (contrato.getFkIdTipoContrato().getPkIdTipoContrato() != null)
          {
               query += " AND c.fkIdTipoContrato.pkIdTipoContrato = :tipoContrato";
          }

          if (contrato.getFkIdEstadoContrato().getPkIdEstadoContrato() != null)
          {
               query += " AND c.fkIdEstadoContrato.pkIdEstadoContrato = :estadoContrato";
          }

          query += " ORDER BY c.fkIdFuncionario.fkIdPessoa.nome, c.fkIdFuncionario.fkIdPessoa.nomeDoMeio, c.fkIdFuncionario.fkIdPessoa.sobreNome, c.dataCadastro";

          return query;
     }
     
}
