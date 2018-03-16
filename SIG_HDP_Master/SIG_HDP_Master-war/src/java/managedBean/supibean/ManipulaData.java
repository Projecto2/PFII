/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.swing.JOptionPane;
/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class ManipulaData implements Serializable
{

   SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
  public String dateToStr(Date data)
  {
    return sdf.format(data);
  }
  public  Date strToDate(String string)
  {
    Date data;
     try
    {
      data = sdf.parse(string);
    }
    catch(ParseException exeption)
    {
      JOptionPane.showMessageDialog(null,"Data invalida");
      return null;
    }
    return data;
    
  }
  
  
  ////Valida Data
  public int retornaIdade(Date dataNascimento)
  {
      int idadeRetornar;
      
      idadeRetornar = new Date().getYear()-dataNascimento.getYear();
      return idadeRetornar;
  }
  
  
  
  
  //RETORNA A APENAS O ANO
  public int retornaAno(Date data)
  {
      int anoRetornar;
      
      anoRetornar = data.getYear();
      return anoRetornar;
  }
  
  
  
  public int diferencaEmMeses(Date data1, Date data2)
  {
      int mes1 = (data1.getYear() * 12) + data1.getMonth();
      int mes2 =(data2.getYear() * 12) + data2.getMonth();
      
      return  (mes1 - mes2);
  }
  
  /**Retorna Hora*/
    public String getTime(Date data) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(data);
    }
     public int diferencaEmDias(Date data1, Date data2)
  {
      
      int mes1 = (data1.getMonth() * 30) + data1.getDay();
      int mes2 =(data2.getMonth()* 30) + data2.getDay();
      return (mes1 - mes2);
  }
   
  
  public int diferencaHoras(Date data1, Date data2)
  {
      int hora1 = (data1.getDay() * 24) + data1.getHours();
      int hora2 =(data2.getDay() * 24) + data2.getHours();
     
      return (hora1 - hora2);
  }
  
  
  
      public List<String>getHorarioAgenda(Date dataInicio,Date dataFim,int duracaoEmMin)
  {
      List<String>datas = new ArrayList<>();
      Date dataAux= new Date(dataInicio.getTime());
      
      while(dataAux.getTime()<=dataFim.getTime())
      {
          System.out.println(getTime(dataAux));
          datas.add(getTime(dataAux));
          dataAux.setTime(dataAux.getTime()+duracaoEmMin*60*1000);
      }
      
      
      return datas;
  }
    
    
}
