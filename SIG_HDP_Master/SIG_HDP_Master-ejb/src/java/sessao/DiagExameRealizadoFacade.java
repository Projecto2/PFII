/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagExameRealizado;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagExameRealizadoFacade extends AbstractFacade<DiagExameRealizado>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagExameRealizadoFacade()
    {
        super(DiagExameRealizado.class);
    }

    public List<DiagExameRealizado> findPesquisaExameRealizado(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim)
    {
        String query = constroiQueryPesquisaHitorico(exameRealizado, dataInicio, dataFim);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            qry.setParameter("pkIdClassificacaoExame", exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
        }

        if (exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            qry.setParameter("categoriaExame", exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria());
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            qry.setParameter("exame", exameRealizado.getFkIdExame().getPkIdExame());
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (dataInicio != null && dataFim != null)
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if (dataInicio != null && dataFim == null)
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if (dataInicio == null && dataFim != null)
        {
            qry.setParameter("dataFim", dataFim);
        }

        List<DiagExameRealizado> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQueryPesquisaHitorico(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim)
    {
        String query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar";

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.pkIdClassificacaoServicoSolicitado = :pkIdClassificacaoExame";
        }

        if (exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            query += " AND d.fkIdExame.fkIdCategoriaExame.pkIdCategoria = :categoriaExame";
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            query += " AND d.fkIdExame.pkIdExame = :exame";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nome LIKE :nomeTecnico";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNomeTecnico";
        }

        if (dataInicio != null && dataFim != null)
        {
            query += " AND d.data >= :dataInicio and d.data <= :dataFim";
        }

        if (dataInicio != null && dataFim == null)
        {
            query += " AND d.data >= :dataInicio";
        }

        if (dataInicio == null && dataFim != null)
        {
            query += " AND d.data <= :dataFim";
        }

        query += " ORDER BY d.data DESC";

        return query;
    }

    public List<DiagExameRealizado> findPesquisaResultadosExames(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, String tipoExame, int numeroRegistos)
    {
        String query = constroiQueryPesquisaResultadosExames(exameRealizado, dataInicio, dataFim, tipoExame);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            qry.setParameter("numProcesso", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if ((tipoExame.equals("Radiografia") || tipoExame.equals("Ecografia")) && exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            qry.setParameter("pkIdClassificacaoExame", exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
        }

        if ((!tipoExame.equals("Radiografia") || !tipoExame.equals("Ecografia")) && exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            qry.setParameter("categoriaExame", exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria());
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            qry.setParameter("exame", exameRealizado.getFkIdExame().getPkIdExame());
        }

        if ((!tipoExame.equals("Radiografia") || !tipoExame.equals("Ecografia")) && exameRealizado.getFkIdAmostra().getFkIdTipoAmostra().getPkIdTipoAmostra() != null)
        {
            qry.setParameter("pkIdTipoAmostra", exameRealizado.getFkIdAmostra().getFkIdTipoAmostra().getPkIdTipoAmostra());
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            qry.setParameter("resultados", "%" + exameRealizado.getResultados() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (dataInicio != null && dataFim != null)
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if (dataInicio != null && dataFim == null)
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if (dataInicio == null && dataFim != null)
        {
            qry.setParameter("dataFim", dataFim);
        }

        System.out.println("query: " + qry.toString());

        qry.setMaxResults(numeroRegistos);
        List<DiagExameRealizado> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQueryPesquisaResultadosExames(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, String tipoExame)
    {
        String query = null;

        System.out.println("tipoExame " + tipoExame);

        if (tipoExame.equals("ExameNormal"))
        {
            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado = 'Normal'";
        }

        if (tipoExame.equals("ExameUrgente"))
        {
            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado <> 'Normal'";
        }

        if (tipoExame.equals("Radiografia"))
        {
            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdExame.fkIdCategoriaExame.descricaoCategoria = 'Radiografia'";
        }

        if (tipoExame.equals("Ecografia"))
        {
            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdExame.fkIdCategoriaExame.descricaoCategoria = 'Ecografia'";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente LIKE :numProcesso";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if ((tipoExame.equals("Radiografia") || tipoExame.equals("Ecografia")) && exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.pkIdClassificacaoServicoSolicitado = :pkIdClassificacaoExame";
        }

        if ((!tipoExame.equals("Radiografia") || !tipoExame.equals("Ecografia")) && exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            query += " AND d.fkIdExame.fkIdCategoriaExame.pkIdCategoria = :categoriaExame";
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            query += " AND d.fkIdExame.pkIdExame = :exame";
        }

        if ((!tipoExame.equals("Radiografia") || !tipoExame.equals("Ecografia")) && exameRealizado.getFkIdAmostra().getFkIdTipoAmostra().getPkIdTipoAmostra() != null)
        {
            query += " AND d.fkIdAmostra.fkIdTipoAmostra.pkIdTipoAmostra = :pkIdTipoAmostra";
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            query += " AND d.resultados LIKE :resultados";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nome LIKE :nomeTecnico";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNomeTecnico";
        }

        if (dataInicio != null && dataFim != null)
        {
            query += " AND d.data >= :dataInicio and d.data <= :dataFim";
        }

        if (dataInicio != null && dataFim == null)
        {
            query += " AND d.data >= :dataInicio";
        }

        if (dataInicio == null && dataFim != null)
        {
            query += " AND d.data <= :dataFim";
        }

        query += " ORDER BY d.data DESC";

        return query;
    }

    public List<DiagExameRealizado> findPesquisaResultadosExamesEcografia(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, String tipoExame)
    {
        String query = constroiQueryPesquisaResultadosExamesEcografia(exameRealizado, dataInicio, dataFim, tipoExame);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            qry.setParameter("numProcesso", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            qry.setParameter("pkIdClassificacaoExame", exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            qry.setParameter("exame", exameRealizado.getFkIdExame().getPkIdExame());
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            qry.setParameter("resultados", "%" + exameRealizado.getResultados() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (dataInicio != null && dataFim != null)
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if (dataInicio != null && dataFim == null)
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if (dataInicio == null && dataFim != null)
        {
            qry.setParameter("dataFim", dataFim);
        }

        System.out.println("query: " + qry.toString());

        List<DiagExameRealizado> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQueryPesquisaResultadosExamesEcografia(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, String tipoExame)
    {
        String query = null;

        System.out.println("tipoExame " + tipoExame);

//        if (tipoExame.equals("ExameNormal"))
//        {
//            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado = 'Normal'";
//        }
//
//        if (tipoExame.equals("ExameUrgente"))
//        {
//            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado <> 'Normal'";
//        }
//
//        if (tipoExame.equals("Radiografia"))
//        {
//            query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdExame.fkIdCategoriaExame.descricaoCategoria = 'Radiografia'";
//        }
//
//        if (tipoExame.equals("Ecografia"))
//        {
        query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdExame.fkIdCategoriaExame.descricaoCategoria = 'Ecografia'";
//        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente LIKE :numProcesso";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if ((tipoExame.equals("Radiografia") || tipoExame.equals("Ecografia")) && exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.pkIdClassificacaoServicoSolicitado = :pkIdClassificacaoExame";
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            query += " AND d.fkIdExame.pkIdExame = :exame";
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            query += " AND d.resultados LIKE :resultados";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nome LIKE :nomeTecnico";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNomeTecnico";
        }

        if (dataInicio != null && dataFim != null)
        {
            query += " AND d.data >= :dataInicio and d.data <= :dataFim";
        }

        if (dataInicio != null && dataFim == null)
        {
            query += " AND d.data >= :dataInicio";
        }

        if (dataInicio == null && dataFim != null)
        {
            query += " AND d.data <= :dataFim";
        }

        query += " ORDER BY d.data DESC";

        return query;
    }

    public List<DiagExameRealizado> findPesquisaResultadosExamesRadiografia(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, String tipoExame)
    {
        String query = constroiQueryPesquisaResultadosExamesRadiografia(exameRealizado, dataInicio, dataFim, tipoExame);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            qry.setParameter("numProcesso", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            qry.setParameter("pkIdClassificacaoExame", exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            qry.setParameter("exame", exameRealizado.getFkIdExame().getPkIdExame());
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            qry.setParameter("resultados", "%" + exameRealizado.getResultados() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (dataInicio != null && dataFim != null)
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if (dataInicio != null && dataFim == null)
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if (dataInicio == null && dataFim != null)
        {
            qry.setParameter("dataFim", dataFim);
        }

        System.out.println("query: " + qry.toString());

        List<DiagExameRealizado> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQueryPesquisaResultadosExamesRadiografia(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, String tipoExame)
    {
        String query = null;

        query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdExame.fkIdCategoriaExame.descricaoCategoria = 'Radiografia'";

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente LIKE :numProcesso";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if ((tipoExame.equals("Radiografia") || tipoExame.equals("Ecografia")) && exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.pkIdClassificacaoServicoSolicitado = :pkIdClassificacaoExame";
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            query += " AND d.fkIdExame.pkIdExame = :exame";
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            query += " AND d.resultados LIKE :resultados";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nome LIKE :nomeTecnico";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNomeTecnico";
        }

        if (dataInicio != null && dataFim != null)
        {
            query += " AND d.data >= :dataInicio and d.data <= :dataFim";
        }

        if (dataInicio != null && dataFim == null)
        {
            query += " AND d.data >= :dataInicio";
        }

        if (dataInicio == null && dataFim != null)
        {
            query += " AND d.data <= :dataFim";
        }

        query += " ORDER BY d.data DESC";

        return query;
    }

    public List<DiagExameRealizado> findPesquisaResultadosExamesInter(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim, int numeroRegistos)
    {
        String query = constroiQueryPesquisaResultadosExamesInter(exameRealizado, dataInicio, dataFim);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            qry.setParameter("numProcesso", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            qry.setParameter("pkIdClassificacaoExame", exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
        }

        if (exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            qry.setParameter("categoriaExame", exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria());
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            qry.setParameter("exame", exameRealizado.getFkIdExame().getPkIdExame());
        }

        if (exameRealizado.getFkIdAmostra().getFkIdTipoAmostra().getPkIdTipoAmostra() != null)
        {
            qry.setParameter("pkIdTipoAmostra", exameRealizado.getFkIdAmostra().getFkIdTipoAmostra().getPkIdTipoAmostra());
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            qry.setParameter("resultados", "%" + exameRealizado.getResultados() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNomeTecnico", exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (dataInicio != null && dataFim != null)
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if (dataInicio != null && dataFim == null)
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if (dataInicio == null && dataFim != null)
        {
            qry.setParameter("dataFim", dataFim);
        }

        qry.setMaxResults(numeroRegistos);
        List<DiagExameRealizado> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQueryPesquisaResultadosExamesInter(DiagExameRealizado exameRealizado, Date dataInicio, Date dataFim)
    {
        String query = null;

        query = "Select d FROM DiagExameRealizado d WHERE :pesquisar = :pesquisar AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado <> 'Normal' ";

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente LIKE :numProcesso";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (exameRealizado.getFkIdServicoSolicitado().getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            query += " AND d.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.pkIdClassificacaoServicoSolicitado = :pkIdClassificacaoExame";
        }

        if (exameRealizado.getFkIdExame().getFkIdCategoriaExame().getPkIdCategoria() != null)
        {
            query += " AND d.fkIdExame.fkIdCategoriaExame.pkIdCategoria = :categoriaExame";
        }

        if (exameRealizado.getFkIdExame().getPkIdExame() != null)
        {
            query += " AND d.fkIdExame.pkIdExame = :exame";
        }

        if (exameRealizado.getFkIdAmostra().getFkIdTipoAmostra().getPkIdTipoAmostra() != null)
        {
            query += " AND d.fkIdAmostra.fkIdTipoAmostra.pkIdTipoAmostra = :pkIdTipoAmostra";
        }

        if (exameRealizado.getResultados() != null && !exameRealizado.getResultados().trim().isEmpty())
        {
            query += " AND d.resultados LIKE :resultados";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nome LIKE :nomeTecnico";
        }

        if (exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !exameRealizado.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNomeTecnico";
        }

        if (dataInicio != null && dataFim != null)
        {
            query += " AND d.data >= :dataInicio and d.data <= :dataFim";
        }

        if (dataInicio != null && dataFim == null)
        {
            query += " AND d.data >= :dataInicio";
        }

        if (dataInicio == null && dataFim != null)
        {
            query += " AND d.data <= :dataFim";
        }

        query += " ORDER BY d.data DESC";

        return query;
    }
}
