/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.tabelasAuxiliares;

import entidade.TbClassificacaoDoenca;
import entidade.TbSubclassificacaoDoenca;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sessao.TbClassificacaoDoencaFacade;
import sessao.TbSubclassificacaoDoencaFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbSubclassificacaoDoencaBean implements Serializable
{

    @EJB
    private TbSubclassificacaoDoencaFacade tbSubclassificacaoDoencaFacade;

    @EJB
    private TbClassificacaoDoencaFacade tbClassificacaoDoencaFacade;

    private List<TbSubclassificacaoDoenca> listaSubclassificacaoDoenca;

    private TbSubclassificacaoDoenca tbSubclassificacaoDoenca;

    private TreeNode raiz;

    /**
     * Creates a new instance of TbSubclassificacaoDoencaBean
     */
    public TbSubclassificacaoDoencaBean()
    {
    }

    public static TbSubclassificacaoDoencaBean getInstanciaBean()
    {
        return (TbSubclassificacaoDoencaBean) GeradorCodigo.getInstanciaBean("tbSubclassificacaoDoencaBean");
    }

    @PostConstruct
    public void init()
    {
        construirArvore();
    }

    public void construirArvore()
    {
        this.raiz = new DefaultTreeNode("Tuberculose", null);

        for (TbClassificacaoDoenca c : tbClassificacaoDoencaFacade.findAll())
        {
            TreeNode no = new DefaultTreeNode(c.getDescricao(), raiz);
            for (TbSubclassificacaoDoenca s : tbSubclassificacaoDoencaFacade.ordenarSubClassificacaoDoenca())
            {
                if (c.getPkClassificacaoDoenca() == s.getFkClassificacaoDoenca().getPkClassificacaoDoenca())
                {
                    no.getChildren().add(new DefaultTreeNode(s.getDescricao(), no));
                }
            }
        }

        if (raiz.getChildCount() == 0)
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + raiz.getChildCount() + TbMensagens.REGISTO);
        }

    }

    public TbSubclassificacaoDoenca getInstanciaTbSubclassificacaoDoenca()
    {
        if (tbSubclassificacaoDoenca == null)
        {
            tbSubclassificacaoDoenca = new TbSubclassificacaoDoenca();
        }
        return tbSubclassificacaoDoenca;
    }

    public TreeNode getRaiz()
    {
        return raiz;
    }

    public void pesquisarSubclassificacaoDoenca()
    {
        listaSubclassificacaoDoenca = tbSubclassificacaoDoencaFacade.findSubclassificacaoDoenca(new TbSubclassificacaoDoenca());
        if (listaSubclassificacaoDoenca.isEmpty())
        {
            Mensagem.warnMsg(TbMensagens.VAZIO);
        }
        else
        {
            Mensagem.sucessoMsg(TbMensagens.SUCESSO + listaSubclassificacaoDoenca.size() + TbMensagens.REGISTO);
        }
    }

    public List<TbSubclassificacaoDoenca> getListaSubclassificacaoDoenca()
    {
        if (listaSubclassificacaoDoenca == null)
        {
            pesquisarSubclassificacaoDoenca();
        }
        return listaSubclassificacaoDoenca;
    }

}
