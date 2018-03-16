/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import Utils.DataUtils;
import entidade.AdmsAgendamento;
import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.DiagCategoriaExame;
import entidade.DiagExame;
import entidade.DiagSubcategoriaExame;
import entidade.FarmUnidadeMedida;
import entidade.RhFuncionario;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagExameFacade extends AbstractFacade<DiagExame>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagExameFacade()
    {
        super(DiagExame.class);
    }

    public List<RhFuncionario> findAllFuncionariosLaboratorioRotina ()
    {
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.fkIdSeccaoTrabalho.descricao = 'Laboratório – Análises Clínicas – Exames de Rotina' "
                                     + "ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return query.getResultList();
    }
    
    public List<RhFuncionario> findAllFuncionariosLaboratorioUrgencia ()
    {
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.fkIdSeccaoTrabalho.descricao = 'Laboratório – Análises Clínicas – Exames de Urgência' "
                                     + "ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return query.getResultList();
    }
    
    public List<AdmsClassificacaoServicoSolicitado> findAllClassificacaoServicoSolicitado()
    {
        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsClassificacaoServicoSolicitado a ORDER BY a.descricaoClassificacaoServicoSolicitado", AdmsClassificacaoServicoSolicitado.class);
        return typedQuery.getResultList();
    }

    public List<FarmUnidadeMedida> findAllUnidades()
    {
        TypedQuery typedQuery = em.createQuery("SELECT f FROM FarmUnidadeMedida f ORDER BY f.abreviatura", FarmUnidadeMedida.class);
        return typedQuery.getResultList();
    }

    public List<AdmsServico> findServicosExamesLaboratorio()
    {
        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsServico a WHERE a.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 1)", AdmsServico.class);

        return typedQuery.getResultList();
    }

    public List<AdmsServico> findServicosExamesImagiologiaRadiografia()
    {
        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsServico a WHERE a.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 2 AND "
                + "d.fkIdCategoriaExame.descricaoCategoria LIKE 'Radiografia')", AdmsServico.class);

        return typedQuery.getResultList();
    }

    public List<AdmsServico> findServicosExamesImagiologiaEcografia()
    {
        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsServico a WHERE a.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 2 AND "
                + "d.fkIdCategoriaExame.descricaoCategoria LIKE 'Ecografia')", AdmsServico.class);

        return typedQuery.getResultList();
    }

    public List<DiagExame> findExamesByCategoria(DiagCategoriaExame categoriaExame)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExame d WHERE d.fkIdCategoriaExame.pkIdCategoria = :idCategoriaExame ORDER BY d.descricaoExame", DiagExame.class).setParameter("idCategoriaExame", categoriaExame.getPkIdCategoria());

        return typedQuery.getResultList();
    }

    public List<DiagExame> findExamesRadiografia()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExame d WHERE d.fkIdCategoriaExame.descricaoCategoria LIKE 'Radiografia' ORDER BY d.descricaoExame", DiagExame.class);

        return typedQuery.getResultList();
    }

    public List<DiagExame> findExamesEcografia()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExame d WHERE d.fkIdCategoriaExame.descricaoCategoria LIKE 'Ecografia' ORDER BY d.descricaoExame", DiagExame.class);

        return typedQuery.getResultList();
    }

    public List<DiagCategoriaExame> findCategoriasLaboratorio()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagCategoriaExame d WHERE d.fkIdSector.pkIdSectorExame = 1 ORDER BY d.descricaoCategoria", DiagCategoriaExame.class);

        return typedQuery.getResultList();
    }

    public List<DiagCategoriaExame> findCategoriasImagiologia()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagCategoriaExame d WHERE d.fkIdSector.pkIdSectorExame = 2 ORDER BY d.descricaoCategoria", DiagCategoriaExame.class);

        return typedQuery.getResultList();
    }

    public List<DiagExame> findExamesBySubcategoria(DiagSubcategoriaExame subcategoriaExame)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExame d WHERE d.fkIdSubcategoriaExame.pkIdSubcategoriaExame = :idSubcategoriaExame ORDER BY d.descricaoExame", DiagExame.class).setParameter("idSubcategoriaExame", subcategoriaExame.getPkIdSubcategoriaExame());

        return typedQuery.getResultList();
    }

    public List<DiagSubcategoriaExame> findSubcategoriasByCategoria(DiagCategoriaExame categoriaExame)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagSubcategoriaExame d WHERE d.fkIdCategoria.pkIdCategoria = :idCategoriaExame ORDER BY d.descricaoSubcategoria", DiagSubcategoriaExame.class).setParameter("idCategoriaExame", categoriaExame.getPkIdCategoria());

        return typedQuery.getResultList();
    }

    public DiagExame findExamePorNomeServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExame d WHERE d.descricaoExame LIKE :nomeServico", DiagExame.class).setParameter("nomeServico", servicoSolicitado.getFkIdServico().getNomeServico());

        return (DiagExame) typedQuery.getSingleResult();
    }

    public List<AdmsAgendamento> findExamesSolicitados()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsAgendamento a WHERE a.dataHoraCheckIn >= :dataOntem AND a.dataHoraCheckIn <= :dataHoje AND a.fkIdEstadoAgendamento.descricaoEstadoAgendamento = 'Chegou' AND a.fkIdServicoSolicitado.fkIdServico.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 1) AND "
                + "a.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado = 'Normal' AND "
                + "a.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT dd.fkIdServicoSolicitado.pkIdServicoSolicitado FROM DiagExameRealizado dd) "
                + "ORDER BY a.fkIdServicoSolicitado.fkIdSolicitacao.data DESC", AdmsAgendamento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }

    public List<AdmsAgendamento> findExamesUrgentesSolicitados()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsAgendamento a WHERE a.dataHoraCheckIn >= :dataOntem AND a.dataHoraCheckIn <= :dataHoje AND a.fkIdEstadoAgendamento.descricaoEstadoAgendamento = 'Chegou' AND a.fkIdServicoSolicitado.fkIdServico.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 1) AND "
                + "a.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado <> 'Normal' AND "
                + "a.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT dd.fkIdServicoSolicitado.pkIdServicoSolicitado FROM DiagExameRealizado dd) "
                + "ORDER BY a.fkIdServicoSolicitado.fkIdSolicitacao.data DESC", AdmsAgendamento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }

    public List<AdmsAgendamento> findRadiografiasSolicitadas()
    {

        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsAgendamento a WHERE a.dataHoraCheckIn >= :dataOntem AND a.dataHoraCheckIn <= :dataHoje AND a.fkIdServicoSolicitado.fkIdServico.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 2 AND "
                + "d.fkIdCategoriaExame.descricaoCategoria LIKE 'Radiografia') AND "
                + "a.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT dd.fkIdServicoSolicitado.pkIdServicoSolicitado FROM DiagExameRealizado dd) "
                + "ORDER BY a.fkIdServicoSolicitado.fkIdSolicitacao.data DESC", AdmsAgendamento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();

    }

    public List<AdmsAgendamento> findEcografiasSolicitadas()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsAgendamento a WHERE a.dataHoraCheckIn >= :dataOntem AND a.dataHoraCheckIn <= :dataHoje AND a.fkIdServicoSolicitado.fkIdServico.nomeServico IN "
                + "(SELECT d.descricaoExame FROM DiagExame d WHERE d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = 2 AND "
                + "d.fkIdCategoriaExame.descricaoCategoria LIKE 'Ecografia') AND "
                + "a.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT dd.fkIdServicoSolicitado.pkIdServicoSolicitado FROM DiagExameRealizado dd) "
                + "ORDER BY a.fkIdServicoSolicitado.fkIdSolicitacao.data DESC", AdmsAgendamento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }

    public List<DiagExame> findPesquisa(DiagExame examePesquisar, int idSectorExame)
    {
        String query = constroiQuery(examePesquisar, idSectorExame);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (examePesquisar.getPkIdExame() != null)
        {
            qry.setParameter("pkIdExame", examePesquisar.getPkIdExame());
        }

        if (examePesquisar.getDescricaoExame() != null && !examePesquisar.getDescricaoExame().trim().isEmpty())
        {
            qry.setParameter("descricaoExame", examePesquisar.getDescricaoExame() + "%");
        }

        if (examePesquisar.getValorReferenciaMinimo() != null)
        {
            qry.setParameter("valorReferenciaMinimo", examePesquisar.getValorReferenciaMinimo());
        }

        if (examePesquisar.getValorReferenciaMaximo() != null)
        {
            qry.setParameter("valorReferenciaMaximo", examePesquisar.getValorReferenciaMaximo());
        }

        if (examePesquisar.getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            qry.setParameter("pkIdCategoriaExame", examePesquisar.getFkIdCategoriaExame().getPkIdCategoria());
        }

        if (examePesquisar.getFkIdSubcategoriaExame().getPkIdSubcategoriaExame() != null)
        {
            qry.setParameter("pkIdSubcategoriaExame", examePesquisar.getFkIdSubcategoriaExame().getPkIdSubcategoriaExame());
        }

        if (examePesquisar.getFkIdUnidadeMedida().getPkIdUnidadeMedida() != null)
        {
            qry.setParameter("pkIdUnidade", examePesquisar.getFkIdUnidadeMedida().getPkIdUnidadeMedida());
        }

        qry.setParameter("pkIdSectorExame", idSectorExame);

        List<DiagExame> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagExame examePesquisar, int idSectorExame)
    {
        String query = "Select d FROM DiagExame d WHERE :pesquisar = :pesquisar";

        if (examePesquisar.getPkIdExame() != null)
        {
            query += " AND d.pkIdExame = :pkIdExame";
        }

        if (examePesquisar.getDescricaoExame() != null && !examePesquisar.getDescricaoExame().trim().isEmpty())
        {
            query += " AND d.descricaoExame LIKE :descricaoExame";
        }

        if (examePesquisar.getValorReferenciaMinimo() != null)
        {
            query += " AND d.valorReferenciaMinimo = :valorReferenciaMinimo";
        }

        if (examePesquisar.getValorReferenciaMaximo() != null)
        {
            query += " AND d.valorReferenciaMaximo = :valorReferenciaMaximo";
        }

        if (examePesquisar.getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            query += " AND d.fkIdCategoriaExame.pkIdCategoria = :pkIdCategoriaExame";
        }

        if (examePesquisar.getFkIdSubcategoriaExame().getPkIdSubcategoriaExame() != null)
        {
            query += " AND d.fkIdSubcategoriaExame.pkIdSubcategoriaExame = :pkIdSubcategoriaExame";
        }

        if (examePesquisar.getFkIdUnidadeMedida().getPkIdUnidadeMedida() != null)
        {
            query += " AND d.fkIdUnidadeMedida.pkIdUnidadeMedida = :pkIdUnidade";
        }

        query += " AND d.fkIdCategoriaExame.fkIdSector.pkIdSectorExame = :pkIdSectorExame  ORDER BY d.descricaoExame";

        return query;
    }

    public boolean isExameTabelaEmpty()
    {
        List<DiagExame> listExames = this.findAll();
        return (listExames == null || listExames.isEmpty());
    }
}
