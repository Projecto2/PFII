/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagTransfusao;
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
public class DiagTransfusaoFacade extends AbstractFacade<DiagTransfusao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTransfusaoFacade()
    {
        super(DiagTransfusao.class);
    }
 
    public List<DiagTransfusao> findTransfusao(DiagTransfusao transfusao, Date dataInicio, Date dataFim, int numeroRegistos)
    {
        String query = constroiQuery(transfusao, dataInicio, dataFim);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null )
        {
            qry.setParameter("pkIdPessoa", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty() )
        {
            qry.setParameter("numProcesso", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() + "%");
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            qry.setParameter("nome", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio().trim().isEmpty() )
        {
            qry.setParameter("nomeDoMeio", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            qry.setParameter("sobreNome", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null )
        {
            qry.setParameter("nacionalidade", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais());
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null )
        {
            qry.setParameter("sexo", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null )
        {
            qry.setParameter("grupo", transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo());
        }

        if ( transfusao.getFkRealizadoPor().getFkIdPessoa().getNome() != null && !transfusao.getFkRealizadoPor().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            qry.setParameter("nomeFuncionario", transfusao.getFkRealizadoPor().getFkIdPessoa().getNome() + "%");
        }

        if ( transfusao.getFkRealizadoPor().getFkIdPessoa().getSobreNome() != null && !transfusao.getFkRealizadoPor().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            qry.setParameter("sobreNomeFuncionario", transfusao.getFkRealizadoPor().getFkIdPessoa().getSobreNome() + "%");
        }

        if ( dataInicio != null && dataFim != null )
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if ( dataInicio != null && dataFim == null )
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if ( dataInicio == null && dataFim != null )
        {
            qry.setParameter("dataFim", dataFim);
        }

        qry.setMaxResults(numeroRegistos);
        List<DiagTransfusao> tipagens = qry.getResultList();

        return tipagens;
    }

    public String constroiQuery(DiagTransfusao transfusao, Date dataInicio, Date dataFim)
    {

        String query = "SELECT d FROM DiagTransfusao d WHERE :pesquisar = :pesquisar";
        
        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().trim().isEmpty() )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente LIKE :numProcesso";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio().trim().isEmpty() )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        if ( transfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null )
        {
            query += " AND d.fkIdRespostaRequisicaoComponenteSanguineo.fkIdRequisicaoComponenteSanguineo.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdGrupoSanguineo.pkIdGrupoSanguineo = :grupo";
        }

        if ( transfusao.getFkRealizadoPor().getFkIdPessoa().getNome() != null && !transfusao.getFkRealizadoPor().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            query += " AND d.fkRealizadoPor.fkIdPessoa.nome LIKE :nomeFuncionario";
        }

        if ( transfusao.getFkRealizadoPor().getFkIdPessoa().getSobreNome() != null && !transfusao.getFkRealizadoPor().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            query += " AND d.fkRealizadoPor.fkIdPessoa.sobreNome LIKE :sobreNomeFuncionario";
        }

        if ( dataInicio != null && dataFim != null )
        {
            query += " AND d.dataTransfusao >= :dataInicio and d.dataTransfusao <= :dataFim";
        }

        if ( dataInicio != null && dataFim == null )
        {
            query += " AND d.dataTransfusao >= :dataInicio";
        }

        if ( dataInicio == null && dataFim != null )
        {
            query += " AND d.dataTransfusao <= :dataFim";
        }

        query += " ORDER BY d.dataTransfusao DESC";

        return query;
    }
}
