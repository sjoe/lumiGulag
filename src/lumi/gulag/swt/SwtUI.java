package lumi.gulag.swt;

import java.awt.Frame;
import java.awt.Panel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import processing.core.PApplet;

public class SwtUI {
	public static void createAppletPanel(Composite parent) {
		final Composite composite = new Composite(parent, SWT.EMBEDDED);
	        final Frame frame = SWT_AWT.new_Frame(composite);

        	PApplet sketch = new SimpleProcessingExample();       
	        Panel panel = new Panel();

        	panel.add(sketch);
	        frame.add(panel);
       
        	sketch.init();
	}

    public static void main(String[] args) {
        Display display = new Display ();
        Shell shell = new Shell(display);

        // Some properties - Size and placement. To keep nicely sided next to the Processing window
        shell.setSize(300,300);
        shell.setBounds(315, 0, 300, 300);
        shell.setText("Button Example");
        shell.setLayout(new RowLayout());

        // The two buttons
        Button button1 = new Button(shell, SWT.PUSH);
        button1.setText("Left");
        button1.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	//myParent.position = 30;
            }
        });
        Button button2 = new Button(shell, SWT.PUSH);
        button2.setText("Right");
        button2.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                //myParent.position = 270;
            }
        });
        
        createAppletPanel(shell);
        shell.pack();
        shell.open();
        
        // Default Swt-stuff
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
}