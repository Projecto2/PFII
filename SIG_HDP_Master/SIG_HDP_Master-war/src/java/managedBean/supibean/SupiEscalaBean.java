/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;


import entidade.RhFuncionario;
import entidade.SupiEscala;
import entidade.SupiSeccao;
import entidade.SupiSupervisorHasEscala;
import entidade.SupiTipoEscala;
import entidade.SupiTurno;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import sessao.SupiEscalaFacade;
import sessao.SupiTurnoFacade;
import util.supi.EscalaModelo;
import util.GeradorCodigo;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiEscalaBean
{

    @EJB
    private SupiTurnoFacade supiTurnoFacade;
    @EJB
    private SupiEscalaFacade supiEscalaFacade;

    private int ano;
    private int mes;
    private int idTurno;
    private int[] turno = new int[31];
    private Calendar dataCorrente;
    private SupiEscala escala;

    private ArrayList<EscalaModelo> listaEscala;
    private List<SupiSupervisorHasEscala> escalaMensalList;

    public SupiEscalaBean()
    {
        dataCorrente = Calendar.getInstance();
        listaEscala = new ArrayList<EscalaModelo>();
    }
    
    
     public static SupiEscalaBean getInstanciaBean()
    {
        return (SupiEscalaBean) GeradorCodigo.getInstanciaBean("supiEscalaBean");
    }
     
     public static SupiEscala getInstancia () {
      SupiEscala  escala = new SupiEscala();
      escala.setFkIdSeccao(new SupiSeccao());
      escala.setFkIdTipoEscala(new SupiTipoEscala());
     
        return escala;
    }
    

    public Calendar getDataCorrente()
    {
        return dataCorrente;
    }

    public void setDataCorrente(Calendar dataCorrente)
    {
        this.dataCorrente = dataCorrente;
    }

    public int getMes()
    {
        return mes;
    }

    public void setMes(int mes)
    {
        this.mes = mes;
    }
    
    

    public int getAno()
    {

        return ano + dataCorrente.get(Calendar.YEAR);
    }

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public int[] getTurno()
    {
        return turno;
    }

    public void setTurno(int[] turno)
    {
        this.turno = turno;
    }

    public int getIdTurno()
    {
        return idTurno;
    }

    public void setIdTurno(int idTurno)
    {
        this.idTurno = idTurno;
    }

    
    
    public ArrayList<SelectItem> selectMeses()
    {
        ArrayList<SelectItem> itens = new ArrayList<>();

        itens.add(new SelectItem(1, "Janeiro"));
        itens.add(new SelectItem(2, "Fevereiro"));
        itens.add(new SelectItem(3, "Março"));
        itens.add(new SelectItem(4, "Abril"));
        itens.add(new SelectItem(5, "Maio"));
        itens.add(new SelectItem(6, "Junho"));
        itens.add(new SelectItem(7, "Julho"));
        itens.add(new SelectItem(8, "Agosto"));
        itens.add(new SelectItem(9, "Setembro"));
        itens.add(new SelectItem(10, "Outubro"));
        itens.add(new SelectItem(11, "Novembro"));
        itens.add(new SelectItem(12, "Dezembro"));

        return itens;
    }

    public ArrayList<EscalaModelo> getListaEscala()
    {
        escalaMensalList = new ArrayList<>();
        listaEscala = new ArrayList();
        for (RhFuncionario func : getListaSupervisores())
        {
            EscalaModelo modelo = new EscalaModelo();
            SupiSupervisorHasEscala item = getInstanciaSupiSupervisorHasEscala();
            item.setFkIdFuncionario(func);

            modelo.setRhFuncionario(func.getPkIdFuncionario());
            modelo.setFuncionario(func);
            System.out.println("idturno:" + turno);
            modelo.setIdturno(0);
            item.setData(dataCorrente.getTime());

            escalaMensalList.add(item);
            listaEscala.add(modelo);
        }
        return listaEscala;
    }

    public SupiSupervisorHasEscala getInstanciaSupiSupervisorHasEscala()
    {
        SupiSupervisorHasEscala item = new SupiSupervisorHasEscala();
        SupiEscala escalaAux = new SupiEscala();
        escalaAux.setFkIdSeccao(new SupiSeccao());
        escalaAux.setFkIdTipoEscala(new SupiTipoEscala());
        item.setFkIdEscala(escalaAux);
        item.setFkIdTurno(new SupiTurno());
        item.getFkIdTurno().setSigla("--");
        return item;
    }

    public void setListaEscala(ArrayList<EscalaModelo> listaEscala)
    {
        this.listaEscala = listaEscala;
    }

    public void SalvaEscala(EscalaModelo escala)

    {
        
        for(int i= 0; i<turno.length; i++){
            System.out.println("turno:"+turno[i]);
        }
        System.out.println("escala:" + escala);
        System.out.println("funcionario:" + escala.getFuncionario().getFkIdPessoa().getNome());
        System.out.println("id turno" + escala.getIdturno());

    }

    public void actualizarEscala(){
        
        
    }
    //listar todos os supervisores
    public List<RhFuncionario> getListaSupervisores()
    {
        //System.out.println("Supervisores:" + supiEscalaFacade.findEnfermeiros());
        return supiEscalaFacade.findEnfermeiros();
    }

    public int numeroDias()
    {
        int anoCorrente;
        int mesParametro = mes;

        anoCorrente = dataCorrente.get(Calendar.YEAR);

        if (mesParametro > 12 || mesParametro < 1)
        {
            return 0;
        }

        if (mesParametro == 2)
        {
            return (anoCorrente % 400 == 0) || (anoCorrente % 4 == 0 && anoCorrente % 100 != 0) ? 29 : 28;
        }

        if (mesParametro == 1 || mesParametro == 3 || mesParametro == 5 || mesParametro == 7 || mesParametro == 10 || mesParametro == 12)
        {
            return 31;
        }

        return 30;
    }

}
