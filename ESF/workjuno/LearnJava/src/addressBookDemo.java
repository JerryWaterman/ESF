import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class addressBookDemo extends ApplicationWindow {
  private static ArrayList<Contact> contactList;

  private static TableViewer tableViewer;

  private Table table;

  public addressBookDemo() {
    super(null);
    this.addMenuBar();

    contactList = new ArrayList<Contact>();
  }

  protected Control createContents(Composite parent) {
    Shell shell = this.getShell();
    shell.setText("Personal Address Book");
    shell.setSize(500, 300);

    SashForm form = new SashForm(parent, SWT.HORIZONTAL | SWT.NULL);

    table = new Table(form, SWT.FULL_SELECTION | SWT.BORDER);

    TableColumn column = new TableColumn(table, SWT.LEFT);
    column.setText("Name");
    column.setWidth(150);
    table.setHeaderVisible(true);

    column = new TableColumn(table, SWT.LEFT);
    column.setText("E-Mail Address");
    column.setWidth(125);
    table.setHeaderVisible(true);

    column = new TableColumn(table, SWT.LEFT);
    column.setText("Business Phone");
    column.setWidth(100);
    table.setHeaderVisible(true);

    column = new TableColumn(table, SWT.LEFT);
    column.setText("Home Phone");
    column.setWidth(100);
    table.setHeaderVisible(true);

    tableViewer = new TableViewer(table);

    return form;
  }

  public static void main(String[] args) {
    addressBookDemo book = new addressBookDemo();

    book.setBlockOnOpen(true);
    book.open();
    
    Display.getCurrent().dispose();
  }

  protected MenuManager createMenuManager() {
    MenuManager bar = new MenuManager("");

    MenuManager fileMenu = new MenuManager("&File");
    MenuManager helpMenu = new MenuManager("&Help");

    bar.add(fileMenu);
    bar.add(helpMenu);

    fileMenu.add(new NewContactAction(this));
    fileMenu.add(new ExitAction(this));

    helpMenu.add(new AboutAction(this));

    return bar;
  }

  public static void addContact(Contact c) {
    getContactList().add(c);

    Table table = tableViewer.getTable();

    TableItem item = new TableItem(table, SWT.NULL);
    item.setText(0, c.getGivenName() + " " + c.getFamilyName());
    item.setText(1, c.getEmailAddress());
    item.setText(2, c.getBusinessPhone());
    item.setText(3, c.getHomePhone());
  }

  public static ArrayList<Contact> getContactList() {
    return contactList;
  }

  public static TableViewer getTbv() {
    return tableViewer;
  }

  public static void setContactList(ArrayList<Contact> list) {
    contactList = list;
  }

  public static void setTbv(TableViewer viewer) {
    tableViewer = viewer;
  }
}

class Contact {
  private String givenName;

  private String familyName;

  private String addressLine1;

  private String addressLine2;

  private String city;

  private String state;

  private String zipCode;

  private String businessPhone;

  private String homePhone;

  private String emailAddress;

  public Contact() {
    this.givenName = "";
    this.familyName = "";
    this.addressLine1 = "";
    this.addressLine2 = "";
    this.city = "";
    this.state = "";
    this.zipCode = "";
    this.businessPhone = "";
    this.homePhone = "";
    this.emailAddress = "";
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setFamilyName(String string) {
    familyName = string;
  }

  public void setGivenName(String string) {
    givenName = string;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setAddressLine1(String string) {
    addressLine1 = string;
  }

  public void setAddressLine2(String string) {
    addressLine2 = string;
  }

  public void setCity(String string) {
    city = string;
  }

  public void setState(String string) {
    state = string;
  }

  public void setZipCode(String string) {
    zipCode = string;
  }

  public String getBusinessPhone() {
    return businessPhone;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public void setBusinessPhone(String string) {
    businessPhone = string;
  }

  public void setHomePhone(String string) {
    homePhone = string;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String string) {
    emailAddress = string;
  }
}

class NewContactAction extends Action {
  ApplicationWindow window;

  public NewContactAction(ApplicationWindow w) {
    window = w;
    this.setText("New Contact");
    this.setToolTipText("Create new contact");
  }

  public void run() {
    ContactWizard wizard = new ContactWizard();

    WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
    dialog.open();
  }
}

class ExitAction extends Action {
  ApplicationWindow window;

  public ExitAction(ApplicationWindow w) {
    window = w;
    this.setText("E&xit");

    this.setToolTipText("Exit the application");
  }

  public void run() {
    window.close();
  }
}

class AboutAction extends Action {
  ApplicationWindow window;

  public AboutAction(ApplicationWindow w) {
    window = w;
    this.setText("&About Address Book");

    this.setToolTipText("Exit the application");
  }

  public void run() {
    MessageDialog.openInformation(Display.getCurrent().getActiveShell(),
        "About Address Book", "IBM developerWorks");
  }
}

class AddressContactPage extends WizardPage {
  private static String[] STATES = { "Alabama", "Alaska", "Arizona",
      "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
      "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho",
      "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
      "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota",
      "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
      "New England", "New Hampshire", "New Jersey", "New Mexico",
      "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
      "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
      "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
      "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };

  private Text addressLine1Text;

  private Text addressLine2Text;

  private Text cityText;

  private Text zipCodeText;

  private Combo stateCombo;

  private ISelection selection;

  private boolean isDisplaySet = false;

  public AddressContactPage(ISelection selection) {
    super("wizardPage");
    setTitle("New Contact");
    setDescription("This wizard creates a new contact.");
    this.selection = selection;
  }

  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 2;
    layout.verticalSpacing = 9;

    Label label = new Label(container, SWT.NULL);
    label.setText("&Address Line 1:");

    addressLine1Text = new Text(container, SWT.BORDER | SWT.MULTI);

    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    addressLine1Text.setLayoutData(gd);
    addressLine1Text.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        dialogChanged();
      }
    });

    label = new Label(container, SWT.NULL);
    label.setText("&Address Line 2:");

    addressLine2Text = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    addressLine2Text.setLayoutData(gd);
    addressLine2Text.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        dialogChanged();
      }
    });

    label = new Label(container, SWT.NULL);
    label.setText("&City:");

    cityText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    cityText.setLayoutData(gd);

    label = new Label(container, SWT.NULL);
    label.setText("&State:");

    stateCombo = new Combo(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    stateCombo.setLayoutData(gd);
    stateCombo.setItems(STATES);

    label = new Label(container, SWT.NULL);
    label.setText("&Zip Code:");

    zipCodeText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    zipCodeText.setLayoutData(gd);

    // dialogChanged();
    setControl(container);
  }

  private void dialogChanged() {
    if (this.getGivenName().length() == 0) {
      updateStatus("Given name must be specified.");

      return;
    }

    if (this.getFamilyName().length() == 0) {
      updateStatus("Family name must be specified.");

      return;
    }

    updateStatus(null);
  }

  private void updateStatus(String message) {
    setErrorMessage(message);
    setPageComplete(message == null);
  }

  public String getFamilyName() {
    return addressLine2Text.getText();
  }

  public String getGivenName() {
    return addressLine1Text.getText();
  }

  public String getNickName() {
    return cityText.getText();
  }

  public String getBusinessPhone() {
    return zipCodeText.getText();
  }

  public void setNickName(String name) {
    cityText.setText(name);
  }

  private void createLine(Composite parent, int ncol) {
    Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
        | SWT.BOLD);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = ncol;
    line.setLayoutData(gridData);
  }
}

class ContactWizard extends Wizard {
  private BasicContactPage page1;

  private AddressContactPage page2;

  private ISelection selection;

  public ContactWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  public void addPages() {
    page1 = new BasicContactPage(selection);
    page2 = new AddressContactPage(selection);
    addPage(page1);
    addPage(page2);
  }

  public boolean performFinish() {
    Contact contact = new Contact();
    contact.setFamilyName(page1.getFamilyName());
    contact.setGivenName(page1.getGivenName());
    contact.setBusinessPhone(page1.getBusinessPhone());
    contact.setHomePhone(page1.getHomePhone());
    contact.setEmailAddress(page1.getEmail());

    addressBookDemo.addContact(contact);

    return true;
  }
}

class BasicContactPage extends WizardPage {
  private Text givenNameText;

  private Text familyNameText;

  private Text nickNameText;

  private Text businessPhoneText;

  private Text homePhoneText;

  private Text emailText;

  private ISelection selection;

  public BasicContactPage(ISelection selection) {
    super("wizardPage");
    setTitle("New Contact");
    setDescription("This wizard creates a new contact.");
    this.selection = selection;
  }

  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 2;
    layout.verticalSpacing = 9;

    Label label = new Label(container, SWT.NULL);
    label.setText("&Given Name:");

    givenNameText = new Text(container, SWT.BORDER | SWT.SINGLE);

    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    givenNameText.setLayoutData(gd);
    givenNameText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        dialogChanged();
      }
    });

    label = new Label(container, SWT.NULL);
    label.setText("&Family Name:");

    familyNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    familyNameText.setLayoutData(gd);
    familyNameText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        dialogChanged();
      }
    });

    label = new Label(container, SWT.NULL);
    label.setText("&Nickname:");

    nickNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    nickNameText.setLayoutData(gd);

    createLine(container, layout.numColumns);

    label = new Label(container, SWT.NULL);
    label.setText("&Business Phone:");

    businessPhoneText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    businessPhoneText.setLayoutData(gd);

    label = new Label(container, SWT.NULL);
    label.setText("&Home Phone:");

    homePhoneText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    homePhoneText.setLayoutData(gd);

    createLine(container, layout.numColumns);

    label = new Label(container, SWT.NULL);
    label.setText("&E-Mail Address:");

    emailText = new Text(container, SWT.BORDER | SWT.SINGLE);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    emailText.setLayoutData(gd);
    emailText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        dialogChanged();
      }
    });

    // dialogChanged();
    setControl(container);
  }

  private void dialogChanged() {
    if (this.getGivenName().length() == 0) {
      updateStatus("Given name must be specified.");

      return;
    }

    if (this.getFamilyName().length() == 0) {
      updateStatus("Family name must be specified.");

      return;
    }

    if (this.getEmail().length() > 0) {
      if (this.getEmail().indexOf("@") < 0) {
        updateStatus("Please enter a complete email address in the form yourname@yourdomain.com");

        return;
      }
    }

    updateStatus(null);
  }

  private void updateStatus(String message) {
    setErrorMessage(message);
    setPageComplete(message == null);
  }

  public String getFamilyName() {
    return familyNameText.getText();
  }

  public String getGivenName() {
    return givenNameText.getText();
  }

  public String getNickName() {
    return nickNameText.getText();
  }

  public String getBusinessPhone() {
    return businessPhoneText.getText();
  }

  public String getHomePhone() {
    return homePhoneText.getText();
  }

  public String getEmail() {
    return emailText.getText();
  }

  public void setNickName(String name) {
    nickNameText.setText(name);
  }

  private void createLine(Composite parent, int ncol) {
    Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
        | SWT.BOLD);
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = ncol;
    line.setLayoutData(gridData);
  }
}

