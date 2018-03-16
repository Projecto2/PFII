/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;

/**
 *
 * @author Ornela F. Boaventura
 */
public class MetodosGerais
{

    public MetodosGerais ()
    {
    }

    /**
     * @author Ornela F. Boaventura Retorna a próxima data, consoante o número
     * de dias e a data fornecida
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
}
