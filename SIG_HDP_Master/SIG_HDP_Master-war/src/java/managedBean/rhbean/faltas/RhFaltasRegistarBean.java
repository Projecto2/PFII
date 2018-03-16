/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.faltas;

import entidade.RhFuncionario;
import entidade.RhFuncionarioHasRhTipoFalta;
import entidade.RhTipoFalta;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhFuncionarioFacade;
import sessao.RhFuncionarioHasRhTipoFaltaFacade;
import sessao.RhTipoFaltaFacade;
import util.rh.Defs;
import util.Mensagem;
import util.rh.MetodosGerais;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFaltasRegistarBean implements Serializable
{
    
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    RhTipoFaltaFacade tipoFaltaFacade;
    @EJB
    RhFuncionarioHasRhTipoFaltaFacade funcionarioHasRhTipoFaltaFacade;
    @EJB
    RhFuncionarioFacade funcionarioFacade;

    /**
     * Entidades
     */
    private List<RhFuncionario> funcionariosList;
    private RhFuncionarioHasRhTipoFalta falta;
    private Integer idSeccaoTrabalho, ano, mes;
    private boolean desabilitarCampos;

    /**
     * Creates a new instance of assiduidadeBean
     */
    public RhFaltasRegistarBean ()
    {
    }
    
    public Integer getIdSeccaoTrabalho ()
    {
        return idSeccaoTrabalho;
    }
    
    public void setIdSeccaoTrabalho (Integer idSeccaoTrabalho)
    {
        this.idSeccaoTrabalho = idSeccaoTrabalho;
    }
    
    public Integer getAno ()
    {
        return ano;
    }
    
    public void setAno (Integer ano)
    {
        this.ano = ano;
    }
    
    public Integer getMes ()
    {
        return mes;
    }
    
    public void setMes (Integer mes)
    {
        this.mes = mes;
    }
    
    public boolean isDesabilitarCampos ()
    {
        return desabilitarCampos;
    }
    
    public void setDesabilitarCampos (boolean desabilitarCampos)
    {
        this.desabilitarCampos = desabilitarCampos;
    }
    
    public List<RhFuncionario> getFuncionariosList ()
    {
        return funcionariosList;
    }
    
    public void setFuncionariosList (List<RhFuncionario> funcionariosList)
    {
        this.funcionariosList = funcionariosList;
    }
    
    public RhFuncionarioHasRhTipoFalta getFalta ()
    {
        return falta;
    }
    
    public void setFalta (RhFuncionarioHasRhTipoFalta falta)
    {
        this.falta = falta;
    }
    
    public ArrayList<SelectItem> selectMeses ()
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
    
    public String limpar ()
    {
        funcionariosList = null;
        
        ano = MetodosGerais.getAnoActual();
        mes = MetodosGerais.getMesActual();
        idSeccaoTrabalho = null;
        desabilitarCampos = false;
        
        return "faltasRegistarRh.xhtml?faces-redirect=true";
    }
    
    public String sair ()
    {
        limpar();
        
        return "faltasVisualizarRh.xhtml?faces-redirect=true";
    }
    
    public String continuarRegistoFaltas ()
    {
        
        final int ANO_ACTUAL = MetodosGerais.getAnoActual();
        final int MES_ACTUAL = MetodosGerais.getMesActual();
        
        if (ano < ANO_ACTUAL - 1 || ano > ANO_ACTUAL)
        {
            Mensagem.erroMsg("Ano inválido ! O ano deve ser o corrente ou o anterior");
            return null;
        }
        else if (ano < ANO_ACTUAL && (mes != 12 || MES_ACTUAL > 1))
        {
            Mensagem.erroMsg("Mês inválido ! O mês deve ser o actual ou o anterior");
            return null;
        }
        else if (ano == ANO_ACTUAL && mes > MES_ACTUAL || mes < MES_ACTUAL - 1)
        {
            Mensagem.erroMsg("Mês inválido ! O mês deve ser o actual ou o anterior");
            return null;
        }
        
        desabilitarCampos = true;
        prepararListaDeFuncionarios();
        
        return "faltasRegistarRh.xhtml?faces-redirect=true";
    }
    
    public int totalDiasDoMes ()
    {
        if (mes == null || null == ano)
        {
            return 0;
        }
        return MetodosGerais.numeroDiasDoMes(ano, mes);
    }
    
    public List<Object> getDiasDoMesList ()
    {
        List<Object> dias = new ArrayList<>();
        
        for (int i = 1; i <= totalDiasDoMes(); i++)
        {
            dias.add(i);
        }
        
        return dias;
    }
    
    public void changeTipoFalta (ValueChangeEvent eve)
    {
        Integer idFalta = (Integer) eve.getNewValue();
        
        if (idFalta != null)
        {
            falta.getFkIdTipoFalta().setDescricao(tipoFaltaFacade.find(idFalta).getDescricao());
        }
        else
        {
            falta.getFkIdTipoFalta().setDescricao(null);
        }
    }
    
    public void alterarFalta ()
    {
        //Altera a falta e passa a ter o tipo de falta que foi modificado
        if (falta.getFkIdTipoFalta().getPkIdTipoFalta() == null)
        {
            falta.setFkIdTipoFalta(new RhTipoFalta());
        }
        else
        {
            falta.setFkIdTipoFalta(tipoFaltaFacade.find(falta.getFkIdTipoFalta().getPkIdTipoFalta()));
        }
        setFalta(null);
    }
    
    public String create ()
    {
        try
        {
            if (funcionariosList.isEmpty())
            {
                Mensagem.erroMsg("Nenhum funcionário encontrado");
                return null;
            }
            
            userTransaction.begin();
            
            for (RhFuncionario func : funcionariosList)
            {
                for (RhFuncionarioHasRhTipoFalta faltaMarcada : func.getRhFuncionarioHasRhTipoFaltaList())
                {
                    //Se o tipo de falta foi deixado em branco
                    //Por exemplo se não for falta justificada nem injustificada
                    if (faltaMarcada.getFkIdTipoFalta() != null && faltaMarcada.getFkIdTipoFalta().getPkIdTipoFalta() == null)
                    {
                        faltaMarcada.setFkIdTipoFalta(null);
                    }

                    //Se a chave pimária estiver nula significa que a falta está a ser editada
                    if (faltaMarcada.getPkIdFuncionarioHasTipoFalta() != null)
                    {
                        funcionarioHasRhTipoFaltaFacade.edit(faltaMarcada);
                    }
                    else
                    {
                        funcionarioHasRhTipoFaltaFacade.create(faltaMarcada);
                    }
                }
            }
            
            userTransaction.commit();
            
            Mensagem.sucessoMsg("Registo de faltas guardado com sucesso!");
            limpar();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        
        return null;
    }
    
    public String corDaFalta (RhTipoFalta tipoFalta)
    {
        
        if (tipoFalta == null || tipoFalta.getDescricao() == null)
        {
            return null;
        }
        
        if (tipoFalta.getDescricao().equals(Defs.RH_FALTA_JUSTIFICADA))
        {
            return "#00ba8b";//Verde

        }
        else if (tipoFalta.getDescricao().equals(Defs.RH_FALTA_NAO_JUSTIFICADA))
        {
            return "tomato";
        }
        
        return "null";
        
    }
    
    private void prepararListaDeFuncionarios ()
    {
        try
        {
            funcionariosList = null;
            List<RhFuncionario> funcionarios = funcionarioFacade.findFuncionariosPorSeccaoSemReformadosEDemitidos(idSeccaoTrabalho);
            
            if (funcionarios.isEmpty())
            {
                Mensagem.warnMsg("Nenhum funcionário encontrado ! ");
                desabilitarCampos = false;
            }

            //Para cada funcionário
            for (RhFuncionario f : funcionarios)
            {
                Date datInf = new SimpleDateFormat("dd-MM-yyyy").parse("1-" + mes + "-" + ano);
                Date datSup = new SimpleDateFormat("dd-MM-yyyy").parse(MetodosGerais.numeroDiasDoMes(ano, mes) + "-" + mes + "-" + ano);

                //Prepara uma lista de faltas para o corrente funcionário
                List<RhFuncionarioHasRhTipoFalta> listaDeFaltas = funcionarioHasRhTipoFaltaFacade.findFaltas(f.getPkIdFuncionario(), datInf, datSup);

                //Se a lista de faltas estiver vazia
                //Significa que não existe nehum registo de faltas deste funcionário ou seja será feito o registo
                if (listaDeFaltas.isEmpty())
                {
                    //Carrega a lista de faltas com todos os dias do mês selecionado
                    for (int i = 1; i <= totalDiasDoMes(); i++)
                    {
                        RhFuncionarioHasRhTipoFalta ft = new RhFuncionarioHasRhTipoFalta();
                        ft.setFkIdTipoFalta(new RhTipoFalta());

                        //O i representa o dia do mês
                        String dt = i + "-" + mes + "-" + ano;
                        Date data = new SimpleDateFormat("dd-MM-yyyy").parse(dt);
                        
                        ft.setFkIdFuncionario(f);
                        ft.setData(data);
                        
                        listaDeFaltas.add(ft);
                    }
                }
                else
                {
                    for (RhFuncionarioHasRhTipoFalta faltaDoFunc : listaDeFaltas)
                    {
                        if (faltaDoFunc.getFkIdTipoFalta() == null)
                        {
                            faltaDoFunc.setFkIdTipoFalta(new RhTipoFalta());
                        }
                    }
                }
                
                f.setRhFuncionarioHasRhTipoFaltaList(listaDeFaltas);
            }
            funcionariosList = funcionarios;
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
    }
    
    public int contarFaltasNaoJustificadas (RhFuncionario funcionario)
    {
        if (funcionario == null || funcionario.getRhFuncionarioHasRhTipoFaltaList() == null)
        {
            return 0;
        }
        
        int cont = 0;
        
        for (RhFuncionarioHasRhTipoFalta falt : funcionario.getRhFuncionarioHasRhTipoFaltaList())
        {
            //Se a falta não estiver nula e for injustificada
            if (falt != null && falt.getFkIdTipoFalta() != null && Defs.RH_FALTA_NAO_JUSTIFICADA.equals(falt.getFkIdTipoFalta().getDescricao()))
            {
                cont++;
            }
        }
        return cont;
    }
    
}
