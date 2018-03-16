/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.validacao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@RequestScoped
public class RhValidarBean implements Validator, Serializable
{

   /**
    * Creates a new instance of RhValidarBean
    */
   public RhValidarBean ()
   {
   }
   
    public static RhValidarBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhValidarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhValidarBean");
    }
    
   public Date getDataDeHoje()
   {
       return Calendar.getInstance().getTime();
   }
   
    public Date getDeHojeSemHora ()
    {
        try
        {
            String dataActual = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

            return new SimpleDateFormat("dd-MM-yyyy").parse(dataActual);
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }
   
    public Integer anoActual ()
    {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
    }

    public Integer anoActualMais1 ()
    {
        return anoActual() + 1;
    }

    public Integer anoActualMais10 ()
    {
        return anoActual() + 10;
    }

   public void validarFoto (FacesContext cont, UIComponent uiComp, Object valor)
   {
      Part img = (Part)valor;
      String ct = img.getContentType();
      
      if(! ct.equalsIgnoreCase("image/png") && ! ct.equalsIgnoreCase("image/jpg") && ! ct.equalsIgnoreCase("image/jpeg"))
         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato do ficheiro inválido", null));
   }
   
   public void validarAnexo (FacesContext cont, UIComponent uiComp, Object valor)
   {
      Part file = (Part)valor;
      String ct = file.getContentType();
      
      if(! ct.equalsIgnoreCase("application/pdf") && ! ct.equalsIgnoreCase("image/png") && ! ct.equalsIgnoreCase("image/jpg") && ! ct.equalsIgnoreCase("image/jpeg"))
         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato do ficheiro inválido", null));
   }

    @Override
    public void validate (FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
