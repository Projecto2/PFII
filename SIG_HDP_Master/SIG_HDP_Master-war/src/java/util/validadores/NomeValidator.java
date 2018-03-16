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
@FacesValidator("nome-validator")
public class NomeValidator implements Validator
{

//    private static final Pattern patt = Pattern.compile("[A-Za-zÃãÁáÂâÇç\\s]+"/*Constants..getNomeRegExp()*/);
//    private static final Pattern patt = Pattern.compile("[A-Za-zÃãÁáÂâẼẽÉéÈĨÍĩíÌÑŃñńǸÕÓõóÒŨÚũÙṼǗṽǘǛỸÝỹýỲÇç\\s]+"/*Constants..getNomeRegExp()*/);
    private static final Pattern patt = Pattern.compile("[\\p{L}\\s]+");

    @Override
    public void validate (FacesContext arg0, UIComponent comp, Object arg2)
            throws ValidatorException
    {
        if (arg2 == null || arg2.toString().trim().length() == 0)
        {
            return;
        }
        if (!patt.matcher(arg2.toString().trim()).matches())
        {
            String label = (String) comp.getAttributes().get("label");
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, (label == null || label.trim().length() == 0 ? "Nome: " : label + ": ")
                                                 + "Formato Inválido", null));
        }
    }

}
