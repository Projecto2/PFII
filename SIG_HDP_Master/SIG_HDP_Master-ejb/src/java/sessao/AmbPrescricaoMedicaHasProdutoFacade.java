/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbPrescricaoMedicaHasProduto;
import entidade.FarmProduto;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbPrescricaoMedicaHasProdutoFacade extends AbstractFacade<AmbPrescricaoMedicaHasProduto>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbPrescricaoMedicaHasProdutoFacade()
    {
        super(AmbPrescricaoMedicaHasProduto.class);
    }

    public String constroiQueryConsulta (String nomeMedico, String nomeDoMeioMedico, String sobreNomeMedico, Date dataRegisto, int viaAdmissao, int formaFarmaceutica, int pkIdMedicamento, String horario, Long idConsulta)
    {   
        String query = "SELECT a FROM AmbPrescricaoMedicaHasProduto a WHERE a.pkIdPrescricaoMedicaProduto = a.pkIdPrescricaoMedicaProduto";

        if (idConsulta != null)   
           query += " AND a.kIdPrescricaoMedica.fkIdConsulta.pkIdConsulta =:pk_id_consulta";
                 
        if (nomeMedico != null && !nomeMedico.trim().isEmpty())    
           query += " AND a.fkIdPrescricaoMedica.fkIdFuncionario.fkIdPessoa.nome LIKE :nome"; 
                
        if (nomeDoMeioMedico != null && !nomeDoMeioMedico.trim().isEmpty())    
           query += " AND a.fkIdPrescricaoMedica.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeMeio"; 
                
        if (sobreNomeMedico != null && !sobreNomeMedico.trim().isEmpty()) 
           query += " AND a.fkIdPrescricaoMedica.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
         
        if (dataRegisto != null)     
           query += " AND a.fkIdPrescricaoMedica.dataHoraPrescricao =:data";
        
        if (pkIdMedicamento > 0)
           query += " AND a.fkIdFarmProduto.pkIdProduto =:pkIdMedicamento"; 
                 
        if (viaAdmissao > 0)
           query += " AND a.fkIdFarmProduto.fkIdViaAdministracao.pkIdViaAdministracao =:viaAdmissao";
             
        if (formaFarmaceutica > 0)
           query += " AND a.fkIdFarmProduto.fkIdFormaFarmaceutica.pkIdFormaFarmaceutica =:forma";
       
        if (horario != null && !horario.trim().isEmpty())
           query += " AND a.fkIdHoraMedicacao.descricao =:horario";
        
        query += " ORDER BY a.fkIdFarmProduto.descricao";
        
        return query;
    }
    
    public List<AmbPrescricaoMedicaHasProduto> pesquisarRegisto(String tipoServico, String nomeMedico, String nomeDoMeioMedico, String sobreNomeMedico, Date dataRegistoPesq, Long idConsulta, int viaAdmissao, int formaFarmaceutica, int pkIdMedicamento, String horario)
    {
        String query = constroiQueryConsulta(nomeMedico, nomeDoMeioMedico, sobreNomeMedico, dataRegistoPesq, viaAdmissao, formaFarmaceutica, pkIdMedicamento, horario, idConsulta);

        TypedQuery<AmbPrescricaoMedicaHasProduto> t = em.createQuery(query, AmbPrescricaoMedicaHasProduto.class);

        if (idConsulta != null)
            t.setParameter("pk_id_consulta", idConsulta);

        if (nomeMedico != null && !nomeMedico.trim().isEmpty())
            t.setParameter("nome", "%" + nomeMedico + "%");

        if (nomeDoMeioMedico != null && !nomeDoMeioMedico.trim().isEmpty())
            t.setParameter("nomeMeio", "%" + nomeDoMeioMedico + "%");

        if (sobreNomeMedico != null && !sobreNomeMedico.trim().isEmpty())
            t.setParameter("sobreNome", "%" + sobreNomeMedico + "%");

        if (dataRegistoPesq != null)
            t.setParameter("data", dataRegistoPesq);

        if (viaAdmissao > 0)
            t.setParameter("viaAdmissao", viaAdmissao);

        if (formaFarmaceutica > 0)
            t.setParameter("forma", formaFarmaceutica);

        if (pkIdMedicamento > 0)
            t.setParameter("pkIdMedicamento", pkIdMedicamento);

        if (horario != null && !horario.trim().isEmpty())
            t.setParameter("horario", horario);

        List<AmbPrescricaoMedicaHasProduto> resultado = t.getResultList();

        return resultado;
    }
   
    public String constroiQueryPrescricoes(AmbPrescricaoMedicaHasProduto item, Date dataInicio, Date dataFim)
    {
        String query = "Select a FROM AmbPrescricaoMedicaHasProduto a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.fkIdPrescricaoMedica.dataHoraPrescricao >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.fkIdPrescricaoMedica.dataHoraPrescricao <= :dataFim";
        }

        query += " ORDER BY a.fkIdPrescricaoMedica.dataHoraPrescricao DESC";

        return query;
    }
    public List<AmbPrescricaoMedicaHasProduto> findPrescricao(AmbPrescricaoMedicaHasProduto item, Date dataInicio, Date dataFim)
    {
        String query = constroiQueryPrescricoes(item, dataInicio, dataFim);

        Query t = em.createQuery(query);

        t.setParameter("pesquisar", true);

        if (dataInicio != null)
        {
            t.setParameter("dataInicio", dataInicio);
        }

        if (dataFim != null)
        {
            t.setParameter("dataFim", dataFim);
        }

        List<AmbPrescricaoMedicaHasProduto> resultado = t.getResultList();

        return resultado;
    }
    
    public String constroiQueryFarmProduto (int viaAdmissao, int formaFarmaceutica, int categoria)
    {       
        String query = "SELECT f FROM FarmProduto f WHERE f.pkIdProduto = f.pkIdProduto";        
        
        if (viaAdmissao > 0)
           query += " AND f.fkIdViaAdministracao.pkIdViaAdministracao =:viaAdmissao";
             
        if (formaFarmaceutica > 0)
           query += " AND f.fkIdFormaFarmaceutica.pkIdFormaFarmaceutica =:forma";
               
        if (categoria > 0)    
           query += " AND f.fkIdCategoriaMedicamento.pkIdCategoriaMedicamento =:categoria";
        
        query += " ORDER BY f.descricao";
        
        return query;
    }
    
    public List<FarmProduto> produtosPorViaAdmissao(int viaAdmissao, int formaFarmaceutica, int categoria)
    {
        String query = constroiQueryFarmProduto(viaAdmissao, formaFarmaceutica, categoria);

        TypedQuery<FarmProduto> t = em.createQuery(query, FarmProduto.class);
        
        if (viaAdmissao > 0)    
           t.setParameter("viaAdmissao", viaAdmissao);
        
        if (formaFarmaceutica > 0)    
           t.setParameter("forma", formaFarmaceutica);
                
        if (categoria > 0)    
           t.setParameter("categoria", categoria);
        
        List<FarmProduto> resultado = t.getResultList();

        return resultado;
    }
}
