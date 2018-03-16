/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.seg;

import entidade.SegFuncionalidade;
import java.util.List;
import managedBean.segbean.SegFuncionalidadeBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sessao.SegFuncionalidadeFacade;
import util.TreeNodeInterface;

/**
 *
 * @author aires
 */
public abstract class SegTreeFuncionalidadesAbstract implements TreeNodeInterface
{

    private SegFuncionalidadeFacade segFuncionalidadeFacade;
    protected TreeNode root, selectedNode;

    public SegTreeFuncionalidadesAbstract(SegFuncionalidadeFacade segFuncionalidadeFacade)
    {
        this.segFuncionalidadeFacade = segFuncionalidadeFacade;
    }

    // Inicio da implementação default dos métodos da Interface TreeNodeInterface
    @Override
    public void onNodeSelect(NodeSelectEvent event)
    {
        if (selectedNode != null)
        {
            selectedNode.setSelected(false);
        }
System.err.println("0: SegTreeFuncionalidadesAbstract.onNodeSelect()");        
debugNodeTeste();
        selectedNode = event.getTreeNode();
        selectedNode.setSelected(true);
        String node = ((SegFuncionalidade)selectedNode.getData()).getNome();
System.err.println("1: SegTreeFuncionalidadesAbstract.onNodeSelect()\tselectedNode: " + node);

    }
    
    void debugNodeTeste()
    {
        TreeNode treeNode = util.TreeNodeUtilities.getTreeNode(root, "Gerir Perfis");
        if (treeNode == null)
            return;
System.err.println("0: SegTreeFuncionalidadesAbstract.debugNodeTeste()\tselectedNode.name: " + ((SegFuncionalidade)treeNode.getData()).getNome());
            
    }

    @Override
    public void onNodeUnSelect(NodeUnselectEvent event)
    {
        //System.err.println("Node Data ::" + ((SegFuncionalidade)event.getTreeNode().getData()).getNome() + " :: UnSelected");
    }

    @Override
    public void onNodeExpand(NodeExpandEvent event)
    {
        String node = ((SegFuncionalidade)event.getTreeNode().getData()).getNome();
System.err.println("0: SegTreeFuncionalidadesAbstract.onNodeExpand()\tnode: " + node);
debugNodeTeste();
    }

    @Override
    public void onNodeCollapse(NodeCollapseEvent event)
    {
        String node = ((SegFuncionalidade)event.getTreeNode().getData()).getNome();
        //System.err.println("0: SegTreeFuncionalidadesAbstract.onNodeCollapse()\tnode: " + node);
    }

    @Override
    public void initSelectedNode()
    {
    }
    // Fim da implementação default dos métodos da Interface TreeNodeInterface
    
    public String getSelectedNodeName()
    {
        return ((SegFuncionalidade)this.selectedNode.getData()).getNome();
    }

    public SegFuncionalidade getSegFuncionalidadeSelectedNode()
    {
System.err.println ("0: SegTreeFuncionalidadesAbstract.getSegFuncionalidadeSelectedNode()\tselectedNode.name: " + ((SegFuncionalidade)selectedNode.getData()).getNome());            
            int pkIdFuncionalidadeSelectedNode = ((SegFuncionalidade)selectedNode.getData()).getPkIdFuncionalidade();

System.err.println ("2: SegTreeFuncionalidadesAbstract.getSegFuncionalidadeSelectedNode()");            
        SegFuncionalidade segFuncionalidade = this.segFuncionalidadeFacade.find(pkIdFuncionalidadeSelectedNode);
        return segFuncionalidade;
    }

    public List<SegFuncionalidade> obterListaSegFuncionalidadeFilhos(int pkIdFuncionalidade)
    {
        // 88888888888888888888
        System.err.println("0: SegTreeFuncionalidadesAbstract.obterListaSegFuncionalidadeFilhos()\tpkIdFuncionalidade: " + pkIdFuncionalidade);
        return this.segFuncionalidadeFacade.obterListaSegFuncionalidadeFilhos(pkIdFuncionalidade);
    }

    public void gerarChildNodeInSelectedNode(SegFuncionalidade segFuncionalidadeFilho)
    {
        SegFuncionalidade segFuncionalidadeChild = this.segFuncionalidadeFacade.findByNomeByPai(segFuncionalidadeFilho.getNome(), segFuncionalidadeFilho.getFkIdFuncionalidadePai());
        
        TreeNode treeNode = new DefaultTreeNode(new SegFuncionalidadeDataNode(segFuncionalidadeChild), this.selectedNode);
        int fkIdFuncionalidadeChild = segFuncionalidadeChild.getPkIdFuncionalidade();
        System.err.println("0: SegTreeFuncionalidadesAbstract.gerarChildNodeInSelectedNode()\ttreeNode.name: " + ((SegFuncionalidade)treeNode.getData()).getNome());

        util.TreeNodeUtilities.setSelected(root, treeNode);
    }

    public void gerarTreeNodes(TreeNode noPai)
    {
        /*
         perfisFilhos = obterPerfisFilhos(noPai)
         if (!perfisFilhos)
         retornar
         para cada perfil de perfisFilhos
         se ! listaAmbCidPerfisNomes contem perfil
         continuar
         criar no(perfil, noPai)
         gerarTreeNodes(no, listaAmbCidPerfisNomes)
         */
System.err.println("0: SegTreeFuncionalidadesAbstract.gerarTreeNodes()\tnoPai.nome: " + ((SegFuncionalidade)noPai.getData()).getNome());
debugNodeTeste();

        int pkIdFuncionalidade = ((SegFuncionalidade)noPai.getData()).getPkIdFuncionalidade();
        List<SegFuncionalidade> listaSegFuncionalidadeFilhos = obterListaSegFuncionalidadeFilhos(pkIdFuncionalidade);
System.err.println("1: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");
debugNodeTeste();
        if (listaSegFuncionalidadeFilhos == null || listaSegFuncionalidadeFilhos.size() == 0)
        {
System.err.println("1.1: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");         
debugNodeTeste();
            return;
        }
System.err.println("2: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");    
debugNodeTeste();
        for (SegFuncionalidade segFuncionalidadeFilho : listaSegFuncionalidadeFilhos)
        {
System.err.println("3: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");            
debugNodeTeste();
System.err.println("3.1: SegTreeFuncionalidadesAbstract.gerarTreeNodes()\tnoPai.name: " + ((SegFuncionalidade)noPai.getData()).getNome());
System.err.println("3.3: SegTreeFuncionalidadesAbstract.gerarTreeNodes()\tsegFuncionalidadeFilho.nome: " + ((SegFuncionalidade)segFuncionalidadeFilho).getNome());
            TreeNode treeNode = new DefaultTreeNode(new SegFuncionalidadeDataNode(segFuncionalidadeFilho), noPai);
System.err.println("4: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");            
debugNodeTeste();
            pkIdFuncionalidade = segFuncionalidadeFilho.getPkIdFuncionalidade();
System.err.println("5: SegTreeFuncionalidadesAbstract.gerarTreeNodes()\ttreeNode.name: " + ((SegFuncionalidade)treeNode.getData()).getNome());
this.debugNodeTeste();

            gerarTreeNodes(treeNode);
//System.err.println("6: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");            
        }
//System.err.println("7: SegTreeFuncionalidadesAbstract.gerarTreeNodes()");        
    }

    public void initRootFromDataBase()
    {
        gerarTreeNodes(root);
    }

    public SegFuncionalidade geraSegFuncionalidadeRoot()
    {
        SegFuncionalidade segFuncionalidade = SegFuncionalidadeBean.getInstancia();
        segFuncionalidade.setPkIdFuncionalidade(0);
        segFuncionalidade.setNome(util.Defs.APPLICATION_NAME);
        segFuncionalidade.setFkIdFuncionalidadePai(null);
        segFuncionalidade.setUrlPadrao(null);
        return segFuncionalidade;
    }
    
    public void initRoot()
    {
        root = new DefaultTreeNode(new SegFuncionalidadeDataNode(geraSegFuncionalidadeRoot()), null);
        root.setRowKey(((SegFuncionalidade)root.getData()).getNome());
        System.err.println("0: SegTreeFuncionalidadesAbstract.initRoot()\troot.name: " + root.toString());
        initRootFromDataBase();
        initSelectedNode();
    }

    public TreeNode getRoot()
    {
        return root;
    }

    public void setRoot(TreeNode root)
    {
        this.root = root;
    }

    public TreeNode getSelectedNode()
    {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode)
    {
        this.selectedNode = selectedNode;
    }

    public SegFuncionalidadeFacade getSegFuncionalidadeFacade()
    {
        return segFuncionalidadeFacade;
    }

    public void setSegFuncionalidadeFacade(SegFuncionalidadeFacade segFuncionalidadeFacade)
    {
        this.segFuncionalidadeFacade = segFuncionalidadeFacade;
    }

}
