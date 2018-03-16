/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.RhFuncionario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sessao.RhFuncionarioFacade;

/**
 *
 * @author helga
 */
@FacesConverter("conversorFuncionario")
public class ConversorFuncionario implements Converter{
    RhFuncionarioFacade rhFuncionarioFacade = lookupRhFuncionarioFacadeBean();
  
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
            Integer codigo= Integer.parseInt(value);
            
            return rhFuncionarioFacade.find(codigo);
        
        }catch(RuntimeException ex){
        return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
       try{
           
           RhFuncionario rhFuncionario = (RhFuncionario)value;
           Integer codigo = rhFuncionario.getPkIdFuncionario();
           
           return codigo.toString();
       }catch(RuntimeException ex){
       
       return null;
       }
    }

    private RhFuncionarioFacade lookupRhFuncionarioFacadeBean() {
        try {
            Context c = new InitialContext();
            return (RhFuncionarioFacade) c.lookup("java:global/SIG_HDP_Master/SIG_HDP_Master-ejb/RhFuncionarioFacade!sessao.RhFuncionarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
