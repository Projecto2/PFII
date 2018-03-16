/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.RhFuncionario;
import entidade.SupiEscala;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sessao.SupiEscalaFacade;
//import util.EscalaModelo;

/**
 *
 * @author helga
 */
@ManagedBean
@RequestScoped
public class EscalaBean {

  
    @EJB
    private SupiEscalaFacade supiEscalaFacade;

    /**
     * Creates a new instance of EscalaBean
     */
    private Date data;
    private int mes;

    public EscalaBean() {
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

   

    

   

//    public int verificaEscalaIndex(List<EscalaModelo> lista, RhFuncionario supervisor) {
//        for (int i = 0; i < lista.size(); i++) {
//            if (lista.get(i).getSupervisores() == supervisor.getFkIdPessoa().getNome()) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public int retornaDia(Date data) {
//        try {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(data);
//            return cal.get(5);
//        } catch (Exception e) {
//        }
//        return 0;
//    }
//
//    public int retornaMes(Date data) {
//        try {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(data);
//            return cal.get(2) + 1;
//        } catch (Exception e) {
//        }
//        return 0;
//    }
//
//    public int retornaAno(Date data) {
//        try {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(data);
//            return cal.get(1);
//        } catch (Exception e) {
//        }
//        return 0;
//    }
}
