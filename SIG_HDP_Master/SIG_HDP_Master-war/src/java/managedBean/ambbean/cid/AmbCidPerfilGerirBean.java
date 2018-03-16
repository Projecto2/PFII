/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidConfiguracoes;
import entidade.AmbCidPerfilTipos;
import entidade.AmbCidPerfis;
import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import entidade.SegConta;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidPerfilTiposFacade;
import sessao.AmbCidPerfisDoencasFacade;
import sessao.AmbCidPerfisFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidPerfilGerirBean implements Serializable
{

    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;

    @EJB
    private AmbCidPerfisDoencasFacade ambCidPerfisDoencasFacade;

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;

    @EJB
    private AmbCidPerfilTiposFacade ambCidPerfilTiposFacade;

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    @Resource
    private UserTransaction userTransaction;

    private SegConta segConta;

    private AmbCidPerfis ambCidPerfis;

    private AmbCidConfiguracoes ambCidConfiguracoes;

    private boolean perfilPreferencialStatus, listaPerfisRendered;

    /**
     * Creates a new instance of AmbCidPerfilAlterarBean
     */
    public AmbCidPerfilGerirBean()
    {
    }

    public static AmbCidPerfilGerirBean getInstanciaBean()
    {
        return (AmbCidPerfilGerirBean) GeradorCodigo.getInstanciaBean("ambCidPerfilAlterarBean");
    }

    public void init()
    {
//System.err.println("0: AmbCidPerfilGerirBean.init()");
        segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        int idConta = segConta.getPkIdConta();
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(idConta);

        initAmbCidPerfis();
//System.err.println("1: AmbCidPerfilGerirBean.init()");        
    }
    
    public int obterPrioridadeMaxima(String perfilNome)
    {
        if (perfilNome.equals(util.amb.Defs.CID_10))
        {
            return util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA;
        }
        return this.ambCidPerfisDoencasFacade.obterPrioridadeMaxima(perfilNome);
    }

    public void initAmbCidPerfis()
    {
//System.err.println("0: AmbCidPerfilGerirBean.initAmbCidPerfis()");        
        perfilPreferencialStatus = listaPerfisRendered = false;
        ambCidPerfis = AmbCidPerfisBean.getInstancia();
//System.err.println("1: AmbCidPerfilGerirBean.initAmbCidPerfis()");        
        initAmbCidPerfisEspecialidades();
//System.err.println("2: AmbCidPerfilGerirBean.initAmbCidPerfis()");        
        AmbCidPerfilTipos ambCidPerfilTipos = this.ambCidPerfilTiposFacade.findByNome(util.amb.Defs.PERFIL_TIPO_PRIVADO);
        this.ambCidPerfis.setFkIdTipo(ambCidPerfilTipos);
//System.err.println("3: AmbCidPerfilGerirBean.initAmbCidPerfis()");                
    }

    GrlEspecialidade initEspecialidadePreferencial()
    {

        int idEspecialidade = ambCidConfiguracoes.getIdEspecialidade();
        if (idEspecialidade == 0)
        {
            return null;
        }
        return this.grlEspecialidadeFacade.find(idEspecialidade);
    }

    public void initAmbCidPerfisEspecialidades()
    {
//System.err.println("0: AmbCidPerfilGerirBean.initAmbCidPerfis()");                
        GrlEspecialidade especialidadePreferencial = initEspecialidadePreferencial();
        if (especialidadePreferencial != null)
        {
//System.err.println("1: AmbCidPerfilGerirBean.initAmbCidPerfis()");                    
            ambCidPerfis.setFkIdEspecialidades(especialidadePreferencial);
//System.err.println("2: AmbCidPerfilGerirBean.initAmbCidPerfis()");                    
            return;
        }
//System.err.println("3: AmbCidPerfilGerirBean.initAmbCidPerfis()");                
        RhProfissao rhProfissao = this.rhProfissaoFacade.findByDescricao(util.amb.Defs.PROFISSAO_MEDICO);
// procurar entender o cod abaixo
        //ambCidPerfis.getFkIdEspecialidades().setFkIdProfissao(rhProfissao);
        ambCidPerfis.getFkIdEspecialidades().setFkIdProfissao(rhProfissao);
//System.err.println("4: AmbCidPerfilGerirBean.initAmbCidPerfis()\tprofissao: " + rhProfissao.getDescricao());                
    }

    public boolean tipoDoPerfilCanChange()
    {
        /* 
         se for privado
         retorna true
         se tiver perfis descendentes
         retorna false
         retorna true
         */

        if (this.ambCidPerfis.getFkIdTipo().getNome().equals("Privado"))
        {
            return true;
        }
        return !this.ambCidPerfisFacade.existemAmbCidPerfisDescendentes(this.ambCidPerfis.getPkIdNome());
    }

    public String geraLegendaListaPerfis()
    {
        String legenda = "Lista dos Perfis autorizados";
        if (isListaPerfisRendered())
        {
            legenda += " na Especialidade de '" + this.ambCidPerfis.getFkIdEspecialidades().getDescricao() + "'";
        }
        legenda += " para a Conta '" + this.segConta.getNomeUtilizador() + "' " + obterAnexoEtiquetaListaPerfis();
        return legenda;
    }

    public String obterAnexoEtiquetaListaPerfis()
    {
        String msg = "";
        String nomePerfilPreferencial = this.ambCidConfiguracoes.getIdNomePerfil();
        if (nomePerfilPreferencial != null)
        {
            msg += " >>>>>>>>>>>>>>>>>>>>>> Perfil Preferencial Corrente: " + nomePerfilPreferencial;
        }
        return msg;
    }

    public String obterPerfilPai()
    {
        AmbCidPerfis perfilPai = this.ambCidPerfis.getFkIdPerfilPai();
        return perfilPai == null ? null : perfilPai.getPkIdNome();
    }

    public void installTipo(int pkIdTipos)
    {
        AmbCidPerfilTipos ambCidPerfilTipos = this.ambCidPerfilTiposFacade.find(pkIdTipos);
        this.ambCidPerfis.setFkIdTipo(ambCidPerfilTipos);
        //System.err.println("0: AmbCidPerfilGerirBean.installTipo()");
    }

    public void installEspecialidade(int pkIdEspecialidade)
    {
//System.err.println("0: AmbCidPerfilGerirBean.installEspecialidade()\tpkIdEspecialidade: " + pkIdEspecialidade);
        GrlEspecialidade especialidade = this.grlEspecialidadeFacade.find(pkIdEspecialidade);
        //grlEspecialidade.setFkIdProfissao(this.ambCidPerfis.getFkIdEspecialidades().getFkIdProfissao());
        this.ambCidPerfis.setFkIdEspecialidades(especialidade);

//System.err.println("1: AmbCidPerfilGerirBean.installEspecialidade()\tpkIdEspecialidade: " + ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
    }

    public boolean existemAmbCidPerfisDescendentes(String pkIdNome)
    {
        return this.ambCidPerfisFacade.existemAmbCidPerfisDescendentes(pkIdNome);
    }

    public boolean existemAmbCidPerfisDoencas(String perfilPkIdNome)
    {
        return this.ambCidPerfisDoencasFacade.findByFk_id_perfil(perfilPkIdNome) != null;
    }

    public void apagarPerfilPreferencial(String pkIdNome)
    {
        //System.err.println("0: AmbCidPerfilGerirBean.apagarPerfilPreferencial()");
        String perfilPreferencial = this.ambCidConfiguracoes.getIdNomePerfil();
        if (perfilPreferencial.equals(pkIdNome))
        {
            ambCidConfiguracoes.setIdNomePerfil(null);
            this.ambCidConfiguracoesFacade.edit(ambCidConfiguracoes);
        }
    }

    public String apagar(String pkIdNome)
    {
        //System.err.println("0: AmbCidPerfilGerirBean.apagar()");
        if (existemAmbCidPerfisDoencas(pkIdNome))
        {

            //System.err.println("1: AmbCidPerfilGerirBean.apagar()\tpkIdNome: " + pkIdNome);
            util.Mensagem.warnMsg("Nao foi possivel apagar o perfil '" + pkIdNome + "' porque existem registos de doencas e respectivas prioridades dependentes dele");
            /*util.amb.Mensagem.enviarJanelaMensagemAlerta("Falha",
             "Nao foi possivel apagar o perfil '" + pkIdNome + "' porque existem registos de doencas e respectivas prioridades dependentes dele");
             */
            return "perfilAlterarAmb?faces-redirect=true";
        }
        if (existemAmbCidPerfisDescendentes(pkIdNome))
        {
            //System.err.println("1: AmbCidPerfilGerirBean.apagar()\tpkIdNome: " + pkIdNome);
            util.Mensagem.warnMsg("Nao foi possivel apagar o perfil '" + pkIdNome + "' porque existem perfis descendentes dele");
            return "perfilAlterarAmb?faces-redirect=true";
        }
        try
        {
            //System.err.println("1.2: AmbCidPerfilGerirBean.apagar()\tpkIdNome: " + pkIdNome);
            this.userTransaction.begin();
            this.ambCidPerfisFacade.remove(new AmbCidPerfis(pkIdNome));
            this.userTransaction.commit();
            apagarPerfilPreferencial(pkIdNome);
            util.Mensagem.sucessoMsg("perfil '" + pkIdNome + "' apagado com sucesso");
            //System.err.println("2: AmbCidPerfilGerirBean.apagar()");
            return "perfilAlterarAmb?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println("3: AmbCidPerfilGerirBean.apagar()");
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
                //System.err.println("4: AmbCidPerfilGerirBean.apagar()");
            }
        }
        //System.err.println("5: AmbCidPerfilGerirBean.apagar()");

        return "perfilAlterarAmb?faces-redirect=true";
    }

    public void gravarPerfilPreferencial()
    {
        //System.err.println("0: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());

        //System.err.println("03: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : "not null"));
        if (perfilPreferencialStatus)
        {
            //System.err.println("1: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            this.ambCidConfiguracoes.setIdNomePerfil(this.ambCidPerfis.getPkIdNome());
            //System.err.println("1.1: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            ambCidConfiguracoes.setIdDoencasPrioridades(obterPrioridadeMaxima(this.ambCidPerfis.getPkIdNome()));
            this.ambCidConfiguracoes.setIdEspecialidade(this.ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
            this.ambCidConfiguracoesFacade.edit(ambCidConfiguracoes);
            //System.err.println("1.2: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            return;
        }
        String perfilPreferencial = ambCidConfiguracoes.getIdNomePerfil();
        //System.err.println("2: AmbCidPerfilGerirBean.gravarPerfilPreferencial()");
        if (perfilPreferencial != null && perfilPreferencial.equals(this.ambCidPerfis.getPkIdNome()))
        {
            //System.err.println("3: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            this.ambCidConfiguracoes.setIdNomePerfil(null);
            //System.err.println("3.1: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            this.ambCidConfiguracoes.setIdEspecialidade(this.ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
            ambCidConfiguracoes.setIdDoencasPrioridades(util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA);

            this.ambCidConfiguracoes.setIdEspecialidade(this.ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
            this.ambCidConfiguracoesFacade.edit(ambCidConfiguracoes);
            //System.err.println("4: AmbCidPerfilGerirBean.gravarPerfilPreferencial()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
        }
    }

    public String alterar()
    {
        try
        {
            //System.err.println("0: AmbCidPerfilGerirBean.alterar()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            this.userTransaction.begin();
            this.ambCidPerfisFacade.edit(this.ambCidPerfis);
            //System.err.println("1: AmbCidPerfilGerirBean.alterar()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            this.userTransaction.commit();
            //System.err.println("2: AmbCidPerfilGerirBean.alterar()\tpkIdNome: " + ambCidPerfis.getPkIdNome());
            gravarPerfilPreferencial();
            util.Mensagem.sucessoMsg("perfil '" + ambCidPerfis.getPkIdNome() + "' actualizado com sucesso");
            //System.err.println("3: AmbCidPerfilGerirBean.alterar()");
            return "perfilAlterarAmb?faces-redirect=true";
        }

        catch (RollbackException e)
        {
            try
            {
                //System.err.println("4: AmbCidPerfilGerirBean.alterar()");
                this.userTransaction.rollback();
                //System.err.println("5: AmbCidPerfilGerirBean.alterar()");
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
                //System.err.println("6: AmbCidPerfilGerirBean.alterar()");
            }
        }

        catch (NotSupportedException | SystemException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e)
        {
            //System.err.println("7: AmbCidPerfilGerirBean.alterar()");
        }
        //System.err.println("7: AmbCidPerfilGerirBean.alterar()");

        return "perfilAlterarAmb?faces-redirect=true";
    }

    public boolean isAlterarBtDisable(AmbCidPerfis perfilVar)
    {
        SegConta contaDaCorrenteSessao = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        SegConta contaDoPerfil = perfilVar.getFkIdDono();
        return (contaDaCorrenteSessao.getPkIdConta() != contaDoPerfil.getPkIdConta());
    }

    public void preAlterar(AmbCidPerfis perfilVar)
    {
        System.err.println("0: AmbCidPerfilAlterarBean.preAlterar()");
        ambCidPerfis = perfilVar;
        initPerfilPrefencialStatus();
        System.err.println("2: AmbCidPerfilAlterarBean.preAlterar()\tperfilDoencaVar.perfil: " + ambCidPerfis.getPkIdNome());
    }

    public void initPerfilPrefencialStatus()
    {
        System.err.println("0: AmbCidPerfilAlterarBean.initPerfilPrefencialStatus()");
        String perfilPrefencialNome = this.ambCidConfiguracoes.getIdNomePerfil();
        if (perfilPrefencialNome != null && perfilPrefencialNome.equals(this.ambCidPerfis.getPkIdNome()))
        {
            this.perfilPreferencialStatus = true;
            System.err.println("1: AmbCidPerfilAlterarBean.initPerfilPrefencialStatus()");
        }
        System.err.println("2: AmbCidPerfilAlterarBean.initPerfilPrefencialStatus()");
    }

    public void installPerfilPreferencialStatus()
    {
        //System.err.println("0: AmbCidPerfilGerirBean.installPerfilPreferencial()\tperfilPreferencialStatus: " + perfilPreferencialStatus);
    }

    public AmbCidPerfis getAmbCidPerfis()
    {
        return ambCidPerfis;
    }

    public void setAmbCidPerfis(AmbCidPerfis ambCidPerfis)
    {
        this.ambCidPerfis = ambCidPerfis;
    }

    public boolean isPerfilPreferencialStatus()
    {
        return perfilPreferencialStatus;
    }

    public void setPerfilPreferencialStatus(boolean perfilPreferencialStatus)
    {
        this.perfilPreferencialStatus = perfilPreferencialStatus;
    }

    public SegConta getSegConta()
    {
        return segConta;
    }

    public void setSegConta(SegConta conta)
    {
        this.segConta = conta;
    }

    public boolean isListaPerfisRendered()
    {
        GrlEspecialidade grlEspecialidade = this.ambCidPerfis.getFkIdEspecialidades();
        if (grlEspecialidade == null
            || grlEspecialidade.getPkIdEspecialidade() == null
            || grlEspecialidade.getPkIdEspecialidade().intValue() == 0)
        {
            listaPerfisRendered = false;
        }
        else
        {
            listaPerfisRendered = true;
        }
        return listaPerfisRendered;
    }

    public void setListaPerfisRendered(boolean listaPerfisRendered)
    {
        this.listaPerfisRendered = listaPerfisRendered;
    }

}
