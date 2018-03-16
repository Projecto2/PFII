/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.seg;

import entidade.SegFuncionalidade;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author aires
 */
public class SegFuncionalidadeDataNode extends SegFuncionalidade
{
    public SegFuncionalidadeDataNode(SegFuncionalidade segFuncionalidade)
    {
        super(segFuncionalidade.getPkIdFuncionalidade());
        
        this.setFkIdFuncionalidadePai(segFuncionalidade.getFkIdFuncionalidadePai());
        this.setFkIdTipoFuncionalidade(segFuncionalidade.getFkIdTipoFuncionalidade());
        this.setNome(segFuncionalidade.getNome());
        this.setSegFuncionalidadeList(segFuncionalidade.getSegFuncionalidadeList());
        this.setUrlPadrao(segFuncionalidade.getUrlPadrao());
    }
    
    public String toString()
    {
        return getNome();
    }
}
