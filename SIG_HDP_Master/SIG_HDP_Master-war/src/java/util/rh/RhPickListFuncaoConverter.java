/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.rh;

import entidade.RhFuncao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import managedBean.rhbean.RhFuncaoBean;

/**
 *
 * @author Ornela F. Boaventura
 */
@FacesConverter(forClass = RhFuncao.class, value = "rhPickListFuncaoConverter")
public class RhPickListFuncaoConverter implements Converter
{

    @Override
    public String getAsString (FacesContext context, UIComponent component, Object value)
    {
        return String.valueOf(((RhFuncao) value).getPkIdFuncao());
    }

    @Override
    public Object getAsObject (FacesContext arg0, UIComponent arg1, String value)
    {
        RhFuncao funcao = RhFuncaoBean.getInstanciaBean().getFuncaoFacade().find(Integer.parseInt(value));
        return funcao;
    }
}
