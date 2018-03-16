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

/**
 *
 * @author gemix
 */
@FacesValidator("tres-caracteres-validator")
public class TresCaracteresValidador implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        if (value == null || value.toString().trim().length() < 3)
        {
            String label = (String) component.getAttributes().get("label");
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, (label == null || label.trim().length() == 0 ? "Nome: " : label + ": ")
                                                 + "Formato Inválido, Pelo Menos Três Caracteres Requeridos", null));
        }   
    }
    
}
