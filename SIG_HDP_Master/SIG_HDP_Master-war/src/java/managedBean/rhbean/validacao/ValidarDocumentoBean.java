/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.validacao;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@RequestScoped
public class ValidarDocumentoBean implements Validator, Serializable
{

    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        Part file = (Part) value;
        String ct = file.getContentType();

        if (!ct.equalsIgnoreCase("application/pdf") && !ct.equalsIgnoreCase("image/png") && !ct.equalsIgnoreCase("image/jpg") && !ct.equalsIgnoreCase("image/jpeg"))
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato do ficheiro inválido", null));
        }
    }

}
