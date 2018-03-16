/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidPerfilTipos;
import entidade.AmbCidPerfis;
import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.segbean.SegLoginBean;
import sessao.AmbCidPerfilTiposFacade;
import sessao.AmbCidPerfisFacade;
import sessao.RhProfissaoFacade;
import util.GeradorCodigo;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidPerfisBean implements Serializable {

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    @EJB
    private AmbCidPerfilTiposFacade ambCidPerfilTiposFacade;

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    /**
     * Creates a new instance of AmbCidPerfisBean
     */
    public AmbCidPerfisBean() {
    }

    public static AmbCidPerfisBean getInstanciaBean() {
        return (AmbCidPerfisBean) GeradorCodigo.getInstanciaBean("ambCidPerfisBean");
    }

    public static AmbCidPerfis getInstancia() {
        AmbCidPerfisBean ambCidPerfisBean = getInstanciaBean();
        SegConta segConta = ambCidPerfisBean.initDono();

        AmbCidPerfis ambCidPerfis = new AmbCidPerfis();
        ambCidPerfis.setFkIdDono(segConta);

        GrlEspecialidade grlEspecialidade = ambCidPerfisBean.obterEspecialidades();

        ambCidPerfis.setFkIdEspecialidades(grlEspecialidade);
        ambCidPerfis.setFkIdPerfilPai(null);

        AmbCidPerfilTipos ambCidPerfilTipos = ambCidPerfisBean.getAmbCidPerfilTiposFacade().findByNome(util.amb.Defs.PERFIL_TIPO_PRIVADO);
        ambCidPerfis.setFkIdTipo(ambCidPerfilTipos);
        return ambCidPerfis;
    }

    public GrlEspecialidade obterEspecialidades() {
        GrlEspecialidade grlEspecialidade = new GrlEspecialidade();
        grlEspecialidade.setFkIdProfissao(new RhProfissao());
        return grlEspecialidade;
    }

    public SegConta initDono() 
    {
        SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        return segConta;
    }

    public List<String> findAllOrderByPkIdNome(SegConta pkIdConta, Integer pkIdEspecialidade) {
//System.err.println("0: AmbCidPerfisBean.findAllOrderByPkIdNome()\tpkIdEspecialidade: " + pkIdEspecialidade);
//System.err.println("00: AmbCidPerfisBean.findAllOrderByPkIdNome()\tpkIdConta: " + (pkIdConta == null ? "null" : ("" + pkIdConta.getPkIdConta())));
        

        List<String> list = new ArrayList();
        list.add(util.amb.Defs.CID_10);

        List<String> listPerfis = this.ambCidPerfisFacade.findAllOrderByPkIdNome(pkIdConta, pkIdEspecialidade);
        if (listPerfis != null) {
//System.err.println("1: AmbCidPerfisBean.findAllOrderByPkIdNome()\tpkIdEspecialidade: " + pkIdEspecialidade.intValue());            
            list.addAll(listPerfis);
        }
//System.err.println("2: AmbCidPerfisBean.findAllOrderByPkIdNome()\tpkIdEspecialidade: " + pkIdEspecialidade.intValue());
        return list;
    }

    public List<AmbCidPerfis> findAllAmbCidPerfisOrderByPkIdNome(SegConta segConta, Integer pkIdEspecialidade) {
        return this.ambCidPerfisFacade.findAllAmbCidPerfisOrderByPkIdNome(segConta, pkIdEspecialidade);
    }

    public String obterNomePerfilPai(AmbCidPerfis perfil) {
        AmbCidPerfis perfilPai = perfil.getFkIdPerfilPai();
        return perfilPai == null ? "CID10" : perfilPai.getPkIdNome();
    }

    public AmbCidPerfilTiposFacade getAmbCidPerfilTiposFacade() {
        return ambCidPerfilTiposFacade;
    }
}
