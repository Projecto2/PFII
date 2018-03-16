/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbDiagnosticoHasDoenca;
import entidade.GrlPessoa;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbDiagnosticoHasDoencaFacade extends AbstractFacade<AmbDiagnosticoHasDoenca>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbDiagnosticoHasDoencaFacade()
    {
        super(AmbDiagnosticoHasDoenca.class);
    }
    
//    SELECT year(c.dataVigencia) FROM Calendario c group by year(c.dataVigencia)
//    FROM AmbDiagnosticoHasDoenca a WHERE month(a.fkIdDiagnostico.dataHoraDiagnostico) = '10' "
//    "SELECT a FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.dataHoraDiagnostico = '2018-01-08 14:49:19.381' "
    
    public List<AmbDiagnosticoHasDoenca> teste()
    {
        return em.createQuery("SELECT a "
                + "FROM AmbDiagnosticoHasDoenca a WHERE month(a.fkIdDiagnostico.dataHoraDiagnostico) >= '0' "
                ).getResultList();
    }     
    
    public List<String> findNomePaciente(String nome)
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome) "
                + "FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome = :nome "
                + "ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome"
                , String.class).setParameter("nome", nome).getResultList();
    }      

    public List<String> findNomeDoMeioPaciente(String nomeDoMeio)
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio) "
                + "FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio = :nomeDoMeio "
                + "ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio"
                , String.class).setParameter("nomeDoMeio", nomeDoMeio).getResultList();
    }     
    
    public List<String> findSobreNomePaciente(String sobreNome)
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome) "
                + "FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome = :sobreNome "
                + "ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome"
                , String.class).setParameter("sobreNome", sobreNome).getResultList();
    }     
    
    public List<AmbDiagnosticoHasDoenca> findDoentes(String nome, String nomeDoMeio, String sobreNome)
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome) "
                + "FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome = :nome "
                + "AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio = :nomeDoMeio "
                + "AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome = :sobreNome "
                + "ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome")
                .setParameter("nome", nome).setParameter("nomeDoMeio", nomeDoMeio).setParameter("sobreNome", sobreNome).getResultList();
    }     
    
    public List<AmbDiagnosticoHasDoenca> findPacientesHomens()
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome) "
                + "FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.codigoSexo = 'M' "
                + "ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome").getResultList();
    }     
    
    public List<AmbDiagnosticoHasDoenca> findPacientesMulheres()
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome) "
                + "FROM AmbDiagnosticoHasDoenca a WHERE a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.codigoSexo = 'F' "
                + "ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome").getResultList();
    }    
    
    public List<AmbDiagnosticoHasDoenca> findPaciente()
    {
        return em.createQuery("SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio), "
                + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome) "
                + "FROM AmbDiagnosticoHasDoenca a ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio, "
                + "a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome").getResultList();
    }  
    
//  CriteriaBuilder cb = em.getCriteriaBuilder();
//
//  CriteriaQuery<Country> q = cb.createQuery(Country.class);
//  Root<Country> c = q.from(Country.class);
//  q.select(c); 
//a.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()    
    
    public List<GrlPessoa> find(String nome/*, String nomeDoMeio, String sobreNome*/)
    {
//        AmbDiagnosticoHasDoenca a = new AmbDiagnosticoHasDoenca();
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GrlPessoa> cq = cb.createQuery(GrlPessoa.class);
        Root<GrlPessoa> pessoa = cq.from(GrlPessoa.class);
        
//        c.where(cb.equal(venda.get("id"), cb.parameter(String.class, "id")));

        cq.select(pessoa).distinct(true).where(cb.equal(pessoa.get("nome"), cb.parameter(String.class, "nome")));
        
//        q.multiselect(c.<String>get("nome")).distinct(true);
//                q.distinct(true);//.get((AmbDiagnosticoHasDoenca)a)).distinct(true);  
        
        Query t = em.createQuery(cq);
        t.setParameter("nome", nome);
        
        List<GrlPessoa> resultado = t.getResultList();

        return resultado;
        
//        return cb.getResultList();
    }    
    
//    public String constroiQuery(AmbDiagnosticoHasDoenca item, String nome, String nomeDoMeio, String sobreNome)
//    {
//        String query = "SELECT DISTINCT a FROM AmbDiagnosticoHasDoenca a WHERE :pesquisar = :pesquisar";
//
//        if (nome != null)
//            query += " AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome = :nome";
//        
//        if (nomeDoMeio != null)
//            query += " AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio = :nomeDoMeio";
//
//        if (sobreNome != null)
//            query += " AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome = :sobreNome";
//
//        query += " ORDER BY a.fkIdDiagnostico.dataHoraDiagnostico DESC";
//
//        return query;
//    }
    
//    public List<AmbDiagnosticoHasDoenca> findPacientes(AmbDiagnosticoHasDoenca item, String nome, String nomeDoMeio, String sobreNome)
//    {
//        String query = constroiQuery(item, nome, nomeDoMeio, sobreNome);
//
//        Query t = em.createQuery(query);
//
//        t.setParameter("pesquisar", true);
//
//        if (nome != null)
//            t.setParameter("nome", nome);
//
//        if (nomeDoMeio != null)
//            t.setParameter("nomeDoMeio", nomeDoMeio);
//        
//        if (sobreNome != null)
//            t.setParameter("sobreNome", sobreNome);
//
//        List<AmbDiagnosticoHasDoenca> resultado = t.getResultList();
//
//        return resultado;
//    }
    
//DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome)    

    public String constroiQuery(AmbDiagnosticoHasDoenca item, String nome, String nomeDoMeio, String sobreNome)
    {
        String query = "SELECT DISTINCT (a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome), "
                     + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio), "
                     + "(a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome) "
                     + "FROM AmbDiagnosticoHasDoenca a WHERE :pesquisar = :pesquisar";

        if (nome != null)
            query += " AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome = :nome";
        
        if (nomeDoMeio != null)
            query += " AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio = :nomeDoMeio";

        if (sobreNome != null)
            query += " AND a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome = :sobreNome";

//        query += " ORDER BY a.fkIdDiagnostico.dataHoraDiagnostico DESC";

        return query;
    }
    
//  CriteriaQuery<Country> q = cb.createQuery(Country.class);
//  Root<Country> c = q.from(Country.class);
//  q.select(c.get("currency")).distinct(true);    
    
    public List<AmbDiagnosticoHasDoenca> findPacientes(AmbDiagnosticoHasDoenca item, String nome, String nomeDoMeio, String sobreNome)
    {
        String query = constroiQuery(item, nome, nomeDoMeio, sobreNome);

        Query t = em.createQuery(query);

        t.setParameter("pesquisar", true);

        if (nome != null)
            t.setParameter("nome", nome);

        if (nomeDoMeio != null)
            t.setParameter("nomeDoMeio", nomeDoMeio);
        
        if (sobreNome != null)
            t.setParameter("sobreNome", sobreNome);

        List<AmbDiagnosticoHasDoenca> resultado = t.getResultList();

        return resultado;
    }  
    
//    public String constroiQuery(String nome, String nome_do_meio, String sobre_nome)
//    {
//        String query = "SELECT DISTINCT grl_pessoa.nome, grl_pessoa.nome_do_meio, grl_pessoa.sobre_nome FROM public.grl_pessoa WHERE ";
//
//        if (nome != null)
//            query += " grl_pessoa.nome = nome AND";
//        
//        if (nome_do_meio != null)
//            query += " grl_pessoa.nome_do_meio = nome_do_meio AND ";
//
//        if (sobre_nome != null)
//            query += " grl_pessoa.sobre_nome = sobre_nome LIMIT 10 ";
//
////        query += " ORDER BY a.fkIdDiagnostico.dataHoraDiagnostico DESC";
//
//        return query;
//    }    
//    
//    public List<GrlPessoa> findPacientes(String nome, String nome_do_meio, String sobre_nome)
//    {
//        String query = constroiQuery(nome, nome_do_meio, sobre_nome);
//
//        Query t = em.createNativeQuery(query);
//
////        t.setParameter("pesquisar", true);
//
//        if (nome != null)
//            t.setParameter("nome", nome);
//
//        if (nome_do_meio != null)
//            t.setParameter("nome_do_meio", nome_do_meio);
//        
//        if (sobre_nome != null)
//            t.setParameter("sobre_nome", sobre_nome);
//
//        List<GrlPessoa> resultado = t.getResultList();
//
//        return resultado;
//    }     
    
//    public List<GrlPessoa> findPacientes(/*GrlPessoa item, */String nome, String nome_do_meio, String sobre_nome)
//    {
////        String query = constroiQuery(item, nome, nomeDoMeio, sobreNome);
//
//        Query t = em.createNativeQuery("SELECT DISTINCT grl_pessoa.nome, " +
//                                       "grl_pessoa.nome_do_meio, " +
//                                       "grl_pessoa.sobre_nome " +
//                                       "FROM  " +
//                                       "public.grl_pessoa " +
//                                       "WHERE grl_pessoa.nome = nome AND " +
//                                       "grl_pessoa.nome_do_meio = nome_do_meio AND " +
//                                       "grl_pessoa.sobre_nome = sobre_nome ");//LIMIT 10
//
////        t.setParameter("pesquisar", true);
////
//        if (nome != null)
//            t.setParameter("nome", nome);
//
//        if (nome_do_meio != null)
//            t.setParameter("nome_do_meio", nome_do_meio);
//        
//        if (sobre_nome != null)
//            t.setParameter("sobre_nome", sobre_nome);
////
//        List<GrlPessoa> resultado = t.getResultList();
//
//        return resultado/*t.getResultList()resultado*/;
//    }      
}
