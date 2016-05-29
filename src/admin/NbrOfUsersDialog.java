package admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

/**
 * SeeYa admin
 * @author Gustav Frigren
 *
 */

public class NbrOfUsersDialog extends Dialog {
	
	public NbrOfUsersDialog(Shell parent, int style) {
		super(parent);
	}

	public NbrOfUsersDialog(Shell parent) {
		this(parent, 0);
	}
	
	public void open() {
		Shell parent = getParent();
		Shell nbrShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		nbrShell.setText("SEEYA USERS!");
		
		Label lblNbr = new Label(nbrShell, SWT.NONE);
		lblNbr.setBounds(222, 127, 70, 17);
		lblNbr.setText("New Label");
		
		Label lblNewLabel = new Label(nbrShell, SWT.NONE);
		lblNewLabel.setBounds(157, 22, 212, 17);
		lblNewLabel.setText("Number of SeeYa users");
		
	}
}
