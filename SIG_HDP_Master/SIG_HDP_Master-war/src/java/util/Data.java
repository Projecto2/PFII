/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Data {
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
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
           System.out.println("Data invalida");
           return null;
        }
        return data;
    }  
    
    public static Date getDataInserida(String dataInserida)
    {
        Calendar data = null;
        
        try 
        {
            java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dataInserida);

            data = Calendar.getInstance();

            data.setTime(date);
        } 

        catch (ParseException e) 
        {
            System.out.println("Data : "+e.getMessage());
        }
        
        
        return new Date(data.getTimeInMillis());
    }
    
    public static boolean getAnoBissexto_Comun(int ano) 
    {
        if (ano % 400 == 0) {
		return true;		
	}
	else if ((ano % 4 == 0) && (ano % 100 != 0)) {
		return true;		
	}
	else {
		return false;
        }
    }    
    
    public static int getDias(int mes, int ano) {
        
        if (mes == 1) {
            return 31;
        } 
        else if (mes == 2) {
            if (getAnoBissexto_Comun(ano))
                return 29;
            else
                return 28;
        } else if (mes == 3) {
            return 31;
        } else if (mes == 4) {
            return 30;
        } else if (mes == 5) {
            return 31;
        } else if (mes == 6) {
            return 30;
        } else if (mes == 7) {
            return 31;
        } else if (mes == 8) {
            return 31;
        } else if (mes == 9) {
            return 30;
        } else if (mes == 10) {
            return 31;
        } else if (mes == 11) {
            return 30;
        } else if (mes == 12) {
            return 31;
        } else {
            return 0;
        }
    }
    
    public static String getMes(int mes) {
        
        if (mes == 1) {
            return "Janeiro";
        } 
        else if (mes == 2) {
            return "Fevereiro";
        } else if (mes == 3) {
            return "Março";
        } else if (mes == 4) {
            return "Abril";
        } else if (mes == 5) {
            return "Maio";
        } else if (mes == 6) {
            return "Junho";
        } else if (mes == 7) {
            return "Julho";
        } else if (mes == 8) {
            return "Agosto";
        } else if (mes == 9) {
            return "Setembro";
        } else if (mes == 10) {
            return "Outubro";
        } else if (mes == 11) {
            return "Novembro";
        } else if (mes == 12) {
            return "Dezembro";
        } else {
            return null;
        }
    }
    
   //Inserido por Elisâmgela Gaspar
   /**
    *
    * @param dataInicial A data Inicial
    * @param dataFinal A data Final
    * @return NumeroDeDias: A diferença de dias entre as duas datas
    * Retorna Positivo se a data Final for superior à data Inicial e negativo se for o contrário.
    */
   public static int getDiferencaDeDias(Date dataInicial, Date dataFinal)
   {
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      dateFormat.setLenient(false);

      Long data = (dataFinal.getTime() - dataInicial.getTime()) + 360000;
      int dias = (int) (data / 86400000);
      return dias;
   }
}
