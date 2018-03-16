/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.List;
import org.primefaces.model.TreeNode;

/**
 *
 * @author aires
 */
public class TreeNodeUtilities
{
    public static TreeNode getTreeNode(TreeNode root, String nome)
    {
        if (root.getData().toString().equals(nome))
            return root;
        
        TreeNode result = null;
        List<TreeNode> listaChildren = root.getChildren();
        for (TreeNode node : listaChildren)
        {
            result = getTreeNode(node, nome);
            if (result != null)
                return result;
        }
        return null;
    }
    
    public static void setSelected(TreeNode root, TreeNode selectedNode)
    {
        for (TreeNode t = selectedNode; ; t = t.getParent())
        {
            t.setExpanded(true);
            if (t == root)
                break;
        }
        selectedNode.setSelected(true);
    }
}
