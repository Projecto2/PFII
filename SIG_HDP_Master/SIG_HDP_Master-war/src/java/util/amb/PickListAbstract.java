/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.amb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author aires
 */
public abstract class PickListAbstract implements PickListInterface
{

    protected DualListModel<String> dualList;
    protected List<String> listSource, listTarget;

    public PickListAbstract()
    {
        init();
    }

    /**
     *
     */
    //@Override
    protected String init()
    {
        //super.init();
        dualList = new DualListModel();
        listSource = new ArrayList();
        listTarget = new ArrayList();
        return null;
    }

    public abstract List<String> geraAllSourceNames();

    public abstract List<String> geraAllTargetNames();

    public abstract void gravar();

    // as sub-classes podem sobreescrever o metodo generateStringCompartor();
    public Comparator generateStringCompartor()
    {
        return new StringComparator();
    }

    private class StringComparator implements Comparator
    {

        public int compare(Object o1, Object o2)
        {
            return ((String) o1).compareToIgnoreCase((String) o2);
        }
    }

    public void inicializar()
    {
        //super.init();
        listSource = new ArrayList();
        listTarget = new ArrayList();
        List<String> inicTargetList = inicListTarget();
        inicListSource(inicTargetList);
//debug();
        dualList = new DualListModel(listSource, listTarget);
    }

    @Override
    public void inicListSource()
    {
        inicListSource(listTarget);
    }

    public void inicListSource(List<String> inicTargetList)
    {
        //System.out.println("0: PickListAbstract.inicListSource(inicTargetList)");
        List<String> allSourceNames = this.geraAllSourceNames();
        listSource = new ArrayList();
        dualList = new DualListModel(listSource, listTarget);
        //System.out.println("1: PickListAbstract.inicListSource(inicTargetList)");
        if (allSourceNames != null && !allSourceNames.isEmpty())
        {
            if (inicTargetList == null || inicTargetList.isEmpty())
            {
                listSource.addAll(allSourceNames);
                sort(listSource);
                for (String l : listSource)
                {
                    //System.out.println("2: PickListAbstract.inicListSource(inicTargetList)\tdoenca: " + l);
                }
                return;
            }
        }
        //System.out.println("3: PickListAbstract.inicListSource(inicTargetList)");
        if (allSourceNames != null && !allSourceNames.isEmpty())
        {
            for (String sourceName : allSourceNames)
            {
                //System.out.println("4: PickListAbstract.inicListSource(inicTargetList)\tsourceName:" + sourceName);
                if (inicTargetList.indexOf(sourceName) == -1)
                {
                    //System.out.println("5: PickListAbstract.inicListSource(inicTargetList)\tsourceName:" + sourceName);
                    listSource.add(sourceName);
                }
            }
        }
        sort(listSource);
        //System.out.println("6: PickListAbstract.inicListSource(inicTargetList)");
    }

    public List<String> inicListTarget()
    {
        List<String> allTargetNames = geraAllTargetNames();
        if (allTargetNames != null && !allTargetNames.isEmpty())
        {
            listTarget.addAll(allTargetNames);
        }
        sort(listTarget);
        return allTargetNames;
    }

    public void onTransfer(TransferEvent event)
    {
//event.getItems() : List of items transferred
//event.isAdd() : Is transfer from source to target
//event.isRemove() : Is transfer from target to source
        List<String> items = (List<String>) event.getItems();
        //System.out.println("0: PickListAbstract.onTransfer()");
        if (items == null || items.isEmpty())
        {
            return;
        }
        //System.out.println("1: PickListAbstract.onTransfer()");
        if (event.isAdd())
        {
            this.listTarget.addAll(items);
            this.listSource.removeAll(items);
        }
        else if (event.isRemove())
        {
            this.listSource.addAll(items);
            this.listTarget.removeAll(items);
        }

        sort(listSource);
        sort(listTarget);
        dualList = new DualListModel(listSource, listTarget);
//debug();
////System.out.println("1: onTransfer()");        
    }

    public void sort(List<String> list)
    {
        if (list == null || list.isEmpty())
            return;
        Collections.sort(list, generateStringCompartor());
    }

    public void debug()
    {
        if (dualList != null)
        {
            //System.out.println("dualList: not null");
        }
        else
        {
            //System.out.println("dualList: null");
            return;
        }
        //List<String> listSource = departamentos.getSource();
        String buffer = "listSource[" + listSource.size() + "]: ";
        int i = 0;

        for (String source : listSource)
        {
            buffer += (i == 0 ? source : ", " + source);
            i++;
        }
        //List<String> listTarget = departamentos.getTarget();
        buffer += "\nlistTarget[" + listTarget.size() + "]: ";
        i = 0;
        for (String target : listTarget)
        {
            buffer += (i == 0 ? target : ", " + target);
            i++;
        }
        System.out.print(buffer);
//        //System.out.println("debug()");
    }

    // metodos get e set
    public List<String> getSource()
    {
        listSource = dualList.getSource();
        return listSource;
    }

    public List<String> getTarget()
    {
        listTarget = dualList.getTarget();
        return listTarget;
    }

    public List<String> getListSource()
    {
        return listSource;
    }

    public void setListSource(List<String> listSource)
    {
        this.listSource = listSource;
    }

    public List<String> getListTarget()
    {
        return listTarget;
    }

    public void setListTarget(List<String> listTarget)
    {
        this.listTarget = listTarget;
    }

    public DualListModel<String> getDualList()
    {
        return dualList;
    }

    public void setDualList(DualListModel<String> dualList)
    {
        this.dualList = dualList;
    }

}
