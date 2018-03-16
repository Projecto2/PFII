/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.rh;

import entidade.RhFuncionario;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import managedBean.rhbean.funcionario.RhFuncionarioPesquisarBean;

/**
 *
 * @author Ornela F. Boaventura
 */
@FacesConverter(forClass = RhFuncionario.class, value = "rhPickListFuncionarioConverter")
public class RhPickListFuncionarioConverter implements Converter
{

    @Override
    public String getAsString (FacesContext context, UIComponent component, Object value)
    {
        return String.valueOf(((RhFuncionario) value).getPkIdFuncionario());
    }

    @Override
    public Object getAsObject (FacesContext arg0, UIComponent arg1, String value)
    {
        RhFuncionario funcionario = RhFuncionarioPesquisarBean.getInstanciaBean().getFuncionarioFacade().find(Integer.parseInt(value));
        return funcionario;
    }
}
