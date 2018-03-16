/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.rh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
public class MetodosGerais
{

    public MetodosGerais ()
    {
    }

    
    public static int numeroDiasDoMes (int ano, int mes)
    {
        if (mes > 12 || mes < 1)
        {
            return 0;
        }

        if (mes == 2)
        {
            return (ano % 400 == 0) || (ano % 4 == 0 && ano % 100 != 0) ? 29 : 28;
        }

        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 10 || mes == 12)
        {
            return 31;
        }

        return 30;
    }
    
    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a próxima data, consoante o número de dias e a data fornecida
     *
     * @param data data actual
     * @param numDias número de dias para acrescentar na data fornecida
     * @return
     */
    public static Date calcularProximaData (Date data, long numDias)
    {
        if (data == null)
        {
            throw new NullPointerException("Data NULL");
        }
        else if (numDias < 0)
        {
            throw new IllegalArgumentException("O Número de dias deve ser >= 0");
        }

        long MILISSEGUNDOS_DE_24_HORAS = 86400000;

        long dataLong = data.getTime() + numDias * MILISSEGUNDOS_DE_24_HORAS;

        return new Date(dataLong);
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a data de hoje sem a hora
     *
     * @return
     */
    public static Date getDataDeHojeSemHora ()
    {
        try
        {
            String dataActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            return new SimpleDateFormat("dd-MM-yyyy").parse(dataActual);
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a primeira data do ano actual
     *
     * @return
     */
    public static Date getPrimeiraDataDoAnoActual ()
    {
        try
        {
            String anoActual = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-" + anoActual);

            return dataDezembro;
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a última data do ano actual
     *
     * @return
     */
    public static Date getUltimaDataDoAnoActual ()
    {
        try
        {
            String anoActual = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + anoActual);

            return dataDezembro;
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a última data do proximo ano
     *
     * @return
     */
    public static Date getUltimaDataDoProximoAno ()
    {
        try
        {
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + (getAnoActual()+1));

            return dataDezembro;
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a última de um ano
     * @param ano
     *
     * @return
     */
    public static Date getPrimeiraDataDoAno (Integer ano)
    {
        try
        {
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-" + ano);

            return dataDezembro;
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna a última de um ano
     * @param ano
     *
     * @return
     */
    
    public static Date getUltimaDataDoAno (Integer ano)
    {
        try
        {
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + ano);

            return dataDezembro;
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna o dia do mês actual
     *
     * @return
     */
    public static Integer getDiaDoMesActual ()
    {
        String diaDoMesActual = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());

        return Integer.parseInt(diaDoMesActual);
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna o mês actual
     *
     * @return
     */
    public static Integer getMesActual ()
    {
        String mesActual = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());

        return Integer.parseInt(mesActual);
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna o ano actual
     *
     * @return
     */
    public static Integer getAnoActual ()
    {
        String anoActual = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

        return Integer.parseInt(anoActual);
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Retorna o nome do mês de uma determinada data
     * @param data
     * @return
     */
    public static String obterNomeDoMes (Date data)
    {
        if (data == null)
        {
            return "";
        }

        int mes = Integer.parseInt(new SimpleDateFormat("MM").format(data));

        switch (mes)
        {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
            default:
                return "";
        }

    }
    
    /**
     * @author Ornela F. Boaventura
     *
     * Retorna o número do mês de uma determinada data
     * @param data
     * @return
     */
    public static int obterNumeroDoMes (Date data)
    {
        if (data == null)
        {
            return 0;
        }

        return Integer.parseInt(new SimpleDateFormat("MM").format(data));
    }
}
