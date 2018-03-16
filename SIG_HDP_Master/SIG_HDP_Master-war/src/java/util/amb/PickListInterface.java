/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.amb;

import java.util.List;

/**
 *
 * @author aires
 */
public interface PickListInterface
{
    public List<String> geraAllSourceNames();

    public List<String> geraAllTargetNames();
    
    public void inicListSource();

    public void gravar();
}
