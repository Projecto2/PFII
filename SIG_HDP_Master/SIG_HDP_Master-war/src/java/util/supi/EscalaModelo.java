/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.supi;

import entidade.RhFuncionario;
import entidade.SupiTurno;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessao.SupiEscalaFacade;

/**
 *
 * @author helga
 */
public class EscalaModelo implements Serializable{
    
     @EJB
    private SupiEscalaFacade supiEscalaFacade;

    private int supiEscala;
    private int rhFuncionario;
    private SupiTurno supiTurno;
    private String Observacao;
    private Date data;
    private Date dataEscala;
    private int supiSeccao;
    private int supiTipoEscala;
    private int ano;
    private int mes;
    private int idturno;
    private RhFuncionario funcionario;
    private List<RhFuncionario> listaFuncionarios;

    public int getSupiEscala()
    {
        return supiEscala;
    }

    public void setSupiEscala(int supiEscala)
    {
        this.supiEscala = supiEscala;
    }

    public int getRhFuncionario()
    {
        return rhFuncionario;
    }

    public void setRhFuncionario(int rhFuncionario)
    {
        this.rhFuncionario = rhFuncionario;
    }

    public SupiTurno getSupiTurno()
    {
        return supiTurno;
    }

    public void setSupiTurno(SupiTurno supiTurno)
    {
        this.supiTurno = supiTurno;
    }

    

    public String getObservacao()
    {
        return Observacao;
    }

    public void setObservacao(String Observacao)
    {
        this.Observacao = Observacao;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public Date getDataEscala()
    {
        return dataEscala;
    }

    public void setDataEscala(Date dataEscala)
    {
        this.dataEscala = dataEscala;
    }

    public int getSupiSeccao()
    {
        return supiSeccao;
    }

    public void setSupiSeccao(int supiSeccao)
    {
        this.supiSeccao = supiSeccao;
    }

    public int getSupiTipoEscala()
    {
        return supiTipoEscala;
    }

    public void setSupiTipoEscala(int supiTipoEscala)
    {
        this.supiTipoEscala = supiTipoEscala;
    }

    public int getIdturno()
    {
        
        return idturno;
    }

    public void setIdturno(int idturno)
    {
        this.idturno = idturno;
    }

    public int getAno()
    {
        return ano;
    }

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public int getMes()
    {
        return mes;
    }

    public void setMes(int mes)
    {
        this.mes = mes;
    }

    public RhFuncionario getFuncionario()
    {
        return funcionario;
    }

    public void setFuncionario(RhFuncionario funcionario)
    {
        this.funcionario = funcionario;
    }

    public List<RhFuncionario> getListaFuncionarios()
    {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<RhFuncionario> listaFuncionarios)
    {
        this.listaFuncionarios = listaFuncionarios;
    }
    
}
