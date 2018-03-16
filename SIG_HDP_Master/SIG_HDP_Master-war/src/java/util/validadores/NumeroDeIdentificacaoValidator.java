/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.validadores;

import java.util.regex.Pattern;
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
@FacesValidator("numero-identificacao-validator")
public class NumeroDeIdentificacaoValidator implements Validator
{
//    private static final Pattern patt = Pattern.compile("[\\p{L}0-9]+"/*Constants..getEmailRegExp()*/);
    private static final Pattern patt = Pattern.compile("[a-zA-Z0-9]+"/*Constants..getEmailRegExp()*/);
    @Override
    public void validate(FacesContext arg0, UIComponent comp, Object arg2)
            throws ValidatorException {
        if (arg2 == null || arg2.toString().trim().length() == 0) {
            return;
        }
        if (!patt.matcher(arg2.toString().trim()).matches()) {
            String label = (String) comp.getAttributes().get("label");
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, (label == null || label.trim().length() == 0 ? "Número de Identificação: " : label+": ") +
                "Formato Invalido", null));
        }
    }
    
}
