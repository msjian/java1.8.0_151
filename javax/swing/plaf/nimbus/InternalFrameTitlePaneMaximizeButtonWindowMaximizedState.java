package javax.swing.plaf.nimbus;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;

class InternalFrameTitlePaneMaximizeButtonWindowMaximizedState
  extends State
{
  InternalFrameTitlePaneMaximizeButtonWindowMaximizedState()
  {
    super("WindowMaximized");
  }
  
  protected boolean isInState(JComponent paramJComponent)
  {
    for (Object localObject = paramJComponent; (((Component)localObject).getParent() != null) && (!(localObject instanceof JInternalFrame)); localObject = ((Component)localObject).getParent()) {}
    if ((localObject instanceof JInternalFrame)) {
      return ((JInternalFrame)localObject).isMaximum();
    }
    return false;
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\javax\swing\plaf\nimbus\InternalFrameTitlePaneMaximizeButtonWindowMaximizedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */