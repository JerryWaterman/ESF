/*
 * SHEM8View.java
 */

package shem8application;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import org.jdesktop.application.Task;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.RollbackException;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 * The application's main frame.
 */
public class SHEM8View extends FrameView {
    
    public SHEM8View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
	messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
	messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        }); 
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        // tracking table selection
        masterTable.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    firePropertyChange("recordSelected", !isRecordSelected(), isRecordSelected());
                    //This method is fired when a selection is made from the GUI table
                    //If a selection is made, we need to set the combobox index to the index that matches
                    //the PI Name #1 field.  This method will need to be expanded to include other drop downs
                    //if this comboboxes are to be used to modify data in other columns
                    Boolean lnameInList = false;
                    int selectedRow =masterTable.getSelectedRow();
                    int selectedIndex = 0;
                    if(masterTable.getValueAt(selectedRow,3) !=null){                    
                        String selectedPI1Lastname = masterTable.getValueAt(selectedRow,3).toString();
                        for(int i=0;i<PI1LastnameCombo.getItemCount();i++){  
                           if(selectedPI1Lastname.equals(PI1LastnameCombo.getItemAt(i).toString())){
                               selectedIndex = i;
                               lnameInList = true;
                           }
                        }
                        if(lnameInList){
                            PI1LastnameCombo.setSelectedIndex(selectedIndex);
                        }
                    }   
                }                
            });

        // tracking changes to save
        bindingGroup.addBindingListener(new AbstractBindingListener() {
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                // save action observes saveNeeded property
                setSaveNeeded(true);
            }
        });

        // have a transaction started
        entityManager.getTransaction().begin();
    }


    public boolean isSaveNeeded() {
        return saveNeeded;
    }

    private void setSaveNeeded(boolean saveNeeded) {
        if (saveNeeded != this.saveNeeded) {
            this.saveNeeded = saveNeeded;
            firePropertyChange("saveNeeded", !saveNeeded, saveNeeded);
        }
    }

    public boolean isRecordSelected() {
        return masterTable.getSelectedRow() != -1;
    }
    

    @Action
    public void newRecord() {
        shem8application.Shasptest s = new shem8application.Shasptest();
        entityManager.persist(s);
        list.add(s);
        int row = list.size()-1;
        masterTable.setRowSelectionInterval(row, row);
        masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
        setSaveNeeded(true);
    }

    @Action(enabledProperty = "recordSelected")
    public void deleteRecord() {
        int[] selected = masterTable.getSelectedRows();
        List<shem8application.Shasptest> toRemove = new ArrayList<shem8application.Shasptest>(selected.length);
        for (int idx=0; idx<selected.length; idx++) {
            shem8application.Shasptest s = list.get(masterTable.convertRowIndexToModel(selected[idx]));
            toRemove.add(s);
            entityManager.remove(s);
        }
        list.removeAll(toRemove);
        setSaveNeeded(true);
    }
    

    @Action(enabledProperty = "saveNeeded")
    public Task save() {
        return new SaveTask(getApplication());
    }

    private class SaveTask extends Task {
        SaveTask(org.jdesktop.application.Application app) {
            super(app);
        }
        @Override protected Void doInBackground() {
            try {
                entityManager.getTransaction().commit();
                entityManager.getTransaction().begin();
            } catch (RollbackException rex) {
                rex.printStackTrace();
                entityManager.getTransaction().begin();
                List<shem8application.Shasptest> merged = new ArrayList<shem8application.Shasptest>(list.size());
                for (shem8application.Shasptest s : list) {
                    merged.add(entityManager.merge(s));
                }
                list.clear();
                list.addAll(merged);
            }
            return null;
        }
        @Override protected void finished() {
            setSaveNeeded(false);
        }
    }

    /**
     * An example action method showing how to create asynchronous tasks
     * (running on background) and how to show their progress. Note the
     * artificial 'Thread.sleep' calls making the task long enough to see the
     * progress visualization - remove the sleeps for real application.
     */
    @Action
    public Task refresh() {
       return new RefreshTask(getApplication());
    }

    private class RefreshTask extends Task {
        RefreshTask(org.jdesktop.application.Application app) {
            super(app);
        }
        @SuppressWarnings("unchecked")
        @Override protected Void doInBackground() {
            try {
                setProgress(0, 0, 4);
                setMessage("Rolling back the current changes...");
                setProgress(1, 0, 4);
                entityManager.getTransaction().rollback();
                Thread.sleep(1000L); // remove for real app
                setProgress(2, 0, 4);

                setMessage("Starting a new transaction...");
                entityManager.getTransaction().begin();
                Thread.sleep(500L); // remove for real app
                setProgress(3, 0, 4);

                setMessage("Fetching new data...");
                java.util.Collection data = query.getResultList();
                for (Object entity : data) {
                    entityManager.refresh(entity);
                }
                Thread.sleep(1300L); // remove for real app
                setProgress(4, 0, 4);

                Thread.sleep(150L); // remove for real app
                list.clear();
                list.addAll(data);
            } catch(InterruptedException ignore) { }
            return null;
        }
        @Override protected void finished() {
            setMessage("Done.");
            setSaveNeeded(false);
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SHEM8Application.getApplication().getMainFrame();
            aboutBox = new SHEM8AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SHEM8Application.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mainPanel = new javax.swing.JPanel();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        haspnumLabel = new javax.swing.JLabel();
        inactiveLabel = new javax.swing.JLabel();
        haspPreparerLabel = new javax.swing.JLabel();
        invest1lastnameLabel = new javax.swing.JLabel();
        invest2lastnameLabel = new javax.swing.JLabel();
        invest3lastnameLabel = new javax.swing.JLabel();
        invest4lastnameLabel = new javax.swing.JLabel();
        invest5lastnameLabel = new javax.swing.JLabel();
        invest6lastnameLabel = new javax.swing.JLabel();
        invest7lastnameLabel = new javax.swing.JLabel();
        invest8lastnameLabel = new javax.swing.JLabel();
        branchchiefLabel = new javax.swing.JLabel();
        officeLabel = new javax.swing.JLabel();
        labLabel = new javax.swing.JLabel();
        divisionIdLabel = new javax.swing.JLabel();
        branchIdLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        notesLabel = new javax.swing.JLabel();
        location1Label = new javax.swing.JLabel();
        location2Label = new javax.swing.JLabel();
        location3Label = new javax.swing.JLabel();
        location4Label = new javax.swing.JLabel();
        location5Label = new javax.swing.JLabel();
        fieldactivitiesLabel = new javax.swing.JLabel();
        labactivitiesLabel = new javax.swing.JLabel();
        treatabilitystudyLabel = new javax.swing.JLabel();
        hazardouswastegeneratorLabel = new javax.swing.JLabel();
        buildingLabel = new javax.swing.JLabel();
        currenthaspversionLabel = new javax.swing.JLabel();
        approvaldateLabel = new javax.swing.JLabel();
        expirationdateLabel = new javax.swing.JLabel();
        nextreviewLabel = new javax.swing.JLabel();
        annualreview1Label = new javax.swing.JLabel();
        annualreview2Label = new javax.swing.JLabel();
        haspnumField = new javax.swing.JTextField();
        inactiveField = new javax.swing.JTextField();
        haspPreparerField = new javax.swing.JTextField();
        invest1lastnameField = new javax.swing.JTextField();
        invest2lastnameField = new javax.swing.JTextField();
        invest3lastnameField = new javax.swing.JTextField();
        invest4lastnameField = new javax.swing.JTextField();
        invest5lastnameField = new javax.swing.JTextField();
        invest6lastnameField = new javax.swing.JTextField();
        invest7lastnameField = new javax.swing.JTextField();
        invest8lastnameField = new javax.swing.JTextField();
        branchchiefField = new javax.swing.JTextField();
        officeField = new javax.swing.JTextField();
        labField = new javax.swing.JTextField();
        divisionIdField = new javax.swing.JTextField();
        branchIdField = new javax.swing.JTextField();
        titleField = new javax.swing.JTextField();
        notesField = new javax.swing.JTextField();
        location1Field = new javax.swing.JTextField();
        location2Field = new javax.swing.JTextField();
        location3Field = new javax.swing.JTextField();
        location4Field = new javax.swing.JTextField();
        location5Field = new javax.swing.JTextField();
        fieldactivitiesField = new javax.swing.JTextField();
        labactivitiesField = new javax.swing.JTextField();
        treatabilitystudyField = new javax.swing.JTextField();
        hazardouswastegeneratorField = new javax.swing.JTextField();
        buildingField = new javax.swing.JTextField();
        currenthaspversionField = new javax.swing.JTextField();
        approvaldateField = new javax.swing.JTextField();
        expirationdateField = new javax.swing.JTextField();
        nextreviewField = new javax.swing.JTextField();
        annualreview1Field = new javax.swing.JTextField();
        annualreview2Field = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        PI1LastnameCombo = new javax.swing.JComboBox();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem newRecordMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem deleteRecordMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem saveMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem refreshMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(shem8application.SHEM8Application.class).getContext().getResourceMap(SHEM8View.class);
        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory(resourceMap.getString("entityManager.persistenceUnit")).createEntityManager(); // NOI18N
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery(resourceMap.getString("query.query")); // NOI18N
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        shasptestQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Shasptest s");
        shasptestList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : shasptestQuery.getResultList();
        shasptestQuery1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Shasptest s");
        shasptestList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : shasptestQuery1.getResultList();
        shasptestQuery2 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT s FROM Shasptest s");
        shasptestList2 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : shasptestQuery2.getResultList();
        haspPiQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT h FROM HaspPi h");
        haspPiList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : haspPiQuery.getResultList();

        mainPanel.setName("mainPanel"); // NOI18N

        masterScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        masterScrollPane.setName("masterScrollPane"); // NOI18N

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setName("masterTable"); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${haspnum}"));
        columnBinding.setColumnName("Haspnum");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${inactive}"));
        columnBinding.setColumnName("Inactive");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${haspPreparer}"));
        columnBinding.setColumnName("Hasp Preparer");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest1lastname}"));
        columnBinding.setColumnName("Invest1lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest2lastname}"));
        columnBinding.setColumnName("Invest2lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest2firstname}"));
        columnBinding.setColumnName("Invest2firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest3lastname}"));
        columnBinding.setColumnName("Invest3lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest3firstname}"));
        columnBinding.setColumnName("Invest3firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest4lastname}"));
        columnBinding.setColumnName("Invest4lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest4firstname}"));
        columnBinding.setColumnName("Invest4firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest5lastname}"));
        columnBinding.setColumnName("Invest5lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest5firstname}"));
        columnBinding.setColumnName("Invest5firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest6lastname}"));
        columnBinding.setColumnName("Invest6lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest6firstname}"));
        columnBinding.setColumnName("Invest6firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest7lastname}"));
        columnBinding.setColumnName("Invest7lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest7firstname}"));
        columnBinding.setColumnName("Invest7firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest8lastname}"));
        columnBinding.setColumnName("Invest8lastname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${invest8firstname}"));
        columnBinding.setColumnName("Invest8firstname");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${branchchief}"));
        columnBinding.setColumnName("Branchchief");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${office}"));
        columnBinding.setColumnName("Office");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lab}"));
        columnBinding.setColumnName("Lab");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${divisionId}"));
        columnBinding.setColumnName("Division Id");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${branchId}"));
        columnBinding.setColumnName("Branch Id");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${title}"));
        columnBinding.setColumnName("Title");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${notes}"));
        columnBinding.setColumnName("Notes");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${location1}"));
        columnBinding.setColumnName("Location1");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${location2}"));
        columnBinding.setColumnName("Location2");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${location3}"));
        columnBinding.setColumnName("Location3");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${location4}"));
        columnBinding.setColumnName("Location4");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${location5}"));
        columnBinding.setColumnName("Location5");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fieldactivities}"));
        columnBinding.setColumnName("Fieldactivities");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${labactivities}"));
        columnBinding.setColumnName("Labactivities");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${treatabilitystudy}"));
        columnBinding.setColumnName("Treatabilitystudy");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${hazardouswastegenerator}"));
        columnBinding.setColumnName("Hazardouswastegenerator");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${building}"));
        columnBinding.setColumnName("Building");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${currenthaspversion}"));
        columnBinding.setColumnName("Currenthaspversion");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${approvaldate}"));
        columnBinding.setColumnName("Approvaldate");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${expirationdate}"));
        columnBinding.setColumnName("Expirationdate");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nextreview}"));
        columnBinding.setColumnName("Nextreview");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${annualreview1}"));
        columnBinding.setColumnName("Annualreview1");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${annualreview2}"));
        columnBinding.setColumnName("Annualreview2");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${testcolumn1}"));
        columnBinding.setColumnName("Testcolumn1");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${TestCheckBox}"));
        columnBinding.setColumnName("Test Check Box");
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        masterTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("masterTable.columnModel.title0")); // NOI18N
        masterTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("masterTable.columnModel.title1")); // NOI18N
        masterTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("masterTable.columnModel.title2")); // NOI18N
        masterTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("masterTable.columnModel.title3")); // NOI18N
        masterTable.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("masterTable.columnModel.title5")); // NOI18N
        masterTable.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("masterTable.columnModel.title6")); // NOI18N
        masterTable.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("masterTable.columnModel.title7")); // NOI18N
        masterTable.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("masterTable.columnModel.title8")); // NOI18N
        masterTable.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("masterTable.columnModel.title9")); // NOI18N
        masterTable.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("masterTable.columnModel.title10")); // NOI18N
        masterTable.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("masterTable.columnModel.title11")); // NOI18N
        masterTable.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("masterTable.columnModel.title12")); // NOI18N
        masterTable.getColumnModel().getColumn(12).setHeaderValue(resourceMap.getString("masterTable.columnModel.title13")); // NOI18N
        masterTable.getColumnModel().getColumn(13).setHeaderValue(resourceMap.getString("masterTable.columnModel.title14")); // NOI18N
        masterTable.getColumnModel().getColumn(14).setHeaderValue(resourceMap.getString("masterTable.columnModel.title15")); // NOI18N
        masterTable.getColumnModel().getColumn(15).setHeaderValue(resourceMap.getString("masterTable.columnModel.title16")); // NOI18N
        masterTable.getColumnModel().getColumn(16).setHeaderValue(resourceMap.getString("masterTable.columnModel.title17")); // NOI18N
        masterTable.getColumnModel().getColumn(17).setHeaderValue(resourceMap.getString("masterTable.columnModel.title18")); // NOI18N
        masterTable.getColumnModel().getColumn(18).setHeaderValue(resourceMap.getString("masterTable.columnModel.title19")); // NOI18N
        masterTable.getColumnModel().getColumn(19).setHeaderValue(resourceMap.getString("masterTable.columnModel.title20")); // NOI18N
        masterTable.getColumnModel().getColumn(20).setHeaderValue(resourceMap.getString("masterTable.columnModel.title21")); // NOI18N
        masterTable.getColumnModel().getColumn(21).setHeaderValue(resourceMap.getString("masterTable.columnModel.title22")); // NOI18N
        masterTable.getColumnModel().getColumn(22).setHeaderValue(resourceMap.getString("masterTable.columnModel.title23")); // NOI18N
        masterTable.getColumnModel().getColumn(23).setHeaderValue(resourceMap.getString("masterTable.columnModel.title24")); // NOI18N
        masterTable.getColumnModel().getColumn(24).setHeaderValue(resourceMap.getString("masterTable.columnModel.title25")); // NOI18N
        masterTable.getColumnModel().getColumn(25).setHeaderValue(resourceMap.getString("masterTable.columnModel.title26")); // NOI18N
        masterTable.getColumnModel().getColumn(26).setHeaderValue(resourceMap.getString("masterTable.columnModel.title27")); // NOI18N
        masterTable.getColumnModel().getColumn(27).setHeaderValue(resourceMap.getString("masterTable.columnModel.title28")); // NOI18N
        masterTable.getColumnModel().getColumn(28).setHeaderValue(resourceMap.getString("masterTable.columnModel.title29")); // NOI18N
        masterTable.getColumnModel().getColumn(29).setHeaderValue(resourceMap.getString("masterTable.columnModel.title30")); // NOI18N
        masterTable.getColumnModel().getColumn(30).setHeaderValue(resourceMap.getString("masterTable.columnModel.title31")); // NOI18N
        masterTable.getColumnModel().getColumn(31).setHeaderValue(resourceMap.getString("masterTable.columnModel.title32")); // NOI18N
        masterTable.getColumnModel().getColumn(32).setHeaderValue(resourceMap.getString("masterTable.columnModel.title33")); // NOI18N
        masterTable.getColumnModel().getColumn(33).setHeaderValue(resourceMap.getString("masterTable.columnModel.title34")); // NOI18N
        masterTable.getColumnModel().getColumn(34).setHeaderValue(resourceMap.getString("masterTable.columnModel.title35")); // NOI18N
        masterTable.getColumnModel().getColumn(35).setHeaderValue(resourceMap.getString("masterTable.columnModel.title36")); // NOI18N
        masterTable.getColumnModel().getColumn(36).setHeaderValue(resourceMap.getString("masterTable.columnModel.title37")); // NOI18N
        masterTable.getColumnModel().getColumn(37).setHeaderValue(resourceMap.getString("masterTable.columnModel.title38")); // NOI18N
        masterTable.getColumnModel().getColumn(38).setHeaderValue(resourceMap.getString("masterTable.columnModel.title39")); // NOI18N
        masterTable.getColumnModel().getColumn(39).setHeaderValue(resourceMap.getString("masterTable.columnModel.title40")); // NOI18N
        masterTable.getColumnModel().getColumn(40).setHeaderValue(resourceMap.getString("masterTable.columnModel.title41")); // NOI18N
        masterTable.getColumnModel().getColumn(41).setHeaderValue(resourceMap.getString("masterTable.columnModel.title42")); // NOI18N
        masterTable.getColumnModel().getColumn(42).setHeaderValue(resourceMap.getString("masterTable.columnModel.title42")); // NOI18N

        haspnumLabel.setText(resourceMap.getString("haspnumLabel.text")); // NOI18N
        haspnumLabel.setName("haspnumLabel"); // NOI18N

        inactiveLabel.setText(resourceMap.getString("inactiveLabel.text")); // NOI18N
        inactiveLabel.setName("inactiveLabel"); // NOI18N

        haspPreparerLabel.setText(resourceMap.getString("haspPreparerLabel.text")); // NOI18N
        haspPreparerLabel.setName("haspPreparerLabel"); // NOI18N

        invest1lastnameLabel.setText(resourceMap.getString("invest1lastnameLabel.text")); // NOI18N
        invest1lastnameLabel.setName("invest1lastnameLabel"); // NOI18N

        invest2lastnameLabel.setText(resourceMap.getString("invest2lastnameLabel.text")); // NOI18N
        invest2lastnameLabel.setName("invest2lastnameLabel"); // NOI18N

        invest3lastnameLabel.setText(resourceMap.getString("invest3lastnameLabel.text")); // NOI18N
        invest3lastnameLabel.setName("invest3lastnameLabel"); // NOI18N

        invest4lastnameLabel.setText(resourceMap.getString("invest4lastnameLabel.text")); // NOI18N
        invest4lastnameLabel.setName("invest4lastnameLabel"); // NOI18N

        invest5lastnameLabel.setText(resourceMap.getString("invest5lastnameLabel.text")); // NOI18N
        invest5lastnameLabel.setName("invest5lastnameLabel"); // NOI18N

        invest6lastnameLabel.setText(resourceMap.getString("invest6lastnameLabel.text")); // NOI18N
        invest6lastnameLabel.setName("invest6lastnameLabel"); // NOI18N

        invest7lastnameLabel.setText(resourceMap.getString("invest7lastnameLabel.text")); // NOI18N
        invest7lastnameLabel.setName("invest7lastnameLabel"); // NOI18N

        invest8lastnameLabel.setText(resourceMap.getString("invest8lastnameLabel.text")); // NOI18N
        invest8lastnameLabel.setName("invest8lastnameLabel"); // NOI18N

        branchchiefLabel.setText(resourceMap.getString("branchchiefLabel.text")); // NOI18N
        branchchiefLabel.setName("branchchiefLabel"); // NOI18N

        officeLabel.setText(resourceMap.getString("officeLabel.text")); // NOI18N
        officeLabel.setName("officeLabel"); // NOI18N

        labLabel.setText(resourceMap.getString("labLabel.text")); // NOI18N
        labLabel.setName("labLabel"); // NOI18N

        divisionIdLabel.setText(resourceMap.getString("divisionIdLabel.text")); // NOI18N
        divisionIdLabel.setName("divisionIdLabel"); // NOI18N

        branchIdLabel.setText(resourceMap.getString("branchIdLabel.text")); // NOI18N
        branchIdLabel.setName("branchIdLabel"); // NOI18N

        titleLabel.setText(resourceMap.getString("titleLabel.text")); // NOI18N
        titleLabel.setName("titleLabel"); // NOI18N

        notesLabel.setText(resourceMap.getString("notesLabel.text")); // NOI18N
        notesLabel.setName("notesLabel"); // NOI18N

        location1Label.setText(resourceMap.getString("location1Label.text")); // NOI18N
        location1Label.setName("location1Label"); // NOI18N

        location2Label.setText(resourceMap.getString("location2Label.text")); // NOI18N
        location2Label.setName("location2Label"); // NOI18N

        location3Label.setText(resourceMap.getString("location3Label.text")); // NOI18N
        location3Label.setName("location3Label"); // NOI18N

        location4Label.setText(resourceMap.getString("location4Label.text")); // NOI18N
        location4Label.setName("location4Label"); // NOI18N

        location5Label.setText(resourceMap.getString("location5Label.text")); // NOI18N
        location5Label.setName("location5Label"); // NOI18N

        fieldactivitiesLabel.setText(resourceMap.getString("fieldactivitiesLabel.text")); // NOI18N
        fieldactivitiesLabel.setName("fieldactivitiesLabel"); // NOI18N

        labactivitiesLabel.setText(resourceMap.getString("labactivitiesLabel.text")); // NOI18N
        labactivitiesLabel.setName("labactivitiesLabel"); // NOI18N

        treatabilitystudyLabel.setText(resourceMap.getString("treatabilitystudyLabel.text")); // NOI18N
        treatabilitystudyLabel.setName("treatabilitystudyLabel"); // NOI18N

        hazardouswastegeneratorLabel.setText(resourceMap.getString("hazardouswastegeneratorLabel.text")); // NOI18N
        hazardouswastegeneratorLabel.setName("hazardouswastegeneratorLabel"); // NOI18N

        buildingLabel.setText(resourceMap.getString("buildingLabel.text")); // NOI18N
        buildingLabel.setName("buildingLabel"); // NOI18N

        currenthaspversionLabel.setText(resourceMap.getString("currenthaspversionLabel.text")); // NOI18N
        currenthaspversionLabel.setName("currenthaspversionLabel"); // NOI18N

        approvaldateLabel.setText(resourceMap.getString("approvaldateLabel.text")); // NOI18N
        approvaldateLabel.setName("approvaldateLabel"); // NOI18N

        expirationdateLabel.setText(resourceMap.getString("expirationdateLabel.text")); // NOI18N
        expirationdateLabel.setName("expirationdateLabel"); // NOI18N

        nextreviewLabel.setText(resourceMap.getString("nextreviewLabel.text")); // NOI18N
        nextreviewLabel.setName("nextreviewLabel"); // NOI18N

        annualreview1Label.setText(resourceMap.getString("annualreview1Label.text")); // NOI18N
        annualreview1Label.setName("annualreview1Label"); // NOI18N

        annualreview2Label.setText(resourceMap.getString("annualreview2Label.text")); // NOI18N
        annualreview2Label.setName("annualreview2Label"); // NOI18N

        haspnumField.setName("haspnumField"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.haspnum}"), haspnumField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), haspnumField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        inactiveField.setName("inactiveField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.inactive}"), inactiveField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), inactiveField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        haspPreparerField.setName("haspPreparerField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.haspPreparer}"), haspPreparerField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), haspPreparerField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest1lastnameField.setName("invest1lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest1lastname}"), invest1lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest1lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest2lastnameField.setName("invest2lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest2lastname}"), invest2lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest2lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest3lastnameField.setName("invest3lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest3lastname}"), invest3lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest3lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest4lastnameField.setName("invest4lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest4lastname}"), invest4lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest4lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest5lastnameField.setName("invest5lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest5lastname}"), invest5lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest5lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest6lastnameField.setName("invest6lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest6lastname}"), invest6lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest6lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest7lastnameField.setName("invest7lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest7lastname}"), invest7lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest7lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        invest8lastnameField.setName("invest8lastnameField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.invest8lastname}"), invest8lastnameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), invest8lastnameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        branchchiefField.setName("branchchiefField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.branchchief}"), branchchiefField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), branchchiefField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        officeField.setName("officeField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.office}"), officeField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), officeField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        labField.setName("labField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.lab}"), labField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), labField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        divisionIdField.setName("divisionIdField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.divisionId}"), divisionIdField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), divisionIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        branchIdField.setName("branchIdField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.branchId}"), branchIdField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), branchIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        titleField.setName("titleField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.title}"), titleField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), titleField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        notesField.setName("notesField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.notes}"), notesField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), notesField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        location1Field.setName("location1Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.location1}"), location1Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), location1Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        location2Field.setName("location2Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.location2}"), location2Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), location2Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        location3Field.setName("location3Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.location3}"), location3Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), location3Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        location4Field.setName("location4Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.location4}"), location4Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), location4Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        location5Field.setName("location5Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.location5}"), location5Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), location5Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        fieldactivitiesField.setName("fieldactivitiesField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fieldactivities}"), fieldactivitiesField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), fieldactivitiesField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        labactivitiesField.setName("labactivitiesField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.labactivities}"), labactivitiesField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), labactivitiesField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        treatabilitystudyField.setName("treatabilitystudyField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.treatabilitystudy}"), treatabilitystudyField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), treatabilitystudyField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        hazardouswastegeneratorField.setName("hazardouswastegeneratorField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.hazardouswastegenerator}"), hazardouswastegeneratorField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), hazardouswastegeneratorField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        buildingField.setName("buildingField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.building}"), buildingField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), buildingField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        currenthaspversionField.setName("currenthaspversionField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.currenthaspversion}"), currenthaspversionField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), currenthaspversionField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        approvaldateField.setName("approvaldateField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.approvaldate}"), approvaldateField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), approvaldateField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        expirationdateField.setName("expirationdateField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.expirationdate}"), expirationdateField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), expirationdateField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        nextreviewField.setName("nextreviewField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nextreview}"), nextreviewField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), nextreviewField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        annualreview1Field.setName("annualreview1Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.annualreview1}"), annualreview1Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), annualreview1Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        annualreview2Field.setName("annualreview2Field"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.annualreview2}"), annualreview2Field, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), annualreview2Field, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(shem8application.SHEM8Application.class).getContext().getActionMap(SHEM8View.class, this);
        saveButton.setAction(actionMap.get("save")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        refreshButton.setAction(actionMap.get("refresh")); // NOI18N
        refreshButton.setName("refreshButton"); // NOI18N

        newButton.setAction(actionMap.get("newRecord")); // NOI18N
        newButton.setName("newButton"); // NOI18N

        deleteButton.setAction(actionMap.get("deleteRecord")); // NOI18N
        deleteButton.setName("deleteButton"); // NOI18N

        PI1LastnameCombo.setName("PI1LastnameCombo"); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, haspPiList, PI1LastnameCombo);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, haspPiList, org.jdesktop.beansbinding.ObjectProperty.create(), PI1LastnameCombo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        PI1LastnameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PI1LastnameComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(masterScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(haspnumLabel)
                            .addComponent(titleLabel)
                            .addComponent(haspPreparerLabel)
                            .addComponent(invest1lastnameLabel)
                            .addComponent(invest2lastnameLabel)
                            .addComponent(invest3lastnameLabel)
                            .addComponent(invest4lastnameLabel)
                            .addComponent(branchchiefLabel)
                            .addComponent(labLabel)
                            .addComponent(divisionIdLabel)
                            .addComponent(branchIdLabel)
                            .addComponent(officeLabel))
                        .addGap(63, 63, 63)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleField, javax.swing.GroupLayout.DEFAULT_SIZE, 1009, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(haspnumField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inactiveLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inactiveField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(labactivitiesLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labactivitiesField))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(fieldactivitiesLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fieldactivitiesField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(treatabilitystudyLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(treatabilitystudyField, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(hazardouswastegeneratorLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(hazardouswastegeneratorField)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 439, Short.MAX_VALUE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(branchIdField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(divisionIdField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(officeField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(branchchiefField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(invest4lastnameField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(invest3lastnameField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(invest2lastnameField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(invest1lastnameField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(haspPreparerField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(invest8lastnameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(invest8lastnameField))
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(invest7lastnameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(invest7lastnameField))
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(invest5lastnameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(invest5lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(invest6lastnameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(invest6lastnameField)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 302, Short.MAX_VALUE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(annualreview1Label)
                                            .addComponent(annualreview2Label)
                                            .addComponent(currenthaspversionLabel))
                                        .addGap(28, 28, 28)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(annualreview2Field)
                                            .addComponent(annualreview1Field)
                                            .addComponent(currenthaspversionField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(approvaldateLabel)
                                            .addComponent(expirationdateLabel)
                                            .addComponent(nextreviewLabel)
                                            .addComponent(notesLabel))
                                        .addGap(32, 32, 32)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(notesField, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(nextreviewField)
                                                    .addComponent(expirationdateField)
                                                    .addComponent(approvaldateField, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 443, Short.MAX_VALUE))))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buildingLabel)
                            .addComponent(location1Label)
                            .addComponent(location2Label)
                            .addComponent(location3Label)
                            .addComponent(location4Label)
                            .addComponent(location5Label))
                        .addGap(86, 86, 86)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(location2Field, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addComponent(location3Field, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addComponent(location4Field, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addComponent(location5Field, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addComponent(location1Field, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                                    .addComponent(buildingField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(359, 359, 359))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(PI1LastnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addGap(156, 156, 156)))
                .addContainerGap())
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, refreshButton, saveButton});

        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(haspnumLabel)
                    .addComponent(haspnumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inactiveLabel)
                    .addComponent(inactiveField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldactivitiesLabel)
                    .addComponent(fieldactivitiesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(treatabilitystudyLabel)
                    .addComponent(treatabilitystudyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labactivitiesLabel)
                    .addComponent(labactivitiesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hazardouswastegeneratorLabel)
                    .addComponent(hazardouswastegeneratorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(haspPreparerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(haspPreparerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invest1lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest5lastnameLabel)
                    .addComponent(invest5lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest1lastnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invest2lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest6lastnameLabel)
                    .addComponent(invest6lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest2lastnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invest3lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest7lastnameLabel)
                    .addComponent(invest7lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest3lastnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invest4lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest8lastnameLabel)
                    .addComponent(invest8lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invest4lastnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branchchiefLabel)
                    .addComponent(branchchiefField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(approvaldateLabel)
                    .addComponent(approvaldateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(officeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(officeLabel)
                    .addComponent(expirationdateLabel)
                    .addComponent(expirationdateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labLabel)
                    .addComponent(labField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nextreviewLabel)
                    .addComponent(nextreviewField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divisionIdLabel)
                    .addComponent(divisionIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notesLabel)
                    .addComponent(notesField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branchIdLabel)
                    .addComponent(branchIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buildingLabel)
                    .addComponent(buildingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currenthaspversionLabel)
                    .addComponent(currenthaspversionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(location1Label)
                            .addComponent(location1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(location2Label)
                            .addComponent(location2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(location3Label)
                            .addComponent(location3Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(location4Label)
                            .addComponent(location4Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(location5Label)
                            .addComponent(location5Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saveButton)
                                .addComponent(refreshButton)
                                .addComponent(deleteButton)
                                .addComponent(newButton))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PI1LastnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(582, 582, 582))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(annualreview1Label)
                            .addComponent(annualreview1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(annualreview2Label)
                            .addComponent(annualreview2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        invest1lastnameField.getAccessibleContext().setAccessibleParent(invest1lastnameField);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newRecordMenuItem.setAction(actionMap.get("newRecord")); // NOI18N
        newRecordMenuItem.setName("newRecordMenuItem"); // NOI18N
        fileMenu.add(newRecordMenuItem);

        deleteRecordMenuItem.setAction(actionMap.get("deleteRecord")); // NOI18N
        deleteRecordMenuItem.setName("deleteRecordMenuItem"); // NOI18N
        fileMenu.add(deleteRecordMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        saveMenuItem.setAction(actionMap.get("save")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        refreshMenuItem.setAction(actionMap.get("refresh")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        fileMenu.add(refreshMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1171, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 996, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

private void PI1LastnameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PI1LastnameComboActionPerformed
    //This method is fired each time an action is performed on the drop down that 
    //contains this list of possible names for the PI Name 1 field.  
    //Note that this is actually changing the value of a field that is 
    //bound to the column in the database.  There will need to be a field
    //bound to the column in order for this method to continue to work.
    if(PI1LastnameCombo.getSelectedIndex()>-1 && evt.getModifiers()>0){    
          invest1lastnameField.setText(PI1LastnameCombo.getSelectedItem().toString());
    }
  
   //CHANGETHIS.setText(PI1LastnameCombo.getSelectedItem().toString());
    ;
}//GEN-LAST:event_PI1LastnameComboActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox PI1LastnameCombo;
    private javax.swing.JTextField annualreview1Field;
    private javax.swing.JLabel annualreview1Label;
    private javax.swing.JTextField annualreview2Field;
    private javax.swing.JLabel annualreview2Label;
    private javax.swing.JTextField approvaldateField;
    private javax.swing.JLabel approvaldateLabel;
    private javax.swing.JTextField branchIdField;
    private javax.swing.JLabel branchIdLabel;
    private javax.swing.JTextField branchchiefField;
    private javax.swing.JLabel branchchiefLabel;
    private javax.swing.JTextField buildingField;
    private javax.swing.JLabel buildingLabel;
    private javax.swing.JTextField currenthaspversionField;
    private javax.swing.JLabel currenthaspversionLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField divisionIdField;
    private javax.swing.JLabel divisionIdLabel;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JTextField expirationdateField;
    private javax.swing.JLabel expirationdateLabel;
    private javax.swing.JTextField fieldactivitiesField;
    private javax.swing.JLabel fieldactivitiesLabel;
    private java.util.List<shem8application.HaspPi> haspPiList;
    private javax.persistence.Query haspPiQuery;
    private javax.swing.JTextField haspPreparerField;
    private javax.swing.JLabel haspPreparerLabel;
    private javax.swing.JTextField haspnumField;
    private javax.swing.JLabel haspnumLabel;
    private javax.swing.JTextField hazardouswastegeneratorField;
    private javax.swing.JLabel hazardouswastegeneratorLabel;
    private javax.swing.JTextField inactiveField;
    private javax.swing.JLabel inactiveLabel;
    private javax.swing.JTextField invest1lastnameField;
    private javax.swing.JLabel invest1lastnameLabel;
    private javax.swing.JTextField invest2lastnameField;
    private javax.swing.JLabel invest2lastnameLabel;
    private javax.swing.JTextField invest3lastnameField;
    private javax.swing.JLabel invest3lastnameLabel;
    private javax.swing.JTextField invest4lastnameField;
    private javax.swing.JLabel invest4lastnameLabel;
    private javax.swing.JTextField invest5lastnameField;
    private javax.swing.JLabel invest5lastnameLabel;
    private javax.swing.JTextField invest6lastnameField;
    private javax.swing.JLabel invest6lastnameLabel;
    private javax.swing.JTextField invest7lastnameField;
    private javax.swing.JLabel invest7lastnameLabel;
    private javax.swing.JTextField invest8lastnameField;
    private javax.swing.JLabel invest8lastnameLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField labField;
    private javax.swing.JLabel labLabel;
    private javax.swing.JTextField labactivitiesField;
    private javax.swing.JLabel labactivitiesLabel;
    private java.util.List<shem8application.Shasptest> list;
    private javax.swing.JTextField location1Field;
    private javax.swing.JLabel location1Label;
    private javax.swing.JTextField location2Field;
    private javax.swing.JLabel location2Label;
    private javax.swing.JTextField location3Field;
    private javax.swing.JLabel location3Label;
    private javax.swing.JTextField location4Field;
    private javax.swing.JLabel location4Label;
    private javax.swing.JTextField location5Field;
    private javax.swing.JLabel location5Label;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton newButton;
    private javax.swing.JTextField nextreviewField;
    private javax.swing.JLabel nextreviewLabel;
    private javax.swing.JTextField notesField;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JTextField officeField;
    private javax.swing.JLabel officeLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.persistence.Query query;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private java.util.List<shem8application.Shasptest> shasptestList;
    private java.util.List<shem8application.Shasptest> shasptestList1;
    private java.util.List<shem8application.Shasptest> shasptestList2;
    private javax.persistence.Query shasptestQuery;
    private javax.persistence.Query shasptestQuery1;
    private javax.persistence.Query shasptestQuery2;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextField titleField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField treatabilitystudyField;
    private javax.swing.JLabel treatabilitystudyLabel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

    private boolean saveNeeded;
}
