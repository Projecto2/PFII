/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhFuncionario;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhFuncionarioFacade extends AbstractFacade<RhFuncionario>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhFuncionarioFacade ()
    {
        super(RhFuncionario.class);
    }

    @Override
    public List<RhFuncionario> findAll ()
    {
        System.out.println("////findAll");
        Query t = em.createQuery("SELECT f FROM RhFuncionario f ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return t.getResultList();
    }

    public List<RhFuncionario> findAllSemHorarioGeral ()
    {
        System.out.println("////findAllSemHorarioGeral");
        Query t = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.fkIdHorarioGeralDeTrabalho IS NULL ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return t.getResultList();
    }

    /**
     *
     * @author Helga Jones
     * @return
     */
    public List<RhFuncionario> listaDosEnfermeiros ()
    {
        System.out.println("////listaDosEnfermeiros");
        Query query = em.createQuery("select f from RhFuncionario f where f.fkIdProfissao.descricao ='Enfermeiro' ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return query.getResultList();
    }

    /**
     * Retorna a lista de todos os médicos
     *
     * @return
     */
    public List<RhFuncionario> listaDosMedicos ()
    {
        System.out.println("////listaDosMedicos");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f "
                                     + "WHERE f.fkIdProfissao.descricao ='Médico' ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return query.getResultList();
    }

    /**
     * Retorna a lista de todos os funcionários em ordem alfabética
     *
     * @return
     */
    public List<RhFuncionario> findAllEmOrdemAlfabetica ()
    {
        System.out.println("////findAllEmOrdemAlfabetica");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class);

        return query.getResultList();
    }

    /**
     * Encontra os funcionários cujo nome contenha o texto passado
     *
     * @param texto
     * @return
     */
    public List<RhFuncionario> findContainsNome (String texto)
    {
        System.out.println("////findContainsNome");
        Query query;

        if (texto != null && !texto.isEmpty())
        {
            query = em.createQuery("SELECT f FROM RhFuncionario f WHERE CONCAT(f.fkIdPessoa.nome,f.fkIdPessoa.nomeDoMeio,f.fkIdPessoa.sobreNome) LIKE :nome "
                                   + "ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class)
                    .setParameter("nome", "%" + texto.trim() + "%");
        }
        else
        {
            query = em.createNativeQuery("SELECT * FROM rh_funcionario f, grl_pessoa p WHERE f.fk_id_pessoa = p.pk_id_pessoa "
                                         + "ORDER BY p.nome, p.nome_do_meio, p.sobre_nome LIMIT 100", RhFuncionario.class);
        }

        return query.getResultList();
    }

    /**
     * Encontra um funcionário de acordo o seu id de pessoa
     *
     * @param idPessoa
     * @return
     */
    public List<RhFuncionario> findPorIdPessoa (Integer idPessoa)
    {
        System.out.println("////findPorIdPessoa");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.fkIdPessoa.pkIdPessoa = :idPessoa", RhFuncionario.class).setParameter("idPessoa", idPessoa);

        return query.getResultList();
    }

    /**
     * Encontra um funcionário de acordo o seu número de cartão
     *
     * @param numCartao
     * @return
     */
    public List<RhFuncionario> findPorNumeroCartaoIdentidade (Integer numCartao)
    {
        System.out.println("////findPorNumeroCartaoIdentidade");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.numeroCartao = :numCartao", RhFuncionario.class).setParameter("numCartao", numCartao);

        return query.getResultList();
    }

    /**
     * Encontra um funcionário de acordo o seu número do BI
     *
     * @param numBI
     * @return
     */
    public List<RhFuncionario> findPorNumeroBI (String numBI)
    {
        System.out.println("////findPorNumeroBI");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.numeroBi = :numBI", RhFuncionario.class).setParameter("numBI", numBI);

        return query.getResultList();
    }

    /**
     * Encontra um funcionário de acordo o seu número de Segurança Social
     *
     * @param numSegSocial
     * @return
     */
    public List<RhFuncionario> findPorNumeroSegurancaSocial (String numSegSocial)
    {
        System.out.println("ola");
        System.out.println("////findPorNumeroSegurancaSocial");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.numeroSegurancaSocial = :numSegSocial", RhFuncionario.class).setParameter("numSegSocial", numSegSocial);

        return query.getResultList();
    }

    /**
     * Pesquisa todos os funcionários com registo de faltas num determinado
     * intervalo de datas por ordem alfabética
     *
     * @param dataInf
     * @param dataSup
     * @return
     */
    public List<RhFuncionario> findFuncionariosPorDataDeFaltas (Date dataInf, Date dataSup)
    {
        Query query;
        query = em.createQuery("SELECT DISTINCT fun FROM RhFuncionario fun WHERE fun.pkIdFuncionario "
                               + "IN (SELECT falta.fkIdFuncionario.pkIdFuncionario FROM RhFuncionarioHasRhTipoFalta falta WHERE falta.data BETWEEN :dataInf AND :dataSup) "
                               + "ORDER BY fun.fkIdPessoa.nome, fun.fkIdPessoa.nomeDoMeio, fun.fkIdPessoa.sobreNome", RhFuncionario.class)
                .setParameter("dataInf", dataInf).setParameter("dataSup", dataSup);

        return query.getResultList();
    }

    /**
     *
     * @author Garcia Paulo
     * @param idEspecialidade
     * @return
     */
    public List<RhFuncionario> findMedicosAtivosEspecialidade (int idEspecialidade)
    {
        System.out.println("/////");
        String qry = "SELECT medico FROM RhFuncionario medico WHERE medico.fkIdProfissao.descricao ='Médico' AND medico.fkIdEstadoFuncionario.descricao = 'Activo' AND medico.fkIdPessoa.nome <> 'Root' ";
        if (idEspecialidade > 0)
        {
            qry += " AND (medico.fkIdEspecialidade1.pkIdEspecialidade = :idEspecialidade OR medico.fkIdEspecialidade2.pkIdEspecialidade = :idEspecialidade)";
        }

        Query query = em.createQuery(qry, RhFuncionario.class);

        if (idEspecialidade > 0)
        {
            query.setParameter("idEspecialidade", idEspecialidade);
        }

        System.out.println(" " + qry);
        return query.getResultList();
    }

    public List<RhFuncionario> findMedicos ()
    {
        System.out.println("olá");
        String qry = "SELECT medico FROM RhFuncionario medico WHERE medico.fkIdProfissao.descricao ='Médico' AND medico.fkIdPessoa.nome <> 'Root' ";

        Query query = em.createQuery(qry, RhFuncionario.class);

        System.out.println(" " + qry);
        return query.getResultList();
    }

    /**
     * Retorna a lista de todos os funcionários com contrato
     *
     * @return
     */
    public List<RhFuncionario> findFuncionariosComContrato ()
    {
        System.out.println("/////");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f "
                                     + "WHERE f.fkIdTipoFuncionario.descricao = :descricao AND f.fkIdPessoa.nome <> 'Root' "
                                     + "ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class).setParameter("descricao", "F. Contratado");

        return query.getResultList();
    }

    /**
     * Retorna a lista de todos os funcionários de acordo uma determinada secção
     * de trabalho
     *
     * @param idSeccao
     * @return
     */
    public List<RhFuncionario> findFuncionariosPorSeccao (Integer idSeccao)
    {
        System.out.println("/////");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.fkIdSeccaoTrabalho.pkIdSeccaoTrabalho = :idSeccao AND f.fkIdPessoa.nome <> 'Root' "
                                     + "ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class)
                .setParameter("idSeccao", idSeccao);

        return query.getResultList();
    }

    /**
     * Retorna a lista de todos os funcionários de acordo uma determinada secção
     * de trabalho
     *
     * @param idSeccao
     * @return
     */
    public List<RhFuncionario> findFuncionariosPorSeccaoSemReformadosEDemitidos (Integer idSeccao)
    {
        System.out.println("/////");
        Query query = em.createQuery("SELECT f FROM RhFuncionario f WHERE f.fkIdSeccaoTrabalho.pkIdSeccaoTrabalho = :idSeccao "
                                     + "AND f.fkIdEstadoFuncionario.descricao <> 'Reformado' "
                                     + "AND f.fkIdEstadoFuncionario.descricao <> 'Demitido' "
                                     + "AND f.fkIdPessoa.nome <> 'Root' "
                                     + "ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome", RhFuncionario.class)
                .setParameter("idSeccao", idSeccao);

        return query.getResultList();
    }

    /**
     * Pesquisa todos os funcionários de acordo com todos os campos que tiverem
     * dados no objecto funcionario, ou seja, procura de acordo os critérios
     * introduzidos
     *
     * @param funcionario
     * @return
     */
    public List<RhFuncionario> findFuncionario (RhFuncionario funcionario)
    {
        System.out.println("/////");
        String query = constroiQuery(funcionario);

        Query t = em.createQuery(query, RhFuncionario.class);


        if (funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario() != null)
        {
            t.setParameter("estadoFuncionario", funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario());
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() != null)
        {
            t.setParameter("tipoFuncionario", funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario());
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() != null)
        {
            t.setParameter("centro", funcionario.getFkIdCentroHospitalar().getPkIdCentro());
        }

        if (funcionario.getFkIdSeccaoTrabalho().getFkIdDepartamento() != null && funcionario.getFkIdSeccaoTrabalho().getFkIdDepartamento().getPkIdDepartamento() != null )
        {
            t.setParameter("departamento", funcionario.getFkIdSeccaoTrabalho().getFkIdDepartamento().getPkIdDepartamento());
        }
        
        if (funcionario.getFkIdPessoa().getNome() != null && !funcionario.getFkIdPessoa().getNome().trim().isEmpty())
        {
            t.setParameter("nome", funcionario.getFkIdPessoa().getNome() + "%");
        }

        if (funcionario.getFkIdPessoa().getNomeDoMeio() != null && !funcionario.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            t.setParameter("nomeDoMeio", funcionario.getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (funcionario.getFkIdPessoa().getSobreNome() != null && !funcionario.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            t.setParameter("sobreNome", funcionario.getFkIdPessoa().getSobreNome() + "%");
        }

        if (funcionario.getNumeroCartao() != null)
        {
            t.setParameter("numeroCartao", funcionario.getNumeroCartao());
        }

        if (funcionario.getFkIdProfissao().getPkIdProfissao() != null)
        {
            t.setParameter("profissao", funcionario.getFkIdProfissao().getPkIdProfissao());
        }

        if (funcionario.getFkIdCategoria().getPkIdCategoriaProfissional() != null)
        {
            t.setParameter("categoria", funcionario.getFkIdCategoria().getPkIdCategoriaProfissional());
        }

        if (funcionario.getFkIdEspecialidade1().getPkIdEspecialidade() != null)
        {
            t.setParameter("especialidade", funcionario.getFkIdEspecialidade1().getPkIdEspecialidade());
        }

        t.setMaxResults(100);

        List<RhFuncionario> resultado = t.getResultList();

        return resultado;
    }

    /**
     * Constrói uma query JPQL com todos os campos que tiverem dados no objecto
     * funcionario
     *
     * @param funcionario
     * @return
     */
    public String constroiQuery (RhFuncionario funcionario)
    {
        String query = "SELECT f FROM RhFuncionario f WHERE f.pkIdFuncionario = f.pkIdFuncionario";

        if (funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario() != null)
        {
            query += " AND f.fkIdEstadoFuncionario.pkIdEstadoFuncionario = :estadoFuncionario";
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() != null)
        {
            query += " AND f.fkIdTipoFuncionario.pkIdTipoFuncionario = :tipoFuncionario";
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() != null)
        {
            query += " AND f.fkIdCentroHospitalar.pkIdCentro = :centro";
        }

        if (funcionario.getFkIdSeccaoTrabalho().getFkIdDepartamento() != null && funcionario.getFkIdSeccaoTrabalho().getFkIdDepartamento().getPkIdDepartamento() != null )
        {
            query += " AND f.fkIdSeccaoTrabalho.fkIdDepartamento.pkIdDepartamento = :departamento";
        }
        
        if (funcionario.getFkIdPessoa().getNome() != null && !funcionario.getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND LOWER(f.fkIdPessoa.nome) LIKE LOWER(:nome)";
        }

        if (funcionario.getFkIdPessoa().getNomeDoMeio() != null && !funcionario.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND LOWER(f.fkIdPessoa.nomeDoMeio) LIKE LOWER(:nomeDoMeio)";
        }

        if (funcionario.getFkIdPessoa().getSobreNome() != null && !funcionario.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND LOWER(f.fkIdPessoa.sobreNome) LIKE LOWER(:sobreNome)";
        }

        if (funcionario.getNumeroCartao() != null)
        {
            query += " AND f.numeroCartao = :numeroCartao";
        }

        if (funcionario.getFkIdProfissao().getPkIdProfissao() != null)
        {
            query += " AND f.fkIdProfissao.pkIdProfissao = :profissao";
        }

        if (funcionario.getFkIdCategoria().getPkIdCategoriaProfissional() != null)
        {
            query += " AND f.fkIdCategoria.pkIdCategoriaProfissional = :categoria";
        }

        if (funcionario.getFkIdEspecialidade1().getPkIdEspecialidade() != null)
        {
            query += " AND (f.fkIdEspecialidade1.pkIdEspecialidade = :especialidade OR f.fkIdEspecialidade2.pkIdEspecialidade = :especialidade)";
        }

        query += " AND LOWER(f.fkIdPessoa.nome) <> 'root' ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome";

        return query;
    }

    /**
     *
     * @author Elizangela Gaspar
     * @param funcionario
     * @return
     */
    public List<RhFuncionario> findFuncionarioSemConta (RhFuncionario funcionario)
    {
        System.out.println("/////");
        String query = constroiQueryParaContas(funcionario);

        Query t = em.createQuery(query, RhFuncionario.class);

        if (funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario() != null)
        {
            t.setParameter("estadoFuncionario", funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario());
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() != null)
        {
            t.setParameter("tipoFuncionario", funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario());
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() != null)
        {
            t.setParameter("centro", funcionario.getFkIdCentroHospitalar().getPkIdCentro());
        }

        if (funcionario.getFkIdPessoa().getNome() != null && !funcionario.getFkIdPessoa().getNome().trim().isEmpty())
        {
            t.setParameter("nome", funcionario.getFkIdPessoa().getNome() + "%");
        }

        if (funcionario.getFkIdPessoa().getNomeDoMeio() != null && !funcionario.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            t.setParameter("nomeDoMeio", funcionario.getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (funcionario.getFkIdPessoa().getSobreNome() != null && !funcionario.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            t.setParameter("sobreNome", funcionario.getFkIdPessoa().getSobreNome() + "%");
        }

        if (funcionario.getNumeroCartao() != null)
        {
            t.setParameter("numeroCartao", funcionario.getNumeroCartao());
        }

        if (funcionario.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            t.setParameter("sexo", funcionario.getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        List<RhFuncionario> resultado = t.getResultList();

        return resultado;
    }

    /**
     *
     * @author Elizangela Gaspar
     * @param funcionario
     * @return
     */
    public String constroiQueryParaContas (RhFuncionario funcionario)
    {
        String query = "SELECT f FROM RhFuncionario f WHERE f.pkIdFuncionario not in (SELECT c.fkIdFuncionario.pkIdFuncionario from SegConta c)";

        if (funcionario.getFkIdEstadoFuncionario().getPkIdEstadoFuncionario() != null)
        {
            query += " AND f.fkIdEstadoFuncionario.pkIdEstadoFuncionario = :estadoFuncionario";
        }

        if (funcionario.getFkIdTipoFuncionario().getPkIdTipoFuncionario() != null)
        {
            query += " AND f.fkIdTipoFuncionario.pkIdTipoFuncionario = :tipoFuncionario";
        }

        if (funcionario.getFkIdCentroHospitalar().getPkIdCentro() != null)
        {
            query += " AND f.fkIdCentroHospitalar.pkIdCentro = :centro";
        }

        if (funcionario.getFkIdPessoa().getNome() != null && !funcionario.getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND f.fkIdPessoa.nome LIKE :nome";
        }

        if (funcionario.getFkIdPessoa().getNomeDoMeio() != null && !funcionario.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND f.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (funcionario.getFkIdPessoa().getSobreNome() != null && !funcionario.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND f.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (funcionario.getNumeroCartao() != null)
        {
            query += " AND f.numeroCartao = :numeroCartao";
        }

        if (funcionario.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND f.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        query += " ORDER BY f.fkIdPessoa.nome, f.fkIdPessoa.nomeDoMeio, f.fkIdPessoa.sobreNome";

        return query;
    }

}
