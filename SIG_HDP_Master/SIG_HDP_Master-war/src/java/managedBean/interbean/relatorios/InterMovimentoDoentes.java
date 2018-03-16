/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.relatorios;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author armindo
 */
public class InterMovimentoDoentes implements Serializable
{
    private String servico, numeroCama, numeroInternamento, nomePaciente, tipoMovimento;
    
    private Date data;
    
    private int numeroDiasAntes, totEntrada, totAlta, totFalecidos, totTransferidos;
    private int totAbandonados, totFinal, cont;

    public InterMovimentoDoentes()
    {
    }

    public String getServico()
    {
        return servico;
    }

    public void setServico(String servico)
    {
        this.servico = servico;
    }

    public String getNumeroCama()
    {
        return numeroCama;
    }

    public void setNumeroCama(String numeroCama)
    {
        this.numeroCama = numeroCama;
    }

    public String getNumeroInternamento()
    {
        return numeroInternamento;
    }

    public void setNumeroInternamento(String numeroInternamento)
    {
        this.numeroInternamento = numeroInternamento;
    }

    public String getNomePaciente()
    {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente)
    {
        this.nomePaciente = nomePaciente;
    }

    public String getTipoMovimento()
    {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento)
    {
        this.tipoMovimento = tipoMovimento;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public int getNumeroDiasAntes()
    {
        return numeroDiasAntes;
    }

    public void setNumeroDiasAntes(int numeroDiasAntes)
    {
        this.numeroDiasAntes = numeroDiasAntes;
    }

    public int getTotEntrada()
    {
        return totEntrada;
    }

    public void setTotEntrada(int totEntrada)
    {
        this.totEntrada = totEntrada;
    }

    public int getTotAlta()
    {
        return totAlta;
    }

    public void setTotAlta(int totAlta)
    {
        this.totAlta = totAlta;
    }

    public int getTotFalecidos()
    {
        return totFalecidos;
    }

    public void setTotFalecidos(int totFalecidos)
    {
        this.totFalecidos = totFalecidos;
    }

    public int getTotTransferidos()
    {
        return totTransferidos;
    }

    public void setTotTransferidos(int totTransferidos)
    {
        this.totTransferidos = totTransferidos;
    }

    public int getTotAbandonados()
    {
        return totAbandonados;
    }

    public void setTotAbandonados(int totAbandonados)
    {
        this.totAbandonados = totAbandonados;
    }

    public int getTotFinal()
    {
        return totFinal;
    }

    public void setTotFinal(int totFinal)
    {
        this.totFinal = totFinal;
    }

    public int getCont()
    {
        return cont;
    }

    public void setCont(int cont)
    {
        this.cont = cont;
    }
}
