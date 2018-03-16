/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Ornela F. Boaventura
 */
@FacesValidator("foto-validator")
public class FotoValidator implements Validator
{

    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        Part img = (Part) value;
        String ct = img.getContentType();

        if (!ct.equalsIgnoreCase("image/png") && !ct.equalsIgnoreCase("image/jpg") && !ct.equalsIgnoreCase("image/jpeg"))
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato do ficheiro inválido", null));
        }
    }

}
