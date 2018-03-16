/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidAgrupamentos;
import entidade.AmbCidCapitulos;
import entidade.AmbCidCategorias;
import entidade.AmbCidConfiguracoes;
import entidade.AmbCidDoencasPrioridades;
import entidade.AmbCidPerfis;
import entidade.AmbCidPerfisDoencas;
import entidade.AmbCidSubcategorias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidPerfisDoencasFacade;
import sessao.AmbCidSubcategoriasFacade;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidPerfisDoencasBean implements Serializable
{

    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;

    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;

    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;

    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;

    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;

    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;

    @EJB
    private AmbCidPerfisDoencasFacade ambCidPerfisDoencasFacade;

    @Resource
    private UserTransaction userTransaction;

    private AmbCidConfiguracoes ambCidConfiguracoes;
    private AmbCidPerfis ambCidPerfis;
    private AmbCidPerfisDoencas ambCidPerfisDoencas;

    private int idConta;

    private boolean novaDoencaRedered, perfilPreferencial;

    /**
     * Creates a new instance of PerfilNovoDoencasBean
     */
    public AmbCidPerfisDoencasBean()
    {

    }

    public static AmbCidPerfisDoencasBean getInstanciaBean()
    {
        return (AmbCidPerfisDoencasBean) GeradorCodigo.getInstanciaBean("ambCidPerfisDoencasBean");
    }

    public static AmbCidPerfisDoencas getInstancia()
    {
        AmbCidPerfisDoencas ambCidPerfisDoencas2 = new AmbCidPerfisDoencas();
        ambCidPerfisDoencas2.setFkIdPrioridades(new AmbCidDoencasPrioridades());
        ambCidPerfisDoencas2.setFkIdSubcategorias(AmbCidSubcategoriasBean.getInstancia());
        return ambCidPerfisDoencas2;
    }

    public String init(AmbCidPerfis ambCidPerfis)
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.init()");
        //perfilPreferencial = false;
        this.ambCidPerfis = ambCidPerfis;

        idConta = this.ambCidPerfis.getFkIdDono().getPkIdConta();
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(idConta);
        initPerfilPreferencial();
        this.ambCidPerfisDoencas = this.getInstancia();
        ambCidPerfisDoencas.setFkIdPerfil(ambCidPerfis);
        novaDoencaRedered = true;
        //System.err.println("1: AmbCidPerfisDoencasBean.init()");

        this.resetAmbiCidCapitulos();

        return "perfilNovoDoencasAmb?faces-redirect=true";
    }

    public void initPerfilPreferencial()
    {
        String perfilPreferencialNome = ambCidConfiguracoes.getIdNomePerfil();
        if (perfilPreferencialNome == null)
        {
            perfilPreferencial = false;
            return;
        }
        perfilPreferencial = perfilPreferencialNome.equals(this.ambCidPerfis.getPkIdNome());
    }

    public boolean temDoencasRegistadas(String perfilNome)
    {
        if (perfilNome.equals(util.amb.Defs.CID_10))
        {
            return true;
        }
        return this.ambCidPerfisDoencasFacade.temDoencasRegistadas(perfilNome);
    }

    public int obterPrioridadeMaxima(String perfilNome)
    {
        if (perfilNome.equals(util.amb.Defs.CID_10))
        {
            return util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA;
        }
        return this.ambCidPerfisDoencasFacade.obterPrioridadeMaxima(perfilNome);
    }
    /*
     public AmbCidSubcategorias obterAmbCidSubcategorias()
     {
     AmbCidSubcategorias ambCidSubcategorias = new AmbCidSubcategorias();
     AmbCidCategorias ambCidCategorias = new AmbCidCategorias();
     AmbCidAgrupamentos ambCidAgrupamentos = new AmbCidAgrupamentos();
     AmbCidCapitulos AmbCidCapitulos = new AmbCidCapitulos();

     ambCidAgrupamentos.setFkIdCapitulos(AmbCidCapitulos);
     ambCidCategorias.setFkIdAgrupamentos(ambCidAgrupamentos);
     ambCidSubcategorias.setFkIdCategorias(ambCidCategorias);

     return ambCidSubcategorias;
     }
     */

    public List<AmbCidPerfisDoencas> findAllByDoencas()
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.findAllByDoencas()");
        return this.ambCidPerfisDoencasFacade.findAllByDoencasFromPerfil(this.ambCidPerfis.getPkIdNome());
    }

    public List<AmbCidDoencasPrioridades> findAllOrderByPkIdSubcategorias(AmbCidPerfis ambCidPerfis, String pkIdSubcategorias)
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.findAllOrderByPkIdSubcategoriasByPkIdDoencasPrioridades()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : "not null"));
        //System.err.println("1: AmbCidPerfisDoencasBean.findAllOrderByPkIdSubcategoriasByPkIdDoencasPrioridades()\tambCidPerfis: " + ambCidPerfis.getPkIdNome());
        //System.err.println("2: AmbCidPerfisDoencasBean.findAllOrderByPkIdSubcategoriasByPkIdDoencasPrioridades()\tpkIdSubcategorias: " + pkIdSubcategorias);
        int prioridade = this.ambCidPerfisDoencasFacade.findHigestPrioridade(ambCidPerfis, pkIdSubcategorias);
        //System.err.println("3: AmbCidPerfisDoencasBean.findAllOrderByPkIdSubcategoriasByPkIdDoencasPrioridades()\tprioridade:" + prioridade);
        return this.ambCidDoencasPrioridadesFacade.findAll(prioridade);
    }

    public void installAmbCidDoencaPrioridade(int pkIdDoencasPrioridades)
    {

        AmbCidDoencasPrioridades ambCidDoencasPrioridades = this.ambCidDoencasPrioridadesFacade.find(pkIdDoencasPrioridades);
        this.ambCidPerfisDoencas.setFkIdPrioridades(ambCidDoencasPrioridades);
    }

    public void installAmbCidSubcategorias(String pkIdSubcategorias)
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.installAmbCidSubcategorias()\tpkIdSubcategorias: " + pkIdSubcategorias);
        AmbCidSubcategorias ambCidSubcategorias = this.ambCidSubcategoriasFacade.find(pkIdSubcategorias);
        //System.err.println("1: AmbCidPerfisDoencasBean.installAmbCidSubcategorias()\tambCidSubcategorias: " + (ambCidSubcategorias == null ? "null" : "not null"));
        if (ambCidSubcategorias != null)
        {
            String pkIdSubcategorias2 = ambCidSubcategorias.getPkIdSubcategorias();
            //System.err.println("2: AmbCidPerfisDoencasBean.installAmbCidSubcategorias()\tpkIdSubcategorias2: " + (pkIdSubcategorias2 == null ? "null" : pkIdSubcategorias2));
        }
        this.ambCidPerfisDoencas.setFkIdSubcategorias(ambCidSubcategorias);
        resetAmbiCidDoencasPrioridades();
    }

    public void installAmbCidCategorias(String pkIdCategorias)
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.installAmbCidCategorias()\tpkIdCategorias: " + pkIdCategorias);
        AmbCidCategorias ambCidCategorias = this.ambCidCategoriasFacade.find(pkIdCategorias);
        this.ambCidPerfisDoencas.getFkIdSubcategorias().setFkIdCategorias(ambCidCategorias);
        //System.err.println("1: AmbCidPerfisDoencasBean.installAmbCidCategorias()\tpkIdCategorias: "
        // + ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
        resetAmbiCidSubcategorias();
    }

    public void installAmbCidAgrupamentos(String pkIdAgrupamentos)
    {
        AmbCidAgrupamentos ambCidAgrupamentos = this.ambCidAgrupamentosFacade.find(pkIdAgrupamentos);
        this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().setFkIdAgrupamentos(ambCidAgrupamentos);
        resetAmbiCidCategorias();
    }

    public void installAmbCidCapitulos(String pkIdCapitulos)
    {
        AmbCidCapitulos ambCidCapitulos = this.ambCidCapitulosFacade.find(pkIdCapitulos);
        this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().setFkIdCapitulos(ambCidCapitulos);
        resetAmbiCidAgrupamentos();
    }

    public boolean resetRegressivoUI()
    {
        if (resetAmbiCidSubcategorias())
        {
            return true;
        }
        if (resetAmbiCidCategorias())
        {
            return true;
        }
        if (this.resetAmbiCidAgrupamentos())
        {
            return true;
        }
        return this.resetAmbiCidCapitulos();
    }

    public boolean resetAmbiCidDoencasPrioridades()
    {
        List<AmbCidDoencasPrioridades> listAmbCidDoencasPrioridades = findAllOrderByPkIdSubcategorias(ambCidPerfis, ambCidPerfisDoencas.getFkIdSubcategorias().getPkIdSubcategorias());
        if (listAmbCidDoencasPrioridades == null || listAmbCidDoencasPrioridades.isEmpty())
        {
            return false;
        }

        this.ambCidPerfisDoencas.setFkIdPrioridades(listAmbCidDoencasPrioridades.get(0));
        installAmbCidDoencaPrioridade(ambCidPerfisDoencas.getFkIdPrioridades().getPkIdDoencasPrioridades());
        return true;
    }

    public boolean resetAmbiCidSubcategorias()
    {
        List<AmbCidSubcategorias> listAmbCidSubcategorias = AmbCidSubcategoriasBean.getInstanciaBean().findAllExceptHighestPriorityOrderByNome(ambCidPerfis, ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
        if (listAmbCidSubcategorias == null || listAmbCidSubcategorias.isEmpty())
        {
            return false;
        }
        for (AmbCidSubcategorias ambCidSubcategorias : listAmbCidSubcategorias)
        {
            this.ambCidPerfisDoencas.setFkIdSubcategorias(ambCidSubcategorias);
            installAmbCidSubcategorias(ambCidPerfisDoencas.getFkIdSubcategorias().getPkIdSubcategorias());
            if (resetAmbiCidDoencasPrioridades())
            {
                return true;
            }
        }
        return false;
    }

    public boolean resetAmbiCidCategorias()
    {
        List<AmbCidCategorias> listAmbCidCategorias = AmbCidCategoriasBean.getInstanciaBean().findAllByPkIdAgrupamentosOrderByNome(ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getPkIdAgrupamentos());
        if (listAmbCidCategorias == null || listAmbCidCategorias.isEmpty())
        {
            return false;
        }

        for (AmbCidCategorias ambCidCategorias : listAmbCidCategorias)
        {
            this.ambCidPerfisDoencas.getFkIdSubcategorias().setFkIdCategorias(ambCidCategorias);
            installAmbCidCategorias(ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getPkIdCategorias());
            if (resetAmbiCidSubcategorias())
            {
                return true;
            }
        }
        return false;
    }

    public boolean resetAmbiCidAgrupamentos()
    {
        List<AmbCidAgrupamentos> listAmbCidAgrupamentos = AmbCidAgrupamentosBean.getInstanciaBean().findAllOrderByNome(ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getFkIdCapitulos().getPkIdCapitulos());
        if (listAmbCidAgrupamentos == null || listAmbCidAgrupamentos.isEmpty())
        {
            return false;
        }

        for (AmbCidAgrupamentos ambCidAgrupamentos : listAmbCidAgrupamentos)
        {
            this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().setFkIdAgrupamentos(ambCidAgrupamentos);
            installAmbCidAgrupamentos(ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getPkIdAgrupamentos());
            if (resetAmbiCidCategorias())
            {
                return true;
            }
        }
        return false;
    }

    public boolean resetAmbiCidCapitulos()
    {
        List<AmbCidCapitulos> listAmbCidCapitulos = AmbCidCapitulosBean.getInstanciaBean().findAllOrderByNome();
        if (listAmbCidCapitulos == null || listAmbCidCapitulos.isEmpty())
        {
            return false;
        }

        for (AmbCidCapitulos ambCidCapitulos : listAmbCidCapitulos)
        {
            this.ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().setFkIdCapitulos(ambCidCapitulos);
            installAmbCidCapitulos(ambCidPerfisDoencas.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getFkIdCapitulos().getPkIdCapitulos());
            if (resetAmbiCidAgrupamentos())
            {
                return true;
            }
        }
        return false;
    }

    public boolean getAlterarBtDisable(AmbCidPerfisDoencas perfilDoencaVar)
    {
////System.err.println("0: AmbCidPerfisDoencasBean.getAlterarBtDisable()\tperfilDoencaVar.nome: " + perfilDoencaVar.getFkIdSubcategorias().getNome());        
////System.err.println("00: AmbCidPerfisDoencasBean.getAlterarBtDisable()\tperfilDoencaVar.prioridade " + perfilDoencaVar.getFkIdPrioridades().getDescricao());
        String perfilPrioridade = perfilDoencaVar.getFkIdPrioridades().getDescricao();
////System.err.println("1: AmbCidPerfisDoencasBean.getAlterarBtDisable()\tambCidPerfis: " + ambCidPerfis.getPkIdNome());                
        List<AmbCidDoencasPrioridades> list = findAllOrderByPkIdSubcategorias(this.ambCidPerfis.getFkIdPerfilPai(), perfilDoencaVar.getFkIdSubcategorias().getPkIdSubcategorias());
////System.err.println("2: AmbCidPerfisDoencasBean.getAlterarBtDisable()\tlist.size: " + list.size());                
        for (int i = 0; i < list.size(); i++)
        {
////System.err.println("3: AmbCidPerfisDoencasBean.getAlterarBtDisable()\tlist[" + i + "]: " + list.get(i).getDescricao());                
        }
        if (list.size() == 1)
        {
////System.err.println("4: AmbCidPerfisDoencasBean.getAlterarBtDisable()\tlist.size: " + list.size());                            
            AmbCidDoencasPrioridades ambCidDoencasPrioridades = list.get(0);
            if (ambCidDoencasPrioridades.getDescricao().equals(perfilPrioridade))
            {
                return true;
            }
        }
////System.err.println("5: AmbCidPerfisDoencasBean.getAlterarBtDisable()");                                    
        return false;
    }

    public boolean formularioValido()
    {
        return true;
    }

    public boolean existeAmbCidPerfisDoencas()
    {
        AmbCidPerfisDoencas ambCidPerfisDoencas2 = this.ambCidPerfisDoencasFacade.find(ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome(), ambCidPerfisDoencas.getFkIdSubcategorias().getPkIdSubcategorias());
        return ambCidPerfisDoencas2 != null;
    }

    public void installPerfilPreferencial()
    {

    }

    public void gravarPerfilPreferencial()
    {
        ambCidConfiguracoes = this.ambCidConfiguracoesFacade.loadAmbCidConfiguracoes(idConta);
        System.err.println("0: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
        String perfilPreferencialNome = ambCidConfiguracoes.getIdNomePerfil();
        System.err.println("00: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()\tperfilPreferencialNome: " + (perfilPreferencialNome == null ? "null" : "not null"));
        if (!this.perfilPreferencial)
        {
            System.err.println("1: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
            if (perfilPreferencialNome != null)
            {
                ambCidConfiguracoes.setIdNomePerfil(null);
                //ambCidConfiguracoes.setIdEspecialidade(0);
                ambCidConfiguracoes.setIdDoencasPrioridades(util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA);
                this.ambCidConfiguracoesFacade.edit(ambCidConfiguracoes);
                System.err.println("1.1: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
            }
            System.err.println("1.2: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
            return;
        }
        System.err.println("1.3: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
        if (perfilPreferencialNome != null && perfilPreferencialNome.equals(this.ambCidPerfis.getPkIdNome()))
        {
            ambCidConfiguracoes.setIdDoencasPrioridades(obterPrioridadeMaxima(this.ambCidPerfis.getPkIdNome()));
            this.ambCidConfiguracoesFacade.edit(ambCidConfiguracoes);
 System.err.println("1.4: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
            return;
        }

        System.err.println("2: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");

        this.ambCidConfiguracoes.setIdNomePerfil(this.ambCidPerfis.getPkIdNome());
        System.err.println("3: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
        this.ambCidConfiguracoes.setIdEspecialidade(this.ambCidPerfis.getFkIdEspecialidades().getPkIdEspecialidade());
        ambCidConfiguracoes.setIdDoencasPrioridades(obterPrioridadeMaxima(this.ambCidPerfis.getPkIdNome()));
        this.ambCidConfiguracoesFacade.edit(ambCidConfiguracoes);
        System.err.println("4: AmbCidPerfisDoencasBean.gravarPerfilPreferencial()");
    }

    public String gravar()
    {
        System.err.println("0: AmbCidPerfisDoencasBean.gravar()");
        if (!formularioValido())
        {
            System.err.println("1: AmbCidPerfisDoencasBean.gravar()");
            util.Mensagem.warnMsg("falhou a gravacao da doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                + "' no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "' porque o formulario nao está bem preenchido");

            return "perfilNovoDoencasAmb?faces-redirect=true";
        }
        System.err.println("1.1: AmbCidPerfisDoencasBean.gravar()");
        if (existeAmbCidPerfisDoencas())
        {
            System.err.println("1.2: AmbCidPerfisDoencasBean.gravar()");
            util.Mensagem.erroMsg("doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                + "' ja existe gravado no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "'");
            return "perfilNovoDoencasAmb?faces-redirect=true";
        }
        System.err.println("2: AmbCidPerfisDoencasBean.gravar()");
        try
        {
            AmbCidSubcategorias ambCidSubcategorias = ambCidPerfisDoencas.getFkIdSubcategorias();
            String pkIdSubcategorias = ambCidSubcategorias.getPkIdSubcategorias();
            System.err.println("2.2: AmbCidPerfisDoencasBean.gravar()\tpkIdSubcategorias: " + (pkIdSubcategorias == null ? "null" : pkIdSubcategorias));
            this.userTransaction.begin();
            this.ambCidPerfisDoencasFacade.create(this.ambCidPerfisDoencas);
            this.userTransaction.commit();

            util.Mensagem.sucessoMsg("doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                + "' gravada com sucesso no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "'");
            gravarPerfilPreferencial();
            System.err.println("3: AmbCidPerfisDoencasBean.gravar()");
            resetRegressivoUI();
            return "perfilNovoDoencasAmb?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                System.err.println("4: AmbCidPerfisDoencasBean.gravar()");
                ////System.err.println(e.toString());
                util.Mensagem.erroMsg("falhou a gravacao da doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                    + "' no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "' mas a operacao de rollback foi bem sucedida");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                ////System.err.println("Rollback: " + ex.toString());
                System.err.println("5: AmbCidPerfisDoencasBean.gravar()");

                util.Mensagem.erroMsg("falhou a gravacao da doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                    + "' no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "' e falhou tambem a operacao de rollback");
            }
        }
        System.err.println("6: AmbCidPerfisDoencasBean.gravar()");

        return "perfilNovoDoencasAmb?faces-redirect=true";
    }

    public void preAlterar(AmbCidPerfisDoencas perfilDoencaVar)
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.preAlterar()");
        ambCidPerfisDoencas = perfilDoencaVar;
        //System.err.println("2: AmbCidPerfisDoencasBean.preAlterar()\tperfilDoencaVar.perfil: " + ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome());
        //System.err.println("3: AmbCidPerfisDoencasBean.preAlterar()\tperfilDoencaVar.subcategoria: " + ambCidPerfisDoencas.getFkIdSubcategorias().getPkIdSubcategorias());
        //System.err.println("4: AmbCidPerfisDoencasBean.preAlterar()\tperfilDoencaVar.prioridade: " + ambCidPerfisDoencas.getFkIdPrioridades().getPkIdDoencasPrioridades());
    }

    public String teste()
    {
        String msg = "ambCidPerfisDoencas: ";
        if (ambCidPerfisDoencas == null)
        {
            msg += "null";
            return msg;
        }
        msg += ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome();
        msg += ",\tsubcategoria: " + ambCidPerfisDoencas.getFkIdSubcategorias().getPkIdSubcategorias();
        msg += ",\tprioridade: " + ambCidPerfisDoencas.getFkIdPrioridades().getPkIdDoencasPrioridades();

        return msg;
    }

    public String alterar()
    {
        try
        {
            //System.err.println("1: AmbCidPerfisDoencasBean.alterar()\tpkIdPerfisDoencas: " + this.ambCidPerfisDoencas.getPkIdPerfisDoencas());
            this.userTransaction.begin();
            this.ambCidPerfisDoencasFacade.edit(this.ambCidPerfisDoencas);
            this.userTransaction.commit();
            util.Mensagem.sucessoMsg("Prioridade da doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                + "' alterada com sucesso no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "'");

            //System.err.println("2: AmbCidPerfisDoencasBean.alterar()");
            return "perfilNovoDoencasAmb?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println("3: AmbCidPerfisDoencasBean.alterar()");
                util.Mensagem.erroMsg("falhou a alteracao da prioridade da doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                    + "' no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "' mas a operacao de rollback foi bem sucedida");
                ////System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                ////System.err.println("Roolback: " + ex.toString());
                util.Mensagem.erroMsg("falhou a alteracao da da prioridade da doenca '" + this.ambCidPerfisDoencas.getFkIdSubcategorias().getNome()
                    + "' no perfil '" + this.ambCidPerfisDoencas.getFkIdPerfil().getPkIdNome() + "' e falhou tambem a operacao de rollback");
                //System.err.println("4: AmbCidPerfisDoencasBean.alterar()");
            }
        }
        //System.err.println("5: AmbCidPerfisDoencasBean.alterar()");

        return "perfilNovoDoencasAmb?faces-redirect=true";
    }

    public String apagar(Integer pkIdPerfisDoencas)
    {
        //System.err.println("0: AmbCidPerfisDoencasBean.apagar()");
        try
        {
            //System.err.println("1: AmbCidPerfisDoencasBean.apagar()\tpkIdPerfisDoencas: " + pkIdPerfisDoencas);
            this.userTransaction.begin();
            this.ambCidPerfisDoencasFacade.remove(new AmbCidPerfisDoencas(pkIdPerfisDoencas));
            this.userTransaction.commit();

            //System.err.println("2: AmbCidPerfisDoencasBean.apagar()");
            return "perfilNovoDoencasAmb?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println("3: AmbCidPerfisDoencasBean.apagar()");
                ////System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                ////System.err.println("Roolback: " + ex.toString());
                //System.err.println("4: AmbCidPerfisDoencasBean.apagar()");
            }
        }
        //System.err.println("5: AmbCidPerfisDoencasBean.apagar()");

        return "perfilNovoDoencasAmb?faces-redirect=true";
    }

    public static boolean listAmbCidCapitulosContem(List<AmbCidCapitulos> lista, AmbCidCapitulos ambCidCapitulos)
    {
        if (lista.size() == 0)
        {
            return false;
        }

        for (AmbCidCapitulos capitulo : lista)
        {
            if (capitulo.getPkIdCapitulos().equals(ambCidCapitulos.getPkIdCapitulos()))
            {
                return true;
            }
        }
        return false;
    }

    public List<AmbCidCapitulos> findAllOrderByNome(AmbCidPerfis perfilPreferencial, int idDoencasPrioridadesPreferencial)
    {
        /*
         se idDoencasPrioridadesPreferencial == 3
         retornar todos os capitulos
         criar listaCapitulosPerfisPreferencial
         listaPerfisDoencas = obter todos os PerfisDoencas()
         para cada perfiDoenca da listaPerfisDoencas
         se perfiDoenca.prioridade <=  idDoencasPrioridadesPreferencial
         incluir capituloperfiDoenca em listaCapitulosPerfisPreferencial
         retornar listaCapitulosPerfisPreferencial
         */
        if (idDoencasPrioridadesPreferencial == util.amb.Defs.DOENCAS_PRIORIDADE_MINIMA)
        {
            return this.ambCidCapitulosFacade.findAllOrderById();
        }
        List<AmbCidCapitulos> listaCapitulosPerfisPreferencial = new ArrayList();
        List<AmbCidPerfisDoencas> listaAmbCidPerfisDoencas = this.ambCidPerfisDoencasFacade.findAll();

        AmbCidCapitulos ambCidCapitulos = null;
        for (AmbCidPerfisDoencas ambCidPerfisDoencasVar : listaAmbCidPerfisDoencas)
        {
            if (ambCidPerfisDoencasVar.getFkIdPrioridades().getPkIdDoencasPrioridades() <= idDoencasPrioridadesPreferencial)
            {
                ambCidCapitulos = ambCidPerfisDoencasVar.getFkIdSubcategorias().getFkIdCategorias().getFkIdAgrupamentos().getFkIdCapitulos();
                if (!listAmbCidCapitulosContem(listaCapitulosPerfisPreferencial, ambCidCapitulos))
                {
                    listaCapitulosPerfisPreferencial.add(ambCidCapitulos);
                }
            }
        }
        return listaCapitulosPerfisPreferencial;
    }

    // metodos set e get
    public AmbCidPerfis getAmbCidPerfis()
    {
        return ambCidPerfis;
    }

    public void setAmbCidPerfis(AmbCidPerfis ambCidPerfis)
    {
        this.ambCidPerfis = ambCidPerfis;
    }

    public boolean isNovaDoencaRedered()
    {
        return novaDoencaRedered;
    }

    public AmbCidPerfisDoencas getAmbCidPerfisDoencas()
    {
        return ambCidPerfisDoencas;
    }

    public void setAmbCidPerfisDoencas(AmbCidPerfisDoencas ambCidPerfisDoencas)
    {
        this.ambCidPerfisDoencas = ambCidPerfisDoencas;
    }

    public boolean isPerfilPreferencial()
    {
        return perfilPreferencial;
    }

    public void setPerfilPreferencial(boolean perfilPreferencial)
    {
        this.perfilPreferencial = perfilPreferencial;
    }

}
